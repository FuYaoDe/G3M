package com.example.navigationdrawer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
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
    
    private static String[] checkText1= new String[]{"�^��","�ƾ�","���z","����"};
    private static String[] checkText2= new String[]{"�W��","�_��","�f��","�U��"};
    private static String[] checkText3= new String[]{"�[�\���X�{�L���^���r","�[�\���X�{�L���ƾǤ���","�[�\���X�{�L�����z����"};
       
       // �o�ӥΨӰO�����X�� item �O�Q���Ī�
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
                                                 Toast.makeText(getActivity(), "�z�I��F�� "+(position+1)+" ��", Toast.LENGTH_SHORT).show();
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
                                                 Toast.makeText(getActivity(), "�z�I��F�� "+(position+1)+" ��", Toast.LENGTH_SHORT).show();
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
        
        listview3 = (ListView) root.findViewById(R.id.listView3);
        listview3.setOnItemClickListener(new OnItemClickListener()
                                       {
                                            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                                            {
                                                 CheckedTextView chkItem = (CheckedTextView) v.findViewById(R.id.check1);
                                                 chkItem.setChecked(!chkItem.isChecked());
                                                 Toast.makeText(getActivity(), "�z�I��F�� "+(position+1)+" ��", Toast.LENGTH_SHORT).show();
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