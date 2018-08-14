package com.example.sonu.jaquar.Adapters;

import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.example.sonu.jaquar.Models.HistoryModel;
import com.example.sonu.jaquar.OrdersHistory;
import com.example.sonu.jaquar.R;
import com.example.sonu.jaquar.Receivers.InternetReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowOrdersDetails extends AppCompatActivity {

    private String TAG ="SHowOrdersDetails";
    InternetReceiver internetReceiver;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<HistoryModel> list;
    String order_id;
    HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order_id = getIntent().getStringExtra("order_id");
        Log.d(TAG, "onCreate: orderid"+order_id);
        setContentView(R.layout.activity_show_orders_details);
        Log.d(TAG, "onCreate: ");
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        internetReceiver = new InternetReceiver();
        registerReceiver(internetReceiver, intentFilter);
        toolbar = findViewById(R.id.historytoolbar);
        toolbar.setTitle("Your Orders Item");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setBackgroundColor(getResources().getColor(R.color.toobarCOlor));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        recyclerView = findViewById(R.id.historyrecycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("orderHistory").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        list.clear();
                        String key = dataSnapshot.getKey();
                        Log.d(TAG, "onDataChange1: key"+key);
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    String key1  = data.getKey();
                                Log.d(TAG, "onDataChange2: key" + data.getKey());
                                if (key1.equals(order_id)) {
                                    Log.d(TAG, "onDataChange: equals yes");
                                    for (DataSnapshot ds : data.getChildren()) {
                                        HistoryModel model = ds.getValue(HistoryModel.class);
                                        list.add(model);
                                        historyAdapter = new HistoryAdapter(list, getApplicationContext());
                                        recyclerView.setAdapter(historyAdapter);
                                    }
                                }
                            }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }



    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        if (internetReceiver != null) {
            unregisterReceiver(internetReceiver);
        }
    }
}


