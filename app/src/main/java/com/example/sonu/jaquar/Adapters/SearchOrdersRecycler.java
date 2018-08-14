package com.example.sonu.jaquar.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.Models.GetValue;
import com.example.sonu.jaquar.Models.HistoryModel;
import com.example.sonu.jaquar.Models.OrderDetailsModel;
import com.example.sonu.jaquar.OrdersHistory;
import com.example.sonu.jaquar.R;

/**
 * Created by sonu on 11/8/18.
 */

public class SearchOrdersRecycler extends RecyclerView.ViewHolder {
    View view;
    HistoryModel getValue;
    public SearchOrdersRecycler(View itemView) {
        super(itemView);
        view =itemView;
        getValue =new HistoryModel();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());

            }
        });

    }
    public  void SetDetails(Context context, String title, String image,String price,String productcode,String quantity)
    {
        TextView h_title,h_price,h_productcode,h_quatntity;
        ImageView h_image;
        h_title =view.findViewById(R.id.historytitle);
        h_image =view.findViewById(R.id.historyimage);
        h_price =view.findViewById(R.id.historypricevalue);
        h_productcode =view.findViewById(R.id.historyproductcode);
        h_quatntity  =view.findViewById(R.id.histroyquantityvalue);
        h_title.setText(title);
        h_price.setText(price);
        h_productcode.setText(productcode);
        Glide.with(context).load(image).into(h_image);
        h_quatntity.setText(quantity);


    }
    private SearchOrdersRecycler.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(SearchOrdersRecycler.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
