package com.example.navigationdrawer;

import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class formula_detal  extends Activity {
    
	private MySQLite db = null;
	private TextView text;
	private ImageView img;
	private String Science_f = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formula_detal);
		ActionBar actionBar = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#032E5C"));     
		actionBar.setBackgroundDrawable(colorDrawable);
		actionBar.setDisplayHomeAsUpEnabled(true);
		Bundle Main1 =formula_detal.this.getIntent().getExtras();
		text=(TextView)findViewById(R.id.textView1);
		img=(ImageView)findViewById(R.id.imageView1);
		
		db = new MySQLite(this);
		db.OpenDB();
		Cursor cursor = db.science_get(db.maxID(Main1.getInt("SelectTab")+4)-Main1.getInt("Selectid"), Main1.getInt("SelectTab"));
		Science_f = cursor.getString(3);
				
		text.setText(cursor.getString(1));
		img.setImageResource(getResources().getIdentifier(Science_f, "drawable", "com.example.navigationdrawer"));
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