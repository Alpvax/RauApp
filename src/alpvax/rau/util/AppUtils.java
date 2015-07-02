package alpvax.rau.util;

import com.google.android.gms.common.GoogleApiAvailability;

import android.app.Activity;
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
	
	public static boolean checkGooglePlayServices(Activity activity)
	{
		GoogleApiAvailability g = GoogleApiAvailability.getInstance();
		return g.getErrorDialog(activity, g.isGooglePlayServicesAvailable(activity), 0) == null;
	}
}
