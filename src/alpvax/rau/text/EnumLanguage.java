package alpvax.rau.text;

import alpvax.rau.R;
import alpvax.rau.util.AppUtils;

public enum EnumLanguage
{
	LATIN(R.string.latin, "latin", "english"),
	RAU(R.string.rau, "aukaa", "auk", "rau");
	public static final EnumLanguage[] values = values();
	
	private int label;
	public String[] dirNames;//TODO: implement directory recognition
	private EnumLanguage(int resID, String... dirAliases)
	{
		label = resID;
		dirNames = dirAliases;
	}
	
	@Override
	public String toString()
	{
		try
		{
			return AppUtils.instance(null).appContext.getString(label);
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
}
