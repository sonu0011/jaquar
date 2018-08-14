package com.example.sonu.jaquar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonu.jaquar.Adapters.HistoryAdapter;
import com.example.sonu.jaquar.Adapters.NewlyProductAdapter;
import com.example.sonu.jaquar.Adapters.RecyclerAdapter;
import com.example.sonu.jaquar.Adapters.SearchOrdersRecycler;
import com.example.sonu.jaquar.Constants.SearchConstants;
import com.example.sonu.jaquar.Models.CategoriesModel;
import com.example.sonu.jaquar.Models.HistoryModel;
import com.example.sonu.jaquar.Receivers.InternetReceiver;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchOrders extends AppCompatActivity {
    public static final String TAG="SearchOrders";
    private InternetReceiver internetReceiver;
    private Toolbar toolbar;
    EditText editText;
    private RecyclerView recyclerView;
    private List<HistoryModel> list;
    private HistoryAdapter historyAdapter;
    ImageView imageView;
    ProgressDialog progressDialog;
    RelativeLayout relativeLayout;
    ImageView noresult;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_orders);
        relativeLayout =findViewById(R.id.ordersearchrelativelayout);
        noresult  =findViewById(R.id.noresultfound);
        progressDialog =new ProgressDialog(SearchOrders.this);
        imageView =findViewById(R.id.orderSearchIMageview);
        list =new ArrayList<>();
        editText =findViewById(R.id.ordersearchEditText);
        Log.d(TAG, "onCreate: ");
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        internetReceiver = new InternetReceiver();
        registerReceiver(internetReceiver, intentFilter);
        toolbar = findViewById(R.id.ordersSearchToolbar);
        toolbar.setTitle("Search in orders");
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
        recyclerView = findViewById(R.id.OrdersearchRecycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        if (internetReceiver != null) {
            unregisterReceiver(internetReceiver);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (i == 1) {
                    i = 0;
                }
                Log.d(TAG, "onClick: ");
                if (editText.getText().toString().trim().isEmpty()) {
//                    if (noresult.getVisibility() == View.VISIBLE){
//                        noresult.setVisibility(View.INVISIBLE);
//                    }
                    Toast.makeText(SearchOrders.this, "Enter some text for search", Toast.LENGTH_SHORT).show();
                    if (recyclerView.getChildCount() > 0 || recyclerView.getVisibility() == View.VISIBLE) {
                        recyclerView.setVisibility(View.GONE);
                        recyclerView.setAdapter(null);

                    }
                    return;
                } else {
                    if (recyclerView.getVisibility() == View.GONE) {

                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    progressDialog.setTitle("Searching");
                    progressDialog.setMessage("Please Wait..");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("orderHistory")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            list.clear();
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                for (DataSnapshot val : data.getChildren()) {
                                    Log.d(TAG, "onDataChange: ");
                                    if (val.child("title").getValue(String.class).toLowerCase().contains(editText.getText().toString().toLowerCase())) {
                                        i = 1;
                                        Log.d(TAG, "onDataChange: data matched");
                                        HistoryModel model = val.getValue(HistoryModel.class);
                                        list.add(model);
                                        historyAdapter = new HistoryAdapter(list, getApplicationContext());
                                        recyclerView.setAdapter(historyAdapter);
                                    }


                                }
                            }

                            progressDialog.dismiss();
                            if (i == 1) {
                                findViewById(R.id.noresultfound).setVisibility(View.INVISIBLE);
                            }
                            if (i == 0) {
                                recyclerView.setVisibility(View.INVISIBLE);
                                findViewById(R.id.noresultfound).setVisibility(View.VISIBLE);
                                relativeLayout.setBackgroundColor(Color.WHITE);
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });




//                FirebaseRecyclerAdapter<HistoryModel, SearchOrdersRecycler> firebaseRecyclerAdapter =
//                        new FirebaseRecyclerAdapter<HistoryModel, SearchOrdersRecycler>(
//                                HistoryModel.class,
//                                R.layout.historykayout,
//                                SearchOrdersRecycler.class,
//                                query
//
//                        ) {
//                            @Override
//                            protected void populateViewHolder(SearchOrdersRecycler viewHolder,HistoryModel model, int position) {
//                                Log.d(TAG, "populateViewHolder: "+model.getProductcode());
//                                Toast.makeText(SearchOrders.this, ""+model.getProductcode(), Toast.LENGTH_SHORT).show();
////                           viewHolder.SetDetails(getApplicationContext(), model.getTitle(), model.getImage(),model.getPrice(),model.getProductcode(),model.getQuantity());
//                            }
//                            @NonNull
//                            @Override
//                            public SearchOrdersRecycler onCreateViewHolder(ViewGroup parent, int viewType) {
//                                SearchOrdersRecycler viewHolder = super.onCreateViewHolder(parent, viewType);
//                                viewHolder.setOnClickListener(new SearchOrdersRecycler.ClickListener() {
//                                    @Override
//                                    public void onItemClick(View view, int position) {
//                                        TextView textView = view.findViewById(R.id.firebasetext);
//                                        String s  =textView.getText().toString();
//                                        SearchConstants.CATEGOTY_NAME=s;
//                                        Intent intent  =new Intent(SearchOrders.this,SubCategory.class);
//                                        intent.putExtra("title",s);
//                                        startActivity(intent);
//                                        //Toast.makeText(getApplicationContext(),"item  at"+s,Toast.LENGTH_SHORT).show();
//                                    }
//
//                                    @Override
//                                    public void onItemLongClick(View view, int position) {
//
//                                    }
//                                });
//                                return  viewHolder;
//                            }
//
//                        };
//                recyclerView.setAdapter(firebaseRecyclerAdapter);

            }





}
