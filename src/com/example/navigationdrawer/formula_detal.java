package com.example.navigationdrawer;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class formula_detal  extends Activity {

	private TextView text;
	private ImageView img;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formula_detal);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		Bundle Main1 =formula_detal.this.getIntent().getExtras();
		text=(TextView)findViewById(R.id.textView1);
		img=(ImageView)findViewById(R.id.imageView1);
		text.setText("使用者點了第:"+Main1.getInt("SelectTab")+"分頁的第"+Main1.getInt("Selectid")+"個list物件");
		img.setImageResource(R.drawable.c);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
		this.finish();
		default:
		return super.onOptionsItemSelected(item);
		}
	}


}
