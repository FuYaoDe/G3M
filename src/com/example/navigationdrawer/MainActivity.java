package com.example.navigationdrawer;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	DrawerLayout drawer;
	ActionBarDrawerToggle drawerToggle;
	final String[] data ={"複習","測驗","統計","設定"};
	final String[] fragments ={
			"com.example.navigationdrawer.FragmentReview",
			"com.example.navigationdrawer.FragmentTest",
			"com.example.navigationdrawer.FragmentStatistics",
			"com.example.navigationdrawer.FragmentSetting"};

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActionBar().getThemedContext(), R.layout.list, data);
		//開啟service
		Intent intent = new Intent(MainActivity.this,com.example.navigationdrawer.service.service.class);
		 startService(intent);
		 Toast.makeText(getApplicationContext(), "開啟", Toast.LENGTH_SHORT).show();

		drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
		drawerToggle = new ActionBarDrawerToggle(this, drawer,
	            R.drawable.ic_drawer, 
	            R.string.open, R.string.close);
	    drawer.setDrawerListener(drawerToggle);
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    getActionBar().setHomeButtonEnabled(true);
	   // updateContent();
		final ListView navList = (ListView) findViewById(R.id.drawer);
		navList.setAdapter(adapter);
		navList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int pos,long id){
				drawer.setDrawerListener( new DrawerLayout.SimpleDrawerListener(){
					@Override
					public void onDrawerClosed(View drawerView){
						super.onDrawerClosed(drawerView);
						FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
						
						tx.replace(R.id.main, Fragment.instantiate(MainActivity.this, fragments[pos]));
						tx.commit();
					
					}
				});
				drawer.closeDrawer(navList);
			}
		});
		FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
		tx.replace(R.id.main,Fragment.instantiate(MainActivity.this, fragments[0]));
		tx.commit();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(drawerToggle.onOptionsItemSelected(item))
	    {
	        return true;
	    }
		switch(item.getItemId()){
		case android.R.id.home:// ActionBar左上角的那個應用程式圖示
			if(!drawer.isDrawerOpen(GravityCompat.START))
				drawer.openDrawer(GravityCompat.START);
			else
				drawer.closeDrawer(GravityCompat.START);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
	    super.onPostCreate(savedInstanceState);
	    drawerToggle.syncState();
	}
}
