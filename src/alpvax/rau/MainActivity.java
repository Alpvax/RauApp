package alpvax.rau;

import java.util.Locale;

import alpvax.rau.fragments.DictionaryFragment;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends Activity
{

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager)findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter
	{
		private FragmentFactory[] fragments = {
			new FragmentFactory(DictionaryFragment.class, R.string.title_dictionary_latin, R.string.title_dictionary_rau)	
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
		private String lat;
		private String rau;
		
		public FragmentFactory(Class<? extends Fragment> fragment, int lat_title, int rau_title)
		{
			frag = fragment;
			lat = getString(lat_title);
			rau = getString(rau_title);
		}
		
		public CharSequence getTitle()
		{
			//TODO: After Settings, get Title Formatted
			return lat;
		}
		
		public Fragment newFragment() throws InstantiationException, IllegalAccessException
		{
			return frag.newInstance();
		}
	}

//	/**
//	 * A placeholder fragment containing a simple view.
//	 */
//	public static class PlaceholderFragment extends Fragment
//	{
//		/**
//		 * The fragment argument representing the section number for this
//		 * fragment.
//		 */
//		private static final String ARG_SECTION_NUMBER = "section_number";
//
//		/**
//		 * Returns a new instance of this fragment for the given section number.
//		 */
//		public static PlaceholderFragment newInstance(int sectionNumber)
//		{
//			PlaceholderFragment fragment = new PlaceholderFragment();
//			Bundle args = new Bundle();
//			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//			fragment.setArguments(args);
//			return fragment;
//		}
//
//		public PlaceholderFragment() {}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//		{
//			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//			TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//			textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
//			return rootView;
//		}
//	}

}
