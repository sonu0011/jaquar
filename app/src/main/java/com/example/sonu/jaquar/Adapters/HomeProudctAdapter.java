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
import com.example.sonu.jaquar.Models.HomeDataModel;
import com.example.sonu.jaquar.Models.NewlyProductModel;
import com.example.sonu.jaquar.Models.SingelProductModel;
import com.example.sonu.jaquar.R;

import java.util.List;

/**
 * Created by sonu on 24/7/18.
 */

public class HomeProudctAdapter extends RecyclerView.Adapter<HomeProudctAdapter.MyViewHolder> {
    Context context;
    List<NewlyProductModel> newList;

    public HomeProudctAdapter(Context context, List<NewlyProductModel> newList) {
        this.context = context;
        this.newList = newList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.newlyproductlayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NewlyProductModel data = newList.get(position);
        Glide.with(context).load(data.getImage()).placeholder(R.drawable.loader).dontAnimate().into(holder.productImage);
        holder.productcode.setText(data.getProductcode());
        holder.productprice.setText(data.getPrice());
        holder.producttitle.setText(data.getTitle());
        holder.getData(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView producttitle,productprice,productcode;
        public MyViewHolder(View itemView) {
            super(itemView);
            productImage =itemView.findViewById(R.id.newlyImageview);
            productprice =itemView.findViewById(R.id.newlyProductPrice);
            productcode =itemView.findViewById(R.id.newlyProductCode);
            producttitle =itemView.findViewById(R.id.newlytitel);
        }
        public void getData(final int position)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NewlyProductModel singelProductModel = newList.get(position);
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
