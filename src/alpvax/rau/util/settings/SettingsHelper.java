package alpvax.rau.util.settings;

import java.lang.reflect.Field;

import alpvax.rau.R;
import alpvax.rau.text.EnumLanguage;
import alpvax.rau.util.AppConstants;
import alpvax.rau.util.AppUtils;
import alpvax.rau.util.fonts.FontPreference;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;

public class SettingsHelper
{
	private static SettingsHelper instance = null;
	
	public static SettingsHelper instance(Context context)
	{
		if(instance == null)
		{
			if(context == null)
			{
				throw new IllegalArgumentException("Cannot create an instance without a valid context.");
			}
			instance = new SettingsHelper(context);
		}
		return instance;
	}
	
	private SharedPreferences prefs;

	private Typeface[] def_fonts = new Typeface[EnumLanguage.values.length];
	private Typeface[] fonts = new Typeface[EnumLanguage.values.length];
	private String[] font_paths = new String[EnumLanguage.values.length];
	
	private SettingsHelper(Context c)
	{
		prefs = PreferenceManager.getDefaultSharedPreferences(c);

		def_fonts[EnumLanguage.LATIN.ordinal()] = Typeface.DEFAULT;
		def_fonts[EnumLanguage.RAU.ordinal()] = Typeface.createFromAsset(c.getAssets(), "fonts/auk/" + AppConstants.DEF_AUK_TF_PATH + ".ttf");
		for(EnumLanguage l : EnumLanguage.values)
		{
			int i = l.ordinal();
			String path = prefs.getString(KEY_FONT_PREFIX + l.name(), "");
			if(!path.equalsIgnoreCase(font_paths[i]))
			{
				if(path.length() == 0)
				{
					fonts[i] = def_fonts[i];
				}
				else
				{
					fonts[i] = Typeface.createFromFile(path);
				}
				font_paths[i] = path;
			}
		}
		
		PreferenceManager.setDefaultValues(c, R.xml.preferences, false);
		KEY_FIRST_RUN = c.getString(R.prefKey.first_run);
		KEY_DETECTED_NUMBER = c.getString(R.prefKey.detected_number);
		KEY_OWN_NUMBER = c.getString(R.prefKey.own_number);
		KEY_AUTO_ADD = c.getString(R.prefKey.auto_add_contact);
		KEY_SEND_TO_SELF = c.getString(R.prefKey.send_to_self);
		KEY_FONT_PREFIX = c.getString(R.prefKey.font_prefix);
		KEY_LANGUAGE = c.getString(R.prefKey.language);
		KEY_NOTIFY_SOUND = c.getString(R.prefKey.notification);
		KEY_KEYBOARD_CONTROLS = c.getString(R.prefKey.kbd_btns);
	}

	public static String KEY_FIRST_RUN;
	public static String KEY_DETECTED_NUMBER;
	public static String KEY_LANGUAGE;
	public static String KEY_KEYBOARD_CONTROLS;
	public static String KEY_NOTIFY_SOUND;
	public static String KEY_OWN_NUMBER;
	public static String KEY_AUTO_ADD;
	public static String KEY_FONT_PREFIX;
	public static String KEY_SEND_TO_SELF;
	
	public EnumLanguage getLanguage()
	{
		return EnumLanguage.get(prefs.getString(KEY_LANGUAGE, null));
	}
	
	public boolean keyboardControls()
	{
		return prefs.getBoolean(KEY_KEYBOARD_CONTROLS, true);
	}
	
	public Uri notification()
	{
		return Uri.parse(prefs.getString(KEY_NOTIFY_SOUND, ""));
	}

	public String number()
	{
		return prefs.getString(KEY_OWN_NUMBER, "");
	}

	public boolean autoAdd()
	{
		return prefs.getBoolean(KEY_AUTO_ADD, true);
	}
	
	public Typeface latinTypeface()
	{
		return getTypeface(EnumLanguage.LATIN);
	}
	
	public Typeface aukTypeface()
	{
		return getTypeface(EnumLanguage.RAU);
	}

	private Typeface getTypeface(EnumLanguage lang)
	{
		return fonts[lang.ordinal()];
	}
	
	/**
	 * Used if more than summary change is necessary (i.e. creating Typefaces)
	 */
	public void update(Preference p)
	{
		String key = p.getKey();
		if(key.startsWith(KEY_FONT_PREFIX))
		{
			FontPreference pref = (FontPreference)p;
			int i = pref.getLang().ordinal();
			String path = pref.getValue();
			Log.d("FONT_PATH", pref.getLang() + path);
			if(!path.equalsIgnoreCase(font_paths[i]))
			{
				if(path.length() == 0)
				{
					fonts[i] = def_fonts[i];
				}
				else
				{
					fonts[i] = Typeface.createFromFile(path);
				}
				font_paths[i] = path;
			}
		}
	}
	
	/**
	 * Should only be called by updateLabels, instance MUST be initialised
	 */
	private static CharSequence getSummary(String key, Preference p, AppUtils util)
	{
    	if(key == KEY_LANGUAGE)
    	{
    		return util.formatText(instance.getLanguage().toString());
    	}
    	if(key == KEY_NOTIFY_SOUND)
    	{
			Uri u = instance.notification();
			CharSequence name = util.getText(R.array.silent);
			if(u != null && "content".equals(u.getScheme()))
			{
				if(!"settings".equals(u.getHost()))
				{
					Cursor c = util.appContext.getContentResolver().query(u, new String[]{MediaStore.Audio.AudioColumns.TITLE}, null, null, null);
					c.moveToFirst();
					name = c.getString(0);
					c.close();
				}
				else
				{
					name = util.getText(R.array.Default);
				}
			}
			return name;
    	}
		if(key == KEY_OWN_NUMBER)
    	{
			return util.formatText(instance.number());
    	}
		if(key.startsWith(KEY_FONT_PREFIX))
		{
			return ((FontPreference)p).getFontName();
		}
		return util.getText(p.getSummary());
	}
	/**
	 * Updates the Titles and Summaries of all preferences (Including Preference Groups)
	 */
	public static void updateLabels(PreferenceManager pm, Context c)
	{
		instance(c);//Make sure instance is initialised
        for(Field f : R.prefKey.class.getDeclaredFields())
        {
        	try
        	{
	        	String key = c.getString(f.getInt(null));
	        	Preference p = pm.findPreference(key);
	    		AppUtils util = AppUtils.instance(c);
	        	p.setTitle(util.getText(p.getTitle()));
				p.setSummary(getSummary(key, p, util));;
			}
        	catch(Exception e)
			{
				e.printStackTrace();
			}
        }
	}
}