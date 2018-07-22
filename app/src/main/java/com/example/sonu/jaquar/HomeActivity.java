package com.example.sonu.jaquar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.AppBarLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.Adapters.HomeAdapter;
import com.example.sonu.jaquar.Adapters.MyPagerAdapter;
import com.example.sonu.jaquar.Adapters.NewlyProductAdapter;
import com.example.sonu.jaquar.Adapters.PagerAdapter;
import com.example.sonu.jaquar.Adapters.RecyclerAdapter;
import com.example.sonu.jaquar.Adapters.SIngleProductAdapter;
import com.example.sonu.jaquar.Adapters.SliderAdapter;
import com.example.sonu.jaquar.Constants.SearchConstants;
import com.example.sonu.jaquar.Models.HomeDataModel;
import com.example.sonu.jaquar.Models.NewlyProductModel;
import com.example.sonu.jaquar.Models.SingelProductModel;
import com.example.sonu.jaquar.Models.SliderModel;
import com.example.sonu.jaquar.Receivers.InternetReceiver;
import com.example.sonu.jaquar.Service.CartService;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
AppBarLayout appBarLayout;
Button serchByCategotyBtn;
EditText searchAnythingAll;
ViewPager viewPager;
SingelProductModel singelProductModel;
List<HomeDataModel>list;
HomeDataModel homeDataModel;
HomeAdapter homeAdapter;
SIngleProductAdapter sIngleProductAdapter;
//ImageView imageView;
 SearchView searchView ;
 Button searchAnything;
InternetReceiver internetReceiver;
    PagerAdapter pagerAdapter;
    private Handler handler;
    private final int delay = 2000;
    private int page = 0;
//    Runnable runnable = new Runnable() {
//        public void run() {
//            if (pagerAdapter.getCount() == page) {
//                page = 0;
//            } else {
//                page++;
//            }
//            viewPager.setCurrentItem(page, true);
//
//            handler.postDelayed(this, delay);
//        }
//    };
String[] imagesArray;
ArrayList<SliderModel>arrayList;
MyPagerAdapter myPagerAdapter;
int[] imagesDrawable ={R.drawable.image1,R.drawable.image2,R.drawable.image3};
ViewFlipper viewFlipper;
RadioButton radioButton1 ,radioButton2,radioButton3;
RecyclerView sliderRecycleView,NewProductRecycleView;
FirebaseDatabase firebaseDatabase1;
DatabaseReference databaseReference1;
Toolbar toolbar;
TextView cartValue,ProfileImage;
String userEmail;
ImageView imageView;
ProgressDialog progressDialog;
SliderAdapter sliderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//       startService(new Intent(HomeActivity.this,CartService.class));
          searchAnything =findViewById(R.id.searchAnyThing);
          searchAnything.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            startActivity(new Intent(HomeActivity.this,HomeSearch.class));
             overridePendingTransition(R.anim.right_in,R.anim.left_out);

         }
     });
        Log.d("@@@onCreate","yes");
       cartValue =findViewById(R.id.cartValueHome);
        list =new ArrayList<>();
        IntentFilter intentFilter =new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        internetReceiver =new InternetReceiver();
        registerReceiver(internetReceiver,intentFilter);
        FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
        FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase1 =FirebaseDatabase.getInstance();
        DatabaseReference databaseReference1 =firebaseDatabase1.getReference(firebaseUser.getUid());
         userEmail = firebaseUser.getEmail();
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count =  dataSnapshot.getChildrenCount();
                SearchConstants.serviceCartValue= (int) count;
                SearchConstants.Productcount = (int) count;
                cartValue.setText(String.valueOf(SearchConstants.Productcount));
                Log.d("TotalProducts", String.valueOf(SearchConstants.Productcount));
                Log.d("ServiceCartValue", String.valueOf(SearchConstants.serviceCartValue));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        toolbar =findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toobarCOlor));
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.logout);
        arrayList =new ArrayList<>();

        NewProductRecycleView =findViewById(R.id.newArrivalsRecycleView);
        NewProductRecycleView.setHasFixedSize(true);
        NewProductRecycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

       toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId()==R.id.logout);
        {
            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
           i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return false;
    }
});

