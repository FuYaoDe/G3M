package com.example.navigationdrawer;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class formula_test extends Fragment
{		
	private static int PagNum = 0;
	private MySQLite db = null;
	private Cursor cursor_math = null, cursor_physics = null;
	private testCOUNT test_count= null;
	private int math = 0;
	private String physics = null;
	private TextView test_name ;
	private ImageView test_quition, test_pam;
	
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
		test_name = (TextView)view.findViewById(R.id.textView2);
		test_quition = (ImageView)view.findViewById(R.id.imageView1);
		test_pam = (ImageView)view.findViewById(R.id.imageView2);
		
		PagNum = getArguments().getInt("position");
		db = new MySQLite(getActivity());
		db.OpenDB();
		test_count = new testCOUNT(db.maxID(PagNum+1));
		
		if(PagNum == 1){//PagNum == 1 ,math; else physics;
			cursor_math = db.science_get(test_count.test_id(), PagNum);
			test_name.setText(cursor_math.getString(1));
			test_quition.setImageResource(getResources().getIdentifier(cursor_math.getString(4), 
					"drawable", "com.example.navigationdrawer"));
			test_pam.setImageResource(getResources().getIdentifier(cursor_math.getString(5), 
					"drawable", "com.example.navigationdrawer"));
		}
		else{
			cursor_physics = db.science_get(test_count.test_id(), PagNum);
			test_name.setText(cursor_physics.getString(1));
			test_quition.setImageResource(getResources().getIdentifier(cursor_physics.getString(4), 
					"drawable", "com.example.navigationdrawer"));
			test_pam.setImageResource(getResources().getIdentifier(cursor_physics.getString(5), 
					"drawable", "com.example.navigationdrawer"));
		}
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
