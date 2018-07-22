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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.BuyProduct;
import com.example.sonu.jaquar.Interfaces.MyRecyclerClickListner;
import com.example.sonu.jaquar.Models.SingelProductModel;
import com.example.sonu.jaquar.R;

import java.util.List;

/**
 * Created by sonu on 1/7/18.
 */

public class SIngleProductAdapter extends RecyclerView.Adapter<SIngleProductAdapter.ViewHolder> {
    Context context;
    List<SingelProductModel> list;
       ProgressBar progressBar;

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
         holder.price.setText(singelProductModel.getPrice());
        holder.title.setText(singelProductModel.getTitle());
        holder.productcode.setText(singelProductModel.getProductcode());
        progressBar.setVisibility(View.INVISIBLE);
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
        ImageView image;
        TextView title,productcode,price,viewMore;
        private MyRecyclerClickListner myRecyclerClickListner;
        public ViewHolder(final View itemView) {
            super(itemView);
            image =itemView.findViewById(R.id.singleProductImage);
            title =itemView.findViewById(R.id.singleProductTitle);
            productcode =itemView.findViewById(R.id.singleProductProductcode);
            price =itemView.findViewById(R.id.singleProductPrice);
           viewMore =itemView.findViewById(R.id.singleProductViewMore);

        }
        public void getData(final int position)
        {
            viewMore.setOnClickListener(new View.OnClickListener() {
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
