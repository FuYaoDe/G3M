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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_formula_list extends ListFragment
{
	private static final List<Item> math_items = new ArrayList<Item>();
	private static final List<Item> Physical_items = new ArrayList<Item>();
	private static int PagNum=0;
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
	
	static
	{
		//�b�o�̷s�W��r
		//line1�����W��
		//line2 ��������Ϥ�
		math_items.add(new Item("n���żƵ��t����", R.drawable.a));
		math_items.add(new Item("n���żƵ��񤽦�", R.drawable.b));
		Physical_items.add(new Item("�o�O���z����1�U���ϥܴ���", R.drawable.a));
		Physical_items.add(new Item("�o�O���z����2�U���ϥܴ���", R.drawable.b));
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
		if(PagNum==1)
		{
			ListAdapter adapter = new ItemAdapter(getActivity(),math_items);
			setListAdapter(adapter);
		}
		else
		{
			ListAdapter adapter = new ItemAdapter(getActivity(),Physical_items);
			setListAdapter(adapter);
		}
	}
	public void onListItemClick (ListView l, View v, int position, long id){
		super.onListItemClick(l, v, position, id);
		
		Toast.makeText(getActivity(),"�I���m"+ position+"\n id:"+id+"\n View:"+v+"\n ��e����:"+getArguments().getInt("position"),Toast.LENGTH_SHORT).show();
			
	}
}

