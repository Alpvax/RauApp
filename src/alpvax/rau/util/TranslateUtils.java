package alpvax.rau.util;

import java.util.Locale;

import alpvax.rau.text.EnumLanguage;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;

public class TranslateUtils//TODO: Re-design translation
{
	public static CharSequence getText(CharSequence string)
	{
		return getText(string, AppUtils.SETTINGS.getLanguage());
	}
	public static CharSequence getText(CharSequence string, EnumLanguage lang)
	{
		Resources r = AppUtils.APP_CONTEXT.getResources();
		int resID = r.getIdentifier(string.toString(), lang.name().toLowerCase(Locale.UK) + "String", "alpvax.rau");
		try
		{
			//Log.d("StringResource", r.getResourceName(resID));
		}
		catch(NotFoundException e)
		{
			//Log.d("StringResource", "No resource found with id " + resID + ". (" + string + ")");
		}
		return resID != 0 ? lang.langEscape() + r.getText(resID) : String.format("%2$sMissing String Resource: \"%1$s\".", string, "");//AppUtils.CONSTANTS.MISSING_STRING));
	}
	public static CharSequence getText(int resID)
	{
		return getText(AppUtils.APP_CONTEXT.getResources().getResourceEntryName(resID));
	}
	/*public static CharSequence getText(int resID)
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
	}*/
}
