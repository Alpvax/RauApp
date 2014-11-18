package alpvax.rau.util.tasks;

import alpvax.rau.util.settings.FontPreference;

public class FontDiscoverTask extends AlpLoadingTask<FontPreference, Void, Void>
{

	public FontDiscoverTask(TaskFinishedListener finishedListener)
	{
		super(finishedListener);
	}

	@Override
	protected Void doInBackground(FontPreference... arg)
	{
		FontPreference p = arg[0];
		if(p != null)
		{
			p.discoverFonts();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result)
	{
		super.onPostExecute(result);
	}
}
