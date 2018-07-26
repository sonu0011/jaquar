package com.example.sonu.jaquar.slider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.sonu.jaquar.R;
public class FragmentSlider extends Fragment {

    private static final String ARG_PARAM1 = "params";
    private static final String ARG_PARAM2 = "title";

    private String imageUrls;
    private String title;


    public FragmentSlider() {
    }

    public static FragmentSlider newInstance(String params,String title) {
        FragmentSlider fragment = new FragmentSlider();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, params);
        args.putString(ARG_PARAM2, title);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        imageUrls = getArguments().getString(ARG_PARAM1);
        title = getArguments().getString(ARG_PARAM2);
        View view = inflater.inflate(R.layout.fragment_slider_item, container, false);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        TextView textView =view.findViewById(R.id.sliderTextView);
        Glide.with(getActivity()).load(imageUrls).into(img);
        textView.setText(title);
        return view;
    }
}