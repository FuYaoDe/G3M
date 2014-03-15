package com.example.navigationdrawer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
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
    
    List<Boolean> listShow3; 
   	List<String> list3;
    ListView listview3;
    
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
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_four, null);
        listview1 = (ListView) root.findViewById(R.id.listView1);
        listview1.setOnItemClickListener(new OnItemClickListener()
                                       {
                                            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                                            {
                                                 CheckedTextView chkItem = (CheckedTextView) v.findViewById(R.id.check1);
                                                 chkItem.setChecked(!chkItem.isChecked());
                                                 Toast.makeText(getActivity(), "�z�I��F�� "+(position+1)+" ��", Toast.LENGTH_SHORT).show();
                                                 listShow.set(position, chkItem.isChecked());
                                            }
                                       }
                                      );
 
        listShow = new ArrayList<Boolean>();
        list = new ArrayList<String>();
        for(int x=0;x<4;x++)
        {
             list.add(checkText1[x]);
             listShow.add(true);
        }
        ListAdapter adapterItem = new setting_ListAdapter(getActivity(), list);
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
                                                 listShow2.set(position, chkItem.isChecked());
                                            }
                                       }
                                      );
 
        listShow2 = new ArrayList<Boolean>();
        list2 = new ArrayList<String>();
        for(int x=0;x<4;x++)
        {
             list2.add(checkText2[x]);
             listShow2.add(true);
        }
        ListAdapter adapterItem1 = new setting_ListAdapter(getActivity(), list2);
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
                                                 listShow3.set(position, chkItem.isChecked());
                                            }
                                       }
                                      );
 
        listShow3 = new ArrayList<Boolean>();
        list3 = new ArrayList<String>();
        for(int x=0;x<3;x++)
        {
             list3.add(checkText3[x]);
             listShow3.add(true);
        }
        ListAdapter adapterItem2 = new setting_ListAdapter(getActivity(), list3);
        listview3.setAdapter(adapterItem2);
        setListViewHeightBasedOnChildren(listview3);
        return root;
    }
    
    public void setListViewHeightBasedOnChildren(ListView listView) { 
        // ���ListView������Adapter 
        ListAdapter listAdapter = listView.getAdapter(); 
        if (listAdapter == null) { 
            return; 
        } 
 
        int totalHeight = 0; 
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { 
            // listAdapter.getCount()��^�ƾڶ����ƥ� 
            View listItem = listAdapter.getView(i, null, listView); 
            // �p��J�Ѫ�View����
            listItem.measure(0, 0);  
            // �έp�Ҧ��J�Ѫ��`����
            totalHeight += listItem.getMeasuredHeight();  
        } 
 
        ViewGroup.LayoutParams params = listView.getLayoutParams(); 
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1)); 
        // listView.getDividerHeight()����l�������j�Ŧ��Ϊ����� 
        // params.height�̫�o����ListView������ܻݭn������
        listView.setLayoutParams(params); 
    } 
 
}