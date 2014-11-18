package alpvax.rau.util.tasks;

import alpvax.rau.util.AppUtils;
import android.content.Context;


/**
 * @author Alpvax
 *
 */
public class AppLoadingTask extends AlpLoadingTask<Context, Integer, Void>
{

	public AppLoadingTask(TaskFinishedListener finishedListener)
	{
		super(finishedListener);
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
	}

	@Override
	protected void onPostExecute(Void result)
	{
		super.onPostExecute(result);
	}
}
