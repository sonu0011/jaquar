package com.example.sonu.jaquar.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.HomeActivity;
import com.example.sonu.jaquar.Interfaces.MyRecyclerClickListner;
import com.example.sonu.jaquar.R;

/**
 * Created by sonu on 28/6/18.
 */

public class NewlyProductAdapter extends RecyclerView.ViewHolder{
    View view;
    public NewlyProductAdapter(View itemView) {
        super(itemView);
        view = itemView;
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
    private NewlyProductAdapter.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(NewlyProductAdapter.ClickListener clickListener){
        mClickListener = clickListener;
    }


    public  void SetDetails(Context context,String image,String title,String productcode,String price)
    {
       ImageView imageView = view.findViewById(R.id.newlyImageview);
      TextView textViewtitle = view.findViewById(R.id.newlytitel);
      TextView textViewcode = view.findViewById(R.id.newlyProductCode);
      TextView textViewprice = view.findViewById(R.id.newlyProductPrice);
        Glide.with(context).load(image).into(imageView);
        textViewtitle.setText(title);
        textViewcode.setText(productcode);
        textViewprice.setText(price);

    }

}
