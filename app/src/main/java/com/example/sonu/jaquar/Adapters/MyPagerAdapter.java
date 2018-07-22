package com.example.sonu.jaquar.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.R;

/**
 * Created by sonu on 27/6/18.
 */

public class MyPagerAdapter extends PagerAdapter {
    Context context;
    int  laypout;
    int[] imagsArray;
    LayoutInflater layoutInflater;
    public MyPagerAdapter(Context context, int laypout, int[] imagsArray) {
        this.context = context;
        this.laypout = laypout;
        this.imagsArray = imagsArray;
    }

    @Override
    public int getCount() {

        return imagsArray.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       View view =  layoutInflater.inflate(laypout,container,false);
        ImageView imageView = view.findViewById(R.id.pagerImage);
        imageView.setImageResource(imagsArray[position]);
        if(imageView.getParent()!=null)
            ((ViewGroup)imageView.getParent()).removeView(imageView); // <- fix
        container.addView(imageView); //  <==========  ERROR IN THIS LINE DURING 2ND RUN
// EDITTEXT

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp  = (ViewPager) container;
        View view =(View)object;
        vp.removeView(view);

    }
}
