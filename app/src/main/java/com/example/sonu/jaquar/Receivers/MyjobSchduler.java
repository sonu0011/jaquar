package com.example.sonu.jaquar.Receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.sonu.jaquar.CheckoutActivity;
import com.example.sonu.jaquar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by sonu on 31/7/18.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyjobSchduler extends JobService {
    private static final String TAG = "MyJobSchduler";
    private boolean jobCancelled = false;
    Intent intent1;
    PendingIntent pendingIntent;

    NotificationManager notificationManager;
    NotificationManagerCompat notificationManagerCompat;
    NotificationChannel notificationChannel;
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.d(TAG, "onStartJob: ");
        for (int i=0;i<=10;i++){
            Log.d(TAG, "onStartJob: "+i);
        }
//        notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
//        notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            notificationChannel = new NotificationChannel("n1", "notification", NotificationManager.IMPORTANCE_HIGH);
//            notificationChannel.setDescription("notificatioonChannel");
//            notificationManager.createNotificationChannel(notificationChannel);
//        }
//        intent1= new Intent(getApplicationContext(), CheckoutActivity.class);
//        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//        pendingIntent = PendingIntent.getActivity(getApplicationContext(), 205, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//        FirebaseDatabase.getInstance().getReference("cartValues").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.getChildrenCount()>0)
//                        {
//                            Log.d(TAG, "onDataChange: greater than zero");
//                                doInBackGround(jobParameters);
//                        }
//                        else if (jobCancelled){
//                            Log.d(TAG, "onDataChange: jobcancelled true");
//                            jobFinished(jobParameters,true);
//                        }
//                        else {
//                            Log.d(TAG, "onDataChange:no cart values");
//                            jobFinished(jobParameters,true);
//                        }
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
        return true;
    }

    private void doInBackGround(JobParameters jobParameters) {
        Log.d(TAG, "doInBackGround: ");

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), "n1")
                .setContentTitle("jaquar!!!")
                .setContentText("There is something in your cart. Would you like to complete your Purchase?")
                .setSmallIcon(R.drawable.ic_add_shopping_cart_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(Color.GREEN)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("There is something in your cart. Would you like to complete your Purchase?")
                        //.setBigContentTitle("Total Rs" + textView.getText().toString())
                        .setSummaryText("Thanks For Visiting Jaquar!!!"))

                .build();
        notificationManagerCompat.notify(4, notification);
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "onStopJob: ");
        jobCancelled=true;
        return true;
    }
}
