package alpvax.rau.text;

import alpvax.rau.R;
import alpvax.rau.util.AppUtils;

public enum EnumLanguage
{
	LATIN(R.string.latin),
	RAU(R.string.rau);
	public static EnumLanguage[] values = values();
	
	private int label;
	private EnumLanguage(int resID)
	{
		label = resID;
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
			if(e.name().equals(name))
			{
				return e;
			}
		}
		return null;
	}
}
