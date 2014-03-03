package com.example.navigationdrawer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_english_list extends ListFragment
{
	private static final List<Item> items = new ArrayList<Item>();
	
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
		
		
		public ViewHolder(TextView text1, TextView text2,TextView text3)
		{
			this.text1 = text1;
			this.text2 = text2;
			this.text3 = text3;
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
				view.setTag(new ViewHolder(text1, text2,text3));
				
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
				holder.text2.setText(item.line2);
				holder.text3.setText(item.line3);
			}
			return view;
		}
	}
	
	static
	{
		//�b�o�̷s�W��r
		//line1 ��r+����
		//line2 �^��y
		//line3����y
		items.add(new Item("appointment  n.���|�F���w", "If you phoned my secretary she'd give you an appointment.","�A���گ��ѥ��ӹq��, �o�N�|���A���w�Ӯɶ��C"));
		items.add(new Item("calendar  n.���,��k", "He put the desk calendar on the shelf.","�L���i���b�Ѭ[�W�C"));
		items.add(new Item("recruit  n.�s�L,�s���l,�s�|�� vt.�ϫ�_,�ɥR,�x�� vi.�x�ҷs�L,�_��", "What is the postage on this parcel?","�H�o�ӥ]�q�n�h�ֿ�?"));
		items.add(new Item("calculator  n.�q��,�p�⾹", "This pocket calculator needs two batteries.","�o�ӳS�íp�⾹�ݥΨ�`���q���C"));
		items.add(new Item("secretary  n.����,�ѰO,����,�j��", "She sued for divorce on the grounds of her husband's alleged misconduct with his secretary.","�o�H��һP���Ѧ��V���Ѵ��_���B�D�^�C"));
		
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
	
	public void onListItemClick (ListView l, View v, int position, long id){
		super.onListItemClick(l, v, position, id);
		
		Toast.makeText(getActivity(),"�I���m"+ position+"\n id:"+id+"\n View:"+v+"\n ��e����:"+getArguments().getInt("position"),Toast.LENGTH_SHORT).show();
			
	}
}

