package alpvax.rau;

import alpvax.rau.util.AppUtils;
import android.content.Context;
import android.os.AsyncTask;


/**
 * @author Alpvax
 *
 */
public class AppLoadingTask extends AsyncTask<Context, Integer, Void>
{
	public interface TaskFinishedListener
	{
		void onTaskFinished(); // If you want to pass something back to the listener add a param to this method
	}

	//private final ProgressBar progressBar;
	// This is the listener that will be told when this task is finished
	private final TaskFinishedListener finishedListener;

	/**
	 * A Loading task that will load some resources that are necessary for the app to start
	 * @param progressBar - the progress bar you want to update while the task is in progress
	 * @param finishedListener - the listener that will be told when this task is finished
	 */
	public AppLoadingTask(TaskFinishedListener finishedListener)
	{
		//this.progressBar = progressBar;
		this.finishedListener = finishedListener;
	}

	@Override
	protected Void doInBackground(Context... arg0)
	{
		AppUtils.init(arg0[0]);
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values)
	{
		super.onProgressUpdate(values);
		//progressBar.setProgress(values[0]); // This is ran on the UI thread so it is ok to update our progress bar ( a UI view ) here
	}

	@Override
	protected void onPostExecute(Void result)
	{
		super.onPostExecute(result);
		finishedListener.onTaskFinished(); // Tell whoever was listening we have finished
	}

}
