package com.example.sonu.jaquar.Models;

/**
 * Created by sonu on 2/7/18.
 */

public class CheckOutMOdel {
    String title,price,image,productcode,totalprice,quantity;

    public String gettotalpricequantity() {
        return totalprice;
    }

    public void settotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public CheckOutMOdel() {
    }

    public CheckOutMOdel(String title, String price, String image,String productcode,String quantity,String totalprice) {
        this.title = title;
        this.price = price;
        this.image = image;
        this.productcode =productcode;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getimage() {
        return image;
    }

    public void setimage(String image) {
        this.image = image;
    }
}
