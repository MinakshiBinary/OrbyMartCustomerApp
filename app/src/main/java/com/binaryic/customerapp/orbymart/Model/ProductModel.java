package com.binaryic.customerapp.orbymart.Model;

import java.util.ArrayList;

/**
 * Created by HP on 04-Apr-17.
 */

public class ProductModel {
    String product_id,product_name,product_description,selling_price,discount_price,sku_code,remain_qty;
    ArrayList<String> product_images;
    String average_rating = "0";
    String total_review = "0";

    public String getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(String average_rating) {
        this.average_rating = average_rating;
    }

    public String getTotal_review() {
        return total_review;
    }

    public void setTotal_review(String total_review) {
        this.total_review = total_review;
    }

    boolean isFav = false;
    public Boolean getIsFav() {
        return isFav;
    }

    public void setIsFav(Boolean isFav) {
        this.isFav = isFav;
    }
    public String getProduct_id() {
        return product_id;
    }

    public String getRemain_qty() {
        return remain_qty;
    }

    public void setRemain_qty(String remain_qty) {
        this.remain_qty = remain_qty;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    public String getSku_code() {
        return sku_code;
    }

    public void setSku_code(String sku_code) {
        this.sku_code = sku_code;
    }

    public ArrayList<String> getProduct_images() {
        return product_images;
    }

    public void setProduct_images(ArrayList<String> product_images) {
        this.product_images = product_images;
    }
}
