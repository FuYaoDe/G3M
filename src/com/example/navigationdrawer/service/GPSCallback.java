package com.example.navigationdrawer.service;
 
import android.location.Location;
 
public interface GPSCallback
{
        public abstract void onGPSUpdate(Location location);
}