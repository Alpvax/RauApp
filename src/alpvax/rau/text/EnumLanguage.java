package alpvax.rau.text;

import java.util.Locale;

import alpvax.rau.text.TextFormatter.TypefaceSpanFactory;
import alpvax.rau.util.AppUtils;
import alpvax.rau.util.TranslateUtils;
import android.graphics.Typeface;

public enum EnumLanguage
{
	LATIN("l"), //"latin", "english"),
	RAU("r");//"aukaa", "auk", "rau");
	public static final EnumLanguage[] values = values();

	private final String langKey;
	private TypefaceSpanFactory fontFactory;
	public String[] dirNames;

	private EnumLanguage(String key)
	{
		if(key.length() > 1)
		{
			System.err.println("Key length must be 1, recieved \"" + key + "\". Using \"" + key.substring(0, 1) + "\"");
		}
		langKey = key.substring(0, 1);
		dirNames = new String[]{name().toLowerCase(Locale.UK)};
	}

	/*private EnumLanguage(int resID, String... dirAliases)
	{
		label = resID;
		dirNames = dirAliases;
	}*/

	public String getKey()
	{
		return langKey;
	}

	public String langEscape()
	{
		return AppUtils.CONSTANTS.ESCAPE_LANG + langKey;
	}
	
	public TypefaceSpanFactory getFontFactory()
	{
		return fontFactory;
	}
	public void setFont(Typeface t)
	{
		fontFactory = new TypefaceSpanFactory(t);
	}

	@Override
	public String toString()
	{
		try
		{
			return TranslateUtils.getText(name().toLowerCase(Locale.UK)).toString();
		}
		catch(IllegalArgumentException e)
		{
			return super.toString();
		}
	}
	public CharSequence formattedString()
	{
		try
		{
			return new TextFormatter(TranslateUtils.getText(name().toLowerCase(Locale.UK), this));
		}
		catch(IllegalArgumentException e)
		{
			return super.toString();
		}
	}
	
	public static EnumLanguage get(int i)
	{
		return values[i];
	}
	public static EnumLanguage get(String name)
	{
		for(EnumLanguage e : values)
		{
			if(e.name().equals(name))
			{
				return e;
			}
		}
		return null;
	}
	public static EnumLanguage fromTranslated(String name)
	{
		for(EnumLanguage e : values)
		{
			if(e.toString().equalsIgnoreCase(name))
			{
				return e;
			}
		}
		return null;
	}

	public static EnumLanguage fromKey(String key)
	{

		for(EnumLanguage e : values)
		{
			if(e.getKey().equals(key))
			{
				return e;
			}
		}
		return null;
	}
}
