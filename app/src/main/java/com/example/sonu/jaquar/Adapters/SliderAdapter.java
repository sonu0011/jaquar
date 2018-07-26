package com.example.sonu.jaquar.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.Models.SliderModel;
import com.example.sonu.jaquar.R;

import java.util.List;

/**
 * Created by sonu on 6/7/18.
 */

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.MyViewHolder> {
    Context context;
    List<SliderModel>list;

    public SliderAdapter(Context context, List<SliderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.sliderayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SliderModel sliderModel = list.get(position);
        Glide.with(context).load(sliderModel.getImage()).into(holder.imageView);
        holder.textView.setText(sliderModel.getTitle());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
//            textView =itemView.findViewById(R.id.sliderText);
//            imageView =itemView.findViewById(R.id.sliderImage);
        }
    }
}
