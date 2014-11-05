package alpvax.rau.util;

import alpvax.rau.text.EnumLanguage;
import android.content.Context;
import android.graphics.Typeface;

public class FontUtils
{
	private String[] def_fonts = new String[EnumLanguage.values.length];
	private String[] font_paths = new String[EnumLanguage.values.length];
	
	protected FontUtils(Context c)
	{
		def_fonts[EnumLanguage.RAU.ordinal()] = "fonts/rau/" + AppUtils.CONSTANTS.DEF_AUK_TF_PATH + ".ttf";
		for(EnumLanguage lang : EnumLanguage.values)
		{
			setFont(lang, AppUtils.SETTINGS.getFontPath(lang));
		}
	}
	
	public void setFont(EnumLanguage lang, String path)
	{
		int i = lang.ordinal();
		if(!path.equalsIgnoreCase(font_paths[i]))
		{
			if(path.length() == 0)
			{
				Typeface t = def_fonts[i] != null ? Typeface.createFromAsset(AppUtils.APP_CONTEXT.getAssets(), def_fonts[i]) : Typeface.DEFAULT;
				lang.setFont(t);
			}
			else
			{
				lang.setFont(Typeface.createFromFile(path));
			}
			font_paths[i] = path;
		}
	}
}
