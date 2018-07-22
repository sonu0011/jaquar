package com.example.sonu.jaquar.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.Constants.SearchConstants;
import com.example.sonu.jaquar.Models.SubAccessoriesModel;
import com.example.sonu.jaquar.ProductsActivity;
import com.example.sonu.jaquar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonu on 30/6/18.
 */

public class SubAccessoriesAdapter extends RecyclerView.Adapter<SubAccessoriesAdapter.ViewHolder> {
    Context context;
    List<SubAccessoriesModel> subAccessoriesModels;

    public SubAccessoriesAdapter(Context context, List<SubAccessoriesModel> subAccessoriesModels) {
        this.context = context;
        this.subAccessoriesModels = subAccessoriesModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.subaccessory,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubAccessoriesModel sa =subAccessoriesModels.get(position);
        holder.textView.setText(sa.getTitle());
        Glide.with(context).load(sa.getImage()).placeholder(R.drawable.loader).crossFade().into(holder.imageView);

    }
    @Override
    public int getItemCount() {
        return subAccessoriesModels.size();
    }

    public void filterlist(ArrayList<SubAccessoriesModel> list1) {
        subAccessoriesModels=list1;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.subcategoryImage);
            textView =itemView.findViewById(R.id.subcategoryTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(context, ProductsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    SearchConstants.SUBCATEGOTY_NAME =textView.getText().toString();
                    context.startActivity(intent);
                    Toast.makeText(context, ""+textView.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
