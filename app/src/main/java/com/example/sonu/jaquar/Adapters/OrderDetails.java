package com.example.sonu.jaquar.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sonu.jaquar.Models.OrderDetailsModel;
import com.example.sonu.jaquar.R;

import java.util.List;

/**
 * Created by sonu on 11/8/18.
 */

public class OrderDetails extends RecyclerView.Adapter<OrderDetails.ViewHolder> {
    public static final String TAG="OrderDetails";
    Context ctx;
    List<OrderDetailsModel> list;

    public OrderDetails(Context ctx, List<OrderDetailsModel> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.orderdetails,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetailsModel model = list.get(position);
        Log.d(TAG, "onBindViewHolder: "+model.getOrder_date()+"  "+model.getOrder_id());

       holder.order_date.setText(model.getOrder_date());
       holder.order_did.setText(model.getOrder_id());
       holder.order_totalprice.setText(model.getTotalprice()+".00");
       if (Integer.valueOf(model.getTotalitems()) == 1) {
           holder.order_totalitems.setText("(" + model.getTotalitems() + " item)");
       }
       else {
           holder.order_totalitems.setText("(" + model.getTotalitems() + " items)");

       }
        holder.getData(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView order_date,order_did,order_totalitems,order_totalprice;
        public ViewHolder(View itemView) {
            super(itemView);
            order_date =itemView.findViewById(R.id.orderDetailsDateValue);
            order_did =itemView.findViewById(R.id.orderDetailsIdValue);
            order_totalitems =itemView.findViewById(R.id.orderDetailsTotalitem);
            order_totalprice =itemView.findViewById(R.id.orderDetailsPriceValue);

        }

        public void getData(int position) {
            final OrderDetailsModel mod =list.get(position);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(ctx,ShowOrdersDetails.class);
                    intent.putExtra("order_id",mod.getOrder_id());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(intent);
                }
            });
        }
    }
}
