package alpvax.rau.util;

import alpvax.rau.core.Runes.Rune;
import alpvax.rau.util.settings.SettingsHelper;
import android.content.Context;
import android.graphics.Canvas;

public class DrawableRune extends DrawableText
{
	private SettingsHelper settingsInstance;
	public DrawableRune(Context context, Rune rune)
	{
		super(context);
		settingsInstance = SettingsHelper.instance(context);
		setText(rune.toString());
	}

	@Override
	public void draw(Canvas canvas)
	{
		setTypeface(settingsInstance.aukTypeface());//TODO optimise
		super.draw(canvas);
	}

}
