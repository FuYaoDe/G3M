package com.example.navigationdrawer.service;

import com.example.navigationdrawer.MainActivity;
import com.example.navigationdrawer.MySQLite;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.Variable;
import com.example.navigationdrawer.english_detal;
import com.example.navigationdrawer.formula_detal;
import com.example.navigationdrawer.testCOUNT;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

public class Notification extends IntentService{
	
	
	SoundPool soundPool;
	public Notification() {
		super("com.example.navigationdrawer.service");
		
	}
	private NotificationManager mNotificationManager;
	private NotificationCompat.Builder builder;
	private Bundle getClass ;
	private MySQLite db = null;
	private Cursor cursor_eng = null, cursor_math = null, cursor_physics = null;
	private testCOUNT eng_count = null, math_count = null, physics_count = null;
	private String formula = null;
	private String kind = null;
	private Bitmap bmp = null;
	
	private void En_Notification(){
		
		mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		db = new MySQLite(this);
		db.OpenDB();
		eng_count = new testCOUNT(db.maxID(1));
	    cursor_eng = db.eng_get(eng_count.test_id(), 1);
	    if(cursor_eng.getInt(14)!=1){
	        	db.Update(1, "eng_table", eng_count.now_id);
	        	db.append(eng_count.now_id, "old_eng_table");
	    }
	    
		Intent againIntent = new Intent(this, Notification.class);
		againIntent.setAction(Variable.English);
        PendingIntent again = PendingIntent.getService(this, 0, againIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        
        Intent detailIntent = new Intent(this,english_detal.class); 
 		Bundle bundle = new Bundle();
        bundle.putInt("SelectTab",0);
        bundle.putInt("Selectid",eng_count.now_id);
        bundle.putBoolean("call", true);
        Log.i("按到按鈕",eng_count.now_id+"");
        detailIntent.putExtras(bundle);
        detailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);   //清除已經推撥的OR新增推播
        // Because clicking the notification opens a new ("special") activity, there's
        // no need to create an artificial back stack.
        PendingIntent detail = PendingIntent.getActivity(
                this,
                1,
                detailIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );
        
        Intent voiceIntent = new Intent(this, Notification.class);
        voiceIntent.setAction("voice");
        Bundle voicebundle = new Bundle();
	    if(getResources().getIdentifier(cursor_eng.getString(1), "raw", "com.example.navigationdrawer")!= 0){
	    	voicebundle.putString("VoiceName", cursor_eng.getString(1));
	    }else{
	    	voicebundle.putString("VoiceName", "test");
		}
	    voiceIntent.putExtras(voicebundle);
        PendingIntent voice = PendingIntent.getService(this, 0, voiceIntent,0);
        
        Intent dismissIntent = new Intent(this, Notification.class);
        dismissIntent.setAction("close");
        PendingIntent piDismiss = PendingIntent.getService(this, 0, dismissIntent,  PendingIntent.FLAG_UPDATE_CURRENT);
        
		 builder =
	                new NotificationCompat.Builder(this)
	                .setSmallIcon(R.drawable.ic_launcher)
	                .setContentTitle(cursor_eng.getString(1)+" "+cursor_eng.getString(3))
	                .setContentText("下拉此行學習多內容")
	                //.setDefaults(-1)
	                /*
	                 * Sets the big view "big text" style and supplies the
	                 * text (the user's reminder message) that will be displayed
	                 * in the detail area of the expanded notification.
	                 * These calls are ignored by the support library for
	                 * pre-4.1 devices.
	                 */
	                .setStyle(new NotificationCompat.BigTextStyle()
	                     .bigText(cursor_eng.getString(5)+"\n"+cursor_eng.getString(4)))
	                .addAction (android.R.drawable.ic_media_play,
	                        "下一個", again)
	                .addAction (android.R.drawable.ic_menu_edit,
	                        "詳細", detail)
	                .addAction (android.R.drawable.ic_lock_silent_mode_off,
	    	        		"發音", voice);
		 
		 builder.setContentIntent(detail);
		 mNotificationManager.notify(1, builder.build());
	}
	
