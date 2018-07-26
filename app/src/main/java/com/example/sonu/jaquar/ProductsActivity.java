package com.example.sonu.jaquar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sonu.jaquar.Adapters.SIngleProductAdapter;
import com.example.sonu.jaquar.Constants.SearchConstants;
import com.example.sonu.jaquar.Models.SingelProductModel;
import com.example.sonu.jaquar.Models.SubAccessoriesModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
String categoryname,subcategory;
Toolbar toolbar;
RecyclerView recyclerView;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
List<SingelProductModel>list;
SIngleProductAdapter sIngleProductAdapter;
SingelProductModel singelProductModel;
ProgressDialog progressDialog;
CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        coordinatorLayout =findViewById(R.id.productsnackbar);
        categoryname = SearchConstants.CATEGOTY_NAME;
        subcategory =SearchConstants.SUBCATEGOTY_NAME;
//        if(categoryname==null)
//        {
//            Log.e("categoryNUll","yes");
//
//            categoryname="Accessories";
//            Log.e("categotyName",categoryname);
//        }
//        Log.d("categotyName",categoryname);
        //Log.d("subCategory",subcategory);
        toolbar =findViewById(R.id.productsToolbar);
        recyclerView =findViewById(R.id.productRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list =new ArrayList<>();
        toolbar =findViewById(R.id.productsToolbar);
        toolbar.setTitle("Jaquar.com");
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchandlogout,menu);
        MenuItem menuItem =menu.findItem(R.id.BothSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("onqueyTextChanged","yes");
                ArrayList<SingelProductModel> itemList  =new ArrayList<>();
                for(SingelProductModel item:list)
                {
                   Log.e("values",item.getTitle());
                   if(item.getTitle().toLowerCase().contains(newText.toLowerCase()))
                   {
                       itemList.add(item);
                   }
                }
                sIngleProductAdapter.filterList(itemList);
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
            Intent i = new Intent(ProductsActivity.this, LoginActivity.class);
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
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference("categoreis");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.e("categotyNameDataSnap",categoryname);

                list.clear();
                if(categoryname==null)
                {
                    for (DataSnapshot ds : dataSnapshot.child("Accessories ").child("SubAccessories").child("Bath Accessories").getChildren()) {

                        String image =ds.child("image").getValue(String.class);
                        String price =ds.child("price").getValue(String.class);
                        String productcode =ds.child("productcode").getValue(String.class);
                        String title =ds.child("title").getValue(String.class);
                        String whishlist    = ds.child("whishlist").getValue(String.class);

                        if (image!=null && price !=null && productcode != null &&title !=null) {
                            Log.d("ppp", image +" "+price+" "+productcode+" "+title);
                            singelProductModel =new SingelProductModel(image,price,productcode,title,whishlist);
                            list.add(singelProductModel);
                            Log.d("listSizeinside", String.valueOf(list.size()));

                        }
                    }
                }
                else {
                    for (DataSnapshot ds : dataSnapshot.child(categoryname + " ").child("SubAccessories").child(subcategory).getChildren()) {

                        String image = ds.child("image").getValue(String.class);
                        String price = ds.child("price").getValue(String.class);
                        String productcode = ds.child("productcode").getValue(String.class);
                        String title = ds.child("title").getValue(String.class);
                        String whishlist    = ds.child("whishlist").getValue(String.class);

                        if (image != null && price != null && productcode != null && title != null) {

                            Log.d("ppp", image + " " + price + " " + productcode + " " + title);
                            singelProductModel = new SingelProductModel(image, price, productcode, title,whishlist);
                            list.add(singelProductModel);
                            Log.d("listSizeinside", String.valueOf(list.size()));
                        }

                    }
                }
                    Log.d("arraylistSize", String.valueOf(list.size()));
                    sIngleProductAdapter =new SIngleProductAdapter(getApplicationContext(),list,categoryname,subcategory,coordinatorLayout);
                    recyclerView.setAdapter(sIngleProductAdapter);
                }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            progressDialog.dismiss();
            }
        });

        }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
