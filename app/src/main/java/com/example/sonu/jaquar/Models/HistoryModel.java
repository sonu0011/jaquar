package com.example.sonu.jaquar.Models;

/**
 * Created by sonu on 27/7/18.
 */

public class HistoryModel  {
    String title,image,price,productcode,totalprice,quantity;

    public HistoryModel() {
    }

    public HistoryModel(String title, String image, String price, String productcode, String totalprice, String quantity) {
        this.title = title;
        this.image = image;
        this.price = price;
        this.productcode = productcode;
        this.totalprice = totalprice;
        this.quantity = quantity;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
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
}
