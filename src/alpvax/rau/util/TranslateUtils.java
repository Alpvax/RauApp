package alpvax.rau.util;

import alpvax.rau.text.TextFormatter;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;

public class TranslateUtils//TODO: Re-design translation
{
	public static CharSequence getText(int resID)
	{
		return formatText(getString(resID));
	}
	public static String getString(int resID)
	{
		if(resID == 0)
		{
			return "Invalid Resource ID: 0";// + resID;
		}
		Resources r = AppUtils.APP_CONTEXT.getResources();
		try
		{
			String[] s = r.getStringArray(resID);
			if(s.length > 1)
			{
				return s[AppUtils.SETTINGS.getLanguage().ordinal()];
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

	public static CharSequence getText(CharSequence string)
	{
		int resID = AppUtils.APP_CONTEXT.getResources().getIdentifier(string.toString(), "array", "alpvax.rau");
		//int resID = AppUtils.APP_CONTEXT.getResources().getIdentifier("alpvax.rau:array/" + string, null, null)
		return resID > 0 ? getText(resID) : string;
	}
	
	public static CharSequence formatText(CharSequence text)
	{
		/*for(int i = 0; i < text.length(); i++)
		{
			
		}
		return text;*/
		return new TextFormatter(text);
	}
}
