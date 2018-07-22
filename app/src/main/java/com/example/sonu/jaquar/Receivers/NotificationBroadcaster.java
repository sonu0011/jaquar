package com.example.sonu.jaquar.Receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.sonu.jaquar.CheckoutActivity;
import com.example.sonu.jaquar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by sonu on 11/7/18.
 */

public class NotificationBroadcaster extends BroadcastReceiver {
    NotificationManager notificationManager;
    NotificationManagerCompat notificationManagerCompat;
    NotificationChannel notificationChannel;
        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d("NotifiacationONRecice","yes");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(firebaseUser.getUid());
        notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel("n1", "notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("notificatioonChannel");
            notificationManager.createNotificationChannel(notificationChannel);
        }


        Intent intent1 = new Intent(context, CheckoutActivity.class);
       intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 205, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        if (firebaseUser != null) {
            Log.d("FirebaseUserNull","no");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount()>0)
                    {
                        Log.d("childerncount","greaterthanZero");
                        Log.d("NotificationShows","yes");
                        Notification notification = new NotificationCompat.Builder(context, "n1")
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
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }
}
