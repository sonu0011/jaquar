package com.example.sonu.jaquar.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.R;

/**
 * Created by sonu on 3/7/18.
 */


public class CheckOutRecycler extends RecyclerView.ViewHolder {
    ArrayAdapter<String> stringArrayAdapter;
    View view;
    public CheckOutRecycler(View itemView) {
        super(itemView);
        view =itemView;
    }
    public  void SetDetails(Context context, String image, String title, String productcode, String price)
    {
        ImageView imageView = view.findViewById(R.id.checkoutImage);
        TextView textViewtitle = view.findViewById(R.id.checkoutTitle);
        TextView textViewcode = view.findViewById(R.id.checkoutProductcode);
        TextView textViewPrice = view.findViewById(R.id.checkoutRsValue);
        Glide.with(context).load(image).into(imageView);
        textViewtitle.setText(title);
        textViewcode.setText(productcode);
        textViewPrice.setText(price);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());

            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });
    }
    public interface ClickListener{
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }
    private CheckOutRecycler.ClickListener mClickListener;
    public void setOnClickListener(CheckOutRecycler.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
