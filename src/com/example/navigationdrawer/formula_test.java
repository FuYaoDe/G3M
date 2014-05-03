package com.example.navigationdrawer;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class formula_test extends Fragment
{		
	private static int PagNum = 0;
	private MySQLite db = null;
	private Cursor cursor_math = null, cursor_physics = null;
	private testCOUNT test_count= null;
	private int math = 0;
	private String physics = null;
	private TextView test_name ;
	private ImageView test_quition, test_pam, answer_img;
	private Button enter_word, next, answer_btn;
	private EditText word;
	private String math_client_a = null;
	private String physics_client_a = null;
	private String client_a = null;
	
	
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
		answer_img = (ImageView)view.findViewById(R.id.imageView3);
		enter_word = (Button)view.findViewById(R.id.button1);
		next = (Button)view.findViewById(R.id.button2);
		answer_btn = (Button)view.findViewById(R.id.button3);
		word = (EditText)view.findViewById(R.id.editText1);
		enter_word.setOnClickListener(btnDoClick);
		next.setOnClickListener(btnDoClick);
		answer_btn.setOnClickListener(btnDoClick);
		
		answer_img.setVisibility(View.GONE);

		PagNum = getArguments().getInt("position");
		db = new MySQLite(getActivity());
		db.OpenDB();
		test_count = new testCOUNT(db.maxID(PagNum+1));
		
		if(PagNum == 1){//PagNum == 1 ,math; else physics;
			math_get();
		}
		else{
			physics_get();
		}
		return view;
	}
	private Button.OnClickListener btnDoClick = new Button.OnClickListener(){
		public void onClick(View v){
			PagNum = getArguments().getInt("position");
			client_a =  word.getText().toString();
			switch(v.getId()){
				case R.id.button1:	
						if(PagNum == 1){//PagNum == 1 ,math; else physics;
							if(math_client_a.equals(client_a)){
								word.setText("");
								math_get();
								Toast toast = Toast.makeText(getActivity(), "答案正確!", Toast.LENGTH_LONG);
								toast.show();
							}	
							else{
								Toast toast = Toast.makeText(getActivity(), "答案錯誤!", Toast.LENGTH_LONG);
								toast.show();
							}
						}
						else{
							if(physics_client_a.equals(client_a) ){
								word.setText("");
								physics_get();
								Toast toast = Toast.makeText(getActivity(), "答案正確!", Toast.LENGTH_LONG);
								toast.show();
							}
							else{
								Toast toast = Toast.makeText(getActivity(), "答案錯誤!", Toast.LENGTH_LONG);
								toast.show();
							}
						}
					break;
				case R.id.button2:
					word.setText("");
					next.setText("跳過");
					enter_word.setEnabled(true);
					answer_btn.setEnabled(true);
					word.setInputType(InputType.TYPE_CLASS_TEXT);
					answer_img.setImageDrawable(null); 
					if(PagNum == 1){//PagNum == 1 ,math; else physics;
						math_get();
					}
					else{
						physics_get();
					}
					break;
				case R.id.button3:
					word.setText("請按下一題!");
					next.setText("下一題");
					enter_word.setEnabled(false);
					answer_btn.setEnabled(false);
					word.setInputType(InputType.TYPE_NULL);	
					if(PagNum == 1){//PagNum == 1 ,math; else physics;
						answer_img.setImageResource(getResources().getIdentifier(cursor_math.getString(6), 
								"drawable", "com.example.navigationdrawer"));
					}
					else{
						answer_img.setImageResource(getResources().getIdentifier(cursor_physics.getString(6), 
								"drawable", "com.example.navigationdrawer"));
					}
					answer_img.setVisibility(View.VISIBLE);
					break;
			}
		}
	};
	public void math_get(){
		cursor_math = db.science_get(test_count.test_id(), PagNum);
		math_client_a = cursor_math.getString(7);
		test_name.setText(cursor_math.getString(1));
		test_quition.setImageResource(getResources().getIdentifier(cursor_math.getString(4), 
			"drawable", "com.example.navigationdrawer"));
		test_pam.setImageResource(getResources().getIdentifier(cursor_math.getString(5), 
			"drawable", "com.example.navigationdrawer"));
	}
	public void physics_get(){
		cursor_physics = db.science_get(test_count.test_id(), PagNum);
		physics_client_a = cursor_physics.getString(7);
		test_name.setText(cursor_physics.getString(1));
		test_quition.setImageResource(getResources().getIdentifier(cursor_physics.getString(4), 
				"drawable", "com.example.navigationdrawer"));
		test_pam.setImageResource(getResources().getIdentifier(cursor_physics.getString(5), 
				"drawable", "com.example.navigationdrawer"));
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
