package alpvax.rau.util.tasks;

import android.os.AsyncTask;

/**
 * @author Alpvax
 *
 */
public abstract class AlpLoadingTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result>
{
	public interface TaskFinishedListener
	{
		void onTaskFinished();
	}

	//private final ProgressBar progressBar;
	// This is the listener that will be told when this task is finished
	private final TaskFinishedListener finishedListener;

	/**
	 * A Loading task that will load some resources that are necessary for the app to start
	 * @param progressBar - the progress bar you want to update while the task is in progress
	 * @param finishedListener - the listener that will be told when this task is finished
	 */
	public AlpLoadingTask(TaskFinishedListener finishedListener)
	{
		//this.progressBar = progressBar;
		this.finishedListener = finishedListener;
	}

	@Override
	protected void onPostExecute(Result result)
	{
		super.onPostExecute(result);
		if(finishedListener != null)
		{
			finishedListener.onTaskFinished();
		}
	}
}