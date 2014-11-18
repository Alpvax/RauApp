package alpvax.rau.util;

import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.widget.TextView;

public class LGFixHelper
{
	private static boolean isLG16 = false &&Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("lg") && Build.VERSION.SDK_INT == 16;
	
	public static void onCreate(Activity a)
	{
        if(isLG16)
        {
			ActionBar ab = a.getActionBar();
			ab.setCustomView(new TextView(a));
			ab.setDisplayShowCustomEnabled(true);
        }
    }
	
	public static void setTitle(Activity a, CharSequence title)
	{
		if(isLG16)
		{
			a.setTitle("");
			((TextView)a.getActionBar().getCustomView()).setText(title);
		}
		else
		{
			a.setTitle(title);
		}
	}
}
