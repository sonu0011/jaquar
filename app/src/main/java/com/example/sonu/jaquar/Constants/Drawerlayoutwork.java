package com.example.sonu.jaquar.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sonu.jaquar.FavouritesActivity;
import com.example.sonu.jaquar.HomeActivity;
import com.example.sonu.jaquar.LoginActivity;
import com.example.sonu.jaquar.OrdersHistory;
import com.example.sonu.jaquar.ProductsActivity;
import com.example.sonu.jaquar.R;
import com.example.sonu.jaquar.ShowByCategoty;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by sonu on 14/8/18.
 */

public class Drawerlayoutwork{
    private final String userEmail;
    private  ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Context context;
    private TextView gallery;
    private TextView ProfileImage;
    private ImageView imageView;
    private TextView newarrivals;

    public Drawerlayoutwork(Context context,DrawerLayout drawerLayout, NavigationView navigationView) {
        this.drawerLayout = drawerLayout;
        this.navigationView = navigationView;
        this.context =context;
        toggle =new ActionBarDrawerToggle((Activity) context,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        AddValue();
        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();



    }

    private void AddValue() {

        gallery = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_profile));
        initializeCountDrawer();

        NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(HomeActivity.this, DividerItemDecoration.VERTICAL);
//        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.drawablerectangle));
//        navMenuView.addItemDecoration(dividerItemDecoration);
        ProfileImage = navigationView.getHeaderView(0).findViewById(R.id.userNameProfile);
        imageView = navigationView.getHeaderView(0).findViewById(R.id.imageViewProfile);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.profilelogo);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        ProfileImage.setText(userEmail);
        imageView.setImageDrawable(roundedBitmapDrawable);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_Home){
                    context.startActivity(new Intent(context,HomeActivity.class));
                }

                if (id == R.id.nav_Products) {
                    context.startActivity(new Intent(context, ProductsActivity.class));

                }
                if (id == R.id.nav_logout) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.Dialog);
                    builder
                            .setIcon(context.getResources().getDrawable(R.drawable.logout))
                            .setTitle("Log out").setMessage("Are you sure you want to Log out?")
                            .setCancelable(false)

                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    FirebaseAuth.getInstance().signOut();

                                    Intent i = new Intent(context, LoginActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    context.startActivity(i);
                                    ((Activity)context).finish();


                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();


                }
                if (id == R.id.nav_share) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Jaquar App");
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.agbe.jaquar");
                    context.startActivity(Intent.createChooser(intent, "Share Via"));
                }
                if (id == R.id.nav_profile) {
                    context.startActivity(new Intent(context, FavouritesActivity.class));
                }
                if (id == R.id.orderHIstory) {

                    context.startActivity(new Intent(context, OrdersHistory.class));

                }
                return false;
            }
        });
    }
    private void initializeCountDrawer() {
        newarrivals = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_Products));

        newarrivals.setGravity(Gravity.CENTER_VERTICAL);
        newarrivals.setTypeface(null, Typeface.BOLD);
        newarrivals.setTextColor(Color.RED);
        newarrivals.setText("4");
        gallery.setGravity(Gravity.CENTER_VERTICAL);
        gallery.setTypeface(null, Typeface.BOLD);
        gallery.setTextColor(Color.RED);

        FirebaseDatabase.getInstance().getReference("favourites").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        long count = dataSnapshot.getChildrenCount();
                        gallery.setText(String.valueOf(count));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


}
