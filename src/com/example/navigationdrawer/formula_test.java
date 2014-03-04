package com.example.navigationdrawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class formula_test extends Fragment
{		
	public formula_test()
	{
		super();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.formula_test, container, false);
		View text = view.findViewById(android.R.id.text1);
		return view;
	}
			
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
	}
	
	@Override
	public void onDetach()
	{
		super.onDetach();
	}
}
