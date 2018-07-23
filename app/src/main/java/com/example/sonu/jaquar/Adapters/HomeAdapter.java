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
import com.example.sonu.jaquar.Models.SingelProductModel;
import com.example.sonu.jaquar.R;

import java.util.List;

/**
 * Created by sonu on 9/7/18.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
List<HomeDataModel>homeDataModels;
Context context;

    public HomeAdapter(List<HomeDataModel> homeDataModels, Context context) {
        this.homeDataModels = homeDataModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==R.layout.sliderayout)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sliderayout,parent,false);
            SliderViewHolder sliderViewHolder =new SliderViewHolder(view);
            return sliderViewHolder;
        }
        else {
            View view =LayoutInflater.from(context).inflate(R.layout.newlyproductlayout,parent,false);
            NewProductViewHolder newProductViewHolder =new NewProductViewHolder(view);
            return newProductViewHolder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof SliderViewHolder)
        {
           HomeDataModel  homeDataModel =  homeDataModels.get(position);
            Glide.with(context).load(homeDataModel.getSliderimage()).into(((SliderViewHolder) holder).sliderimage);
            ((SliderViewHolder) holder).sliderTitle.setText(homeDataModel.getSlidertitle());
        }
        if(holder instanceof NewProductViewHolder)
        {
            HomeDataModel data = homeDataModels.get(position);
            Glide.with(context).load(data.getImage()).placeholder(R.drawable.loader).dontAnimate().into(((NewProductViewHolder) holder).productImage);
            ((NewProductViewHolder) holder).productcode.setText(data.getProductcode());
            ((NewProductViewHolder) holder).productprice.setText(data.getPrice());
            ((NewProductViewHolder) holder).producttitle.setText(data.getTitle());
            ((NewProductViewHolder) holder).getData(position);

        }


    }

    @Override
    public int getItemCount() {
        return homeDataModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
        {
            return R.layout.sliderayout;
        }
        else {
            return R.layout.newlyproductlayout;
        }
         }

    private class  SliderViewHolder extends RecyclerView.ViewHolder
    {
        ImageView sliderimage;
        TextView sliderTitle;

        public SliderViewHolder(View itemView) {
            super(itemView);
            sliderimage =itemView.findViewById(R.id.sliderImage);
            sliderTitle =itemView.findViewById(R.id.sliderText);
        }
    }
    public class NewProductViewHolder extends RecyclerView.ViewHolder
    {
        ImageView productImage;
        TextView producttitle,productprice,productcode;
        public NewProductViewHolder(View itemView) {
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
                    HomeDataModel singelProductModel = homeDataModels.get(position);
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
