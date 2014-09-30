package com.example.navigationdrawer;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class english_detal extends Activity {
	Cursor cursor;
	private MySQLite db=null;
	private TextView word;
	private TextView kk;
	private TextView tra;
	private ListView listView1;
	private SoundPool mSoundPool;
	private ImageButton mImageButton;
	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
	 private SimpleAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.english_detal);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		// ActionBar 顏色
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#032E5C"));     
        actionBar.setBackgroundDrawable(colorDrawable);
		word = (TextView)findViewById(R.id.word);
		kk=(TextView)findViewById(R.id.kk);
		tra=(TextView)findViewById(R.id.tra);
		listView1=(ListView)findViewById(R.id.listView1);
		mImageButton = (ImageButton)findViewById(R.id.imageButton1);
		mImageButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO 自動產生的方法 Stub
				mSoundPool = new SoundPool(10,AudioManager.STREAM_MUSIC,5);
				
				if(getResources().getIdentifier(cursor.getString(1), "raw", "com.example.navigationdrawer")!= 0){
					mSoundPool.load(v.getContext(), getResources().getIdentifier(cursor.getString(1), "raw", "com.example.navigationdrawer"), 1);
				}else{
					mSoundPool.load(v.getContext(), R.raw.test, 1);
				}
				mSoundPool.setOnLoadCompleteListener(new OnLoadCompleteListener(){
					 @Override
					 public void onLoadComplete(SoundPool arg0, int arg1, int arg2) {
						 mSoundPool.play(1, 1, 1, 0, 0, 1.0f);
				 }});
			}
		});
			
	
		db =new MySQLite(this); 
		db.OpenDB();
		
		Bundle Main1 =english_detal.this.getIntent().getExtras();
		//Cursor old_cursor = db.eng_get(Main1.getInt("Selectid"), 2);
		cursor = db.eng_get(Main1.getInt("Selectid"), 1);
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