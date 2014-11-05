package alpvax.rau.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import alpvax.rau.R;
import alpvax.rau.text.EnumLanguage;
import alpvax.rau.text.TextFormatter;
import alpvax.rau.util.fonts.FontPreference;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.provider.MediaStore;

public class SettingsHelper//TODO: Language changing
{
	public static class SettingsKeys
	{
		public static String KEY_FIRST_RUN;
		public static String KEY_DETECTED_NUMBER;
		public static String KEY_LANGUAGE;
		//public static String KEY_KEYBOARD_CONTROLS;
		public static String KEY_NOTIFY_SOUND;
		public static String KEY_OWN_NUMBER;
		public static String KEY_AUTO_ADD;
		public static String KEY_FONT_PREFIX;
		public static String KEY_SEND_TO_SELF;

		public static String GROUP_GENERAL;
		public static String GROUP_MESSAGING;
		public static String GROUP_FONTS;
		public static String GROUP_DEBUG;
		
		/**
		 * List of all keys corresponding to internal preferences,
		 * as opposed to the ones in settings
		 */
		private static List<String> invisible = new ArrayList<String>();
		
		private static void init(Context c)
		{
			invisible.add(KEY_FIRST_RUN = c.getString(R.prefKey.first_run));
			invisible.add(KEY_DETECTED_NUMBER = c.getString(R.prefKey.detected_number));
			
			KEY_OWN_NUMBER = c.getString(R.prefKey.own_number);
			KEY_AUTO_ADD = c.getString(R.prefKey.auto_add_contact);
			KEY_SEND_TO_SELF = c.getString(R.prefKey.send_to_self);
			KEY_FONT_PREFIX = c.getString(R.prefKey.font_prefix);
			KEY_LANGUAGE = c.getString(R.prefKey.language);
			KEY_NOTIFY_SOUND = c.getString(R.prefKey.notification);
			//KEY_KEYBOARD_CONTROLS = c.getString(R.prefKey.kbd_btns);
			
			GROUP_GENERAL = c.getString(R.prefKey.general_settings);
			GROUP_MESSAGING = c.getString(R.prefKey.messaging_settings);
			GROUP_FONTS = c.getString(R.prefKey.fonts_settings);
			GROUP_DEBUG = c.getString(R.prefKey.debug_settings);
		}
	}
	private SharedPreferences prefs;
	
	protected SettingsHelper(Context c)
	{
		prefs = PreferenceManager.getDefaultSharedPreferences(c);
		
		PreferenceManager.setDefaultValues(c, R.xml.preferences, false);
		SettingsKeys.init(c);
	}
	
	public EnumLanguage getLanguage()
	{
		return EnumLanguage.get(prefs.getString(SettingsKeys.KEY_LANGUAGE, null));
	}
	
	public String getFontPath(EnumLanguage lang)
	{
		return prefs.getString(SettingsKeys.KEY_FONT_PREFIX + lang.name(), "");
	}
	
	/*public boolean keyboardControls()
	{
		return prefs.getBoolean(SettingsKeys.KEY_KEYBOARD_CONTROLS, true);
	}*/
	
	public Uri notification()
	{
		return Uri.parse(prefs.getString(SettingsKeys.KEY_NOTIFY_SOUND, ""));
	}

	public String number()
	{
		return prefs.getString(SettingsKeys.KEY_OWN_NUMBER, "");
	}

	public boolean autoAdd()
	{
		return prefs.getBoolean(SettingsKeys.KEY_AUTO_ADD, true);
	}
	
	/**
	 * Used if more than summary change is necessary (i.e. creating Typefaces)
	 */
	public void update(Preference p)
	{
		String key = p.getKey();
		if(key.startsWith(SettingsKeys.KEY_FONT_PREFIX))
		{
			FontPreference pref = (FontPreference)p;
			AppUtils.FONTS.setFont(pref.getLang(), pref.getValue());//XXX:Crash here somewhere
		}
	}
	
	/**
	 * Should only be called by updateLabels
	 */
	private CharSequence getTitle(String key, Preference p)
	{
		if(key.startsWith(SettingsKeys.KEY_FONT_PREFIX))
		{
			return ((FontPreference)p).getLang().formattedString();
		}
		return TranslateUtils.getText(key.replaceAll("(?<=pref_)key", "title"));
	}
	/**
	 * Should only be called by updateLabels
	 */
	private CharSequence getSummary(String key, Preference p)
	{
    	if(key == SettingsKeys.KEY_LANGUAGE)
    	{
    		return AppUtils.SETTINGS.getLanguage().formattedString();
    	}
    	if(key == SettingsKeys.KEY_NOTIFY_SOUND)
    	{
			Uri u = AppUtils.SETTINGS.notification();
			CharSequence name = TranslateUtils.getText("silent");
			if(u != null && "content".equals(u.getScheme()))
			{
				if(!"settings".equals(u.getHost()))
				{
					Cursor c = AppUtils.APP_CONTEXT.getContentResolver().query(u, new String[]{MediaStore.Audio.AudioColumns.TITLE}, null, null, null);
					c.moveToFirst();
					name = c.getString(0);
					c.close();
				}
				else
				{
					name = TranslateUtils.getText("Default");
				}
			}
			return name;
    	}
		if(key == SettingsKeys.KEY_OWN_NUMBER)
    	{
			return TranslateUtils.getText(AppUtils.SETTINGS.number());
    	}
		if(key.startsWith(SettingsKeys.KEY_FONT_PREFIX))
		{
			return ((FontPreference)p).getFontName();
		}
		return p instanceof PreferenceGroup ? "" : TranslateUtils.getText(key.replaceAll("(?<=pref_)key", "summary"));
	}
	/**
	 * Updates the Titles and Summaries of all preferences (Including Preference Groups)
	 */
	public void updateLabels(PreferenceManager pm)
	{
		for(Field f : R.prefKey.class.getDeclaredFields())
        {
        	try
        	{
	        	String key = AppUtils.APP_CONTEXT.getString(f.getInt(null));
				if(!SettingsKeys.invisible.contains(key))
				{
		        	Preference p = pm.findPreference(key);
					if(p != null)
					{
			        	p.setTitle(new TextFormatter(getTitle(key, p)));
						p.setSummary(new TextFormatter(getSummary(key, p)));
					}
				}
			}
        	catch(Exception e)
			{
				e.printStackTrace();
			}
        }
		/*System.out.println("General settings preference exists using SharedPrefs: " + prefs.contains(AppUtils.APP_CONTEXT.getString(R.prefKey.general_settings)));
		System.out.println("General settings preference exists using PreferenceManager: " + (pm.findPreference(AppUtils.APP_CONTEXT.getString(R.prefKey.general_settings)) != null));
		for(String key : prefs.getAll().keySet())
		{
			if(!SettingsKeys.invisible.contains(key))
			{
				Preference p = pm.findPreference(key);
				Log.d("Loading settings labels", "Key: " + key + ". Preference: " + p);
				if(p != null)
				{
		        	p.setTitle(new TextFormatter(getTitle(key, p)));
					p.setSummary(new TextFormatter(getSummary(key, p)));
				}
			}
		}*/
	}
}