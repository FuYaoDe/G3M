package com.example.navigationdrawer.service;

import com.example.navigationdrawer.MainActivity;
import com.example.navigationdrawer.MySQLite;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.Variable;
import com.example.navigationdrawer.english_detal;
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
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

public class Notification extends IntentService{
	
	
	
	public Notification() {
		super("com.example.servicedemo");
		
	}
	private NotificationManager mNotificationManager;
	private NotificationCompat.Builder builder;
	private Bundle getClass ;
	private MySQLite db = null;
	private Cursor cursor_eng = null, cursor_math = null, cursor_physics = null;
	private testCOUNT eng_count = null, math_count = null, physics_count = null;
	private String formula = null;
	private Bitmap bmp = null;
	
	private void En_Notification(){
		mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		db = new MySQLite(this);
		db.OpenDB();
        eng_count = new testCOUNT(db.maxID(1));
        cursor_eng = db.eng_get(eng_count.test_id());
		
		Intent againIntent = new Intent(this, Notification.class);
		againIntent.setAction(Variable.English);
        PendingIntent again = PendingIntent.getService(this, 0, againIntent, 0);
        
        Intent detailIntent = new Intent(this,english_detal.class); 
 		Bundle bundle = new Bundle();
        bundle.putInt("SelectTab",0);
        bundle.putInt("Selectid",eng_count.now_id-1);    //這裡id -1 配合你的detail介面
        detailIntent.putExtras(bundle);
        detailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);   //清除已經推撥的OR新增推播
        // Because clicking the notification opens a new ("special") activity, there's
        // no need to create an artificial back stack.
        PendingIntent detail = PendingIntent.getActivity(
                this,
                0,
                detailIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        
        Intent voiceIntent = new Intent(this, Notification.class);
        voiceIntent.setAction("voice");
        PendingIntent voice = PendingIntent.getService(this, 0, voiceIntent, 0);
        
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
		//這裡無法使用Toast,要開多現執行續
		if(a==1)
		{
			cursor_math = db.science_get(math_count.test_id(), a);
			formula = cursor_math.getString(1);
			bmp = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(cursor_math.getString(2), "drawable", "com.example.navigationdrawer"));
			//數學 
			Log.d("數學",""+a);
		}
		else
		{
			cursor_physics = db.science_get(physics_count.test_id(), a);
			formula = cursor_physics.getString(1);
			bmp = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(cursor_physics.getString(2), "drawable", "com.example.navigationdrawer"));
			Log.d("物理",""+a);
			//物理
		}
		mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
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
	                        "下一個", piDismiss)
	                .addAction (android.R.drawable.ic_menu_edit,
	                        "詳細", piDismiss);
		 
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
		if(action.equals(Variable.English))
		{
			En_Notification();
		}
		else if(action.equals(Variable.Formula))
		{
			getClass = intent.getExtras();
			int ForClass=getClass.getInt("class");
			Formula_Notification(ForClass);	
		}
		else if(action.equals("close"))
		{	
			nm.cancel(1);
		}
		else if(action.equals("voice")){
			 SoundPool soundPool;
			 soundPool= new SoundPool(10,AudioManager.STREAM_SYSTEM,5);
			 soundPool.load(this,R.raw.test,1);
			 soundPool.play(1,1, 1, 0, 0, 1);
		}
	}
}
