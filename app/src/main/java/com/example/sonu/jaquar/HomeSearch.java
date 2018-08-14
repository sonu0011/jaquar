package com.example.sonu.jaquar;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sonu.jaquar.Adapters.SIngleProductAdapter;
import com.example.sonu.jaquar.Constants.CheckInternetCoonnection;
import com.example.sonu.jaquar.Models.SearchModel;
import com.example.sonu.jaquar.Models.SingelProductModel;
import com.example.sonu.jaquar.Receivers.InternetReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HomeSearch extends AppCompatActivity {
    private static final String TAG = "HomeSearch";
    EditText searchEditText;
    List<SingelProductModel> list;
    RecyclerView searchrecycleview;
    SIngleProductAdapter sIngleProductAdapter;
    ProgressDialog progressDialog;
    private AlertDialog.Builder mbuilder;
    ImageView searchbtn,notfound;
    RelativeLayout relativeLayout;
    int i=0,j;
    CoordinatorLayout coordinatorLayout;
    private BroadcastReceiver internetReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        relativeLayout =findViewById(R.id.homeserchlayout);
        searchbtn =findViewById(R.id.homesearch_btn);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        internetReceiver = new InternetReceiver();
        registerReceiver(internetReceiver, intentFilter);
        coordinatorLayout =findViewById(R.id.searchCoridnate);

        notfound =findViewById(R.id.noresultfound);
        list =new ArrayList<>();
        progressDialog =new ProgressDialog(HomeSearch.this);
        searchrecycleview =findViewById(R.id.searchRecycleview);
        searchrecycleview.setHasFixedSize(true);
        searchrecycleview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHomeSearch);
        toolbar.setTitle("Search for products");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toobarCOlor));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });
        searchEditText =findViewById(R.id.searchfromfirebase);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i==1){
                    i=0;
                }

                progressDialog.setTitle("Searching");
                progressDialog.setMessage("Please Wait..");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                final String val =searchEditText.getText().toString().trim();
                if (val.isEmpty()){
                    progressDialog.dismiss();
                    if (notfound.getVisibility() == View.VISIBLE){
                        notfound.setVisibility(View.INVISIBLE);
                    }
                    Log.d(TAG, "onClick: "+searchrecycleview.getChildCount());
                    Toast.makeText(HomeSearch.this, "Enter some text for search", Toast.LENGTH_SHORT).show();
                   if (searchrecycleview.getChildCount() > 0 || searchrecycleview.getVisibility() == View.VISIBLE){
                       searchrecycleview.setVisibility(View.GONE);
                       searchrecycleview.setAdapter(null);

                   }
                    return;
                }
                else {
                    if (searchrecycleview.getVisibility() == View.GONE){

                        searchrecycleview.setVisibility(View.VISIBLE);
                    }

                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("categoreis");
                    Query query =db.limitToFirst(4);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            list.clear();
                            for (final DataSnapshot data:dataSnapshot.getChildren()){
                                for (DataSnapshot data1:data.getChildren()){
                                    for (DataSnapshot data2:data1.getChildren())
                                    {
                                        data2.getRef().limitToFirst(4).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                int status =0;
                                                for (DataSnapshot data:dataSnapshot.getChildren()) {
                                                    String title = data.child("title").getValue(String.class);
                                                    if (title!=null) {
                                                        if (title.toLowerCase().contains(val.toLowerCase())) {
                                                            if (notfound.getVisibility() == View.VISIBLE){
                                                                notfound.setVisibility(View.INVISIBLE);
                                                            }

                                                            i=1;

//                                                  if (title.toLowerCase().contains(val.toLowerCase())){
//                                                      Log.d(TAG, "onDataChange: "+val);
//                                                      Log.d(TAG, "onDataChange: Data matches");
////                                                      Log.e(TAG, "onDataChange: datamatches");
                                                            SingelProductModel singelProductModel = data.getValue(SingelProductModel.class);
                                                            list.add(singelProductModel);
                                                            sIngleProductAdapter = new SIngleProductAdapter(HomeSearch.this, list,coordinatorLayout,"HomeSearch");
                                                            searchrecycleview.setAdapter(sIngleProductAdapter);
//
//                                                  }
                                                        }
                                                    }

//                                              if (title != null) {
//                                                  if (editable.toString().toLowerCase().contains(title.toLowerCase())) {
//                                                      Log.d(TAG, "onDataChange: titlematches");

//                                                  }
//
//                                              }
                                                }
                                                progressDialog.dismiss();
                                                if (i !=1){
                                                    searchrecycleview.setAdapter(null);
                                                    relativeLayout.setBackgroundColor(Color.WHITE);
                                                    notfound.setVisibility(View.VISIBLE);
                                                }
//                                                if (status ==0){
//                                                    Log.d(TAG, "onDataChange: data not matches");
//                                                }

//                                                progressDialog.dismiss();
//                                                Log.e(TAG, "onDataChange: vlaue of i "+i);
//                                                if (i ==0){
//                                                    notfound.setVisibility(View.VISIBLE);
//                                                    searchrecycleview.setVisibility(View.GONE);
//                                                    relativeLayout.setBackgroundColor(Color.WHITE);
//
//                                                }


                                            }


                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
//                                   for (DataSnapshot dataSnapshot1:data2.getChildren()){
//                                       Log.d(TAG, "onDataChange: "+dataSnapshot1);
//
////                                       dataSnapshot1.getRef().limitToFirst(4)
////                                               .addValueEventListener(new ValueEventListener() {
////                                                   @Override
////                                                   public void onDataChange(DataSnapshot data) {
////                                                       Log.d(TAG, "onDataChange: data "+data);
////                                                   }
////
////                                                   @Override
////                                                   public void onCancelled(DatabaseError databaseError) {
////                                                       Log.d(TAG, "onCancelled: "+databaseError.toString());
////
////                                                   }
////                                               });
//                                   }
                                    }

                                }
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                }
            }
        });
