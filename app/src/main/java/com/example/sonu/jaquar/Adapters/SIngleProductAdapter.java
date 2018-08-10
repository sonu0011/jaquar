package com.example.sonu.jaquar.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.BuyProduct;
import com.example.sonu.jaquar.Constants.AnimUtill;
import com.example.sonu.jaquar.Interfaces.MyRecyclerClickListner;
import com.example.sonu.jaquar.Models.SingelProductModel;
import com.example.sonu.jaquar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

/**
 * Created by sonu on 1/7/18.
 */

public class SIngleProductAdapter extends RecyclerView.Adapter<SIngleProductAdapter.ViewHolder> {
    int previousPosition=0;
    public static final String TAG ="SingleProdeuctApapter";
    int i=0;
    Context context;
    List<SingelProductModel> list;
       ProgressBar progressBar;
        String categoryname;
        String subcategory;
        String anothercat;
        CoordinatorLayout coordinatorLayout;

    public SIngleProductAdapter(Context context, List<SingelProductModel> list, String categoryname, String subcategory, CoordinatorLayout coordinatorLayout) {
        this.context = context;
        this.list = list;
        this.categoryname = categoryname;
        this.subcategory = subcategory;
        this.coordinatorLayout = coordinatorLayout;
        Log.d(TAG, "SIngleProductAdapter: "+categoryname);
        Log.d(TAG, "SIngleProductAdapter: "+subcategory);
    }

    public SIngleProductAdapter(Context context, List<SingelProductModel> list, String anothercat,CoordinatorLayout cl) {
        this.context = context;
        this.list = list;
        this.anothercat = anothercat;
        Log.d("####an",anothercat+"sonu");
        this.coordinatorLayout =cl;
    }

    public SIngleProductAdapter(Context context, List<SingelProductModel> list, String categoryname, String subcategory) {
        this.context = context;
        this.list = list;
        this.categoryname = categoryname;
        this.subcategory = subcategory;
        Log.d("####cat",categoryname+"sonu");
        Log.d("####sub",subcategory);


    }

