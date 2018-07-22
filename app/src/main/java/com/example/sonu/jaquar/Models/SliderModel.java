package com.example.sonu.jaquar.Models;

/**
 * Created by sonu on 27/6/18.
 */

public class SliderModel {
    String image,title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SliderModel() {
    }

    public SliderModel(String image,String title)
    {
        this.title=title;
        this.image = image;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
