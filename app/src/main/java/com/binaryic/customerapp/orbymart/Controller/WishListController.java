package com.binaryic.customerapp.orbymart.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.binaryic.customerapp.orbymart.Model.ProductModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.binaryic.customerapp.orbymart.Common.Constants.CONTENT_WISHLIST;
import static com.binaryic.customerapp.orbymart.Common.Constants.WISHLIST_PRODUCT;
import static com.binaryic.customerapp.orbymart.Common.Constants.WISHLIST_PRODUCT_ID;


/**
 * Created by Asd on 29-09-2016.
 */
public class WishListController {
    public static void AddProduct(Context context, ProductModel productModel) {
        try {
            ContentValues values = new ContentValues();
            values.put(WISHLIST_PRODUCT_ID, productModel.getProduct_id());
            values.put(WISHLIST_PRODUCT, new Gson().toJson(productModel));
            context.getContentResolver().insert(CONTENT_WISHLIST, values);
        } catch (Exception ex) {
        }
    }

    public static void DeleteProduct(Context context, ProductModel productModel) {
        try {
            String selection = WISHLIST_PRODUCT_ID + " = '" + productModel.getProduct_id() + "'";
            context.getContentResolver().delete(CONTENT_WISHLIST, selection, null);
        } catch (Exception ex) {
        }
    }

    public static String GetFavProductId(Context context) {
        String ids = "";
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_WISHLIST, null, null, null, null);
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    ids = ids + "," + cursor.getString(cursor.getColumnIndex(WISHLIST_PRODUCT_ID));
                }
                ids.substring(1);
            }
        } catch (Exception ex) {
            String temp = ex.getMessage();
        }
        return ids;
    }

    public static ArrayList<ProductModel> GetFavProducts(Context context) {
        ArrayList<ProductModel> productList = new ArrayList<>();
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_WISHLIST, null, null, null, null);
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    ProductModel productModel = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(WISHLIST_PRODUCT)), ProductModel.class);
                    productModel.setIsFav(true);
                    productList.add(productModel);
                }
            }
        } catch (Exception ex) {
        }
        return productList;
    }

    public static int GetWishCount(Context context) {
        int count = 0;
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_WISHLIST, null, null, null, null);
            count = cursor.getCount();
            cursor.close();
        } catch (Exception ex) {
        }
        return count;
    }
}