	private void Formula_Notification(int a){
		
		db = new MySQLite(this);
		db.OpenDB();
		math_count = new testCOUNT(db.maxID(2));
		physics_count = new testCOUNT(db.maxID(3));
		Intent detailIntent = new Intent(this,formula_detal.class); 
		Bundle bundle = new Bundle();
		
	 	
		//這裡無法使用Toast,要開多現執行續
		if(a==1)
		{
			cursor_math = db.science_get(math_count.test_id(), a);
			 if(cursor_math.getInt(8)!=1){
				 db.Update(1, "math_table", math_count.now_id);
				 db.append(math_count.now_id, "old_math_table");
			 }
			formula = cursor_math.getString(1);
			bmp = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(cursor_math.getString(2), "drawable", "com.example.navigationdrawer"));
			//數學 
			kind = "math";
	        bundle.putInt("SelectTab",1);
	        bundle.putInt("Selectid",math_count.now_id);
	        bundle.putBoolean("call", true);
			Log.d("數學",""+a);
			
		}
		else
		{
			cursor_physics = db.science_get(physics_count.test_id(), a);
			if(cursor_physics.getInt(8)!=1){
		    	 db.Update(1, "physics_table", physics_count.now_id);
		    	 db.append(physics_count.now_id,  "old_physics_table");
			}
			formula = cursor_physics.getString(1);
			bmp = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(cursor_physics.getString(2), "drawable", "com.example.navigationdrawer"));
			
			kind = "physics";
	        bundle.putInt("SelectTab",2);
	        bundle.putInt("Selectid",physics_count.now_id);
	        bundle.putBoolean("call", true);
			Log.d("物理",""+a);
			//物理
		}
		mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
			
//	        Log.i("按到按鈕",eng_count.now_id+"");
	        detailIntent.putExtras(bundle);
	        detailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);   //清除已經推撥的OR新增推播
	        // Because clicking the notification opens a new ("special") activity, there's
	        // no need to create an artificial back stack.
	        PendingIntent detail = PendingIntent.getActivity(
	                this,
	                1,
	                detailIntent,
	                PendingIntent.FLAG_CANCEL_CURRENT
	        );
	    
	        Intent againIntent = new Intent(this, Notification.class);
			againIntent.setAction(Variable.Formula);
			againIntent.putExtra("class", a);
	        PendingIntent again = PendingIntent.getService(this, 0, againIntent,PendingIntent.FLAG_CANCEL_CURRENT);
		
		Intent dismissIntent = new Intent(this, Notification.class);
        dismissIntent.setAction("close");
        PendingIntent piDismiss = PendingIntent.getService(this, 0, dismissIntent, 0);
        
                                                       //關鍵公式圖片
		//Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.e);   //drawable轉bitmap
		//Bitmap mBitmap = Bitmap.createScaledBitmap(bmp, 256, 256, true);  
        
		 builder =
	                new NotificationCompat.Builder(this)
	                .setSmallIcon(R.drawable.ic_launcher)
	                .setContentTitle(formula)        
	                .setContentText("下拉此行學習多內容")
	                //.setDefaults(-1)
	                /*
	                 * Sets the big view "big text" style and supplies the
	                 * text (the user's reminder message) that will be displayed
	                 * in the detail area of the expanded notification.
	                 * These calls are ignored by the support library for
	                 * pre-4.1 devices.
	                 */
	                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bmp))
	                
	                .addAction (android.R.drawable.ic_media_play,
	                        "下一個", again)
	                .addAction (android.R.drawable.ic_menu_edit,
	                        "詳細", detail);
		 
		 Intent resultIntent = new Intent(this, MainActivity.class);
         resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);   //清除已經推撥的OR新增推播

         // Because clicking the notification opens a new ("special") activity, there's
         // no need to create an artificial back stack.
         PendingIntent resultPendingIntent =
                 PendingIntent.getActivity(
                 this,
                 0,
                 resultIntent,
                 PendingIntent.FLAG_UPDATE_CURRENT
         );
         
		 builder.setContentIntent(resultPendingIntent);
		 mNotificationManager.notify(1, builder.build());
	}

	
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		String action = intent.getAction();
		Log.e("action", action);
		if(action.equals(Variable.English))
		{
			En_Notification();
			db.set_statistics_data("En");
		}
		else if(action.equals(Variable.Formula))
		{
			getClass = intent.getExtras();
			int ForClass=getClass.getInt("class");
			Formula_Notification(ForClass);	
			db.set_statistics_data(kind);
		}
		else if(action.equals("close"))
		{	
			Log.d("music", "test");
			nm.cancel(1);
		}
		else if(action.equals("voice")){
			 Log.d("music", "test");
			 soundPool= new SoundPool(10,AudioManager.STREAM_MUSIC,5);
			 getClass = intent.getExtras();
			 String VoiceName=getClass.getString("VoiceName");
			 soundPool.load(this,getResources().getIdentifier(VoiceName, "raw", "com.example.navigationdrawer"),1);
			 soundPool.play(1, 1, 1, 0, 0, 1.0f);
//			 soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener(){
//				 @Override
//				 public void onLoadComplete(SoundPool arg0, int arg1, int arg2) {
//					 soundPool.play(1, 1, 1, 0, 0, 1.0f);
//			 }});
		}
	}
	
}
