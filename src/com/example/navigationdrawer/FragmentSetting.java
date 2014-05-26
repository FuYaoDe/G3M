package com.example.navigationdrawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
 
public class FragmentSetting extends Fragment {
	List<Boolean> listShow; 
	List<String> list;
    ListView listview1;
    
    List<Boolean> listShow2; 
	List<String> list2;
    ListView listview2;
    ListView listview4;
    
    List<Boolean> listShow3; 
   	List<String> list3;
    ListView listview3;
    SharedPreferences settings;
    
    private static String[] checkText1= new String[]{"英文","數學","物理","笑話"};
    private static String[] checkText2= new String[]{"上床","起床","搭車","下課"};
    private static String[] checkText3= new String[]{"涵蓋未出現過的英文單字","涵蓋未出現過的數學公式","涵蓋未出現過的物理公式"};
    private static String[] TimeTitle = new String[]{"開始時段","結束時段"};
    private  int[] Time = new int[2];
       // 這個用來記錄哪幾個 item 是被打勾的
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}


	public static Fragment newInstance(Context context) {
    	FragmentStatistics f = new FragmentStatistics();
 
        return f;
    }
 
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	settings = this.getActivity().getSharedPreferences(Variable.SetName, 0);
    	
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_four, null);
        listview1 = (ListView) root.findViewById(R.id.listView1);
        listview1.setOnItemClickListener(new OnItemClickListener()
                                       {
                                            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                                            {
                                                 CheckedTextView chkItem = (CheckedTextView) v.findViewById(R.id.check1);
                                                 chkItem.setChecked(!chkItem.isChecked());
                                                 Toast.makeText(getActivity(), "您點選了第 "+(position+1)+" 項", Toast.LENGTH_SHORT).show();
                                                 //listShow.set(position, chkItem.isChecked());
                                                 settings.edit().putBoolean(Variable.SharedPreferencesText1[position], !(settings.getBoolean(Variable.SharedPreferencesText1[position], true))).commit();
                                            }
                                       }
                                      );
 
        listShow = new ArrayList<Boolean>();
        list = new ArrayList<String>();
        for(int x=0;x<4;x++)
        {
             list.add(checkText1[x]);
             //listShow.add(true);
             listShow.add(settings.getBoolean(Variable.SharedPreferencesText1[x], true));
        }
        ListAdapter adapterItem = new setting_ListAdapter(getActivity(), list,listShow);
        listview1.setAdapter(adapterItem);
        setListViewHeightBasedOnChildren(listview1);
        
        
        listview2 = (ListView) root.findViewById(R.id.listView2);
        listview2.setOnItemClickListener(new OnItemClickListener()
                                       {
                                            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                                            {
                                                 CheckedTextView chkItem = (CheckedTextView) v.findViewById(R.id.check1);
                                                 chkItem.setChecked(!chkItem.isChecked());
                                                 Toast.makeText(getActivity(), "您點選了第 "+(position+1)+" 項", Toast.LENGTH_SHORT).show();
                                                 //listShow2.set(position, chkItem.isChecked());
                                                 settings.edit().putBoolean(Variable.SharedPreferencesText2[position], !(settings.getBoolean(Variable.SharedPreferencesText2[position], true))).commit();
                                            }
                                       }
                                      );
 
        listShow2 = new ArrayList<Boolean>();
        list2 = new ArrayList<String>();
        for(int x=0;x<4;x++)
        {
             list2.add(checkText2[x]);
             //listShow2.add(true);
             listShow2.add(settings.getBoolean(Variable.SharedPreferencesText2[x], true));
        }
        ListAdapter adapterItem1 = new setting_ListAdapter(getActivity(), list2,listShow2);
        listview2.setAdapter(adapterItem1);
        setListViewHeightBasedOnChildren(listview2);
        listview4 = (ListView) root.findViewById(R.id.listView4);
        final int mHour =0;
        final int mMinute = 0;
        for(int i = 0 ;i<Time.length;i++)
        {
        	Time[i]=settings.getInt(Variable.SharedPreferencesTime[i], 0);
        }
        /** This handles the message send from TimePickerDialogFragment on setting Time */
        final Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message m){
                /** Creating a bundle object to pass currently set Time to the fragment */
                Bundle b = m.getData();
     
                /** Getting the Hour of day from bundle */
                int mHour = b.getInt("set_hour");
     
                /** Getting the Minute of the hour from bundle */
                int mMinute = b.getInt("set_minute");
     
                /** Displaying a short time message containing time set by Time picker dialog fragment */
                Toast.makeText(getActivity(), b.getString("set_time"), Toast.LENGTH_SHORT).show();
            }
        };
     
        listview4.setOnItemClickListener(new OnItemClickListener()
        {
             public void onItemClick(AdapterView<?> parent, View v, int position, long id)
             {
                  Toast.makeText(getActivity(), "您點選了第 "+(position+1)+" 項", Toast.LENGTH_SHORT).show();
                  //listShow2.set(position, chkItem.isChecked());
                  /** Creating a bundle object to pass currently set time to the fragment */
                  Bundle b = new Bundle();
                  
				/** Adding currently set hour to bundle object */
                  b.putInt("set_hour", mHour);
   
				/** Adding currently set minute to bundle object */
                  b.putInt("set_minute", mMinute);
                  
                  /** Instantiating TimePickerDialogFragment */
                  TimePickerDialogFragment timePicker = new TimePickerDialogFragment(mHandler);
                  
                  /** Setting the bundle object on timepicker fragment */
                  timePicker.setArguments(b);
   
                  /** Getting fragment manger for this activity */
                  FragmentManager fm = getFragmentManager();
   
                  /** Starting a fragment transaction */
                  FragmentTransaction ft = fm.beginTransaction();
   
                  /** Adding the fragment object to the fragment transaction */
                  ft.add(timePicker, "time_picker");
   
                  /** Opening the TimePicker fragment */
                  ft.commit();
                  
             }
        }
       );
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        for(int i=0; i<TimeTitle.length; i++){
        	 HashMap<String,String> item = new HashMap<String,String>();
        	 item.put( "title", TimeTitle[i]);
        	// item.put( "time",TimeTitle[i] );
        	 item.put("time",(Time[i]/60)+":"+String.format("%02d",(Time[i]%60)));
        	 list.add( item );
        	 }
        
        listview4.setAdapter(new SimpleAdapter( 
        		getActivity(),
        		list,
        		R.layout.time_setting_list,
        		new String[] { "title","time" },
        		new int[] { R.id.textView1, R.id.textView2 }
        		)
        	);
        setListViewHeightBasedOnChildren(listview4);
        
        listview3 = (ListView) root.findViewById(R.id.listView3);
        listview3.setOnItemClickListener(new OnItemClickListener()
                                       {
                                            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                                            {
                                                 CheckedTextView chkItem = (CheckedTextView) v.findViewById(R.id.check1);
                                                 chkItem.setChecked(!chkItem.isChecked());
                                                 Toast.makeText(getActivity(), "您點選了第 "+(position+1)+" 項", Toast.LENGTH_SHORT).show();
                                                // listShow3.set(position, chkItem.isChecked());
                                                 settings.edit().putBoolean(Variable.SharedPreferencesText3[position], !(settings.getBoolean(Variable.SharedPreferencesText3[position], true))).commit();
                                                 
                                            }
                                       }
                                      );
        
        listShow3 = new ArrayList<Boolean>();
        list3 = new ArrayList<String>();
        for(int x=0;x<3;x++)
        {
             list3.add(checkText3[x]);
             //listShow3.add(true);
             listShow3.add(settings.getBoolean(Variable.SharedPreferencesText3[x], true));
        }
        ListAdapter adapterItem2 = new setting_ListAdapter(getActivity(), list3,listShow3);
        listview3.setAdapter(adapterItem2);
        setListViewHeightBasedOnChildren(listview3);
        return root;
    }
    
    public void setListViewHeightBasedOnChildren(ListView listView) { 
        
        ListAdapter listAdapter = listView.getAdapter(); 
        if (listAdapter == null) { 
            return; 
        } 
 
        int totalHeight = 0; 
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { 
            
            View listItem = listAdapter.getView(i, null, listView); 
            
            listItem.measure(0, 0);  
            
            totalHeight += listItem.getMeasuredHeight();  
        } 
 
        ViewGroup.LayoutParams params = listView.getLayoutParams(); 
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1)); 
        listView.setLayoutParams(params); 
    } 
 
}