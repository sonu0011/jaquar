package com.example.sonu.jaquar.Receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.CheckoutActivity;
import com.example.sonu.jaquar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    Bitmap bitmap;
    public static final String TAG="NotificationReceiver";

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.e("NotifiacationONRecice", "yes");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("cartValues").child(firebaseUser.getUid());
        notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel("n1", "notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("notificatioonChannel");
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent intent1 = new Intent(context, CheckoutActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 205, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager!=null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if ((networkInfo != null) && (networkInfo.isConnected())) {
                if (firebaseUser != null) {
                    Query query = databaseReference.orderByKey().limitToFirst(1);

                    Log.e("FirebaseUserNull", "no");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() > 0) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    int childcount = (int) dataSnapshot.getChildrenCount();
                                    Log.e("childerncount", "greaterthanZero");
                                    Log.e("NotificationShows", "yes");
                                    String image = ds.child("image").getValue(String.class);
                                    String productcode = ds.child("productcode").getValue(String.class);
                                    String title = ds.child("title").getValue(String.class);
                                    String price = ds.child("totalprice").getValue(String.class);


                                    Log.d(TAG, "onDataChange: " +bitmap+ image + productcode + title + price + dataSnapshot);
                                    RemoteViews notificationLayout = new RemoteViews(context.getPackageName(), R.layout.notificationlayout);
//                                    notificationLayout.setImageViewBitmap(R.id.notificationImageview,bitmap);
//                                    notificationLayout.setImageViewResource(R.id.notificationImageview, Integer.parseInt(image));
                                    notificationLayout.setTextViewText(R.id.notificationProductCode, productcode);
                                    notificationLayout.setOnClickPendingIntent(R.id.notificationviewdetails,pendingIntent);

                                    notificationLayout.setTextViewText(R.id.notificationtitel, title);

                                    notificationLayout.setTextViewText(R.id.notificationProductPrice, price);


                                    Notification notification = new NotificationCompat.Builder(context, "n1")
                                    .setContentTitle("Your cart is waiting!")
//                                    .setContentText("There is something in your cart. Would you like to complete your Purchase?")

                                            .setCustomBigContentView(notificationLayout)
                                                         .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                                           .setColor(Color.parseColor("#FF69B4"))
                                            .setPriority(NotificationCompat.PRIORITY_HIGH)

                                            .setContentIntent(pendingIntent)
//                                    .setStyle(new NotificationCompat.BigTextStyle()
//                                            .bigText("There is something in your cart. Would you like to complete your Purchase?")
//                                            //.setBigContentTitle("Total Rs" + textView.getText().toString())
//                                            .setSummaryText("Thanks For Visiting Jaquar!!!"))

                                            .build();
                                    notificationManagerCompat.notify(4, notification);
//                                databaseReference.getRef().limitToFirst(1).addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//
//                                    }
//                                });

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }
        }
    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}
