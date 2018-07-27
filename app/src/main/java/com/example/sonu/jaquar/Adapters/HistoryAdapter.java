package com.example.sonu.jaquar.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.BuyProduct;
import com.example.sonu.jaquar.Models.HistoryModel;
import com.example.sonu.jaquar.Models.HomeDataModel;
import com.example.sonu.jaquar.Models.SingelProductModel;
import com.example.sonu.jaquar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by sonu on 27/7/18.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    List<HistoryModel> lst;
    Context ctx;

    public HistoryAdapter(List<HistoryModel> lst, Context ctx) {
        this.lst = lst;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.historykayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
         final HistoryModel model = lst.get(position);
        Glide.with(ctx).load(model.getImage()).placeholder(R.drawable.loader).into(holder.image);
        holder.price.setText(model.getTotalprice());
        holder.title.setText(model.getTitle());
        holder.productcode.setText(model.getProductcode());
        holder.quantity.setText(model.getQuantity());
        FirebaseDatabase.getInstance().getReference("OrdersDate").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds:dataSnapshot.getChildren())
                        {
                            for (DataSnapshot vl:ds.getChildren())
                            {
                                String productcode =vl.child("productcode").getValue(String.class);
                                String date =vl.child("time").getValue(String.class);
                                if (model.getProductcode().equals(productcode))
                                {
                                   holder.date.setText(date);
                                }
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        holder.getData(position);
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title,productcode,price,buyagain,quantity,date;
        public MyViewHolder(View itemView) {
            super(itemView);
            image =itemView.findViewById(R.id.historyimage);
            title =itemView.findViewById(R.id.historytitle);
            productcode =itemView.findViewById(R.id.historyproductcode);
            price =itemView.findViewById(R.id.historypricevalue);
            quantity =itemView.findViewById(R.id.histroyquantityvalue);
            buyagain =itemView.findViewById(R.id.historybuyitagain);
            date =itemView.findViewById(R.id.historydate);
        }
        public void getData(final int position)
        {
            buyagain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HistoryModel singelProductModel = lst.get(position);
                    String title =  singelProductModel.getTitle();
                    String image = singelProductModel.getImage();
                    String price = singelProductModel.getPrice();
                    String productcode = singelProductModel.getProductcode();
                    Log.d("clickevent",title+image+price+productcode);
                    Intent intent =new Intent(ctx, BuyProduct.class);
                    intent.putExtra("title",title);
                    intent.putExtra("image",image);
                    intent.putExtra("price",price);
                    intent.putExtra("productcode",productcode);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(intent);

                }
            });

        }
    }
}
