package com.example.sonu.jaquar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sonu.jaquar.Adapters.SIngleProductAdapter;
import com.example.sonu.jaquar.Adapters.SubAccessoriesAdapter;
import com.example.sonu.jaquar.Models.SingelProductModel;
import com.example.sonu.jaquar.Models.SubAccessoriesModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubCategory extends AppCompatActivity {
String getTitleName;
RecyclerView recyclerView;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
List<SubAccessoriesModel> list;
List<SingelProductModel>listSingleProduct;

SingelProductModel singelProductModel;
SIngleProductAdapter sIngleProductAdapter;
SubAccessoriesAdapter subAccessoriesAdapter;
Toolbar toolbar;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        toolbar =findViewById(R.id.toolbarSubCategory);
        toolbar.setTitle("Jaquar.com");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toobarCOlor));
        setSupportActionBar(toolbar);getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        list =new ArrayList<>();
        listSingleProduct =new ArrayList<>();
        recyclerView =findViewById(R.id.subCategotyRecycView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getTitleName = getIntent().getStringExtra("title");

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        Log.d("getTitleName",getTitleName);
        Log.d("SubCategoty","onCreate");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("SubCategoty","onRestart");
        listSingleProduct.clear();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SubCategoty","onPause");
        listSingleProduct.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SubCategoty","onDestroy");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SubCategoty","onResume");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchandlogout,menu);
        MenuItem menuItem =menu.findItem(R.id.BothSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        //searchView.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("onQueryTextChabge","yes");
                ArrayList<SingelProductModel> _list =new ArrayList<>();
                ArrayList<SubAccessoriesModel>_list1 =new ArrayList<>();
                if (getTitleName.equals("Steam Cabins") || getTitleName.equals("Whirlpools")) {
                        for (SingelProductModel item : listSingleProduct) {
                            if (item.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                                _list.add(item);
                            }
                        }
                        sIngleProductAdapter.filterList(_list);

                    }
                    else {
                    for (SubAccessoriesModel subAccessoriesModel:list)
                    {
                        if(subAccessoriesModel.getTitle().toLowerCase().contains(newText.toLowerCase()))
                        {
                            _list1.add(subAccessoriesModel);
                        }
                    }
                    subAccessoriesAdapter.filterlist(_list1);

                }


                return false;
            }
        });
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.BothSearch)
        {
            Log.d("Serch","yes");

        }
        else {

            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(SubCategory.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
            Log.d("logout","yes");
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {

        super.onStart();

        Log.d("SubCategoty","onStart");
        //Firebase fr =new Firebase("https://console.firebase.google.com/u/0/project/jaquarapp/database/jaquarapp/data/categoreis");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("categoreis");
        Log.d("databaseReference", databaseReference.toString());
        if (getTitleName.equals("Steam Cabins")) {
            Log.d("getTitelName",getTitleName);
            Log.d("DataBaseReference","Steam Cabins");
  databaseReference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
          listSingleProduct.clear();
            for (DataSnapshot ds:dataSnapshot.child(getTitleName+" ").getChildren()) {
//                SingelProductModel singelProductModel =ds.getValue(SingelProductModel.class);
//                listSingleProduct.add(singelProductModel);
                String image = ds.child("image").getValue(String.class);
                String price = ds.child("price").getValue(String.class);
                String productcode = ds.child("productcode").getValue(String.class);
                String title = ds.child("title").getValue(String.class);
                if (image!=null && price !=null && productcode != null &&title !=null) {
                    Log.d("ppp", image +" "+price+" "+productcode+" "+title);
                    //listSingleProduct =new ArrayList<>();

                    singelProductModel =new SingelProductModel(image,price,productcode,title);
                    listSingleProduct.add(singelProductModel);
                    Log.d("listSizeinside", String.valueOf(listSingleProduct.size()));
                }
            }
          Log.d("arraylistSize", String.valueOf(listSingleProduct.size()));

          sIngleProductAdapter =new SIngleProductAdapter(getApplicationContext(),listSingleProduct);
          recyclerView.setAdapter(sIngleProductAdapter);
          ;
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
  });


        }
        if (getTitleName.equals("Whirlpools")) {
            Log.d("getTitelName",getTitleName);
            Log.d("DataBaseReference","Steam Cabins");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listSingleProduct.clear();
                    for (DataSnapshot ds:dataSnapshot.child(getTitleName+" ").getChildren()) {
//                SingelProductModel singelProductModel =ds.getValue(SingelProductModel.class);
//                listSingleProduct.add(singelProductModel);
                        String image = ds.child("image").getValue(String.class);
                        String price = ds.child("price").getValue(String.class);
                        String productcode = ds.child("productcode").getValue(String.class);
                        String title = ds.child("title").getValue(String.class);

                        if (image!=null && price !=null && productcode != null &&title !=null) {
                            Log.d("ppp", image +" "+price+" "+productcode+" "+title);
                            //listSingleProduct =new ArrayList<>();
                            singelProductModel =new SingelProductModel(image,price,productcode,title);
                            listSingleProduct.add(singelProductModel);
                            Log.d("listSizeinside", String.valueOf(listSingleProduct.size()));
                        }
                    }
                    Log.d("arraylistSize", String.valueOf(listSingleProduct.size()));

                    sIngleProductAdapter =new SIngleProductAdapter(getApplicationContext(),listSingleProduct);
                    recyclerView.setAdapter(sIngleProductAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
      else {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.child(getTitleName + " ").child("SubAccessories").getChildren()) {

                        SubAccessoriesModel subAccessoriesModel = ds.getValue(SubAccessoriesModel.class);
                        Log.d("====", subAccessoriesModel.getImage() + subAccessoriesModel.getTitle());
                        list.add(subAccessoriesModel);


                    }
                } else {
                    Log.d("dataExist", "NO");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.toString());

            }
        });
    }

        subAccessoriesAdapter =new SubAccessoriesAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(subAccessoriesAdapter);

    }

}
