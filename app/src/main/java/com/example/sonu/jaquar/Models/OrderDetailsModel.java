package com.example.sonu.jaquar.Models;

/**
 * Created by sonu on 11/8/18.
 */

public class OrderDetailsModel {
    String order_date,order_id,totalitems,totalprice;

    public OrderDetailsModel() {
    }

    public OrderDetailsModel(String order_date, String order_id, String totalitems, String totalprice) {
        this.order_date = order_date;
        this.order_id = order_id;
        this.totalitems = totalitems;
        this.totalprice = totalprice;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTotalitems() {
        return totalitems;
    }

    public void setTotalitems(String totalitems) {
        this.totalitems = totalitems;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }
}
