package alpvax.rau.util.settings;

import alpvax.rau.R;
import alpvax.rau.text.EnumLanguage;
import alpvax.rau.text.TextFormatter;
import alpvax.rau.util.AppUtils;
import alpvax.rau.util.SettingsHelper.SettingsKeys;
import alpvax.rau.util.TranslateUtils;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener
{
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        Activity a = getActivity();
        PreferenceCategory fonts = (PreferenceCategory)findPreference(SettingsKeys.GROUP_FONTS);
        for(int i = 0; i < EnumLanguage.values.length; i++)
        {
        	EnumLanguage lang = EnumLanguage.values[i];
        	FontPreference pref = new FontPreference(a);
        	pref.setKey(SettingsKeys.add(SettingsKeys.KEY_FONT_PREFIX + lang.name()));
        	pref.setTitle(new TextFormatter(lang.formattedString()));
        	pref.setLang(lang);
        	fonts.addPreference(pref);
        	//pref.setSummary(pref.getFontName());
        }
        ListPreference p = (ListPreference)findPreference(SettingsKeys.KEY_LANGUAGE);
        p.setEntries(new CharSequence[]{EnumLanguage.LATIN.formattedString(), EnumLanguage.RAU.formattedString()});
        p.setEntryValues(new String[]{EnumLanguage.LATIN.name(), EnumLanguage.RAU.name()});
        //p.setValueIndex(1);
        AppUtils.SETTINGS.updateLabels(getPreferenceManager());
        //LGFixHelper.setTitle(a, new TextFormatter(TranslateUtils.getText("title_activity_settings")));
        a.setTitle(new TextFormatter(TranslateUtils.getText("title_activity_settings")));
    }
	
	@Override
	public void onResume()
	{
		super.onResume();
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
    	Log.d("Action bar", "Pressed item: " + item.getTitle());
	    if(alpvax.rau.util.LGFixHelper.isLG16 && item.getItemId() == android.R.id.home)
	    {
	    	Log.i("Navigating UP", "Jumping to app HOME due to you a bug on LG devices runing Android 4.1.2 (Jellybean)");
	    	NavUtils.navigateUpFromSameTask(getActivity());
	    	/*Activity a = getActivity();
	        startActivity(new Intent(a, MainActivity.class));
	        a.finish();*/
	        return true;
	    }
	    return false;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
	{
        PreferenceManager pm = getPreferenceManager();
		AppUtils.SETTINGS.update(pm.findPreference(key));
		if(key == SettingsKeys.KEY_LANGUAGE || key.startsWith(SettingsKeys.KEY_FONT_PREFIX))
		{
	        Activity a = getActivity();
	        AppUtils.SETTINGS.updateLabels(getPreferenceManager());
	        //LGFixHelper.setTitle(a, new TextFormatter(TranslateUtils.getText("title_activity_settings")));
	        a.setTitle(new TextFormatter(TranslateUtils.getText("title_activity_settings")));
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