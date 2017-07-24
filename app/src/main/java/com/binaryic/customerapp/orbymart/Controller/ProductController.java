package com.binaryic.customerapp.orbymart.Controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.customerapp.orbymart.Common.MyApplication;
import com.binaryic.customerapp.orbymart.Model.ProductModel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.binaryic.customerapp.orbymart.Common.Constants.SH_USER_ID;


/**
 * Created by HP on 05-Apr-17.
 */

public class ProductController {
    public void getProductApiCall(final Context context,final String category_id,final int page_count, final CallBackResult<ArrayList<ProductModel>> callBackResult) {
        String url = MyApplication.ApiUrl + "get_category_products";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BannerResponse", response);
                ArrayList<ProductModel> productModels = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        JSONArray products = object.getJSONArray("products");
                        productModels = getListFromArrayProduct(products,"all");
                    }
                } catch (JSONException e) {
                }
                callBackResult.onSuccess(productModels);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBackResult.onError("Error");
                Log.e("BannerErrorResponse", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", HomeController.getAccessToken(context));
                params.put("seller_id", HomeController.getSellerId(context));
                params.put("category_id",category_id);
                params.put("page",page_count+"");
                params.put("limit","20");
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "BannerApiCall");
    }

    public ArrayList<ProductModel> getListFromArrayProduct(JSONArray products,String count){
        ArrayList<ProductModel> productModels = new ArrayList<>();
        try {
            for (int i = 0; i < products.length(); i++) {
                ProductModel productModel = new ProductModel();
                productModel.setProduct_id(products.getJSONObject(i).getString("product_id"));
                productModel.setProduct_name(products.getJSONObject(i).getString("product_name"));
                productModel.setProduct_description(products.getJSONObject(i).getString("product_description"));
                productModel.setSelling_price(products.getJSONObject(i).getString("discount_price"));
                productModel.setDiscount_price(products.getJSONObject(i).getString("selling_price"));
                productModel.setSku_code(products.getJSONObject(i).getString("sku_code"));
                productModel.setRemain_qty(products.getJSONObject(i).getString("product_quantity"));
                productModel.setAverage_rating(products.getJSONObject(i).getString("average_rating"));
                productModel.setTotal_review(products.getJSONObject(i).getString("rating_count"));
                JSONArray product_images = products.getJSONObject(i).getJSONArray("product_images");
                ArrayList<String> product_image = new ArrayList<>();
                for (int j = 0; j < product_images.length(); j++) {
                    product_image.add(product_images.getJSONObject(j).getString("images"));
                }
                productModel.setProduct_images(product_image);

                if (!count.matches("all")) {
                    if (productModels.size() < Integer.parseInt(count))
                        productModels.add(productModel);

                } else {
                    productModels.add(productModel);
                }

            }
        }catch (Exception ex){}
        return productModels;
    }
    public void abuseApiCall(final Context context,final String product_id,final String title,final String message, final CallBackResult<Boolean> callBackResult) {
        String url = MyApplication.ApiUrl + "report_abuse";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BannerResponse", response);
                try {
                    JSONObject object = new JSONObject(response);
                    if(object.getString("success").equals("1")) {
                        callBackResult.onSuccess(true);
                    }else{
                        callBackResult.onError(object.getString("message"));
                    }
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBackResult.onError("Error");
                Log.e("BannerErrorResponse", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", HomeController.getAccessToken(context));
                params.put("seller_id", HomeController.getSellerId(context));
                params.put("product_id",product_id);
                params.put("message",message);
                params.put("title",title);
                params.put("customer_id",MyApplication.setting.getString(SH_USER_ID, ""));
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "BannerApiCall");
    }

    public void addReviewCall(final Context context,final String product_id,final String star,final String title,final String message, final CallBackResult<Boolean> callBackResult) {
        String url = MyApplication.ApiUrl + "product_review";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BannerResponse", response);
                try {
                    JSONObject object = new JSONObject(response);
                    if(object.getString("success").equals("1")) {
                        callBackResult.onSuccess(true);
                    }else{
                        callBackResult.onError(object.getString("message"));
                    }
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBackResult.onError("Error");
                Log.e("BannerErrorResponse", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", HomeController.getAccessToken(context));
                params.put("seller_id", HomeController.getSellerId(context));
                params.put("product_id",product_id);
                params.put("stars",star);
                params.put("title",title);
                params.put("review_message",message);
                params.put("customer_id",MyApplication.setting.getString(SH_USER_ID, ""));
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "BannerApiCall");
    }

    public void getReviewCall(final Context context,final String product_id,final String order, final CallBackResult<String> callBackResult) {
        String url = MyApplication.ApiUrl + "get_product_review";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BannerResponse", response);
                try {
                    JSONObject object = new JSONObject(response);
                    if(object.getString("success").equals("1")) {
                        callBackResult.onSuccess(response);
                    }else{
                        callBackResult.onError(object.getString("message"));
                    }
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBackResult.onError("Something went wrong.");
                Log.e("BannerErrorResponse", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", HomeController.getAccessToken(context));
                params.put("seller_id", HomeController.getSellerId(context));
                params.put("product_id",product_id);
                params.put("sort",order);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "get_product_review");
    }
}
