package alpvax.rau.fragments;

import alpvax.rau.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DictionaryFragment extends Fragment
{
	//TODO: Implement Dictionary Fragment
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
	    View view = inflater.inflate(R.layout.fragment_dictionary, container, false);
		return view;
	}
}
