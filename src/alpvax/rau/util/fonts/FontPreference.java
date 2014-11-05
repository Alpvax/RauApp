package alpvax.rau.util.fonts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import alpvax.rau.R;
import alpvax.rau.text.EnumLanguage;
import alpvax.rau.text.TextFormatter;
import alpvax.rau.util.AppUtils;
import alpvax.rau.util.TranslateUtils;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

public class FontPreference extends ListPreference implements DialogInterface.OnClickListener
{
    private List<String> fontPaths;
    private List<CharSequence> fontNames;
    private EnumLanguage fontLang;

	public FontPreference(Context context)
	{
		super(context);
		setEntries(new CharSequence[0]);
		setEntryValues(new CharSequence[0]);
	}
	public FontPreference(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FontPreference, 0, 0);
		setLang(EnumLanguage.get(a.getInt(R.styleable.FontPreference_language, 0)));
		a.recycle();
	}
 
    public void setLang(EnumLanguage lang)
    {
    	fontLang = lang;
	}
	public EnumLanguage getLang()
    {
    	return fontLang;
    }
    
    public CharSequence getFontName()
    {
    	discoverFonts();
    	Log.d("FontName", "prefs: "+ getSharedPreferences());
    	Log.d("FontName", "value: "+ getValue());
    	int i = fontPaths.indexOf(getValue());
    	return i >= 0 && i < fontNames.size() ? fontNames.get(i) : "";//XXX:Maybe here, "" means Default
    }
    
    private int discoverFonts()
    {
    	//TODO: Get the fonts on the device
        FontManager fm = new FontManager(fontLang.dirNames);
        if(fontLang == EnumLanguage.LATIN)
        {
        	fm.useSystemFonts();
        }
        HashMap<String, String> fonts = fm.enumerateFonts();
        fontPaths = new ArrayList<>();
        fontPaths.add("");
        fontNames = new ArrayList<>();
        fontNames.add(new TextFormatter(TranslateUtils.getText("Default")));
 
        // Get the current value to find the checked item
        String selectedFontPath = getValue();//getSharedPreferences().getString(getKey(), "");
        int idx = 0, checked_item = 0;

        //Log.d("Fonts", fonts.toString());
        //Log.d("Fonts: Keys", fonts.keySet().toString());
        for(String path : fonts.keySet())
        {
            if(path.equals(selectedFontPath))
                checked_item = idx;
 
            fontPaths.add(path);
            fontNames.add(fonts.get(path));
            idx++;
        }
        setSummary(fontNames.get(checked_item));
        return checked_item;
	}

	@Override
    protected void onPrepareDialogBuilder(Builder builder)
    {
        super.onPrepareDialogBuilder(builder);
 
        int checked_item = discoverFonts();
 
        // Create out adapter
        // If you're building for API 11 and up, you can pass builder.getContext
        // instead of current context
        FontAdapter adapter = new FontAdapter();
 
        builder.setSingleChoiceItems(adapter, checked_item, this);
 
        // The typical interaction for list-based dialogs is to have click-on-an-item dismiss the dialog
        builder.setPositiveButton(null, null);
    }

    @Override
    public void onClick(DialogInterface dialog, int i)
    {
        if(i >=0 && i < fontPaths.size())
        {
            String selectedFontPath = fontPaths.get(i);
            getSharedPreferences().edit().putString(getKey(), selectedFontPath).commit();
            setSummary(fontNames.get(i));
            AppUtils.SETTINGS.updateLabels(getPreferenceManager());
            dialog.dismiss();
        }
    }
    
    public class FontAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            return fontNames.size();
        }
 
        @Override
        public Object getItem(int position)
        {
            return fontNames.get(position);
        }
 
        @Override
        public long getItemId(int position)
        {
            // We use the position as ID
            return position;
        }
 
        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            // This function may be called in two cases: a new view needs to be created,
            // or an existing view needs to be reused
            if(view == null)
            {
                // Since we're using the system list for the layout, use the system inflater
                final LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
                // And inflate the view android.R.layout.select_dialog_singlechoice
                // Why? See com.android.internal.app.AlertController method createListView()
                view = inflater.inflate(android.R.layout.select_dialog_singlechoice, parent, false);
            }
 
            if(view != null)
            {
                // Find the text view from our interface
                CheckedTextView tv = (CheckedTextView)view.findViewById(android.R.id.text1);
 
                if(!fontPaths.get(position).equals(""))
                {
	                // Replace the string with the current font name using our typeface
	                Typeface tface = Typeface.createFromFile(fontPaths.get(position));
	                tv.setTypeface(tface);
                }
                // If you want to make the selected item having different foreground or background color,
                // be aware of themes. In some of them your foreground color may be the background color.
                // So we don't mess with anything here and just add the extra stars to have the selected
                // font to stand out.
                tv.setText(fontNames.get(position));
            }
            return view;
        }
    }
}
