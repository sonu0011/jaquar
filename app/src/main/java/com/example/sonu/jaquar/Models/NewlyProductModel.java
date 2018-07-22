package com.example.sonu.jaquar.Models;

/**
 * Created by sonu on 28/6/18.
 */

public class NewlyProductModel {
    private String image,title,productcode,price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public NewlyProductModel() {
    }

    public NewlyProductModel(String image, String title, String productcode) {
        this.image = image;
        this.title = title;
        this.productcode = productcode;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }
}
