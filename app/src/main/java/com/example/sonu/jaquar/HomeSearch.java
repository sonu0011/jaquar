package com.example.sonu.jaquar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sonu.jaquar.Adapters.SIngleProductAdapter;
import com.example.sonu.jaquar.Models.SearchModel;
import com.example.sonu.jaquar.Models.SingelProductModel;
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
    EditText searchEditText;
    List<SingelProductModel> list;
    RecyclerView searchrecycleview;
    SIngleProductAdapter sIngleProductAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        list =new ArrayList<>();
        progressDialog =new ProgressDialog(HomeSearch.this);
        searchrecycleview =findViewById(R.id.searchRecycleview);
        searchrecycleview.setHasFixedSize(true);
        searchrecycleview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHomeSearch);
        toolbar.setTitle("Jaquor.com");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toobarCOlor));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        searchEditText =findViewById(R.id.searchfromfirebase);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               // String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                //Query query = FirebaseDatabase.getInstance().getReference(uid).orderByChild("title").startAt(editable.toString()).endAt(editable.toString()+"\uf888");
                List<SingelProductModel> newlist =new ArrayList<>();
                for (SingelProductModel data:list)
                {
                    if(data.getTitle().toLowerCase().contains(editable.toString().toLowerCase()))
                    {
                        newlist.add(data);
                    }
                }
                sIngleProductAdapter.filterList(newlist);



            }
        });
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
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("categoreis");
        Query query =  FirebaseDatabase.getInstance().getReference("categoreis").limitToFirst(4);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    for (DataSnapshot data:ds.getChildren())
                    {
                        for (DataSnapshot subdata:data.getChildren())
                        {subdata.getRef().limitToFirst(3).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot value:dataSnapshot.getChildren()) {

                                    Log.d("firebaseValue", value.toString());
                                    String image = value.child("image").getValue(String.class);
                                    String productcode = value.child("productcode").getValue(String.class);
                                    String price = value.child("price").getValue(String.class);
                                    String title = value.child("title").getValue(String.class);
                                    if (image != null && productcode != null && price != null && title != null) {
                                        list.add(new SingelProductModel(image,price,productcode,title));
                                    }
                                }
                                progressDialog.dismiss();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        sIngleProductAdapter =new SIngleProductAdapter(getApplicationContext(),list);
        searchrecycleview.setAdapter(sIngleProductAdapter);
       //progressDialog.dismiss();
    }
}
