package com.example.sonu.jaquar;

import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sonu.jaquar.Adapters.FavourateAdapter;
import com.example.sonu.jaquar.Adapters.NewlyProductAdapter;
import com.example.sonu.jaquar.Adapters.SIngleProductAdapter;
import com.example.sonu.jaquar.Constants.CheckInternetCoonnection;
import com.example.sonu.jaquar.Models.NewlyProductModel;
import com.example.sonu.jaquar.Models.SingelProductModel;
import com.example.sonu.jaquar.Receivers.InternetReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {
    private static final String TAG ="FavoritesActivity" ;
    RecyclerView recyclerView;
List<NewlyProductModel>list;
 Toolbar toolbar;
 ImageView imageView ;
FavourateAdapter favourateAdapter;
    private AlertDialog.Builder mbuilder;
    private InternetReceiver internetReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        internetReceiver = new InternetReceiver();
        registerReceiver(internetReceiver, intentFilter);
        imageView =findViewById(R.id.nowhishlistimage);
        toolbar =findViewById(R.id.favtoolbar);
        toolbar.setTitle("Your Wish List");
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
        recyclerView =findViewById(R.id.favouriterecycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(FavouritesActivity.this,
                DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list =new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("favourites").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        list.clear();
                        if (!dataSnapshot.exists())
                        {
                            imageView.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.INVISIBLE);

                        }
                        else {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {

                                NewlyProductModel model = data.getValue(NewlyProductModel.class);
                                list.add(model);
                                favourateAdapter = new FavourateAdapter(list, FavouritesActivity.this);
                                recyclerView.setAdapter(favourateAdapter);

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
        Log.d(TAG, "onRestart: ");
        super.onRestart();
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
