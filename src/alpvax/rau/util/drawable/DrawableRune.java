package alpvax.rau.util.drawable;

import alpvax.rau.core.Runes.Rune;
import alpvax.rau.text.EnumLanguage;
import android.content.Context;
import android.graphics.Canvas;

public class DrawableRune extends DrawableText
{
	public DrawableRune(Context context, Rune rune)
	{
		super(context);
		setText(rune.toString());
	}

	@Override
	public void draw(Canvas canvas)
	{
		setTypeface(EnumLanguage.RAU.getFontFactory().getTypeface());
		super.draw(canvas);
	}

}
