package alpvax.rau.util;

import alpvax.rau.R;
import android.content.Context;
import android.content.res.Resources;

public class AppConstants
{
	
	public static String PORT;
	public static int START_TAB;
	public static String DEF_AUK_TF_PATH;
	public static String ESCAPE_LANG;
	public static String ESCAPE_FORMAT;
	
	public static void init(Context c)
	{
		Resources r = c.getResources();
		PORT = r.getString(R.string.port);
		START_TAB = r.getInteger(R.integer.start_tab);
		DEF_AUK_TF_PATH = r.getString(R.string.def_auk_font);
		ESCAPE_LANG = r.getString(R.string.escape_lang);
		ESCAPE_FORMAT = r.getString(R.string.escape_format);
	}
}
