package com.example.sonu.jaquar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;


import com.example.sonu.jaquar.slider.FragmentSlider;
import com.example.sonu.jaquar.slider.SliderIndicator;
import com.example.sonu.jaquar.slider.SliderPagerAdapter;
import com.example.sonu.jaquar.slider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class sliderNewActivity extends AppCompatActivity {

    private SliderPagerAdapter mAdapter;
    private SliderIndicator mIndicator;

    private SliderView sliderView;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidenew);
        sliderView = (SliderView) findViewById(R.id.sliderView);
        mLinearLayout = (LinearLayout) findViewById(R.id.pagesContainer);
        setupSlider();
    }


    private void setupSlider() {
//        sliderView.setDurationScroll(800);
//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(FragmentSlider.newInstance("https://image.tmdb.org/t/p/w250_and_h141_bestv2/zYFQM9G5j9cRsMNMuZAX64nmUMf.jpg"));
//        fragments.add(FragmentSlider.newInstance("https://image.tmdb.org/t/p/w250_and_h141_bestv2/rXBB8F6XpHAwci2dihBCcixIHrK.jpg"));
//        fragments.add(FragmentSlider.newInstance("https://image.tmdb.org/t/p/w250_and_h141_bestv2/biN2sqExViEh8IYSJrXlNKjpjxx.jpg"));
//        fragments.add(FragmentSlider.newInstance("https://image.tmdb.org/t/p/w250_and_h141_bestv2/o9OKe3M06QMLOzTl3l6GStYtnE9.jpg"));
//
//        mAdapter = new SliderPagerAdapter(getSupportFragmentManager(), fragments);
//        sliderView.setAdapter(mAdapter);
//        mIndicator = new SliderIndicator(this, mLinearLayout, sliderView, R.drawable.indicator_circle);
//        mIndicator.setPageCount(fragments.size());
//        mIndicator.show();
    }

}
