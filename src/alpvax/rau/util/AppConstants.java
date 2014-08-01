package alpvax.rau.util;

import alpvax.rau.R;
import android.content.res.Resources;

public class AppConstants
{
	private static AppConstants instance = null;
	
	public static AppConstants instance(Resources resources)
	{
		if(instance == null)
		{
			if(resources == null)
			{
				throw new IllegalArgumentException("Cannot create an instance without a valid resources object.");
			}
			instance = new AppConstants(resources);
		}
		return instance;
	}
	
	public final String PORT;
	public final int START_TAB;
	
	private AppConstants(Resources r)
	{
		PORT = r.getString(R.string.port);
		START_TAB = r.getInteger(R.integer.start_tab);
	}
}
