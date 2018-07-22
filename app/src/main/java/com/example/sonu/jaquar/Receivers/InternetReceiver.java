package com.example.sonu.jaquar.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.example.sonu.jaquar.LoginActivity;

public class InternetReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isConnected())
        {
            Toast.makeText(context, "No internet connection\n Please turn on internet  ", Toast.LENGTH_SHORT).show();
           LoginActivity.intetnetonnection=false;
        }
        else
            {
                LoginActivity.intetnetonnection=true;
            }

    }
}
