package com.example.navigationdrawer;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class english_detal extends Activity {

	private TextView word;
	private TextView kk;
	private TextView tra;
	private ListView listView1;
	private String[] En=new String[] {"�^�^�y1�^�^�y1�^�^�y1�^�^�y1�^�^�y1�^�^�y1",
			"�^�^�y2�^�^�y2�^�^�y2�^�^�y2�^�^�y2�^�^�y2",
			"�^�^�y3�^�^�y3�^�^�y3�^�^�y3�^�^�y3�^�^�y3",
			"�^�^�y4�^�^�y4�^�^�y4�^�^�y4�^�^4�^�^�y4",
			"�^�^�y5�^�^�y5�^�^�y5�^�^�y5�^�^�y5�^�^�y5",};
	private String[] Ch=new String[] {"�^���y1�^���y1�^���y1�^���y1�^���y1�^���y1",
			"�^���y2�^���y2�^���y2�^���y2�^���y2�^���y2",
			"�^���y3�^���y3�^���y3�^���y3�^���y3�^���y3",
			"�^���y4�^���y4�^���y4�^���y4�^��4�^���y4",
			"�^���y5�^���y5�^���y5�^���y5�^���y5�^���y5",};
	
	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
	 private SimpleAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.english_detal);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		word = (TextView)findViewById(R.id.word);
		kk=(TextView)findViewById(R.id.kk);
		tra=(TextView)findViewById(R.id.tra);
		listView1=(ListView)findViewById(R.id.listView1);
		Bundle Main1 =english_detal.this.getIntent().getExtras();
		word.setText("�ϥΪ��I�F��:"+Main1.getInt("SelectTab")+"��������"+Main1.getInt("Selectid")+"��list����");
		kk.setText("KK����");
		tra.setText("½Ķ");
		
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
