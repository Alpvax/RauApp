package alpvax.rau;

import alpvax.rau.text.TextFormatter;
import alpvax.rau.util.AppUtils;
import alpvax.rau.util.LGFixHelper;
import alpvax.rau.util.TranslateUtils;
import alpvax.rau.util.settings.SettingsFragment.SettingsActivity;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends Activity implements OnPageChangeListener
{

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter sectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LGFixHelper.onCreate(this);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		sectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		viewPager = (ViewPager)findViewById(R.id.pager);
		viewPager.setAdapter(sectionsPagerAdapter);
		
		viewPager.setOnPageChangeListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		recursiveMenu(menu);
		return true;
	}
	private void recursiveMenu(Menu menu)
	{
		for(int i = 0; i < menu.size(); i++)
		{
			MenuItem m = menu.getItem(i);
			String s = m.getTitleCondensed().toString();
			m.setTitle(new TextFormatter(TranslateUtils.getText(s)));
			m.setTitleCondensed(s);
			if(m.hasSubMenu())
			{
				recursiveMenu(m.getSubMenu());
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if(id == R.id.action_settings)
		{
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPageScrollStateChanged(int pos) {}

	@Override
	public void onPageScrolled(int pos, float arg1, int arg2) {}

	@Override
	public void onPageSelected(int pos)
	{
		LGFixHelper.setTitle(this, sectionsPagerAdapter.getPageTitle(pos));
	}
	
	@Override
	protected void onStart()
	{
	    super.onStart();
	    viewPager.setCurrentItem(AppUtils.CONSTANTS.START_TAB);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		invalidateOptionsMenu();
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter
	{
		private FragmentFactory[] fragments = {
				new FragmentFactory(PlaceholderFragment.class, "title_dictionary"),
				new FragmentFactory(PlaceholderFragment.class, "title_messages"),
				new FragmentFactory(PlaceholderFragment.class, "title_chatcontacts"),
				new FragmentFactory(PlaceholderFragment.class, "title_chat")
		};

		public SectionsPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position)
		{
			try
			{
				return fragments[position].newFragment();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public int getCount()
		{
			return fragments.length;
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			return fragments[position].getTitle();
		}
	}
	
	private class FragmentFactory
	{
		private Class<? extends Fragment> frag;
		private String title;

		public FragmentFactory(Class<? extends Fragment> fragment, String titleKey)
		{
			frag = fragment;
			title = titleKey;
		}
		
		public CharSequence getTitle()
		{
			return new TextFormatter(TranslateUtils.getText(title));
		}
		
		public Fragment newFragment() throws InstantiationException, IllegalAccessException
		{
			return frag == PlaceholderFragment.class ? PlaceholderFragment.newInstance(title) : frag.newInstance();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment
	{
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_TITLE = "fragment_title";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(String titleKey)
		{
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putCharSequence(ARG_TITLE, titleKey);
			fragment.setArguments(args);
			return fragment;
		}

		private TextView text;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.fragment_placeholder, container, false);
			text = (TextView)rootView.findViewById(R.id.placeholderText);
			text.setTag(getArguments().getCharSequence(ARG_TITLE));
			//text.setText(AppUtils.instance(getActivity()).getText(((Integer)text.getTag()).intValue()));
			return rootView;
		}
		
		@Override
		public void onResume()
		{
			super.onResume();
			text.setText(new TextFormatter(TranslateUtils.getText((CharSequence)text.getTag())));
		}
	}

}
