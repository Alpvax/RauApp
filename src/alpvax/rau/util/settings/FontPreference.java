package alpvax.rau.util.settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import alpvax.rau.R;
import alpvax.rau.text.EnumLanguage;
import alpvax.rau.text.TextFormatter;
import alpvax.rau.util.AppUtils;
import alpvax.rau.util.TranslateUtils;
import alpvax.rau.util.fonts.FontManager;
import alpvax.rau.util.tasks.AlpLoadingTask.TaskFinishedListener;
import alpvax.rau.util.tasks.FontDiscoverTask;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.preference.ListPreference;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

public class FontPreference extends ListPreference implements DialogInterface.OnClickListener, TaskFinishedListener
{
    private List<String> fontPaths = new ArrayList<String>();
    private List<CharSequence> fontNames = new ArrayList<CharSequence>();
    private EnumLanguage fontLang;

	public FontPreference(Context context)
	{
		super(context);
		setEntries(new CharSequence[0]);
		setEntryValues(new CharSequence[0]);
		setDefaultValue("");
	}
	public FontPreference(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FontPreference, 0, 0);
		setLang(EnumLanguage.get(a.getInt(R.styleable.FontPreference_language, 0)));
		a.recycle();
		setDefaultValue("");
	}
 
    public void setLang(EnumLanguage lang)
    {
    	fontLang = lang;
		setEnabled(false);
    	new FontDiscoverTask(this).execute(this);
	}
	public EnumLanguage getLang()
    {
    	return fontLang;
    }
    
    public CharSequence getFontName()
    {
    	int i = getSelectedIndex();
    	if(i < 0)
    	{
    		return "";
    	}
    	SpannableString s = new SpannableString(fontNames.get(i));
    	s.setSpan(getLang().getFontFactory().newSpan(), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    	return s;
    }

    private int getSelectedIndex()
    {
    	int i = fontPaths.indexOf(getValue());
    	return i >= 0 && i < fontNames.size() ? i : -1;
	}
    
    public void discoverFonts()
    {
    	//TODO: Get the fonts on the device
        FontManager fm = new FontManager(getLang().dirNames);
        if(fontLang == EnumLanguage.LATIN)
        {
        	fm.useSystemFonts();
        }
        HashMap<String, String> fonts = fm.enumerateFonts();
        fontPaths.clear();
        //fontPaths.add("");
        fontNames.clear();
        //fontNames.add(new TextFormatter(TranslateUtils.getText("Default")));
        
        List<SortHelper> list = new ArrayList<SortHelper>();
        list.add(new SortHelper(new TextFormatter(TranslateUtils.getText("Default")), ""));

        //Log.d("Fonts", fonts.toString());
        //Log.d("Fonts: Keys", fonts.keySet().toString());
        for(String path : fonts.keySet())
        {
        	SortHelper s = new SortHelper(fonts.get(path), path);
        	if(!list.contains(s))
        	{
        		list.add(s);
        	}
        	/*if(!fontPaths.contains(path))
        	{
	            fontPaths.add(path);
	            fontNames.add(fonts.get(path));
        	}*/
        }
        Collections.sort(list);
        for(SortHelper s : list)
        {
        	fontNames.add(s.csName);
        	fontPaths.add(s.path);
        }
        //setSummary(getFontName());
	}

	@Override
	public void onTaskFinished()
	{
		setEnabled(true);
        setSummary(getFontName());
	}

	@Override
    protected void onPrepareDialogBuilder(Builder builder)
    {
        super.onPrepareDialogBuilder(builder);
 
        // Create out adapter
        // If you're building for API 11 and up, you can pass builder.getContext
        // instead of current context
        FontAdapter adapter = new FontAdapter();
 
        builder.setSingleChoiceItems(adapter, getSelectedIndex(), this);
 
        // The typical interaction for list-based dialogs is to have click-on-an-item dismiss the dialog
        builder.setPositiveButton(null, null);
    }

	@Override
    public void onClick(DialogInterface dialog, int i)
    {
        if(i >=0 && i < fontPaths.size())
        {
            String selectedFontPath = fontPaths.get(i);
            setValue(selectedFontPath);
            setSummary(fontNames.get(i));
            AppUtils.SETTINGS.updateLabels(getPreferenceManager());
            dialog.dismiss();
        }
    }
    
    private class SortHelper implements Comparable<SortHelper>
    {
    	private CharSequence csName;
    	private String name;
    	private String path;
    	private SortHelper(CharSequence name, String path)
    	{
    		this.csName = name;
    		this.name = name.toString();
    		this.path = path;
    	}
		@Override
		public int compareTo(SortHelper another)
		{
			return name.equalsIgnoreCase("default") ? -1 : another.name.equalsIgnoreCase("default") ? 1 : name.compareToIgnoreCase(another.name);
		}
    	@Override
    	public boolean equals(Object other)
    	{
    		return other != null && other instanceof SortHelper && ((SortHelper)other).path.equalsIgnoreCase(path);
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
                if(fontNames.get(position).toString().equalsIgnoreCase("Default"))
                {
                	Log.d("Default font: " + getLang().name(), "Path: \"" + fontPaths.get(position) + "\"");
                }
                if(!fontPaths.get(position).equals(""))
                {
	                // Replace the string with the current font name using our typeface
	                Typeface tface = Typeface.createFromFile(fontPaths.get(position));
	                tv.setTypeface(tface);
                }
                else
                {
                	Log.d("Null font: " + getLang().name(), "Name: \"" + fontNames.get(position) + "\"");
	                tv.setTypeface(AppUtils.FONTS.getDefault(getLang()));//TODO:Work out what is going wrong here
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
