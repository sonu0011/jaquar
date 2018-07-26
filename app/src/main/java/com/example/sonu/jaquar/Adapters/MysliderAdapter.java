package com.example.sonu.jaquar.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.Models.SliderModel;
import com.example.sonu.jaquar.R;

import java.util.List;

/**
 * Created by sonu on 24/7/18.
 */

public class MysliderAdapter extends android.support.v4.view.PagerAdapter {
    List<SliderModel> list;
    Context context;
    LayoutInflater layoutInflater;
    int[] images ={R.drawable.image3,R.drawable.image2,R.drawable.image1};

    public MysliderAdapter( Context context) {
//        this.list = list;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("sliderAdapter","constructor");
    }

    @Override
    public int getCount() {
        Log.d("sliderAdapter","getCount");

        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((RelativeLayout)object);

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d("sliderAdapter","instantiateitem");

//        SliderModel sliderModel = list.get(position);
//        Log.d("sliderAdapterlist",sliderModel.getImage());
//        Log.d("sliderAdapterlist",sliderModel.getTitle());
        View view = layoutInflater.inflate(R.layout.sliderayout, container, false);
        Log.d("sliderView",view.toString());
//        ImageView displayImage = (ImageView)view.findViewById(R.id.sliderImage);
//        TextView imageText = (TextView)view.findViewById(R.id.sliderText);
//        displayImage.setImageResource(images[position]);
//        imageText.setText("title");
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.d("sliderAdapter","destrouItem");

        container.removeView((RelativeLayout) object);
    }
}
