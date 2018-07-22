package com.example.sonu.jaquar.Models;

/**
 * Created by sonu on 9/7/18.
 */

public class HomeDataModel {
    String title,image,price,productcode,sliderimage,slidertitle;

    public HomeDataModel() {
    }

    public HomeDataModel(String title, String image, String price, String productcode, String sliderimage, String slidertitle) {
        this.title = title;
        this.image = image;
        this.price = price;
        this.productcode = productcode;
        this.sliderimage = sliderimage;
        this.slidertitle = slidertitle;
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

    public String getSliderimage() {
        return sliderimage;
    }

    public void setSliderimage(String sliderimage) {
        this.sliderimage = sliderimage;
    }

    public String getSlidertitle() {
        return slidertitle;
    }

    public void setSlidertitle(String slidertitle) {
        this.slidertitle = slidertitle;
    }
}
