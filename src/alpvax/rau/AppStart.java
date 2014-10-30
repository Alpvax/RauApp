package alpvax.rau;

import alpvax.rau.util.AppUtils;
import android.app.Application;

public class AppStart extends Application
{
	@Override
    public void onCreate()
	{
		super.onCreate();
		AppUtils.init(this);
    }
}
