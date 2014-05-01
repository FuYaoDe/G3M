package com.example.navigationdrawer.service;

import java.util.ArrayList;

import com.example.navigationdrawer.Variable;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ServiceSetting {

	SharedPreferences settings;
	Context context;
	//private static int[] NotificationSetting ;
	ArrayList<Integer> NotificationSetting =new ArrayList<Integer>();
	private Intent mServiceIntent;
	ServiceSetting(SharedPreferences settings,Context context){
		this.settings = settings;
		this.context=context;
		//獲取設定 抓有勾起那些資料庫
		//NotificationSetting = new int[Variable.SharedPreferencesText1.length];
		
		for (int i = 0,j=0; i < Variable.SharedPreferencesText1.length; i++) {
			if(this.settings.getBoolean(Variable.SharedPreferencesText1[i], true))
				NotificationSetting.add(i);
		}
	}
	
	public void ChoseNotification(){
		//Toast.makeText(context,""+settings.getBoolean(Variable.SharedPreferencesText1[2], true), Toast.LENGTH_SHORT).show();
		int a =NotificationSetting.get((int)(Math.random()*NotificationSetting.size()));
		switch (a) {
		case 0:
			EnNotification();
			Log.d("選到英文","!"+a);
			break;
		case 1:
			FormulaNotification(1);
			Log.d("選到數學","!"+a);
			break;
		case 2:
			FormulaNotification(2);
			Log.d("選到物理","!"+a);
			break;
		case 3:
			//JockNotification();
			Log.d("選到笑話","!"+a);
			break;
		}
	}
	
//	public void test(){
//		settings.edit().putBoolean(Variable.SharedPreferencesText1[2], false).commit();
//	}
	
	
	public void EnNotification(){
		mServiceIntent = new Intent(context, Notification.class);
		mServiceIntent.setAction(Variable.English);
		context.startService(mServiceIntent);
	}
	
	public void FormulaNotification(int a){
		mServiceIntent = new Intent(context, Notification.class);
		mServiceIntent.setAction(Variable.Formula);
		Bundle bundle = new Bundle();
		bundle.putInt("class", a);
		mServiceIntent.putExtras(bundle);
		context.startService(mServiceIntent);
	}
	
	//0"GoBed", 1"GetUp", 2"UsTran", 3"Class"
	public boolean getTimeEvent(int a)
	{
		return settings.getBoolean(Variable.SharedPreferencesText2[a], true);
	}
	

}
