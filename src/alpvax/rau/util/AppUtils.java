package alpvax.rau.util;

import alpvax.rau.util.settings.SettingsHelper;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

public class AppUtils
{	
	private static AppUtils instance = null;
	
	public static AppUtils instance(Context context)
	{
		if(instance == null)
		{
			if(context == null)
			{
				throw new IllegalArgumentException("Cannot create an instance without a valid context.");
			}
			instance = new AppUtils(context);
		}
		return instance;
	}
	
	public Context appContext;
	public SettingsHelper settings;
	
	private AppUtils(Context c)
	{
		appContext = c.getApplicationContext();
		settings = SettingsHelper.instance(appContext);
	}

	public CharSequence getText(int resID)
	{
		return formatText(getTextFromResource(resID));
	}
	private String getTextFromResource(int resID)
	{
		if(resID == 0)
		{
			return "Invalid Resource ID: 0";// + resID;
		}
		Resources r = appContext.getResources();
		Log.d("Getting String:", r.getResourceName(resID));
		String[] s = r.getStringArray(resID);
		int i = 0;
		for(String s1 : s)
		{
			Log.d("Getting String [" + i++ + "]", s1);
		}
		if(s.length > 1)
		{
			return s[settings.getLanguage().ordinal()];
		}
		if(s.length > 0)
		{
			return s[0];
		}
		return "Missing String Resource: " + resID;
	}

	public CharSequence getText(CharSequence string)
	{
		int resID = appContext.getResources().getIdentifier(string.toString(), "array", "alpvax.rau");
		//int resID = appContext.getResources().getIdentifier("alpvax.rau:array/" + string, null, null)
		return resID > 0 ? getText(resID) : string;
	}
	
	public CharSequence formatText(CharSequence text)
	{
		//TODO: Add TypeFaces and implement formatting
		Log.i("Translated String", text.toString());
		return text;
	}
}
