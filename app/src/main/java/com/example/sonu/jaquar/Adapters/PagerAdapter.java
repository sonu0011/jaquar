package com.example.sonu.jaquar.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
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
 * Created by sonu on 27/6/18.
 */

public class PagerAdapter extends android.support.v4.view.PagerAdapter{
    private Context context;
    private List<SliderModel> list;
    private LayoutInflater layoutInflater;

    public PagerAdapter(Context context, List<SliderModel> list) {
        this.context = context;
        this.list = list;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view  = layoutInflater.inflate(R.layout.sliderayout,container,false);
           SliderModel sliderModel =  list.get(position);
//          ImageView imageView = view.findViewById(R.id.sliderImage);
//          TextView  textView = view.findViewById(R.id.sliderText);
//        Glide.with(context).load(sliderModel.getImage()).into(imageView);
//        textView.setText(sliderModel.getTitle());
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view,0);
        viewPager.notifyAll();
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}
