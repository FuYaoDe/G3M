package com.example.navigationdrawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Fragment_list  extends ListFragment{
	
	private static final String TAG = "MyFragment";
	public Fragment_list()
	{
		super();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.english_list, container, false);
		ListView listView = (ListView) view.findViewById(R.id.listView1);
		/*View text = view.findViewById(android.R.id.text1);
		if(text != null && text instanceof TextView)
		{
			((TextView)text).setText(titles[getArguments().getInt("position")]);
		}*/
		return view;
	}
   
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		//Log.d(TAG, "Attached " + getArguments().getInt("position"));
	}
	
	@Override
	public void onDetach()
	{
		//Log.d(TAG, "Detached " + getArguments().getInt("position"));
		super.onDetach();
	}
}
