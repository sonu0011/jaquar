package com.example.sonu.jaquar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sonu.jaquar.Adapters.CheckOutAdapter;
import com.example.sonu.jaquar.Constants.CheckInternetCoonnection;
import com.example.sonu.jaquar.Constants.SearchConstants;
import com.example.sonu.jaquar.Models.CheckOutMOdel;
import com.example.sonu.jaquar.Receivers.InternetReceiver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CheckoutActivity extends AppCompatActivity{
RecyclerView                      recyclerView;
List<CheckOutMOdel>               checkOutMOdelList;
CheckOutAdapter                   checkOutAdapter;
FirebaseDatabase                  firebaseDatabase;
DatabaseReference                 databaseReference;
FirebaseAuth                      firebaseAuth;
FirebaseUser                      firebaseUser;
TextView                          textView;
Button                            button;
NotificationManagerCompat         notificationManagerCompat;
android.support.v7.widget.Toolbar toolbar;
int                               totalprice;
String                            tag ="0001CheckOutActivity";
LinearLayoutManager               linearLayoutManager;
NotificationManager               notificationManager;
NotificationChannel               notificationChannel;
Handler handler;
Runnable runnable;
ImageView emptyimage;
    String currentDateandTime;
    int r;
    String strDate;
ProgressDialog progressDialog;
    private AlertDialog.Builder mbuilder;
    private BroadcastReceiver internetReceiver;
    public static final String TAG ="CheckoutActivity";
    TextView totalpricetext,totalpricesymbol;
    LinearLayout linearLayout;
    Button continueshopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        continueshopping =findViewById(R.id.continueshopping);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        strDate = dateFormat.format(date).toString();
        setContentView(R.layout.activity_checkout);
        linearLayout =findViewById(R.id.checkoutlinear);
        totalpricetext =findViewById(R.id.totalPrice);
        totalpricesymbol =findViewById(R.id.totalPriceSymbol);
        emptyimage =findViewById(R.id.emptycartimage);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        currentDateandTime= sdf.format(new Date());
        Log.d(TAG, "onCreate: "+currentDateandTime);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        internetReceiver = new InternetReceiver();
        registerReceiver(internetReceiver, intentFilter);
        Log.d(tag,"OnCreate");
        progressDialog =new ProgressDialog(CheckoutActivity.this);
        notificationManagerCompat =NotificationManagerCompat.from(getApplicationContext());
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =new NotificationChannel("n1","notification",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("notificatioonChannel");
            notificationManager.createNotificationChannel(notificationChannel);
        }
        //spinner =findViewById(R.id.checkoutQuantity);
        Log.d("whichMethod","onCreate");
        toolbar =findViewById(R.id.toolbarCheckout);
        toolbar.setTitle("Proceed to Checkout");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toobarCOlor));
            setSupportActionBar(toolbar);
           getSupportActionBar().setDisplayShowTitleEnabled(true);
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           getSupportActionBar().setDisplayShowHomeEnabled(true);
        textView =findViewById(R.id.totalPriceValue);
        button =findViewById(R.id.paynowbtn);
        Log.d("threadidCreate", String.valueOf(Thread.currentThread().getId()));
        checkOutMOdelList =new ArrayList<>();
        recyclerView  =findViewById(R.id.checkoutRecyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        firebaseAuth =FirebaseAuth.getInstance();
        firebaseUser =firebaseAuth.getCurrentUser();
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference("cartValues").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        Log.d("SearchConstantValueoc", String.valueOf(SearchConstants.totalpriceValue));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.valueOf(textView.getText().toString());
                Log.d(TAG, "onClick: value of total price"+a);
                int childcount = recyclerView.getChildCount();
                Log.d(TAG, "onClick: childcount"+childcount);
                if (a>0) {
                FirebaseDatabase fd=FirebaseDatabase.getInstance();
                DatabaseReference db  =fd.getReference();
                DatabaseReference orderdetails =db.child("OrderDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(currentDateandTime);
                orderdetails.child("order_id").setValue(currentDateandTime);
                orderdetails.child("totalitems").setValue(String.valueOf(childcount));
                orderdetails.child("order_date").setValue(strDate);
                orderdetails.child("totalprice").setValue(String.valueOf(a));
                final DatabaseReference db1 =db.child("orderHistory").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(currentDateandTime);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds:dataSnapshot.getChildren()) {
                            String pcode =ds.child("productcode").getValue(String.class);

                            FirebaseDatabase val = FirebaseDatabase.getInstance();
                            DatabaseReference ref = val.getReference();
//                            Random random =new Random();
//                             r = 10000000 + random.nextInt(90000000);
////                             r =random.nextInt(10000000);
                            DatabaseReference data = ref.child("OrdersDate").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(String.valueOf(currentDateandTime))
                                    .child(pcode);
                            data.child("time").setValue(strDate);
                            data.child("productcode").setValue(pcode);
                        }

//                        Date currentTime = Calendar.getInstance().getTime();
//                        Log.d(TAG, "onDataChange: random "+r+" ");
                                       db1.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Log.d("orderHistorySaved","yes");
                                }
                                else {
                                    Log.d("orderHistorySaved","NO");

                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

//                    FirebaseDatabase.getInstance().getReference("orderHistory").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                            .addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                for (DataSnapshot ds:dataSnapshot.getChildren())
//                                {
//                                    String pcode = ds.child("productcide").getValue(String.class);
//                                    if (pcode != null) {
//                                        Log.d("***",""+pcode);
////                                        FirebaseDatabase val = FirebaseDatabase.getInstance();
////                                        DatabaseReference ref = val.getReference();
////                                        DatabaseReference data = ref.child("OrdersDate").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
////                                                .child(pcode);
////                                        data.child("time").setValue(ServerValue.TIMESTAMP);
//
//                                    }
//                                }
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//
//                                }
//                            });

                    final Notification notification = new NotificationCompat.Builder(getApplicationContext(), "n1")

                            .setContentText("Your order has been placed successfully")
                            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setColor(Color.GREEN)
                            //Big text Style
                            .setAutoCancel(true)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .setSummaryText("Thanks For Shopping!!!"))

                            .build();

                    //databaseReference.removeValue();
                    notificationManagerCompat.notify(2, notification);
                    Intent intent =new Intent(CheckoutActivity.this,HomeActivity.class);
                    intent.putExtra("deleteitem","delete");
                    startActivity(intent);
                    finish();

                }


                Toast.makeText(CheckoutActivity.this, "Thanks for using this app", Toast.LENGTH_SHORT).show();

            }
        });
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()) {
                   //Toast.makeText(CheckoutActivity.this, "Data Exist", Toast.LENGTH_SHORT).show();
                   for (DataSnapshot ds : dataSnapshot.getChildren()) {

                       String tprice = (ds.child("totalprice").getValue(String.class));
                       if (!TextUtils.isEmpty(tprice)) {
                           SearchConstants.totalpriceValue = SearchConstants.totalpriceValue + Integer.valueOf(tprice);
                           Log.d("totalpricefirebase", String.valueOf(tprice));
                           Log.d("totalpriceAg", String.valueOf(SearchConstants.totalpriceValue));
                           textView.setText(String.valueOf(SearchConstants.totalpriceValue));
                       }

                   }
               }
               else {
                   //Toast.makeText(CheckoutActivity.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                   textView.setText("0");
               }
               SearchConstants.totalpriceValue=0;
               Log.d("baharvaluetotal", String.valueOf(SearchConstants.totalpriceValue));
               Log.d("baharvalue", textView.getText().toString());
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    PendingIntent pendingIntent = PendingIntent.getActivity(CheckoutActivity.this, 201, new Intent(CheckoutActivity.this, CheckoutActivity.class), 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    if (alarmManager != null) {

                        //alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.d("whichMethod","onResume");

//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds:dataSnapshot.getChildren())
//                {
//                    int tprice = Integer.parseInt(ds.child("totalprice").getValue(String.class));
//                    Log.d("totalpricefirebase", String.valueOf(tprice));
//                    SearchConstants.totalpriceValue =SearchConstants.totalpriceValue+tprice;
//                    Log.d("totalpriceAg", String.valueOf(totalprice));
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.searchandlogout,menu);
        MenuItem menuItem =menu.findItem(R.id.BothSearch);

        getMenuInflater().inflate(R.menu.logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(CheckoutActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
            Log.d("logout","yes");
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
                Log.d("whichMethod","onstart");
                Log.d("SearchConstantValueonSt", String.valueOf(SearchConstants.totalpriceValue));
                super.onStart();
                Log.d("totalPriceonstart", String.valueOf(totalprice));

                Log.d(tag,"onStart");
                  progressDialog.setTitle("Loading...");
                  progressDialog.setMessage("Please Wait..");
                  progressDialog.setCanceledOnTouchOutside(false);
                  progressDialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()){
                    Log.d(TAG, "onDataChange: data does not exist");
                    emptyimage.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    toolbar.setTitle("Continue Shopping..");
                    totalpricetext.setVisibility(View.GONE);
                    totalpricesymbol.setVisibility(View.GONE);
                    linearLayout.setBackgroundColor(Color.WHITE);
                    continueshopping = findViewById(R.id.continueshopping);
                    continueshopping.setVisibility(View.VISIBLE);
                    continueshopping.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(CheckoutActivity.this,HomeActivity.class));
                            finish();
                        }
                    });

                }
                if (dataSnapshot.exists()) {
                    emptyimage.setVisibility(View.GONE);
                    checkOutMOdelList.clear();
                    Log.d("threadidDataChange", String.valueOf(Thread.currentThread().getId()));
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        Log.d(tag, "for loop");
                        CheckOutMOdel checkOutMOdel = dataSnapshot1.getValue(CheckOutMOdel.class);
                        checkOutMOdelList.add(checkOutMOdel);
                        checkOutAdapter = new CheckOutAdapter(CheckoutActivity.this, checkOutMOdelList);
                        recyclerView.setAdapter(checkOutAdapter);
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        databaseReference.addValueEventListener(new ValueEventListener() {
//           @Override
//           public void onDataChange(DataSnapshot dataSnapshot) {
//               Log.d(tag,"OnDataChange");
////           for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
//                  Log.d(tag, "for loop");
//                  CheckOutMOdel checkOutMOdel = dataSnapshot1.getValue(CheckOutMOdel.class);
//                  checkOutMOdelList.add(checkOutMOdel);
////           }
//         checkOutAdapter =new CheckOutAdapter(getApplicationContext(),checkOutMOdelList);
//           recyclerView.setAdapter(checkOutAdapter);
//           }
//           @Override
//           public void onCancelled(DatabaseError databaseError) {
//               Log.d(tag,"onCancelleed");
//          }
//              });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("whichMethod","onBackPressed");
        SearchConstants.totalpriceValue=0;

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SearchConstants.totalpriceValue=0;
        Log.d("whichMethod","onDestroy");
        if (internetReceiver != null) {
            unregisterReceiver(internetReceiver);
        }

    }
    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d("whichMethod","onRestart");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("whichMethod","onPause");
    }

}
