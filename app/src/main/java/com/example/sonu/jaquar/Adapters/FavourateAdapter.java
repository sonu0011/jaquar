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
import com.example.sonu.jaquar.Models.NewlyProductModel;
import com.example.sonu.jaquar.Models.SingelProductModel;
import com.example.sonu.jaquar.R;

import java.util.List;

/**
 * Created by sonu on 26/7/18.
 */

public class FavourateAdapter extends RecyclerView.Adapter<FavourateAdapter.ViewHolder> {
    List<NewlyProductModel> list;
    Context ctx;

    public FavourateAdapter(List<NewlyProductModel> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

return new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.newlyproductlayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NewlyProductModel singelProductModel = list.get(position);
        Glide.with(ctx).load(singelProductModel.getImage()).placeholder(R.drawable.loader).into(holder.image);
        holder.price.setText(singelProductModel.getPrice()+".00");
        holder.title.setText(singelProductModel.getTitle());
        holder.productcode.setText(singelProductModel.getProductcode());
        holder.getData(position);



    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title,productcode,price,viewMore;

        public ViewHolder(View itemView) {
            super(itemView);
            image =itemView.findViewById(R.id.newlyImageview);
            title =itemView.findViewById(R.id.newlytitel);
            productcode =itemView.findViewById(R.id.newlyProductCode);
            price =itemView.findViewById(R.id.newlyProductPrice);
            viewMore =itemView.findViewById(R.id.newlyViewMore);

        }
        public void getData(final int position)
        {
            viewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NewlyProductModel singelProductModel = list.get(position);
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
