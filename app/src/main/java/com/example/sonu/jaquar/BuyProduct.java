package com.example.sonu.jaquar;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.Constants.CheckInternetCoonnection;
import com.example.sonu.jaquar.Constants.SearchConstants;
import com.example.sonu.jaquar.Models.SingelProductModel;
import com.example.sonu.jaquar.Receivers.InternetReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BuyProduct extends AppCompatActivity {
    private static final String TAG ="BuyProduct" ;
    ImageView buyImageView;
Toolbar toolbar;
TextView buyTitle,buyPrice,buyProductcode,cartValue;
Button addtocart,buynow;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
FirebaseUser firebaseUser;
FirebaseAuth firebaseAuth;
int ProductCount =0;
String uid;
int checkstatus=0;
SQLiteDatabase sqLiteDatabase;
int whish =0;
int checkProduct =0;
    String image,price,title,productcode;
    CoordinatorLayout coordinatorLayout;
    private AlertDialog.Builder mbuilder;
    private InternetReceiver internetReceiver;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_product);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        internetReceiver = new InternetReceiver();
        registerReceiver(internetReceiver, intentFilter);
            cartValue = findViewById(R.id.cartValue);
            coordinatorLayout = findViewById(R.id.adtocartCordinaterlayout);
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();
            databaseReference = firebaseDatabase.getReference();
            buyImageView = findViewById(R.id.buyProductImage);
            toolbar = findViewById(R.id.buyProductToolbar);
            buyTitle = findViewById(R.id.buyProductTitle);
            buyPrice = findViewById(R.id.buyProductGetProductPrice);
            buyProductcode = findViewById(R.id.buyProductGetProductCode);
            addtocart = findViewById(R.id.buyProductAddtocartBtn);
            buynow = findViewById(R.id.buyProductBuyNowBtn);
            image = getIntent().getStringExtra("image");
            title = getIntent().getStringExtra("title");
            price = getIntent().getStringExtra("price");
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("cartValues").child(firebaseUser.getUid());
            productcode = getIntent().getStringExtra("productcode");
            toolbar.setTitle("Add to Cart ");
            toolbar.setTitleTextColor(Color.WHITE);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setBackgroundColor(getResources().getColor(R.color.toobarCOlor));
            //toolbar.inflateMenu(R.menu.shoppingcart);
            if (image != null && title != null && price != null && productcode != null) {
                Log.d("IntentValuesAre", image + "  " + title + "  " + price + "  " + buyProductcode);
                Glide.with(BuyProduct.this).load(image).placeholder(R.drawable.loader).into(buyImageView);
                buyImageView.setClipToOutline(true);
                buyTitle.setText(title);
                buyPrice.setText(price + ".00");
                buyProductcode.setText(productcode);
            }
            cartValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (SearchConstants.Productcount > 0) {
                        startActivity(new Intent(BuyProduct.this, CheckoutActivity.class));



                }
            });
            buynow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: ");
                    FirebaseDatabase fd =FirebaseDatabase.getInstance();
                     DatabaseReference db =fd.getReference("categoreis");
                     final DatabaseReference dbref =fd.getReference();
                    Query query =db.limitToFirst(4);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot data:dataSnapshot.getChildren())
                            {
                                for (DataSnapshot data1:data.getChildren())
                                {
                                   for (DataSnapshot data2:data1.getChildren())
                                   {
                                      data2.getRef().limitToFirst(4)
                                              .addListenerForSingleValueEvent(new ValueEventListener() {
                                                  @Override
                                                  public void onDataChange(DataSnapshot dataSnapshot) {
                                                      for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                                          String code =dataSnapshot1.child("productcode").getValue(String.class);
                                                          String whishlist =dataSnapshot1.child("whishlist").getValue(String.class);
                                                          if (productcode.equals(code))
                                                          {
                                                              Log.d(TAG, "onDataChange:first four matches");
                                                              Log.d(TAG, "onDataChange: whishlist value "+whishlist);
                                                              checkstatus=1;
                                                              if (Integer.valueOf(whishlist) == 0)
                                                              {

                                                                  DatabaseReference databaseReference1 = dbref.child("favourites").child((FirebaseAuth.getInstance().getCurrentUser().getUid())).child(productcode);
                                                                  databaseReference1.child("title").setValue(title);
                                                                  databaseReference1.child("image").setValue(image);
                                                                  databaseReference1.child("price").setValue(price);
                                                                  databaseReference1.child("productcode").setValue(productcode);
                                                                  dataSnapshot1.getRef().child("whishlist").setValue("1");
                                                                  Snackbar.make(coordinatorLayout,"Added to Whishlist",Snackbar.LENGTH_SHORT).show();


                                                              }
                                                              else{
                                                                  Snackbar.make(coordinatorLayout,"Product is already in whishlist",Snackbar.LENGTH_SHORT).show();
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
                            }



                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    Log.d(TAG, "onDataChange:value of checkstatus"+checkstatus);
                    if (checkstatus == 0)
                    {
                       Query query1 =db.limitToLast(2);
                       query1.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot data:dataSnapshot.getChildren())
                            {
                                for (DataSnapshot dat:data.getChildren()){
                                    String pcode  =dat.child("productcode").getValue(String.class);
                                    String whishlist =dat.child("whishlist").getValue(String.class);

                                    if (productcode.equals(pcode))
                                    {
                                        Log.d(TAG, "onDataChange: lsttwo mathces");
                                        Log.d(TAG, "onDataChange: whishlist value "+whishlist);

                                        if (Integer.valueOf(whishlist) == 0)
                                        {
                                            DatabaseReference databaseReference1 = dbref.child("favourites").child((FirebaseAuth.getInstance().getCurrentUser().getUid())).child(productcode);
                                            databaseReference1.child("title").setValue(title);
                                            databaseReference1.child("image").setValue(image);
                                            databaseReference1.child("price").setValue(price);
                                            databaseReference1.child("productcode").setValue(productcode);
                                            dat.getRef().child("whishlist").setValue("1");
                                            Snackbar.make(coordinatorLayout,"Added to Whishlist",Snackbar.LENGTH_SHORT).show();

                                        }
                                        else {
                                            Snackbar.make(coordinatorLayout,"Product is already in whishlist",Snackbar.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                            }
                           }

                           @Override
                           public void onCancelled(DatabaseError databaseError) {

                           }
                       });
                    }

//
//                    Log.d(TAG, "onClick: ");
//                        fd = FirebaseDatabase.getInstance();
//                        db = fd.getReference("categoreis");
//                        db.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                for (DataSnapshot ds : dataSnapshot.child("Accessories ").child("SubAccessories").child("Accessories (For Disable-friendly Bathroom)").getChildren()) {
//                                    {        String pcode = ds.child("productcode").getValue(String.class);
//                                        String fav = ds.child("whishlist").getValue(String.class);
//                                        if (pcode != null) {
//                                            if (pcode.equals(productcodev)) {
//                                                if (fav != null) {
//                                                    if (fav.equals("0")) {
//                                                        i = 1;
//                                                        Log.d("fav", "yes");
//                                                        firebaseDatabase = FirebaseDatabase.getInstance();
//                                                        databaseReference = firebaseDatabase.getReference();
//                                                        DatabaseReference databaseReference1 = databaseReference.child("favourites").child((FirebaseAuth.getInstance().getCurrentUser().getUid())).child(productcodev);
//                                                        databaseReference1.child("title").setValue(title);
//                                                        databaseReference1.child("image").setValue(image);
//                                                        databaseReference1.child("price").setValue(price);
//                                                        databaseReference1.child("productcode").setValue(productcodev);
//                                                        int color = Color.parseColor("#FF0000");
//                                                        wishlistimage.setColorFilter(color);
//                                                        ds.getRef().child("whishlist").setValue("1");
//                                                        Snackbar.make(coordinatorLayout,"Added to Whishlist",Snackbar.LENGTH_SHORT).show();
//
//                                                    }
//
//
//                                                    if (fav.equals("1")) {
//                                                        int color = Color.parseColor("#39000000");
//                                                        wishlistimage.setColorFilter(color);
//                                                        ds.getRef().child("whishlist").setValue("0");
//                                                        FirebaseDatabase.getInstance().getReference("favourites").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                                                .addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                                                        for (DataSnapshot val : dataSnapshot.getChildren()) {
//                                                                            String code = val.child("productcode").getValue(String.class);
//                                                                            if (code.equals(productcodev)) {
//                                                                                val.getRef().removeValue();
//                                                                            }
//                                                                        }
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                    }
//                                                }
//
//                                            }
//                                        }
//
//                                    }
//
//                                }
//                            }
//
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//

//                    if (SearchConstants.Productcount > 0) {
//                        startActivity(new Intent(BuyProduct.this, CheckoutActivity.class));
//                    } else {
//                        Toast.makeText(BuyProduct.this, "Cart is Empty!!!", Toast.LENGTH_SHORT).show();
//
//                    }

                }
            });
        }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void addToCart() {
        Log.d("##elseValueBefore", String.valueOf(SearchConstants.CheckProduct));
        SearchConstants.Productcount =SearchConstants.Productcount+1;
        Log.d("!!!!","Add to cart");
        Log.d("New Item ","COUNT"+String.valueOf(SearchConstants.Productcount));
        DatabaseReference ref  =FirebaseDatabase.getInstance().getReference("cartValues").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference child_db =ref.child(productcode);
        child_db.child("image").setValue(image);
        child_db.child("title").setValue(title);
        child_db.child("price").setValue(price);
        child_db.child("productcode").setValue(productcode);
        child_db.child("quantity").setValue("1");
        child_db.child("totalprice").setValue(price);
        cartValue.setText(String.valueOf(SearchConstants.Productcount));
        Snackbar.make(coordinatorLayout,"Added to cart successfully",Snackbar.LENGTH_SHORT).show();
        //Toast.makeText(BuyProduct.this, title+" is added to cart successfully", Toast.LENGTH_SHORT).show();
        Log.d("##elseProductValueAfter", String.valueOf(SearchConstants.CheckProduct));


    }
    @Override
    protected void onStart() {
        super.onStart();

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    long count = dataSnapshot.getChildrenCount();
                    SearchConstants.Productcount = (int) count;
                    cartValue.setText(String.valueOf(SearchConstants.Productcount));
                    Log.d("TotalProducts", String.valueOf(SearchConstants.Productcount));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    cartValue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (SearchConstants.Productcount > 0) {
                                startActivity(new Intent(BuyProduct.this, CheckoutActivity.class));
                            } else {
                                Toast.makeText(BuyProduct.this, "Cart is Empty!!!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            });
            addtocart.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseUser = firebaseAuth.getCurrentUser();
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference("cartValues").child(firebaseUser.getUid());
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Log.d("insideDataChange", "yes");
                                String titleValue = ds.child("productcode").getValue(String.class);
                                if (productcode.equals(titleValue)) {
                                    Log.d("Data Matched", "yes");
                                    checkProduct = 1;
                                    Log.d("CheckProductMatched", String.valueOf(checkProduct));
                                    Snackbar.make(coordinatorLayout, "Product is already in cart", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                            if (checkProduct == 0) {
                                Log.d("insideCheckProduct", String.valueOf(checkProduct));
                                addToCart();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }


            });

        }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (internetReceiver != null) {
            unregisterReceiver(internetReceiver);
        }
        checkProduct=0;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        checkProduct=0;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        checkProduct=0;
    }
}
