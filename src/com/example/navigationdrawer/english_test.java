package com.example.navigationdrawer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class english_test extends Fragment
{		
	private MySQLite db = null;
	private testCOUNT test_count = null;
	private int maxID = 0;
	private String testWord = null;
	private EditText word;
	private TextView test_word,prompt_word;
	private Button enter_word, next, answer, detailed;
	
	public english_test()
	{
		super();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.english_test, container, false);
		View text = view.findViewById(android.R.id.text1);
		test_word = (TextView)view.findViewById(R.id.textView3);
		prompt_word = (TextView)view.findViewById(R.id.textView4);
		word = (EditText)view.findViewById(R.id.editText1);
		enter_word = (Button)view.findViewById(R.id.button1);
		detailed = (Button)view.findViewById(R.id.button2);
		next =  (Button)view.findViewById(R.id.button3);
		answer =  (Button)view.findViewById(R.id.button4);
		enter_word.setOnClickListener(btnDoClick);
		detailed.setOnClickListener(btnDoClick);
		next.setOnClickListener(btnDoClick);
		answer.setOnClickListener(btnDoClick);
		
		detailed.setEnabled(false);
		
		db = new MySQLite(getActivity());
		db.OpenDB();
		maxID = db.maxID(4);
		test_count = new testCOUNT(maxID);
		eng_get();
		
		return view;
	}
	private Button.OnClickListener btnDoClick = new Button.OnClickListener(){
		public void onClick(View v){
			String userWord = word.getText().toString();
			userWord.toUpperCase(); /*update*/
			testWord.toUpperCase();	/*update*/
			switch (v.getId()){
				case R.id.button1:
					if(userWord.equals(testWord)){
						word.setText("");
						eng_get();
						detailed.setEnabled(false);
						Toast toast = Toast.makeText(getActivity(), "答案正確!", Toast.LENGTH_LONG);
						toast.show();
					}
					else{
						Toast toast = Toast.makeText(getActivity(), "答案錯誤!", Toast.LENGTH_LONG);
						toast.show();
					}
				break;
				case R.id.button2:
					 Intent intent = new Intent(); 
			  		 intent.setClass(getActivity(),english_detal.class);
			  		 Bundle bundle = new Bundle();
			         bundle.putInt("SelectTab",0);  //把當前點擊的節目代號傳過去
			         bundle.putInt("Selectid",test_count.now_id-1);   //把節目名稱傳過去
			         intent.putExtras(bundle);
			  		 startActivity(intent);
			  		 break;
				case R.id.button3:
					next.setText("跳過");
					word.setText("");
					detailed.setEnabled(false);
					answer.setEnabled(true);
					enter_word.setEnabled(true);
					word.setInputType(InputType.TYPE_CLASS_TEXT);
					eng_get();
					//word.setInputType(InputType.TYPE_NULL);
					break;
				case R.id.button4:
					test_word.setText(testWord);
					word.setText("請按下一題!");
					next.setText("下一題");
					detailed.setEnabled(true);
					enter_word.setEnabled(false);
					answer.setEnabled(false);
					word.setInputType(InputType.TYPE_NULL);
					break;
			}
		}
	};
	public void eng_get(){
		Cursor old_cursor = db.eng_get(test_count.test_id(), 2);
		Cursor cursor = db.eng_get(old_cursor.getInt(1), 1);
		testWord = cursor.getString(1);
		test_word.setText(test_Word(testWord));
		prompt_word.setText(cursor.getString(3));
	}
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
	}
	public String test_Word(String testWord){
		int[] test_num = new int[testWord.length()];
		int num = testWord.length()/2;
		String pt = testWord;
		for(int i=0 ; i<test_num.length ; i++){
			test_num[i]=i+1;
		}
		for(int i=0 ; i<test_num.length ; i++) { 
            int j = (int) (Math.random() * test_num.length);
            int tmp = test_num[i]; 
            test_num[i] = test_num[j]; 
            test_num[j] = tmp; 
        } 
		for(int c=0 ; c < num ; c++){
			pt = pt.substring(0 , test_num[c]-1)+"?"+ pt.substring(test_num[c], pt.length()); 
			}
		return pt;
	}
	@Override
	public void onDetach()
	{
		super.onDetach();
	}
}