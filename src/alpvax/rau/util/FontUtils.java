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
		def_fonts[EnumLanguage.RAU.ordinal()] = Typeface.createFromAsset(c.getAssets(), "fonts/rau/" + AppUtils.CONSTANTS.DEF_AUK_TF_PATH + ".ttf");
		for(EnumLanguage lang : EnumLanguage.values)
		{
			int i = lang.ordinal();
			//font_paths[i] =  AppUtils.SETTINGS.getFontPath(lang);
			if(def_fonts[i] == null)
			{
				def_fonts[i] = Typeface.DEFAULT;
			}
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
