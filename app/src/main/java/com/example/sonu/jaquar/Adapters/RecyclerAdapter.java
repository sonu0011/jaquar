package com.example.sonu.jaquar.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.Models.GetValue;
import com.example.sonu.jaquar.R;

/**
 * Created by sonu on 27/6/18.
 */

public class RecyclerAdapter extends RecyclerView.ViewHolder {
    View view;
    GetValue getValue;
    public RecyclerAdapter(View itemView) {
        super(itemView);
        view =itemView;
        getValue =new GetValue();
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
    public  void SetDetails(Context context,String title,String image)
    {
      TextView textView = view.findViewById(R.id.firebasetext);
      ImageView imageView = view.findViewById(R.id.firebaseImage);
      textView.setText(title);
        Glide.with(context).load(image).placeholder(R.drawable.loader).into(imageView);

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
}
