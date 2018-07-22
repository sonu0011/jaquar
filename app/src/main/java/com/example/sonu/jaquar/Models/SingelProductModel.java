package com.example.sonu.jaquar.Models;

/**
 * Created by sonu on 1/7/18.
 */

public class SingelProductModel {
    String image,price,productcode,title;
    public SingelProductModel() {
    }

    public SingelProductModel(String image, String price, String productcode, String title) {
        this.image = image;
        this.price = price;
        this.productcode = productcode;
        this.title = title;
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
