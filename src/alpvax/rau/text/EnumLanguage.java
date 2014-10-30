package alpvax.rau.text;

import java.util.Locale;

import alpvax.rau.R;
import alpvax.rau.text.TextFormatter.TypefaceSpanFactory;
import alpvax.rau.util.TranslateUtils;
import android.graphics.Typeface;

public enum EnumLanguage
{
	LATIN(R.string.latin, "l"), //"latin", "english"),
	RAU(R.string.rau, "r");//"aukaa", "auk", "rau");
	public static final EnumLanguage[] values = values();

	private final int label;
	private final String langKey;
	private TypefaceSpanFactory fontFactory;
	public String[] dirNames;//TODO: implement directory recognition

	private EnumLanguage(int resID, String key)
	{
		label = resID;
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
			return TranslateUtils./*instance(null).appContext.*/getString(label);
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
			if(e.name().equals(name) || e.toString().equalsIgnoreCase(name))
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
