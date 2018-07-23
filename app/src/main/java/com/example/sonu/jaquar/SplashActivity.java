package com.example.sonu.jaquar;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    Runnable runnable;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        coordinatorLayout =findViewById(R.id.cordinatelayout_splash);
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if(connectivityManager!=null)
        {
            networkInfo =connectivityManager.getActiveNetworkInfo();
            if(networkInfo !=null && networkInfo.isConnected())
            {
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                        finish();

                    }
                };
                handler.postDelayed(runnable,2000);
            }
            else {
                Snackbar.make(coordinatorLayout,"No Internet Connection",Snackbar.LENGTH_SHORT).show();
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
//                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                        finish();

                    }
                };
                handler.postDelayed(runnable,1000);
            }
            }
        }

}
