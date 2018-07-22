package com.example.sonu.jaquar.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.Interfaces.MyRecyclerClickListner;
import com.example.sonu.jaquar.Models.CategoriesModel;
import com.example.sonu.jaquar.R;

import java.util.List;

/**
 * Created by sonu on 29/6/18.
 */

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder>{
 Context context;
 List<CategoriesModel> categoriesModels;

    public CustomRecyclerAdapter(Context context, List<CategoriesModel> categoriesModels) {
        this.context = context;
        this.categoriesModels = categoriesModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categotylayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    final CategoriesModel categoriesModel1 = categoriesModels.get(position);
    holder.textView.setText(categoriesModel1.getName());
        Glide.with(context).load(categoriesModel1.getImage()).into(holder.imageView);
       holder.setData(new MyRecyclerClickListner() {
        @Override
        public void onClick(View view, int positon) {
           TextView textView = view.findViewById(R.id.firebasetext);
            Log.d("textview",""+textView.getText().toString());
            Toast.makeText(context, ""+textView.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    });
    }

    @Override
    public int getItemCount() {
        return categoriesModels.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyRecyclerClickListner myRecyclerClickListner;
        TextView textView;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.firebasetext);
            imageView =itemView.findViewById(R.id.firebaseImage);
        }

        @Override
        public void onClick(View view) {
            myRecyclerClickListner.onClick(view,getAdapterPosition());
        }
        public void setData(MyRecyclerClickListner myRecyclerClickListner)
        {
            this.myRecyclerClickListner =myRecyclerClickListner;
        }
    }

}
