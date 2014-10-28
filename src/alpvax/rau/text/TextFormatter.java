package alpvax.rau.text;

import static alpvax.rau.util.AppConstants.ESCAPE_FORMAT;
import static alpvax.rau.util.AppConstants.ESCAPE_LANG;
import alpvax.rau.util.settings.SettingsHelper;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

public class TextFormatter extends SpannableString
{
	private static final String DEFAULT_FAMILY = "monospace";
	private static final String tagExp = "(" + ESCAPE_LANG + "(a|l))|(" + ESCAPE_FORMAT + "(u|p))";

	public TextFormatter(CharSequence taggedString)
	{
		super(taggedString.toString().replaceAll(tagExp, ""));
		//Allows for existing spans to be kept and passed into the new CharSequence
		if(taggedString instanceof Spannable)
		{
			Spannable s = (Spannable)taggedString;
			for(Object o : s.getSpans(0, s.length(), Object.class))
			{
				setSpan(o, s.getSpanStart(o), s.getSpanEnd(o), s.getSpanFlags(o));
			}
		}
		//TODO Make all matching use regEx
		String string = taggedString.toString();
		int i;
		int j = 1;//Set to >0 to enable
		int start = 0;
		while((i = string.indexOf(ESCAPE_LANG)) >= 0 && start < length())
		{
			if(i < 0)
			{
				return;
			}
			i += ESCAPE_LANG.length();
			TypefaceSpanFactory t = null;
			switch(string.charAt(i))//TODO: Organise and use variables for case statements
			{
			case 'a':
				t = new TypefaceSpanFactory(SettingsHelper.instance(null).aukTypeface());
				break;
			case 'l':
				t = new TypefaceSpanFactory(SettingsHelper.instance(null).latinTypeface());
				break;
			}
			j = string.indexOf(ESCAPE_LANG, i + 1);
			if(j < 0)
			{
				j = string.length();
			}
			else
			{
				string = string.substring(j);
			}
			int len = j - i - 1;
			if(t != null)
			{
				setSpan(t.newSpan(), start, start + len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			start += len;
		}
	}
	
	public static class TypefaceSpanFactory
	{
		private final String family;
		private final Typeface font;

		public TypefaceSpanFactory(Typeface newFont)
		{
			this(DEFAULT_FAMILY, newFont);
		}
		public TypefaceSpanFactory(String fontFamily, Typeface newFont)
		{
			family = fontFamily;
			font = newFont;
		}
        
        public CustomTypefaceSpan newSpan()
        {
        	return new CustomTypefaceSpan(family, font);
        }
	}
	
	private static class CustomTypefaceSpan extends TypefaceSpan
    {
        private final Typeface newType;
        
        public CustomTypefaceSpan(String family, Typeface type)
        {
            super(family);
            newType = type;
        }

        @Override
        public void updateDrawState(TextPaint ds)
        {
            applyCustomTypeFace(ds, newType);
        }

        @Override
        public void updateMeasureState(TextPaint paint)
        {
            applyCustomTypeFace(paint, newType);
        }

        private static void applyCustomTypeFace(Paint paint, Typeface tf)
        {
            int oldStyle;
            Typeface old = paint.getTypeface();
            if (old == null) {
                oldStyle = 0;
            } else {
                oldStyle = old.getStyle();
            }

            int fake = oldStyle & ~tf.getStyle();
            if ((fake & Typeface.BOLD) != 0)
            {
                paint.setFakeBoldText(true);
            }

            if ((fake & Typeface.ITALIC) != 0)
            {
                paint.setTextSkewX(-0.25f);
            }

            paint.setTypeface(tf);
        }
    }
}