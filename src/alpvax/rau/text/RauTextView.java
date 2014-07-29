package alpvax.rau.text;

import alpvax.rau.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

public class RauTextView extends TextView implements IRauText
{
	private CharSequence rauText;
	private CharSequence latText;
	private EnumLanguage currentLang;

	public RauTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RauButton, 0, 0);
		try
		{
			setRauText(a.getString(R.styleable.RauButton_rauText));
	    	setLatinText(a.getString(R.styleable.RauButton_latinText));
	    	setCurrentLang(EnumLanguage.get(a.getInt(R.styleable.RauButton_defaultLang, /*TODO: get from settings*/0)));
		}
		finally
		{
			a.recycle();
		}
	}

	@Override
	public CharSequence getRau()
	{
		return rauText;
	}
	@Override
	public void setRauText(CharSequence text)
	{
		rauText = text;
	}

	@Override
	public CharSequence getLatin()
	{
		return latText;
	}
	@Override
	public void setLatinText(CharSequence text)
	{
		latText = text;
	}

	@Override
	public EnumLanguage currentLang()
	{
		return currentLang;
	}
	@Override
	public void setCurrentLang(EnumLanguage lang)
	{
		currentLang = lang;
	}
}
