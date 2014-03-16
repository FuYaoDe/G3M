package com.example.navigationdrawer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MySQLite{
	
	private Context context;
	private String DB_NAME = "MySQLite.db";// �ƾڮw���W�r
	private String DB_PATH ;// �ƾڮw�b����ت����|
	private SQLiteDatabase db = null;
	
	private static String MATH_TABLE_NAME = "math_table";
	private static String PHYSICS_TABLE_NAME = "physics_table";
	private static String ENG_TABLE_NAME = "eng_table";
	private static String OLD_ENG_TABLE_NAME = "old_eng_table";
	private static String OLD_MATH_TABLE_NAME = "old_math_table";
	private static String OLD_PHYSICS_TABLE_NAME = "old_physics_table";
	private static String _ID = "_id";
	private static String SCIENCE_NAME = "name_f";
	private static String SCIENCE_K = "science_k";
	private static String SCIENCE_Q = "science_q";
	private static String SCIENCE_F = "science_f";
	private static String SCIENCE_P = "science_p";
	private static String SCIENCE_A = "science_a";
	private static String ENG_WORD = "eng_word"; 
	private static String ENG_PT = "eng_pt";
	private static String CNI_WORD = "cni_word";
	private static String CNI_EX01 = "cni_ex01";
	private static String CNI_EX02 = "cni_ex02";
	private static String CNI_EX03 = "cni_ex03";
	private static String CNI_EX04 = "cni_ex04";
	private static String CNI_EX05 = "cni_ex05";
	private static String ENG_EX01 = "eng_ex01";
	private static String ENG_EX02 = "eng_ex02";
	private static String ENG_EX03 = "eng_ex03";
	private static String ENG_EX04 = "eng_ex04";
	private static String ENG_EX05 = "eng_ex05";
	
	public MySQLite(Context context) {
		this.context= context;
        this.DB_PATH=this.context.getFilesDir().getAbsolutePath() +"/";
        Log.e("Path", DB_PATH);
        if(!checkDbExists())            
        {       
            try {
                this.CopyDB();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    private Boolean checkDbExists(){
        return new File(this.DB_PATH+this.DB_NAME).exists();
    }
 
    public void CopyDB() throws IOException {
        AssetManager assetManager = this.context.getAssets();
        InputStream in = assetManager.open(this.DB_NAME);
        OutputStream out = new FileOutputStream(this.DB_PATH+this.DB_NAME);
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
        in.close();
        out.flush();
        out.close();
    }
 
    public void OpenDB() throws SQLException {
    	if(checkDbExists())
    	{
	        this.db = SQLiteDatabase.openDatabase(this.DB_PATH+this.DB_NAME
	        , null,SQLiteDatabase.OPEN_READWRITE);
    	}
    }
 
    public void CloseDB() {
        if(db != null)
            db.close();
 
    }
    public Cursor eng_get(long id) throws SQLException {
		 Cursor cursor = db.query(OLD_ENG_TABLE_NAME,
	                new String[] {_ID, ENG_WORD,  ENG_PT, CNI_WORD, CNI_EX01, ENG_EX01,
				 CNI_EX02, ENG_EX02, CNI_EX03, ENG_EX03, CNI_EX04, ENG_EX04, CNI_EX05, ENG_EX05},
	                _ID +"=" + id, null, null, null, null,null);
	        if (cursor != null) {
	            cursor.moveToFirst();
	        }
	        return cursor;
	}
    public Cursor science_get(long id, int choes) throws SQLException {
    	String table = null;
    	if(choes == 1){
    		table = OLD_MATH_TABLE_NAME;
    	}
    	else{
    		table = OLD_PHYSICS_TABLE_NAME;
    	}
		 Cursor cursor = db.query(table,
	                new String[] {_ID, SCIENCE_NAME , SCIENCE_K, SCIENCE_F},
	                _ID +"=" + id, null, null, null, null,null);
	        if (cursor != null) {
	            cursor.moveToFirst();
	        }
	        return cursor;
	}
    public int maxID(int choes){
    	String query = null;
    	if(choes == 1)
    		query = "SELECT MAX(_id) AS max_id FROM old_eng_table";
    	else if(choes == 2)
    		query = "SELECT MAX(_id) AS max_id FROM old_math_table";
    	else
    		query = "SELECT MAX(_id) AS max_id FROM old_physics_table";
    		
    	Cursor cursor = db.rawQuery(query, null);
    	 int id = 0;     
    	    if (cursor.moveToFirst())
    	    {
    	        do
    	        {           
    	            id = cursor.getInt(0);                  
    	        } while(cursor.moveToNext());           
    	    } 
    	return id;
    }
}