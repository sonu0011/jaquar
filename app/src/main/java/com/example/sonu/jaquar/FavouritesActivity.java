package com.example.sonu.jaquar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.sonu.jaquar.Adapters.FavourateAdapter;
import com.example.sonu.jaquar.Adapters.NewlyProductAdapter;
import com.example.sonu.jaquar.Adapters.SIngleProductAdapter;
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
RecyclerView recyclerView;
List<NewlyProductModel>list;
 Toolbar toolbar;
FavourateAdapter favourateAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
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
                        for (DataSnapshot data:dataSnapshot.getChildren()){
                            NewlyProductModel model = data.getValue(NewlyProductModel.class);
                            list.add(model);
                            favourateAdapter =new FavourateAdapter(list,getApplicationContext());
                            recyclerView.setAdapter(favourateAdapter);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
