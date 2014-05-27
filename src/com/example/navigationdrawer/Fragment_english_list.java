package com.example.navigationdrawer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Fragment_english_list extends ListFragment
{
	
	private static final List<Item> items = new ArrayList<Item>();
	private static MySQLite db=null;
	private static int maxID;
	private static class Item
	{
		public final String line1;
		public final String line2;
		public final String line3;
		
		public Item(String line1, String line2,String line3)
		{
			this.line1 = line1;
			this.line2 = line2;
			this.line3 = line3;
		}
	}
	
	public static class ViewHolder 
	{
		public final TextView text1;
		public final TextView text2;
		public final TextView text3;
		public final Button voiceButon;
		
		
		public ViewHolder(TextView text1, TextView text2,TextView text3,
				Button voiceButton)
		{
			this.text1 = text1;
			this.text2 = text2;
			this.text3 = text3;
			this.voiceButon=voiceButton;
		}


	}
	public class ItemButton_Click implements OnClickListener {
	    private int position;
	     
	    ItemButton_Click(int pos) {
	        position = pos;
	    }
	 
	    public void onClick(View v) {
	        Log.i("按到按鈕",position+"");
	         SoundPool soundPool;
			 soundPool= new SoundPool(10,AudioManager.STREAM_SYSTEM,5);
			 soundPool.load(getActivity(),R.raw.test,1);
			 soundPool.play(1,1, 1, 0, 0, 1);
	    }
	}
	
	private class ItemAdapter extends ArrayAdapter<Item>
	{
		public ItemAdapter(Context context)
		{
			super(context, android.R.layout.simple_list_item_2, items);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View view = convertView;
			ViewHolder holder = null;
			
			if(view == null)
			{
				view = LayoutInflater.from(getContext()).inflate(R.layout.english_custom_list, parent, false);
				TextView text1 = (TextView)view.findViewById(R.id.ItemTitle);
				TextView text2 = (TextView)view.findViewById(R.id.EnText);
				TextView text3 = (TextView)view.findViewById(R.id.ChText);
				Button VoiceButton = (Button)view.findViewById(R.id.voice);
				holder = new ViewHolder(text1, text2,text3,VoiceButton);
				view.setTag(holder);
				
			}
			if(holder == null && view != null)
			{
				Object tag = view.getTag();
				if(tag instanceof ViewHolder)
				{
					holder = (ViewHolder)tag;
				}
			}
			
			holder.voiceButon.setOnClickListener(new ItemButton_Click( position));
			
			Item item = getItem(position);

			if(item != null && holder != null)
			{
				holder.text1.setText(item.line1);
				holder.text2.setText(item.line2);
				holder.text3.setText(item.line3);
			}
			return view;
		}
	}
	static
	{

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		
		return inflater.inflate(R.layout.listview, container, false);
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		db =new MySQLite(getActivity()); 
		db.OpenDB();
		maxID = db.maxID(1);
		String[] word = new String[maxID];
		String[] en =  new String[maxID];
		String[] ch =  new String[maxID];
		items.clear();
		for(int i = 0 ; i<maxID ; i++){
			Cursor cursor = db.eng_get(i+1);
    	 	word[i]=cursor.getString(1)+"\n"+cursor.getString(3);
    	 	en[i]=cursor.getString(5);
    	 	ch[i]=cursor.getString(4);
    	 	items.add(new Item(word[i] , en[i] , ch[i]));
		}
		
		ListAdapter adapter = new ItemAdapter(getActivity());
		setListAdapter(adapter);
	}
	 @Override
	 public void onDestroy(){
	    	super.onDestroy();
	    	db.CloseDB(); // 關閉資料庫
	  }
	public void onListItemClick (ListView l, View v, int position, long id){
		super.onListItemClick(l, v, position, id);
		Log.i("按到list",position+"");
		 Intent intent = new Intent(); 
  		 intent.setClass(getActivity(),english_detal.class);
  		 Bundle bundle = new Bundle();
         bundle.putInt("SelectTab",getArguments().getInt("position"));
         bundle.putInt("Selectid",position);   
         intent.putExtras(bundle);
  		 startActivity(intent);
		//Toast.makeText(getActivity(),"點選位置"+ position+"\n id:"+id+"\n View:"+v+"\n 當前頁面:"+getArguments().getInt("position"),Toast.LENGTH_SHORT).show();
			
	}
}
