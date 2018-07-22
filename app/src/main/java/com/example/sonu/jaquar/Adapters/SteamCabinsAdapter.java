package com.example.sonu.jaquar.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.R;

/**
 * Created by sonu on 1/7/18.
 */

public class SteamCabinsAdapter extends RecyclerView.ViewHolder {
    View view;
    public SteamCabinsAdapter(View itemView) {
        super(itemView);
        view  =itemView;
    }

    public  void SetDetails(Context context, String image, String title, String productcode,String price)
    {
        ImageView imageView = view.findViewById(R.id.singleProductImage);
        TextView textViewtitle = view.findViewById(R.id.singleProductTitle);
        TextView textViewcode = view.findViewById(R.id.singleProductProductcode);
        TextView textViewPrice = view.findViewById(R.id.singleProductPrice);
        Glide.with(context).load(image).placeholder(R.drawable.loader).crossFade().into(imageView);
        textViewtitle.setText(title);
        textViewcode.setText(productcode);
        textViewPrice.setText(price);

    }
}
