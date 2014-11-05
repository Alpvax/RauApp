package alpvax.rau.text;

import static alpvax.rau.util.AppUtils.CONSTANTS;
//import static alpvax.rau.util.AppUtils.CONSTANTS.ESCAPE_LANG;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

public class TextFormatter extends SpannableString
{
	/**
     * The default font family used for creating a new typeface.
     * Examples include "monospace", "serif", and "sans-serif".
     */
	private static final String DEFAULT_FAMILY = "sans-serif";
	private static final String tagExp = "(" + CONSTANTS.ESCAPE_LANG + getLangExPart() + ")|(" + CONSTANTS.ESCAPE_FORMAT + getFormatExPart() + ")";

	private static String getLangExPart()
	{
		StringBuilder sb = new StringBuilder("(");
		boolean first = true;
		for(EnumLanguage l : EnumLanguage.values)
		{
			if(first)
			{
				first = false;
			}
			else
			{
				sb.append("|");
			}
			sb.append(l.getKey());
		}
		return sb.append(")").toString();
	}

	private static String getFormatExPart()
	{
		/*TODO:Create EnumFormat
		StringBuilder sb = new StringBuilder("(");
		boolean first = true;
		for(EnumTextFormat t : EnumTextFormat.values)
		{
			if(first)
			{
				first = false;
			}
			else
			{
				sb.append("|");
			}
			sb.append(t.getKey());
		}
		return sb.append(")").toString();*/
		return "(u|p)";
	}

	public TextFormatter(CharSequence taggedString)
	{
		super(taggedString.toString().replaceAll(tagExp, "")
				//escaped ESCAPE_LANG and ESCAPE_FORMAT is "ESCAPED_<type>e"
				.replaceAll(CONSTANTS.ESCAPE_LANG + "e", CONSTANTS.ESCAPE_LANG).replaceAll(CONSTANTS.ESCAPE_FORMAT + "e", CONSTANTS.ESCAPE_FORMAT));
		//Allows for existing spans to be kept and passed into the new CharSequence
		if(taggedString instanceof Spannable)
		{
			Spannable s = (Spannable)taggedString;
			for(Object o : s.getSpans(0, s.length(), Object.class))
			{
				setSpan(o, s.getSpanStart(o), s.getSpanEnd(o), s.getSpanFlags(o));
			}
		}
		String pattern = "(?<=" + CONSTANTS.ESCAPE_LANG + ")(l|r).+?(?=(" + CONSTANTS.ESCAPE_LANG + "|$))";
		Matcher m = Pattern.compile(pattern).matcher(taggedString.toString());
		//System.out.println("Matching \"" + pattern + "\" against \"" + taggedString.toString() + "\"");
		int i = 0;
		while(m.find())
		{
			String type = m.group().substring(0, 1);
			String s = m.group().substring(1);
			//System.out.println(type + ": " + s);
			int start = i;
			int end = i += s.length();
			//String s1 = subSequence(start, end).toString();
			//System.out.printf("\"%1$s\" %3$s \"%2$s\"%n", s, s1, s.equals(s1) ? "equals" : "does not equal");
			setSpan(EnumLanguage.fromKey(type).getFontFactory().newSpan(), start, end, SPAN_EXCLUSIVE_EXCLUSIVE);
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
        
        public Typeface getTypeface()
        {
        	return font;
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