package com.example.sonu.jaquar;

import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        boolean b = new CheckInternetCoonnection().CheckNetwork(FavouritesActivity.this);
        if (!b) {
            mbuilder = new AlertDialog.Builder(FavouritesActivity.this);
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.internetconnectiondialog, null);
            mbuilder.setView(view);
            mbuilder.setCancelable(false);
            mbuilder.create();
            final AlertDialog alertDialog = mbuilder.show();

            view.findViewById(R.id.cancel_internet).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    alertDialog.dismiss();
                    finish();

                }
            });
        }
        imageView =findViewById(R.id.nowhishlistimage);
        toolbar =findViewById(R.id.favtoolbar);
        toolbar.setTitle("Favourites");
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
                                favourateAdapter = new FavourateAdapter(list, getApplicationContext());
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
        boolean b = new CheckInternetCoonnection().CheckNetwork(FavouritesActivity.this);
        if (!b) {
            mbuilder = new AlertDialog.Builder(FavouritesActivity.this);
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.internetconnectiondialog, null);
            mbuilder.setView(view);
            mbuilder.setCancelable(false);
            mbuilder.create();
            final AlertDialog alertDialog = mbuilder.show();

            view.findViewById(R.id.cancel_internet).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    alertDialog.dismiss();
                    finish();

                }
            });
        }
        super.onRestart();
    }
}
