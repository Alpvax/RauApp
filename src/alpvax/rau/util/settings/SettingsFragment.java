package alpvax.rau.util.settings;

import alpvax.rau.R;
import alpvax.rau.text.EnumLanguage;
import alpvax.rau.util.AppUtils;
import alpvax.rau.util.SettingsHelper;
import alpvax.rau.util.SettingsHelper.SettingsKeys;
import alpvax.rau.util.TranslateUtils;
import alpvax.rau.util.fonts.FontPreference;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener
{
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        Activity a = getActivity();
        PreferenceCategory fonts = (PreferenceCategory)findPreference(a.getString(R.prefKey.fonts_settings));
        for(int i = 0; i < EnumLanguage.values.length; i++)
        {
        	EnumLanguage lang = EnumLanguage.values[i];
        	FontPreference pref = new FontPreference(a);
        	pref.setKey(SettingsKeys.KEY_FONT_PREFIX + lang.name());
        	pref.setTitle(TranslateUtils.formatText(lang.toString()));
        	pref.setLang(lang);
        	pref.setSummary(pref.getFontName());
        	fonts.addPreference(pref);
        }
        ListPreference p = (ListPreference)findPreference(SettingsKeys.KEY_LANGUAGE);
        p.setEntries(new String[]{EnumLanguage.LATIN.toString(), EnumLanguage.RAU.toString()});
        p.setEntryValues(new String[]{EnumLanguage.LATIN.name(), EnumLanguage.RAU.name()});
        //p.setValueIndex(1);
        SettingsHelper.updateLabels(getPreferenceManager(), a);
        a.setTitle(TranslateUtils.getText(R.array.title_activity_settings));
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
		AppUtils.SETTINGS.update(pm.findPreference(key));
		if(key == SettingsKeys.KEY_LANGUAGE)
		{
	        Activity a = getActivity();
	        SettingsHelper.updateLabels(getPreferenceManager(), a);
	        a.setTitle(TranslateUtils.getText(R.array.title_activity_settings));
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