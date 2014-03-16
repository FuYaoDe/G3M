package com.example.navigationdrawer;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_formula_list extends ListFragment
{
	private static final List<Item> math_items = new ArrayList<Item>();
	private static final List<Item> Physical_items = new ArrayList<Item>();
	private static int PagNum=0;
	private MySQLite db=null;
	
	private static class Item
	{
		public final String line1;
		public final int line2;
		
		public Item(String line1, int line2)
		{
			this.line1 = line1;
			this.line2 = line2;
		}
	}
	
	public static class ViewHolder 
	{
		public final TextView text1;
		public final ImageView Image;
		
		
		public ViewHolder(TextView text1,ImageView Image)
		{
			this.text1 = text1;
			this.Image = Image;
		}
	}
	
	private class ItemAdapter extends ArrayAdapter<Item>
	{

		public ItemAdapter(Context context,List<Item> item)
		{
			
			super(context, android.R.layout.simple_list_item_2, item);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View view = convertView;
			ViewHolder holder = null;
			if(view == null)
			{
				view = LayoutInflater.from(getContext()).inflate(R.layout.formula_custom_list, parent, false);
				TextView text1 = (TextView)view.findViewById(R.id.ItemTitle);
				ImageView Image = (ImageView)view.findViewById(R.id.formula);
				view.setTag(new ViewHolder(text1, Image));
			}
			if(holder == null && view != null)
			{
				Object tag = view.getTag();
				if(tag instanceof ViewHolder)
				{
					holder = (ViewHolder)tag;
				}
			}
			Item item = getItem(position);
			if(item != null && holder != null)
			{
				holder.text1.setText(item.line1);
				holder.Image.setImageResource(item.line2);
			}
			return view;
		}
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		PagNum=getArguments().getInt("position");
		return inflater.inflate(R.layout.listview, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		db =new MySQLite(getActivity()); 
		db.OpenDB();
		
		int maxID = db.maxID(PagNum+1);
		String[] mathName = new String[maxID];
		String[] mathImag = new String[maxID];
		String[] physcisName = new String[maxID];
		String[] physcisImag = new String[maxID];

		
		if(PagNum==1)
		{
			math_items.clear();
			for(int i = 0 ; i<maxID ; i++){
				Cursor cursor = db.science_get(i+1,PagNum);
				mathName[i] =cursor.getString(1);
				mathImag[i] =cursor.getString(2);
				math_items.add(new Item(mathName[i], getResources().getIdentifier(mathImag[i], "drawable", "com.example.navigationdrawer")));
			}
			ListAdapter adapter = new ItemAdapter(getActivity(),math_items);
			setListAdapter(adapter);
		}
		else
		{
			Physical_items.clear();
			for(int i = 0 ; i<maxID ; i++){
				Cursor cursor = db.science_get(i+1,PagNum);
				physcisName[i] =cursor.getString(1);
				physcisImag[i] =cursor.getString(2);
				Physical_items.add(new Item(physcisName[i], getResources().getIdentifier(physcisImag[i], "drawable", "com.example.navigationdrawer")));
			}
			ListAdapter adapter = new ItemAdapter(getActivity(),Physical_items);
			setListAdapter(adapter);
		}
	}
	public void onListItemClick (ListView l, View v, int position, long id){
		super.onListItemClick(l, v, position, id);
		
		Intent intent = new Intent(); 
 		intent.setClass(getActivity(),formula_detal.class);
 		Bundle bundle = new Bundle();
		bundle.putInt("SelectTab",getArguments().getInt("position"));  //把當前點擊的節目代號傳過去
        bundle.putInt("Selectid",position);   //把節目名稱傳過去
        intent.putExtras(bundle);
 		startActivity(intent);
		//Toast.makeText(getActivity(),"點選位置"+ position+"\n id:"+id+"\n View:"+v+"\n 當前頁面:"+getArguments().getInt("position"),Toast.LENGTH_SHORT).show();
			
	}
}
