package com.eagleemperor.googlemapssource;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyLocationService extends Service {

    private DatabaseReference mDataBaseReference;
    private static final String TAG = "MyLocationService";
    private boolean bool=true;
    Thread thread;
    private String name="";
    private int number=1;

    public  MyLocationService()
    {

    }


    @Override
    public int onStartCommand(final Intent intent, int flags, final int startId) {

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        final String name=sharedPreferences.getString("Emp_Name",null);
        try {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    mDataBaseReference= FirebaseDatabase.getInstance().getReference();

                    for(int i=0;i<100;i++) {
                        try {
                            if (bool) {
                                mDataBaseReference.child("users").child(name).child("name").setValue("" + i + " " + number);
                            } else {
                                stopSelf(startId);
                                break;
                            }
                        }catch (Exception e)
                        {
                            mDataBaseReference.child("users").child("").child("name").setValue("" +e.toString());
                        }
                        try {
                            Thread.sleep(1000);
                            Log.d(TAG, "FirebaseDatabase " + i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }catch (Exception e)
        {
            mDataBaseReference.child("users").child(name).child("name").setValue("" +e.toString());
        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bool=false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
