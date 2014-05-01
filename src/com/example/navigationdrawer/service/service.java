package com.example.navigationdrawer.service;

import java.util.Date;
import java.util.List;

import com.example.navigationdrawer.Variable;

import android.app.ActivityManager;
import android.app.Service;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class service extends Service implements GPSCallback{
//	�O�o���U<service android:name=".ExampleService"></service>
//	<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"></uses-permission>
//    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"></uses-permission>
	 private GPSManager gpsManager = new GPSManager();
	 private double speed = 0.0;
	 private boolean isSpeedGet=false;
	 private boolean startGPS=false;
	 private int AppearedAppID;  //�����x���O�_�X�{�L
	 private Handler handler = new Handler();
	 private ActivityManager a ;
	 SharedPreferences settings ;
	 private ServiceSetting Set;
	 private Runnable showTime = new Runnable() {
	  public void run() {
	   //Toast.makeText(getApplicationContext(),"time:"+new Date().toString(),Toast.LENGTH_SHORT).show();
	   List<RunningTaskInfo> info2 = a.getRunningTasks(3);
	   if(info2.get(0).baseActivity.toShortString().indexOf("clock")!=-1 && info2.get(0).id!=AppearedAppID)
	   {
		   Set.ChoseNotification();
		   AppearedAppID=info2.get(0).id;
		 //Toast.makeText(getApplicationContext(), "�o�{�x��APP ID:"+info2.get(0).id, Toast.LENGTH_SHORT).show();
	   }
	   handler.postDelayed(this, 5000);
	  }
	 };
	 
	 private Runnable Speed= new Runnable() {
		
		@Override
		public void run() {
			if(isSpeedGet)
			{
				Toast.makeText(getApplicationContext(),"��e�t��:"+speed,Toast.LENGTH_SHORT).show();
				if(speed>=55)
				{
					//Toast.makeText(getApplication(),"�o�{�f����q�u��",Toast.LENGTH_LONG).show();
					Set.ChoseNotification();
				}
				gpsManager.stopListening();
			    gpsManager.setGPSCallback(null);
			    isSpeedGet=false;
			    startGPS=false;
			    handler.postDelayed(this,30000);
			}else
			{
				if(startGPS)
				{
					handler.postDelayed(this,1000);
				}
				else
				{
					gpsManager.startListening(getApplicationContext());
					gpsManager.setGPSCallback(service.this);
					startGPS=true;
					handler.postDelayed(this,1000);
				}
			}
		}
	};
	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	 public void onCreate() {
	  // TODO Auto-generated method stub
	  super.onCreate();
	  Log.i("�A��", "�إ�");
	 }
	 
	 @Override
	 public void onDestroy() {
	  // TODO Auto-generated method stub
	  super.onDestroy();
	  Log.i("�A��", "�P��");
	  handler.removeCallbacks(showTime);
	  handler.removeCallbacks(Speed);
	  if(gpsManager!=null)
	  {
		  gpsManager.stopListening();
	      gpsManager.setGPSCallback(null);
	  }
	 }
	 
	 @Override
	 public int onStartCommand(Intent intent, int flags, int startId) {
	  // TODO Auto-generated method stub
	  Log.i("�A��", "����");
	  settings = getSharedPreferences(Variable.SetName, 0);
	  
	  Set = new ServiceSetting(settings,getApplicationContext());
	  a = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
	  handler.postDelayed(showTime, 5000);
	  if(Set.getTimeEvent(3))   //�P�_�ϥΪ̦��S���n�ηf���P�_
		  handler.postDelayed(Speed, 5000);
	  return super.onStartCommand(intent, flags, startId);
	 }

	@Override
	public void onGPSUpdate(Location location) {
		// TODO Auto-generated method stub
		location.getLatitude();
        location.getLongitude();
        speed = location.getSpeed()*3.6;   //*3.6  m/s �� km/h
        isSpeedGet=location.hasSpeed();
	}
}
