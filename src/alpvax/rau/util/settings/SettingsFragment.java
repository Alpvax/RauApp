package alpvax.rau.util.settings;

import alpvax.rau.R;
import alpvax.rau.text.EnumLanguage;
import alpvax.rau.util.AppUtils;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener
{
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        ListPreference p = (ListPreference)findPreference(SettingsHelper.KEY_LANGUAGE);
        p.setEntries(new String[]{EnumLanguage.LATIN.toString(), EnumLanguage.RAU.toString()});
        p.setEntryValues(new String[]{EnumLanguage.LATIN.name(), EnumLanguage.RAU.name()});
        //p.setValueIndex(1);
        Context c = getActivity();
        SettingsHelper.updateAll(getPreferenceManager(), c);
    }
	
	@Override
	public void onResume()
	{
		super.onResume();
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
	{
        PreferenceManager pm = getPreferenceManager();
		SettingsHelper.instance(getActivity()).update(pm.findPreference(key));
		if(key == SettingsHelper.KEY_LANGUAGE)
		{
	        Activity a = getActivity();
	        SettingsHelper.updateAll(getPreferenceManager(), a);
	        a.setTitle(AppUtils.instance(a).getText(R.array.title_activity_settings));
		}
	}
	
	public static class SettingsActivity extends Activity
	{
		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
	        super.onCreate(savedInstanceState);
	        // Display the fragment as the main content.
	        getFragmentManager().beginTransaction()
	                .replace(android.R.id.content, new SettingsFragment())
	                .commit();
	    }
	}
}