package alpvax.rau.util.settings;

import java.lang.reflect.Field;

import alpvax.rau.R;
import alpvax.rau.text.EnumLanguage;
import alpvax.rau.util.AppUtils;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

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
	
	private SettingsHelper(Context c)
	{
		prefs = PreferenceManager.getDefaultSharedPreferences(c);
		
		PreferenceManager.setDefaultValues(c, R.xml.preferences, false);
		KEY_FIRST_RUN = c.getString(R.prefKey.first_run);
		KEY_DETECTED_NUMBER = c.getString(R.prefKey.detected_number);
		KEY_OWN_NUMBER = c.getString(R.prefKey.own_number);
		KEY_AUTO_ADD = c.getString(R.prefKey.auto_add_contact);
		KEY_SEND_TO_SELF = c.getString(R.prefKey.send_to_self);
		KEY_LATIN_FONT = c.getString(R.prefKey.font_lat);
		KEY_AUKAA_FONT = c.getString(R.prefKey.font_auk);
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
	public static String KEY_LATIN_FONT;
	public static String KEY_AUKAA_FONT;
	public static String KEY_SEND_TO_SELF;
	
	public EnumLanguage getLanguage()
	{
		return EnumLanguage.get(prefs.getString(KEY_LANGUAGE, null));
	}

	public void update(Preference p)
	/*{
		
	}
	public void update(PreferenceManager pm, Preference p)*/
	{
		String key = p.getKey();
		AppUtils util = AppUtils.instance(null);//Should have been Initialised previously, so null context isn't a problem
    	p.setTitle(util.getText(p.getTitle()));
    	if(key == KEY_OWN_NUMBER)
    	{
    	}
    	else if(key == KEY_LANGUAGE)
    	{
    		EnumLanguage lang = getLanguage();
    		p.setSummary(lang.toString());
    	}
    	else if(key == KEY_NOTIFY_SOUND)
    	{
    	}
    	else if(key == KEY_OWN_NUMBER)
    	{
    	}
    	else if(key == KEY_LATIN_FONT)
    	{
    	}
    	else if(key == KEY_AUKAA_FONT)
    	{
    	}
    	else//Has no dynamic summary
    	{
    		p.setSummary(util.getText(p.getSummary()));
    	}
	}
	
	public static void updateAll(PreferenceManager pm, Context c)
	{
		SettingsHelper s = instance(c);
        for(Field f : R.prefKey.class.getDeclaredFields())
        {
        	try
        	{
				s.update(pm.findPreference(c.getString(f.getInt(null))));
			}
        	catch(Exception e)
			{
				e.printStackTrace();
			}
        }
	}
}