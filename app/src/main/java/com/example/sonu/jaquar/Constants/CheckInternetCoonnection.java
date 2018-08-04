package com.example.sonu.jaquar.Constants;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.sonu.jaquar.HomeActivity;
import com.example.sonu.jaquar.R;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by sonu on 2/8/18.
 */

public class CheckInternetCoonnection {
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    private AlertDialog.Builder mbuilder;

    public   boolean CheckNetwork(Context context){
        connectivityManager = (ConnectivityManager)context.getSystemService(CONNECTIVITY_SERVICE);
        if(connectivityManager!=null)
        {
            networkInfo =connectivityManager.getActiveNetworkInfo();
            if(networkInfo !=null && networkInfo.isConnected())
            {
                return true;
            }
            else {
                return false;
            }
        }

        return false;
    }
    public void  showMessage(final Context context){
        mbuilder =new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.internetconnectiondialog,null);
        mbuilder.setView(view);
        mbuilder.setCancelable(false);
        mbuilder.create();
        final AlertDialog alertDialog =mbuilder.show();

        view.findViewById(R.id.cancel_internet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();


            }
        });
        view.findViewById(R.id.interner_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
//                        Intent intent = new Intent(Intent.ACTION_MAIN);
//                        intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
//                        startActivity(intent);
            }
        });
    }
}
