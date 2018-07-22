package com.example.sonu.jaquar.Models;

/**
 * Created by sonu on 27/6/18.
 */

public class CategoriesModel  {
    String image,name;//name shoule be same as firebase key value

    public CategoriesModel(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public CategoriesModel() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
