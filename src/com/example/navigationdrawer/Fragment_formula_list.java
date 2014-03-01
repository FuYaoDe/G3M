package com.example.navigationdrawer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class Fragment_formula_list extends ListFragment
{
	private static final List<Item> items = new ArrayList<Item>();
	private static int a;
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
	
	static
	{
		//在這裡新增單字
		//line1公式名稱
		//line2 公式關鍵圖片
		items.add(new Item("n項級數等差公式", R.drawable.a));
		items.add(new Item("n項級數比差公式", R.drawable.b));
			
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
		ListAdapter adapter = new ItemAdapter(getActivity());
		setListAdapter(adapter);
	}
}

