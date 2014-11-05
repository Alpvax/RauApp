package alpvax.rau.util;

import alpvax.rau.R;
import android.content.Context;
import android.content.res.Resources;

public class AppConstants
{
	public final String PORT;
	public final int START_TAB;
	public final String DEF_AUK_TF_PATH;
	public final String ESCAPE_LANG;
	public final String ESCAPE_FORMAT;
	public final String MISSING_STRING;
	
	protected AppConstants(Context c)
	{
		Resources r = c.getResources();
		PORT = r.getString(R.string.port);
		START_TAB = r.getInteger(R.integer.start_tab);
		DEF_AUK_TF_PATH = r.getString(R.string.def_auk_font);
		ESCAPE_LANG = r.getString(R.string.escape_lang);
		ESCAPE_FORMAT = r.getString(R.string.escape_format);
		MISSING_STRING = r.getString(R.string.missing_string);
	}
}