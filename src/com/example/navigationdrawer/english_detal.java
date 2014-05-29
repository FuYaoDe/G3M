package com.example.navigationdrawer;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class english_detal extends Activity {

	private MySQLite db=null;
	private TextView word;
	private TextView kk;
	private TextView tra;
	private ListView listView1;
	
	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
	 private SimpleAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.english_detal);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		// ActionBar ÃC¦â
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#032E5C"));     
        actionBar.setBackgroundDrawable(colorDrawable);
		word = (TextView)findViewById(R.id.word);
		kk=(TextView)findViewById(R.id.kk);
		tra=(TextView)findViewById(R.id.tra);
		listView1=(ListView)findViewById(R.id.listView1);
		
		db =new MySQLite(this); 
		db.OpenDB();
		
		Bundle Main1 =english_detal.this.getIntent().getExtras();
		Cursor cursor = db.eng_get(db.maxID(4)-Main1.getInt("Selectid"), 2); /*update*/
		String[] En=new String[] {cursor.getString(5), cursor.getString(7), cursor.getString(9), cursor.getString(11), cursor.getString(13)};
		String[] Ch=new String[] {cursor.getString(4), cursor.getString(6), cursor.getString(8), cursor.getString(10), cursor.getString(12)};
		String ch = cursor.getString(3);
		ch = ch.replace(" ","\n");
		word.setText(cursor.getString(1));
		kk.setText(cursor.getString(2));
		tra.setText(ch);
		
		for(int a=0;a<5;a++)
		{
			HashMap<String,String> item = new HashMap<String,String>();
			item.put("En",En[a]);
			item.put("Ch", Ch[a]);
			list.add(item);
		}
		adapter = new SimpleAdapter( 
				 this, 
				 list,
				 android.R.layout.simple_list_item_2,
				 new String[] { "En","Ch" },
				 new int[] { android.R.id.text1, android.R.id.text2 } );
		listView1.setAdapter( adapter );
		
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