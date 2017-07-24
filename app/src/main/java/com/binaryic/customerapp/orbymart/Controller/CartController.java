package com.binaryic.customerapp.orbymart.Controller;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.binaryic.customerapp.orbymart.Model.ProductModel;
import com.binaryic.customerapp.orbymart.Model.ProductModelQty;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.binaryic.customerapp.orbymart.Common.Constants.CART_ID;
import static com.binaryic.customerapp.orbymart.Common.Constants.CART_PRODUCT;
import static com.binaryic.customerapp.orbymart.Common.Constants.CART_PRODUCT_ID;
import static com.binaryic.customerapp.orbymart.Common.Constants.CART_QTY;
import static com.binaryic.customerapp.orbymart.Common.Constants.CONTENT_CART;



/**
 * Created by Asd on 06-10-2016.
 */
public class CartController {


    public static void AddProduct(Context context, ProductModel productModel) {
        try {
            int qty = 1 + checkAlready(context, productModel.getProduct_id());
            ContentValues values = new ContentValues();
            values.put(CART_PRODUCT_ID, productModel.getProduct_id());
            values.put(CART_PRODUCT, new Gson().toJson(productModel));
            values.put(CART_QTY, qty+"");
            context.getContentResolver().insert(CONTENT_CART, values);
        } catch (Exception ex) {
        }
    }


    private static int checkAlready(Context context, String product_id) {
        int qty = 0;
        try {
            String selection = CART_PRODUCT_ID + " = '" + product_id + "'";
            Cursor cursor = context.getContentResolver().query(CONTENT_CART, null, selection, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                qty = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CART_QTY)));

            }
        } catch (Exception ex) {
        }
        return qty;
    }

    public static boolean getSingleProduct(Activity context, String product_Id) {
        boolean availalbe = false;
        Log.e("product_Id", product_Id);
        String selection = CART_PRODUCT_ID + " = '" + product_Id + "'";
        Log.e("selection", selection);

        Cursor cursor = context.getContentResolver().query(CONTENT_CART, null, selection, null, null);
        if (cursor.getCount() > 0) {
            availalbe = true;
        }
        return availalbe;
    }
    public static void ChangeQty(Context context, ProductModel productModel, int qty) {
        try {
            String selection = CART_PRODUCT_ID + " = '" + productModel.getProduct_id() + "'";
            Cursor cursor = context.getContentResolver().query(CONTENT_CART, null, selection, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                ContentValues values = new ContentValues();
                values.put(CART_PRODUCT_ID, productModel.getProduct_id());
                values.put(CART_PRODUCT, new Gson().toJson(productModel));
                values.put(CART_QTY, String.valueOf(qty));
                int upd = context.getContentResolver().update(CONTENT_CART, values, selection, null);
                Log.e("upd", upd + "");
            }
        } catch (Exception ex) {
        }

    }

    public static void DeleteProduct(Context context, ProductModel productModel) {
        try {
            String selection = CART_PRODUCT_ID + " = '" + productModel.getProduct_id() + "'";
            int del = context.getContentResolver().delete(CONTENT_CART, selection, null);
            Log.e("del", del + "");
        } catch (Exception ex) {
        }
    }
    public static void RemoveCart(Context context){
        try{
            context.getContentResolver().delete(CONTENT_CART,null,null);
        }catch (Exception ex){}
    }

    public static ArrayList<ProductModelQty> GetCartProducts(Context context) {
        ArrayList<ProductModelQty> productList = new ArrayList<>();
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_CART, null, null, null, null);
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    ProductModel productModel = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(CART_PRODUCT)), ProductModel.class);
                    productList.add(new ProductModelQty(productModel, Integer.parseInt(cursor.getString(cursor.getColumnIndex(CART_QTY))), cursor.getInt(cursor.getColumnIndex(CART_ID))));
                }
            }
            cursor.close();
        } catch (Exception ex) {
        }
        return productList;
    }

    public static int GetCartCount(Context context) {
        int count = 0;
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_CART, null, null, null, null);
            count = cursor.getCount();
            cursor.close();
        } catch (Exception ex) {
        }
        return count;
    }
    public static String GetProductInfo(Context context){
        String ids = "";
        try{
            Cursor cursor = context.getContentResolver().query(CONTENT_CART, null, null, null, null);
            if (cursor.getCount() > 0) {
                for(int i = 0;i < cursor.getCount();i++){
                    cursor.moveToNext();
                    ids = ids + "," + cursor.getString(cursor.getColumnIndex(CART_PRODUCT_ID));
                }
                ids.substring(1);
            }
        }catch (Exception ex){}
        return ids;
    }
}
