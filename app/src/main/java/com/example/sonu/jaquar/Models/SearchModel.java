package com.example.sonu.jaquar.Models;

/**
 * Created by sonu on 11/7/18.
 */

public class SearchModel {
    String image,price,productcode,title;

    public SearchModel(String image, String price, String productcode, String title) {
        this.image = image;
        this.price = price;
        this.productcode = productcode;
        this.title = title;
    }

    public SearchModel() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
