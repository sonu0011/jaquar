package com.example.sonu.jaquar.Models;

/**
 * Created by sonu on 30/6/18.
 */

public class SubAccessoriesModel {
    String image,title;

    public SubAccessoriesModel() {
    }

    public SubAccessoriesModel(String image, String title) {
        this.image = image;
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }
}
