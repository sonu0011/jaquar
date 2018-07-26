package com.example.sonu.jaquar.Models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.sonu.jaquar.R;

public class SliderActivity extends AppCompatActivity {
ViewPager viewPager;
Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        viewPager =findViewById(R.id.viewpager);
        adapter =new Adapter(getApplicationContext());
        viewPager.setAdapter(adapter);
    }


    private class Adapter extends PagerAdapter{
        Context context;
        int[] images ={R.drawable.image1,R.drawable.image2,R.drawable.image3};
        public Adapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == ((ConstraintLayout)object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.slide,container,false);
             ImageView imageView =view.findViewById(R.id.slideNewImage);
             imageView.setImageResource(images[position]);
             container.addView(imageView);
             return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ConstraintLayout) object);
        }
    }
}
