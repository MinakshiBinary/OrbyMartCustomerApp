package com.binaryic.customerapp.orbymart.Model;

/**
 * Created by Asd on 07-10-2016.
 */
public class ProductModelQty {
    int qty;
    ProductModel productModel;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductModelQty(ProductModel productModel, int qty, int id){
        this.productModel = productModel;
        this.qty = qty;
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }
}
