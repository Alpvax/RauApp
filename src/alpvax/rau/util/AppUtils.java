package alpvax.rau.util;

import alpvax.rau.text.TextFormatter;
import alpvax.rau.util.settings.SettingsHelper;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;

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
	
	private AppUtils(Context c)
	{
		appContext = c.getApplicationContext();
		AppConstants.init(c);
	}

	public CharSequence getText(int resID)
	{
		return formatText(getString(resID));
	}
	private String getString(int resID)
	{
		if(resID == 0)
		{
			return "Invalid Resource ID: 0";// + resID;
		}
		Resources r = appContext.getResources();
		try
		{
			String[] s = r.getStringArray(resID);
			if(s.length > 1)
			{
				return s[SettingsHelper.instance(appContext).getLanguage().ordinal()];
			}
			if(s.length > 0)
			{
				return s[0];
			}
		}
		catch(NotFoundException e)
		{
			return r.getString(resID);
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
		/*for(int i = 0; i < text.length(); i++)
		{
			
		}
		return text;*/
		return new TextFormatter(text);
	}
}
