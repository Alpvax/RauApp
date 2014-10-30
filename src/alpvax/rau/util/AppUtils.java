package alpvax.rau.util;

import android.content.Context;

public class AppUtils
{
	public static AppConstants CONSTANTS;
	//public static TranslateUtils STRINGS;
	public static SettingsHelper SETTINGS;
	public static FontUtils FONTS;
	
	protected static Context APP_CONTEXT;
	
	public static void init(Context c)
	{
		APP_CONTEXT = c.getApplicationContext();
		CONSTANTS = new AppConstants(c);
		//STRINGS = new TranslateUtils(c);
		SETTINGS = new SettingsHelper(c);
		FONTS = new FontUtils(c);
	}
}
