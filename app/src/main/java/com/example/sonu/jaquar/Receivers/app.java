package com.example.sonu.jaquar.Receivers;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.example.sonu.jaquar.CheckoutActivity;
import com.example.sonu.jaquar.LoginActivity;
import com.example.sonu.jaquar.SplashActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by sonu on 11/7/18.
 */


public class app extends Application {
    String TAG ="app";
    AlarmManager alarmManager;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference db;
    Handler handler;
    Runnable runnable;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onncreate");
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), NotificationBroadcaster.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 101, intent,0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                if (alarmManager != null) {
                    Log.e(TAG,"AlarmManger not null");
                   // alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+60000,pendingIntent);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 60*1000, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);

                }
            }
        };
        handler.postDelayed(runnable,40000);
    }
    
    }