cartValue.setText(String.valueOf(SearchConstants.Productcount));
cartValue.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (SearchConstants.Productcount > 0) {
            startActivity(new Intent(HomeActivity.this, CheckoutActivity.class));
        }
        else {
            Toast.makeText(HomeActivity.this, "Cart is Empty!!!", Toast.LENGTH_SHORT).show();

        }

    }
});

        appBarLayout =findViewById(R.id.AppBarLayout);
        serchByCategotyBtn = appBarLayout.findViewById(R.id.searchByCategotyBtn);
        //searchAnything =appBarLayout.findViewById(R.id.searchAnything);
        serchByCategotyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomeActivity.this, "button clicked", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(HomeActivity.this,ShowByCategoty.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in,R.anim.left_out);

            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);
        DividerItemDecoration dividerItemDecoration =new DividerItemDecoration(HomeActivity.this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.drawablerectangle));
        navMenuView.addItemDecoration(dividerItemDecoration);
        ProfileImage =navigationView.getHeaderView(0).findViewById(R.id.userNameProfile);
        imageView =navigationView.getHeaderView(0).findViewById(R.id.imageViewProfile);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.profilelogo);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        ProfileImage.setText(userEmail);
        imageView.setImageDrawable(roundedBitmapDrawable);

    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog =new ProgressDialog(HomeActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
     //   setTextValue();
        Log.d("@@@@OnStart","yes");
        firebaseDatabase1 =FirebaseDatabase.getInstance();
        databaseReference1 =firebaseDatabase1.getReference("NewlyProducts");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    Log.d("checkData",ds.toString());
                    String image = ds.child("image").getValue(String.class);
                    String price = ds.child("price").getValue(String.class);
                    String productcode = ds.child("productcode").getValue(String.class);
                    String title = ds.child("title").getValue(String.class);
                    String sliderImage =ds.child("sliderimage").getValue(String.class);
                    String slidertitel =ds.child("slidertitle").getValue(String.class);
                    if (image!=null && price !=null && productcode != null &&title !=null) {
                        Log.d("ppp", image +" "+price+" "+productcode+" "+title);
                        homeDataModel =new HomeDataModel(title,image,price,productcode,sliderImage,slidertitel);
                        list.add(homeDataModel);
                        Log.d("listSizeinside", String.valueOf(list.size()));
                        Log.e("end", String.valueOf(list.size()));
                        homeAdapter =new HomeAdapter(list,HomeActivity.this);
                        NewProductRecycleView.setAdapter(homeAdapter);
                    }
                    progressDialog.dismiss();
                }



                Log.d("arraylistSize", String.valueOf(list.size()));

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setTextValue() {
        FirebaseAuth fa =FirebaseAuth.getInstance();
        FirebaseUser firebaseUser =fa.getCurrentUser();
        FirebaseDatabase fd =FirebaseDatabase.getInstance();
        DatabaseReference db =fd.getReference(firebaseUser.getUid());
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count =  dataSnapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        super.onBackPressed();
        SearchConstants.CheckProduct=0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("oncreateOPtionMenu","yes");
        getMenuInflater().inflate(R.menu.logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ShoppingCartIcon) {
            return true;
        }
        if(id == R.id.logout)
        {
            Log.d("clickd","yes");
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id==R.id.nav_Products)
        {
           startActivity(new Intent(HomeActivity.this,ProductsActivity.class));

        }
        if(id==R.id.nav_logout)
        {
            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();

        }
        if(id == R.id.nav_share){
            Intent intent =new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Jaquar App");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey there! I’m using JaquarApp");
            startActivity(Intent.createChooser(intent,"Share Via"));


        }
        if(id==R.id.nav_profile)
        {
            Toast.makeText(this, "In Working...", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (internetReceiver != null) {
            unregisterReceiver(internetReceiver);
        }
        SearchConstants.Productcount=0;

    }

}
