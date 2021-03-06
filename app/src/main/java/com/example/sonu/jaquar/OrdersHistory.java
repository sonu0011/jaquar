package com.example.sonu.jaquar;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sonu.jaquar.Adapters.FavourateAdapter;
import com.example.sonu.jaquar.Adapters.HistoryAdapter;
import com.example.sonu.jaquar.Adapters.OrderDetails;
import com.example.sonu.jaquar.Constants.CheckInternetCoonnection;
import com.example.sonu.jaquar.Models.HistoryModel;
import com.example.sonu.jaquar.Models.NewlyProductModel;
import com.example.sonu.jaquar.Models.OrderDetailsModel;
import com.example.sonu.jaquar.Receivers.InternetReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrdersHistory extends AppCompatActivity {
    private static final String TAG = "OrderHistory";
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<OrderDetailsModel> list;
    OrderDetails orderDetails;
    ImageView imageView;
    private AlertDialog.Builder mbuilder;
    private InternetReceiver internetReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_history);
        Log.d(TAG, "onCreate: ");
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        internetReceiver = new InternetReceiver();
        registerReceiver(internetReceiver, intentFilter);
        imageView =findViewById(R.id.noorderhistory);
        toolbar = findViewById(R.id.historytoolbar);
        toolbar.setTitle("Your Orders");
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
        FirebaseDatabase.getInstance().getReference("OrderDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list.clear();
                        if (!dataSnapshot.exists()){
                            imageView.setVisibility(View.VISIBLE);
                        }
                        else {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                Log.d(TAG, "onDataChange: "+data.toString());
                                String date =data.child("order_date").getValue(String.class);
                                String order_id =data.child("order_id").getValue(String.class);
                                String totalitems =data.child("totalitems").getValue(String.class);
                                String totalprice =data.child("totalprice").getValue(String.class);
                                OrderDetailsModel model  =new OrderDetailsModel(date,order_id,totalitems,totalprice);


//                                    OrderDetailsModel orderDetailsModel =data.getValue(OrderDetailsModel.class);
                                    list.add(model);
                                    orderDetails = new OrderDetails(getApplicationContext(),list);
                                    recyclerView.setAdapter(orderDetails);



                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
//
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        getMenuInflater().inflate(R.menu.searchmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search_bar_activity) {
            if (recyclerView.getChildCount() > 0) {
                startActivity(new Intent(getApplicationContext(), SearchOrders.class));
            }
        }
        return super.onOptionsItemSelected(item);
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