    public SIngleProductAdapter(Context context, List<SingelProductModel> list) {
        this.context = context;
        this.list = list;
        progressBar =new ProgressBar(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.singelproduct,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SingelProductModel singelProductModel = list.get(position);
        Glide.with(context).load(singelProductModel.getImage()).placeholder(R.drawable.loader).into(holder.image);
         holder.price.setText(singelProductModel.getPrice()+".00");
        holder.title.setText(singelProductModel.getTitle());
        holder.productcode.setText(singelProductModel.getProductcode());

//        if(position > previousPosition){ // We are scrolling DOWN
//
//          AnimUtill.animate(holder, true);
//
//        }else{ // We are scrolling UP
//
//            AnimUtill.animate(holder, false);
//
//
//        }
//
//        previousPosition = position;


//        int color = Color.parseColor("#AE6118"); //The color u want
//        holder.image.setColorFilter(color);
if(singelProductModel.getWhishlist().equals("1"))
{
    Log.d(TAG, "onBindViewHolder: whishlist:1");
        int color = Color.parseColor("#FF0000"); //The color u want
        holder.wishlistimage.setColorFilter(color);

}



        holder.getData(position);

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public void filterList(List<SingelProductModel> listItem) {
        list=listItem;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image,wishlistimage;
        TextView title,productcode,price,viewMore,whishlisttext;

        private MyRecyclerClickListner myRecyclerClickListner;
        public ViewHolder(final View itemView) {
            super(itemView);
            image =itemView.findViewById(R.id.singleProductImage);
            title =itemView.findViewById(R.id.singleProductTitle);
            productcode =itemView.findViewById(R.id.singleProductProductcode);
            price =itemView.findViewById(R.id.singleProductPrice);
            viewMore =itemView.findViewById(R.id.singleProductViewMore);

            wishlistimage =itemView.findViewById(R.id.whishlistimage);

        }
        public void getData(final int position)
        {
        wishlistimage.setOnClickListener(new View.OnClickListener() {
            FirebaseDatabase firebaseDatabase,fd;
            DatabaseReference databaseReference,databaseReference2,db;
            FirebaseAuth firebaseAuth;
            FirebaseUser firebaseUser;
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                SingelProductModel singelProductModel = list.get(position);
                final String title = singelProductModel.getTitle();
                final String image = singelProductModel.getImage();
                final String price = singelProductModel.getPrice();
                final String productcodev = singelProductModel.getProductcode();
                String whishlist = singelProductModel.getWhishlist();
                if (categoryname == null && anothercat ==null) {
                    fd = FirebaseDatabase.getInstance();
                    db = fd.getReference("categoreis");
                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.child("Accessories ").child("SubAccessories").child("Accessories (For Disable-friendly Bathroom)").getChildren()) {
                                {        String pcode = ds.child("productcode").getValue(String.class);
                                    String fav = ds.child("whishlist").getValue(String.class);
                                    if (pcode != null) {
                                        if (pcode.equals(productcodev)) {
                                            if (fav != null) {
                                                if (fav.equals("0")) {
                                                    i = 1;
                                                    Log.d("fav", "yes");
                                                    firebaseDatabase = FirebaseDatabase.getInstance();
                                                    databaseReference = firebaseDatabase.getReference();
                                                    DatabaseReference databaseReference1 = databaseReference.child("favourites").child((FirebaseAuth.getInstance().getCurrentUser().getUid())).child(productcodev);
                                                    databaseReference1.child("title").setValue(title);
                                                    databaseReference1.child("image").setValue(image);
                                                    databaseReference1.child("price").setValue(price);
                                                    databaseReference1.child("productcode").setValue(productcodev);
                                                    int color = Color.parseColor("#FF0000");
                                                    wishlistimage.setColorFilter(color);
                                                    ds.getRef().child("whishlist").setValue("1");
                                                    Snackbar.make(coordinatorLayout,"Added to Whishlist",Snackbar.LENGTH_SHORT).show();

                                                }


                                                if (fav.equals("1")) {
                                                    int color = Color.parseColor("#39000000");
                                                    wishlistimage.setColorFilter(color);
                                                    ds.getRef().child("whishlist").setValue("0");
                                                    FirebaseDatabase.getInstance().getReference("favourites").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    for (DataSnapshot val : dataSnapshot.getChildren()) {
                                                                        String code = val.child("productcode").getValue(String.class);
                                                                        if (code.equals(productcodev)) {
                                                                            val.getRef().removeValue();
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

                                }

                            }
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

//                    Log.d(TAG, "onClick: newArrivals");
//                    fd = FirebaseDatabase.getInstance();
//                    db = fd.getReference("categoreis");
//                    db.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            for (final DataSnapshot ds : dataSnapshot.child("Accessories ").child("SubAccessories").child("Accessories (For Disable-friendly Bathroom)").getChildren()) {
//                                {
//                                    db.limitToFirst(4).addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//
//                                        }
//                                    });
////                                    Log.d(TAG, "onDataChange datasnap: "+dataSnapshot);
//
//
//                                }
//
//                            }
//                        }
//
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//
//                }
                else if(anothercat !=null) {
                    if (anothercat.equals("Steam Cabins")) {
                        Log.d(TAG, "onClick: steamcabinbs");
//                    Toast.makeText(context, ""+anothercat, Toast.LENGTH_SHORT).show();

                        fd = FirebaseDatabase.getInstance();
                        db = fd.getReference("categoreis");
                        db.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.child("Steam Cabins ").getChildren()) {
                                    {
                                        String pcode = ds.child("productcode").getValue(String.class);
                                        String fav = ds.child("whishlist").getValue(String.class);
                                        if (pcode != null) {
                                            if (pcode.equals(productcodev)) {
                                                if (fav != null) {
                                                    if (fav.equals("0")) {
                                                        i = 1;
                                                        Log.d("fav", "yes");
                                                        firebaseDatabase = FirebaseDatabase.getInstance();
                                                        databaseReference = firebaseDatabase.getReference();
                                                        DatabaseReference databaseReference1 = databaseReference.child("favourites").child((FirebaseAuth.getInstance().getCurrentUser().getUid())).child(productcodev);
                                                        databaseReference1.child("title").setValue(title);
                                                        databaseReference1.child("image").setValue(image);
                                                        databaseReference1.child("price").setValue(price);
                                                        databaseReference1.child("productcode").setValue(productcodev);
                                                        int color = Color.parseColor("#FF0000"); //The color u want
                                                        wishlistimage.setColorFilter(color);
                                                        ds.getRef().child("whishlist").setValue("1");
                                                        Snackbar.make(coordinatorLayout, "Added to Whishlist", Snackbar.LENGTH_SHORT).show();

                                                    }
                                                    if (fav.equals("1")) {
                                                        int color = Color.parseColor("#39000000"); //The color u want
                                                        wishlistimage.setColorFilter(color);
                                                        ds.getRef().child("whishlist").setValue("0");
                                                        FirebaseDatabase.getInstance().getReference("favourites").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                        for (DataSnapshot val : dataSnapshot.getChildren()) {
                                                                            String code = val.child("productcode").getValue(String.class);
                                                                            if (code.equals(productcodev)) {
                                                                                val.getRef().removeValue();
                                                                            }
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(DatabaseError databaseError) {

                                                                    }
                                                                });

                                                        ds.getRef().child("whishlist").setValue("0");

                                                    }
                                                }

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
                    if (anothercat.equals("Whirlpools")) {
                        Log.d(TAG, "onClick: Whirlpools");
                        fd = FirebaseDatabase.getInstance();
                        db = fd.getReference("categoreis");
                        db.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.child("Whirlpools ").getChildren()) {
                                    {
                                        String pcode = ds.child("productcode").getValue(String.class);
                                        String fav = ds.child("whishlist").getValue(String.class);
                                        if (pcode != null) {
                                            if (pcode.equals(productcodev)) {
                                                if (fav != null) {
                                                    if (fav.equals("0")) {
                                                        i = 1;
                                                        Log.d("fav", "yes");
                                                        firebaseDatabase = FirebaseDatabase.getInstance();
                                                        databaseReference = firebaseDatabase.getReference();
                                                        DatabaseReference databaseReference1 = databaseReference.child("favourites").child((FirebaseAuth.getInstance().getCurrentUser().getUid())).child(productcodev);
                                                        databaseReference1.child("title").setValue(title);
                                                        databaseReference1.child("image").setValue(image);
                                                        databaseReference1.child("price").setValue(price);
                                                        databaseReference1.child("productcode").setValue(productcodev);
                                                        int color = Color.parseColor("#FF0000"); //The color u want
                                                        wishlistimage.setColorFilter(color);
                                                        ds.getRef().child("whishlist").setValue("1");
                                                        Snackbar.make(coordinatorLayout, "Added to Whishlist", Snackbar.LENGTH_SHORT).show();

                                                    }


                                                    if (fav.equals("1")) {
                                                        int color = Color.parseColor("#39000000"); //The color u want
                                                        wishlistimage.setColorFilter(color);
                                                        ds.getRef().child("whishlist").setValue("0");
                                                        FirebaseDatabase.getInstance().getReference("favourites").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                        for (DataSnapshot val : dataSnapshot.getChildren()) {
                                                                            String code = val.child("productcode").getValue(String.class);
                                                                            if (code.equals(productcodev)) {
                                                                                val.getRef().removeValue();
                                                                            }
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(DatabaseError databaseError) {

                                                                    }
                                                                });

//                                                ds.getRef().child("whishlist").setValue("0");

                                                    }
                                                }

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
                }


                else{
                    Log.d(TAG, "onClick: 4 categories");

                    fd = FirebaseDatabase.getInstance();
                    db = fd.getReference("categoreis");
                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.child(categoryname+" ").child("SubAccessories").child(subcategory).getChildren()) {
                                {
                                    String pcode = ds.child("productcode").getValue(String.class);
                                    String fav = ds.child("whishlist").getValue(String.class);
                                    if (pcode != null) {
                                        if (pcode.equals(productcodev)) {
                                            if (fav != null) {
                                                if (fav.equals("0")) {
                                                    i = 1;
                                                    Log.d("fav", "yes");
                                                    firebaseDatabase = FirebaseDatabase.getInstance();
                                                    databaseReference = firebaseDatabase.getReference();
                                                    DatabaseReference databaseReference1 = databaseReference.child("favourites").child((FirebaseAuth.getInstance().getCurrentUser().getUid())).child(productcodev);
                                                    databaseReference1.child("title").setValue(title);
                                                    databaseReference1.child("image").setValue(image);
                                                    databaseReference1.child("price").setValue(price);
                                                    databaseReference1.child("productcode").setValue(productcodev);
                                                    int color = Color.parseColor("#FF0000");
                                                    wishlistimage.setColorFilter(color);
                                                    ds.getRef().child("whishlist").setValue("1");
                                                    Snackbar.make(coordinatorLayout,"Added to Whishlist",Snackbar.LENGTH_SHORT).show();

                                                }


                                                if (fav.equals("1")) {
                                                    int color = Color.parseColor("#39000000");
                                                    wishlistimage.setColorFilter(color);
                                                    ds.getRef().child("whishlist").setValue("0");
                                                    FirebaseDatabase.getInstance().getReference("favourites").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    for (DataSnapshot val : dataSnapshot.getChildren()) {
                                                                        String code = val.child("productcode").getValue(String.class);
                                                                        if (code.equals(productcodev)) {
                                                                            val.getRef().removeValue();
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SingelProductModel singelProductModel = list.get(position);
                    String title =  singelProductModel.getTitle();
                    String image = singelProductModel.getImage();
                    String price = singelProductModel.getPrice();
                    String productcode = singelProductModel.getProductcode();
                    Log.d("clickevent",title+image+price+productcode);
                    Intent intent =new Intent(context, BuyProduct.class);
                    intent.putExtra("title",title);
                    intent.putExtra("image",image);
                    intent.putExtra("price",price);
                    intent.putExtra("productcode",productcode);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        }
    }

}
