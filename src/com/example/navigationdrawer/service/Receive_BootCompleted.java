package com.example.navigationdrawer.service;

import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.util.Log;
  
public class Receive_BootCompleted extends BroadcastReceiver{
	//註冊開機自動啟動<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"></uses-permission>
     @Override
     public void onReceive(Context context, Intent intent) {
        //we double check here for only boot complete event
        if(intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED))
        {
           //here we start the service            
        	Log.d("開機自動啟動service","OK!!");
           Intent serviceIntent = new Intent(context, service.class);
           context.startService(serviceIntent);
       }
    }
}
