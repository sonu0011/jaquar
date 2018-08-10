package com.example.sonu.jaquar.Adapters;

import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.CheckoutActivity;
import com.example.sonu.jaquar.Constants.SearchConstants;
import com.example.sonu.jaquar.Models.CheckOutMOdel;
import com.example.sonu.jaquar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by sonu on 2/7/18.
 */

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.ViewHOlder> {
    Context context;
    List<CheckOutMOdel> list;
    CheckOutMOdel checkOutMOdel;
    String _title;
    int price;
    int priceAgain;
    int selecteditemd = 0;
    String tag ="0001CheckoutAdapter";
    ArrayAdapter<String> stringArrayAdapter;
//    Cllback cllback;
int quantity1,quantity,resultValue,result1;
    int remainderPrice;
TextView tpriceValue;
    public CheckOutAdapter() {

    }

    public CheckOutAdapter(Context context, List<CheckOutMOdel> list) {
        this.context = context;
        this.list = list;
        Log.d(tag,"constructorAdapter");
       // View view = LayoutInflater.from(context).inflate(R.layout.activity_checkout,null);
        //tpriceValue = view.findViewById(R.id.totalPriceValue);
        //Toast.makeText(context, ""+tpriceValue.getText().toString(), Toast.LENGTH_SHORT).show();
    }
    @NonNull
    @Override
    public ViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(tag,"ViewHolder");
        ViewHOlder viewHOlder =null;
        View view =LayoutInflater.from(context).inflate(R.layout.checkoutlayout, parent, false);
        viewHOlder =new ViewHOlder(view,list);
        return viewHOlder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHOlder holder, final int position) {
        Log.d(tag,"OnBindViewHolder");
        checkOutMOdel = list.get(position);
        Glide.with(context).load(checkOutMOdel.getimage()).placeholder(R.drawable.loader).dontAnimate().into(holder.imageView);

        holder.price.setText(checkOutMOdel.gettotalpricequantity()+".00");
        holder.title.setText(checkOutMOdel.getTitle());
        holder.productcode.setText(checkOutMOdel.getProductcode());
        holder.countTextview.setText(checkOutMOdel.getQuantity());
        priceAgain = Integer.valueOf(checkOutMOdel.getPrice());
        final int c_position=position;
    final CheckOutMOdel Model =  list.get(position);
    holder.incbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            IncreaseItem(holder,Model);

        }
    });
    holder.decbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DecreaseItem(holder);
        }
    });
    }

    private void DecreaseItem(final ViewHOlder holder) {
        final String pcode1 = holder.productcode.getText().toString();
        Log.d("DecreaseProductcode",pcode1);
        final String uid1 =  FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference("cartValues").child(uid1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                    if(Integer.valueOf(ds1.child("quantity").getValue(String.class))>1) {
                        if (pcode1.equals(ds1.child("productcode").getValue(String.class))) {
                            String totalItemprice1 = ds1.child("totalprice").getValue(String.class);
                            String priceperItem1 = ds1.child("price").getValue(String.class);
                            String totalquantity = ds1.child("quantity").getValue(String.class);
                            Log.d("totalitemPrice", totalItemprice1);
                            Log.d("priceperitem", priceperItem1);
                            int tpi1 = Integer.valueOf(totalItemprice1);//totalprice
                            int ppi1 = Integer.valueOf(priceperItem1);//price per item
                            int qu =Integer.valueOf(totalquantity);//total quantity
                            qu = qu - 1;
                            holder.price.setText(String.valueOf(tpi1-ppi1));
                            holder.countTextview.setText(String.valueOf(qu));
                            HashMap<String, Object> result = new HashMap<>();
                            result.put("quantity", String.valueOf(qu));
                            result.put("totalprice", String.valueOf(ppi1 * qu));
                            FirebaseDatabase.getInstance().getReference("cartValues").child(uid1).child(pcode1).updateChildren(result);
                            FirebaseDatabase.getInstance().getReference("cartValues").child(uid1).child(pcode1).updateChildren(result);

                        }
                    }
                    else {
            Toast.makeText(context, "At least one product must be there", Toast.LENGTH_SHORT).show();

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }
    private void IncreaseItem(final ViewHOlder holder, CheckOutMOdel model) {
        final String pcode = holder.productcode.getText().toString();
        Log.d("increaseProductcode",pcode);
       final String uid =  FirebaseAuth.getInstance().getCurrentUser().getUid();

       FirebaseDatabase.getInstance().getReference("cartValues").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

                   for (DataSnapshot ds : dataSnapshot.getChildren()) {
                       if(Integer.valueOf(ds.child("quantity").getValue(String.class))<5) {
                           if (pcode.equals(ds.child("productcode").getValue(String.class))) {
                               String totalItemprice = ds.child("totalprice").getValue(String.class);
                               String priceperItem = ds.child("price").getValue(String.class);
                               Log.d("totalitemPrice", totalItemprice);
                               Log.d("priceperitem", priceperItem);
                               int tpi = Integer.valueOf(totalItemprice);
                               int ppi = Integer.valueOf(priceperItem);
                               int qty = tpi / ppi;
                               qty = qty + 1;
                               holder.price.setText(String.valueOf(ppi * qty));
                               holder.countTextview.setText(String.valueOf(qty));
                               HashMap<String, Object> result = new HashMap<>();
                               result.put("quantity", String.valueOf(qty));
                               result.put("totalprice", String.valueOf(ppi * qty));
                               FirebaseDatabase.getInstance().getReference("cartValues").child(uid).child(pcode).updateChildren(result);
                               FirebaseDatabase.getInstance().getReference("cartValues").child(uid).child(pcode).updateChildren(result);

                           }
                       }
                       else {
                   Toast.makeText(context, "You can't have more than  5 items of same product", Toast.LENGTH_SHORT).show();

                       }
                   }
               }



           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
    }

    private void DeleteItemFromFirebase(final CheckOutMOdel model) {
        final String pcode = model.getProductcode();
        final int p = list.indexOf(model);
        FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
        FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
     final DatabaseReference databaseReference =firebaseDatabase.getReference(firebaseUser.getUid());
     databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             for(DataSnapshot ds:dataSnapshot.getChildren())
             {
                 Log.d("insideForLoop","yes");
                 String _title =ds.child("productcode").getValue(String.class);
                 if(pcode.equals(_title))
                 {
                     Log.d("productcodeMatches","yes");
                     databaseReference.child(_title).removeValue();
                     list.remove(p);
                     notifyItemRemoved(p);
                     Log.d("DeletionSuccessfull","Deleted");


                 }
             }
         }

         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
     });
    }
    @Override
    public int getItemCount() {
        Log.d(tag,"getItemCount");
        return list.size();

    }
    public class ViewHOlder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, price, productcode,countTextview;

        Button deletebtn,incbtn,decbtn;
        String pcode;
            List<CheckOutMOdel>checkOutMOdels;
        public ViewHOlder(final View itemView, final List<CheckOutMOdel> checkOutMOdels) {

            super(itemView);

            this.checkOutMOdels =checkOutMOdels;
            Log.d(tag,"ViewHolderClass");
            imageView = itemView.findViewById(R.id.checkoutImage);
            title = itemView.findViewById(R.id.checkoutTitle);
            price = itemView.findViewById(R.id.checkoutRsValue);
            incbtn =itemView.findViewById(R.id.increaseItem);
            decbtn =itemView.findViewById(R.id.decreaseItem);
            deletebtn = itemView.findViewById(R.id.checkoutDeleteButton);
            productcode = itemView.findViewById(R.id.checkoutProductcode);
            countTextview =itemView.findViewById(R.id.increaseItemTextView);
           deletebtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   AlertDialog.Builder  builder = new AlertDialog.Builder(context);
                     builder.setTitle("Remove");
                           builder.setMessage("Do you want to Remove ? ");
                           builder.setIcon(R.drawable.ic_delete_black_24dp);

                           builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {

                               public void onClick(DialogInterface dialog, int whichButton) {
                                   //your deleting code
                                   if (checkOutMOdels.size()>0) {
                                       checkOutMOdels.remove(getAdapterPosition());
                                       notifyItemChanged(getAdapterPosition());
                                       notifyItemRangeRemoved(getAdapterPosition(), checkOutMOdels.size());
                                   }
                                   FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
                                   FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
                                   FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
                                   final DatabaseReference databaseReference = firebaseDatabase.getReference("cartValues").child(firebaseUser.getUid());
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
    }
}

