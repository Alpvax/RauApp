package alpvax.rau;

import alpvax.rau.util.tasks.AlpLoadingTask.TaskFinishedListener;
import alpvax.rau.util.tasks.AppLoadingTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashActivity extends Activity implements TaskFinishedListener
{
	private boolean done = false;

	/** Splash screen timer (ms)*/
	private static int SPLASH_TIME_OUT = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.splash_screen);

		new AppLoadingTask(this).execute(this);

		new Handler().postDelayed(new Runnable(){
			@Override
			public void run()
			{
				launchMain();
			}
		}, SPLASH_TIME_OUT);
	}

	/* (non-Javadoc)
	 * @see alpvax.rau.AppLoadingTask.TaskFinishedListener#onTaskFinished()
	 */
	@Override
	public void onTaskFinished()
	{
		launchMain();
	}

	public void launchMain()
	{
		if(done)// needs to be run twice in order for app to actually finish
		{
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		else
		{
			done = true;
		}
	}
}