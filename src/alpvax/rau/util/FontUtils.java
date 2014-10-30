package alpvax.rau.util;

import alpvax.rau.text.EnumLanguage;
import android.content.Context;
import android.graphics.Typeface;

public class FontUtils
{
	private Typeface[] def_fonts = new Typeface[EnumLanguage.values.length];
	private String[] font_paths = new String[EnumLanguage.values.length];
	
	protected FontUtils(Context c)
	{
		def_fonts[EnumLanguage.LATIN.ordinal()] = Typeface.DEFAULT;
		def_fonts[EnumLanguage.RAU.ordinal()] = Typeface.createFromAsset(c.getAssets(), "fonts/rau/" + AppConstants.DEF_AUK_TF_PATH + ".ttf");
		for(EnumLanguage lang : EnumLanguage.values)
		{
			font_paths[lang.ordinal()] =  AppUtils.SETTINGS.getFontPath(lang);
			setFont(lang);
		}
	}

	public void setFont(EnumLanguage lang)
	{
		setFont(lang, AppUtils.SETTINGS.getFontPath(lang));
	}
	public void setFont(EnumLanguage lang, String path)
	{
		int i = lang.ordinal();
		if(!path.equalsIgnoreCase(font_paths[i]))
		{
			if(path.length() == 0)
			{
				Typeface t = def_fonts[i];
				if(t != null)
				{
					lang.setFont(t);
				}
			}
			else
			{
				lang.setFont(Typeface.createFromFile(path));
			}
			font_paths[i] = path;
		}
	}
	
	public Typeface getDefault(EnumLanguage lang)
	{
		return def_fonts[lang.ordinal()];
	}
}
