package alpvax.rau.text;

public interface IRauText
{
	public CharSequence getRau();
	public void setRauText(CharSequence text);

	public CharSequence getLatin();
	public void setLatinText(CharSequence text);

	public EnumLanguage currentLang();
	public void setCurrentLang(EnumLanguage lang);
}