//
//        searchEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(final Editable editable) {
//                Toast.makeText(HomeSearch.this, ""+editable.toString(), Toast.LENGTH_SHORT).show();
//
//
//
////               // String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
////                //Query query = FirebaseDatabase.getInstance().getReference(uid).orderByChild("title").startAt(editable.toString()).endAt(editable.toString()+"\uf888");
////                List<SingelProductModel> newlist =new ArrayList<>();
////                for (SingelProductModel data:list)
////                {
////                    if(data.getTitle().toLowerCase().contains(editable.toString().toLowerCase()))
////                    {
////                        newlist.add(data);
////                    }
////                }
////                sIngleProductAdapter.filterList(newlist);
//
//
//
//            }
//        });
        Drawable backArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        backArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(HomeSearch.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        progressDialog.setTitle("Loading...");
//        progressDialog.setMessage("please wait...");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("categoreis");
//        Query query =  FirebaseDatabase.getInstance().getReference("categoreis").limitToFirst(4);
//
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                list.clear();
//                for (DataSnapshot ds:dataSnapshot.getChildren())
//                {
//                    for (DataSnapshot data:ds.getChildren())
//                    {
//                        for (DataSnapshot subdata:data.getChildren())
//                        {
//                            subdata.getRef().limitToFirst(3).addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                for (DataSnapshot value:dataSnapshot.getChildren()) {
//
//                                    Log.d("firebaseValue", value.toString());
//                                    String image = value.child("image").getValue(String.class);
//                                    String productcode = value.child("productcode").getValue(String.class);
//                                    String price = value.child("price").getValue(String.class);
//                                    String title = value.child("title").getValue(String.class);
//                                    String whishlist = value.child("whishlist").getValue(String.class);
//                                    if (image != null && productcode != null && price != null && title != null) {
//                                        list.add(new SingelProductModel(image,price,productcode,title,whishlist));
//                                    }
//                                }
//                                progressDialog.dismiss();
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        sIngleProductAdapter =new SIngleProductAdapter(getApplicationContext(),list);
//        searchrecycleview.setAdapter(sIngleProductAdapter);
//       //progressDialog.dismiss();
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
