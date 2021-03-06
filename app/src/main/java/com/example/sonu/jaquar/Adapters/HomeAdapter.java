package com.example.sonu.jaquar.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.BuyProduct;
import com.example.sonu.jaquar.Models.HomeDataModel;
import com.example.sonu.jaquar.R;

import java.util.List;

/**
 * Created by sonu on 9/7/18.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG ="HomeAdapter";
List<HomeDataModel>homeDataModels;
Context context;
    float x ;
    float y;

    public HomeAdapter(List<HomeDataModel> homeDataModels, Context context) {
        this.homeDataModels = homeDataModels;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==R.layout.flipperlayout)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.flipperlayout,parent,false);
            SliderViewHolder sliderViewHolder =new SliderViewHolder(view);
            return sliderViewHolder;
        }
        else {
            View view =LayoutInflater.from(context).inflate(R.layout.newlyproductlayout,parent,false);
            NewProductViewHolder newProductViewHolder =new NewProductViewHolder(view);
            return newProductViewHolder;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof SliderViewHolder)

        {

            ((SliderViewHolder) holder).viewFlipper.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        x =motionEvent.getX();
                        Log.d(TAG, "onTouch: ActionDown");
                    }
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    {
                        y =motionEvent.getX();

                        Log.d(TAG, "onTouch: ActionUp");
                    }

                    if (motionEvent.getAction() == MotionEvent.ACTION_MOVE)
                    {
                        Log.d(TAG, "onTouch: "+Math.abs(x-y));
                        Log.d(TAG, "onTouch:ActionMove x is"+x+"y is "+y);
                        if (Math.abs(x-y) >900) {
                            if (x > y) {
                                Log.d(TAG, "onTouch: x>y");

                                ((SliderViewHolder) holder).viewFlipper.setInAnimation(context, R.anim.slidein_right);
                                ((SliderViewHolder) holder).viewFlipper.setOutAnimation(context, R.anim.slideout_left);

                                ((SliderViewHolder) holder).viewFlipper.showNext();
                                int i = ((SliderViewHolder) holder).viewFlipper.getDisplayedChild();
                                Log.d(TAG, "onClicknext: " + i);

                                if (i == 0) {
                                    Log.d(TAG, "onBindViewHolder: " + i);
                                    ((SliderViewHolder) holder).r1.setChecked(true);
                                }
                                if (i == 1) {
                                    Log.d(TAG, "onBindViewHolder: " + i);
                                    ((SliderViewHolder) holder).r2.setChecked(true);
                                }
                                if (i == 2) {
                                    Log.d(TAG, "onBindViewHolder: " + i);
                                    ((SliderViewHolder) holder).r3.setChecked(true);
                                }
                            }
                        }
                        if (Math.abs(x-y)<100)
                        {
                            Log.d(TAG, "onTouch:swipe from left to right ");
                            ((SliderViewHolder) holder).viewFlipper.setInAnimation(context, android.R.anim.slide_in_left);
                            ((SliderViewHolder) holder).viewFlipper.setOutAnimation(context,  android.R.anim.slide_out_right);
                            ((SliderViewHolder) holder).viewFlipper.showPrevious();
                            int i =((SliderViewHolder) holder).viewFlipper.getDisplayedChild();
                            Log.d(TAG, "onClickprev: "+i);
                            if (i==0){
                                Log.d(TAG, "onBindViewHolder: "+i);
                                ((SliderViewHolder) holder).r1.setChecked(true);
                            }
                            if (i==1){
                                Log.d(TAG, "onBindViewHolder: "+i);
                                ((SliderViewHolder) holder).r2.setChecked(true);
                            }
                            if (i==2){
                                Log.d(TAG, "onBindViewHolder: "+i);
                                ((SliderViewHolder) holder).r3.setChecked(true);
                            }
                        }
                            
                        
                       
                    }
                  
                    return false;
                }
            });

            ((SliderViewHolder) holder).img.setImageResource(R.drawable.image2);
            ((SliderViewHolder) holder).img1.setImageResource(R.drawable.image3);
            ((SliderViewHolder) holder).imag2.setImageResource(R.drawable.image1);

            int i =((SliderViewHolder) holder).viewFlipper.getDisplayedChild();
            if (i==0){
                Log.d(TAG, "onBindViewHolder: "+i);
                ((SliderViewHolder) holder).r1.setChecked(true);
            }
                ((SliderViewHolder) holder).r1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((SliderViewHolder) holder).viewFlipper.setDisplayedChild(0);
                    }
                });
            ((SliderViewHolder) holder).r2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((SliderViewHolder) holder).viewFlipper.setDisplayedChild(1);
                }
            });
            ((SliderViewHolder) holder).r3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((SliderViewHolder) holder).viewFlipper.setDisplayedChild(2);
                }
            });
            ((SliderViewHolder) holder).next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ((SliderViewHolder) holder).viewFlipper.setInAnimation(context, R.anim.slidein_right);
                    ((SliderViewHolder) holder).viewFlipper.setOutAnimation(context, R.anim.slideout_left);

                    ((SliderViewHolder) holder).viewFlipper.showNext();
                    int i =((SliderViewHolder) holder).viewFlipper.getDisplayedChild();
                    Log.d(TAG, "onClicknext: "+i);

                    if (i==0){
                        Log.d(TAG, "onBindViewHolder: "+i);
                        ((SliderViewHolder) holder).r1.setChecked(true);
                    }
                    if (i==1){
                        Log.d(TAG, "onBindViewHolder: "+i);
                        ((SliderViewHolder) holder).r2.setChecked(true);
                    }
                    if (i==2){
                        Log.d(TAG, "onBindViewHolder: "+i);
                        ((SliderViewHolder) holder).r3.setChecked(true);
                    }
                }
            });
            ((SliderViewHolder) holder).prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                ((SliderViewHolder) holder).viewFlipper.setInAnimation(context, android.R.anim.slide_in_left);
                  ((SliderViewHolder) holder).viewFlipper.setOutAnimation(context,  android.R.anim.slide_out_right);
                    ((SliderViewHolder) holder).viewFlipper.showPrevious();
                    int i =((SliderViewHolder) holder).viewFlipper.getDisplayedChild();
                    Log.d(TAG, "onClickprev: "+i);
                    if (i==0){
                        Log.d(TAG, "onBindViewHolder: "+i);
                        ((SliderViewHolder) holder).r1.setChecked(true);
                    }
                    if (i==1){
                        Log.d(TAG, "onBindViewHolder: "+i);
                        ((SliderViewHolder) holder).r2.setChecked(true);
                    }
                    if (i==2){
                        Log.d(TAG, "onBindViewHolder: "+i);
                        ((SliderViewHolder) holder).r3.setChecked(true);
                    }
                }
            });
//            ((SliderViewHolder) holder).viewFlipper.setFlipInterval(3000);
//            ((SliderViewHolder) holder).viewFlipper.setAutoStart(true);
            ((SliderViewHolder) holder).viewFlipper.setOutAnimation(context, android.R.anim.slide_out_right);
            ((SliderViewHolder) holder).viewFlipper.setInAnimation(context, android.R.anim.slide_in_left);
            ((SliderViewHolder) holder).viewFlipper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i =((SliderViewHolder) holder).viewFlipper.getDisplayedChild();
                    Intent intent =new Intent(context, BuyProduct.class);
                    if (i==0){

                        intent.putExtra("title","Opal Prime");
                        intent.putExtra("image","https://firebasestorage.googleapis.com/v0/b/jaquarapp.appspot.com/o/sliderImages%2Fimage1.jpeg?alt=media&token=6e3b3294-31cb-4451-8aab-8e1cec23a376");
                        intent.putExtra("price","1500");
                        intent.putExtra("productcode","OPP-CHR-15173BPM");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                    if (i==1){
                        intent.putExtra("title","Single Lever Wall Mixer 3-in-1");
                        intent.putExtra("image","https://firebasestorage.googleapis.com/v0/b/jaquarapp.appspot.com/o/sliderImages%2Fimage3.jpeg?alt=media&token=8c251b56-c94e-4516-a607-0d6ce6d20a72");
                        intent.putExtra("price","1600");
                        intent.putExtra("productcode","ORP-CHR-10173BPM");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                    if (i==2){
                        intent.putExtra("title","Kubix Prime");
                        intent.putExtra("image","https://firebasestorage.googleapis.com/v0/b/jaquarapp.appspot.com/o/sliderImages%2Fimage2.jpeg?alt=media&token=59a85e17-0e9e-457f-977b-626aec4d7c95");
                        intent.putExtra("price","1800");
                        intent.putExtra("productcode","KUP-CHR-35083KPM");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }


//            ImageView imageView =new ImageView(context);
//            imageView.setImageResource(R.drawable.loader);
//            TextView textView =new TextView(context);
//            textView.setText("fasdf");
//
//            ((SliderViewHolder) holder).viewFlipper.addView(textView);
//           HomeDataModel  homeDataModel =  homeDataModels.get(position);
//            Glide.with(context).load(homeDataModel.getSliderimage()).into(((SliderViewHolder) holder).sliderimage);
//            ((SliderViewHolder) holder).sliderTitle.setText(homeDataModel.getSlidertitle());

        if(holder instanceof NewProductViewHolder)
        {
            HomeDataModel data = homeDataModels.get(position);
            Glide.with(context).load(data.getImage()).placeholder(R.drawable.loader).dontAnimate().into(((NewProductViewHolder) holder).productImage);
            ((NewProductViewHolder) holder).productcode.setText(data.getProductcode());
            ((NewProductViewHolder) holder).productprice.setText(data.getPrice()+".00");
            ((NewProductViewHolder) holder).producttitle.setText(data.getTitle());
            ((NewProductViewHolder) holder).getData(position);

        }

    }
    @Override
    public int getItemCount() {
        return homeDataModels.size();
    }
    @Override
    public int getItemViewType(int position) {
        if(position==0)
        {
            return R.layout.flipperlayout;
        }
        else {
            return R.layout.newlyproductlayout;
        }
         }

    private class  SliderViewHolder extends RecyclerView.ViewHolder
    {
//        ImageView sliderimage;
//        TextView sliderTitle;
ImageView next,prev;
RadioButton r1,r2,r3;

        ImageView img,img1,imag2;
        ViewFlipper viewFlipper;

        public SliderViewHolder(View itemView) {
            super(itemView);
            viewFlipper =itemView.findViewById(R.id.viewFlipper);
            img =itemView.findViewById(R.id.flipimage);
            img1 =itemView.findViewById(R.id.flipimage1);
            imag2 =itemView.findViewById(R.id.flipimage2);
            next =itemView.findViewById(R.id.sliderNext);
            prev =itemView.findViewById(R.id.sliderPrev);
            r1 =itemView.findViewById(R.id.rgb1);
            r2 =itemView.findViewById(R.id.rgb2);
            r3 =itemView.findViewById(R.id.rgb3);


//            sliderimage =itemView.findViewById(R.id.sliderImage);
//            sliderTitle =itemView.findViewById(R.id.sliderText);
        }
    }
    public class NewProductViewHolder extends RecyclerView.ViewHolder
    {
        ImageView productImage;

        TextView producttitle,productprice,productcode;
        public NewProductViewHolder(View itemView) {
            super(itemView);
            productImage =itemView.findViewById(R.id.newlyImageview);
            productprice =itemView.findViewById(R.id.newlyProductPrice);
            productcode =itemView.findViewById(R.id.newlyProductCode);
            producttitle =itemView.findViewById(R.id.newlytitel);

        }
        public void getData(final int position)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HomeDataModel singelProductModel = homeDataModels.get(position);
                    String title =  singelProductModel.getTitle();
                    String image = singelProductModel.getImage();
                    String price = singelProductModel.getPrice();
                    String productcode = singelProductModel.getProductcode();
                    Log.d("clickevent",title+image+price+productcode);
                    Intent intent =new Intent(context, BuyProduct.class);
                    intent.putExtra("title",title);
                    intent.putExtra("image",image);
                    intent.putExtra("price",price);
                    intent.putExtra("productcode",productcode);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }
    }
}
