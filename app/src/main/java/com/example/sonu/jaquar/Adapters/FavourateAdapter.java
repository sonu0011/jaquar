package com.example.sonu.jaquar.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.BuyProduct;
import com.example.sonu.jaquar.Models.NewlyProductModel;
import com.example.sonu.jaquar.Models.SingelProductModel;
import com.example.sonu.jaquar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by sonu on 26/7/18.
 */

public class FavourateAdapter extends RecyclerView.Adapter<FavourateAdapter.ViewHolder> {
    List<NewlyProductModel> list;
    Context ctx;

    public FavourateAdapter(List<NewlyProductModel> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

return new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.favlayout,parent,false),list);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NewlyProductModel singelProductModel = list.get(position);
        Glide.with(ctx).load(singelProductModel.getImage()).placeholder(R.drawable.loader).into(holder.image);
        holder.price.setText(singelProductModel.getPrice()+".00");
        holder.title.setText(singelProductModel.getTitle());
        holder.productcode.setText(singelProductModel.getProductcode());
        holder.getData(position);



    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title,productcode,price,viewMore;
        Button deletefav;

        public ViewHolder(View itemView, final List<NewlyProductModel> newlyProductModels) {
            super(itemView);
            image =itemView.findViewById(R.id.newlyImageview);
            title =itemView.findViewById(R.id.newlytitel);
            productcode =itemView.findViewById(R.id.newlyProductCode);
            price =itemView.findViewById(R.id.newlyProductPrice);
            viewMore =itemView.findViewById(R.id.newlyViewMore);
            deletefav =itemView.findViewById(R.id.newlyDeletefav);
            deletefav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder  builder = new AlertDialog.Builder(ctx,R.style.Dialog);

                    builder.setTitle("Delete");
                    builder.setMessage("Do you want to Delete ? ");
                    builder.setIcon(R.drawable.deletewhite);

                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            //your deleting code
                            if (newlyProductModels.size()>0) {
                                newlyProductModels.remove(getAdapterPosition());
                                notifyItemChanged(getAdapterPosition());
                                notifyItemRangeRemoved(getAdapterPosition(), newlyProductModels.size());
                            }
                            FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
                            FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
                            FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
                            final DatabaseReference databaseReference = firebaseDatabase.getReference("favourites").child(firebaseUser.getUid());
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for(DataSnapshot ds:dataSnapshot.getChildren())
                                    {
                                        databaseReference.child(productcode.getText().toString()).removeValue();

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }

                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    });
                    builder.create();
                    builder.show();
                    Log.d("button clicked","yes");
                }
            });

        }
        public void getData(final int position)
        {
            viewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NewlyProductModel singelProductModel = list.get(position);
                    String title =  singelProductModel.getTitle();
                    String image = singelProductModel.getImage();
                    String price = singelProductModel.getPrice();
                    String productcode = singelProductModel.getProductcode();
                    Log.d("clickevent",title+image+price+productcode);
                    Intent intent =new Intent(ctx, BuyProduct.class);
                    intent.putExtra("title",title);
                    intent.putExtra("image",image);
                    intent.putExtra("price",price);
                    intent.putExtra("productcode",productcode);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(intent);
                }
            });
        }
    }
}
