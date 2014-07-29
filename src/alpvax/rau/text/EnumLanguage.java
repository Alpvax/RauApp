package alpvax.rau.text;

public enum EnumLanguage
{
	AUKAA,LATIN;
	public static EnumLanguage[] values = values();
	
	public static EnumLanguage get(int i)
	{
		return values[i];
	}
}
