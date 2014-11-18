package alpvax.rau.util.settings;

import alpvax.rau.text.TextFormatter;
import alpvax.rau.util.TranslateUtils;
import android.content.Context;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class CustomPrefCat extends PreferenceCategory
{
	public CustomPrefCat(Context context)
	{
		super(context);
	}

	public CustomPrefCat(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public CustomPrefCat(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}
	
	@Override
    protected void onBindView(View view)
	{
        super.onBindView(view);
        TextView title = (TextView)view.findViewById(android.R.id.title);
        title.setText(new TextFormatter(TranslateUtils.getText(getKey().replaceAll("(?<=pref_)key", "title"))));
    }
}
