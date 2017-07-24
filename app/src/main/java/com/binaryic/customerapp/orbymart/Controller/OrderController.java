package com.binaryic.customerapp.orbymart.Controller;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.customerapp.orbymart.Common.MyApplication;
import com.binaryic.customerapp.orbymart.Model.OrderModel;
import com.binaryic.customerapp.orbymart.Model.OrderProductModel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.binaryic.customerapp.orbymart.Common.Constants.SH_USER_ID;

/**
 * Created by HP on 17-Apr-17.
 */

public class OrderController {
    public void createOrder(final Context context, final String order_data, final String coupon_code, final String transaction_type, final String transaction_id, final String other_details, final String grand_total, final String discount_price, final String selling_price, final CallBackResult<String> callBackResult) {
        String url = MyApplication.ApiUrl + "customer_orders_create";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        callBackResult.onSuccess("success");

                    } else {
                        callBackResult.onError(object.getString("message"));
                    }
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBackResult.onError("Something went wrong.");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", new HomeController().getAccessToken(context));
                params.put("seller_id", new HomeController().getSellerId(context));
                params.put("customer_id", MyApplication.setting.getString(SH_USER_ID, ""));
                params.put("order_data", order_data);
                //if (!coupon_code.equals(""))
                params.put("applied_coupon_code", coupon_code);
                params.put("transaction_type", transaction_type);
                params.put("transaction_id", transaction_id);
                params.put("other_details", other_details);
                params.put("grand_total", grand_total);
                params.put("discount_price", discount_price);
                params.put("selling_price", selling_price);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "create_order");
    }
    public void getOrderApiCall(final Context context,final CallBackResult<ArrayList<OrderModel>> callBackResult) {
        String url = MyApplication.ApiUrl + "get_customer_orders";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        JSONArray order_data = object.getJSONArray("order_data");
                        ArrayList<OrderModel> list = new ArrayList<>();
                        for(int i = 0;i < order_data.length();i++){
                            OrderModel orderModel = new OrderModel();
                            orderModel.setOrder_no(order_data.getJSONObject(i).getString("order_id"));
                            orderModel.setTotal_amount(order_data.getJSONObject(i).getString("grand_total"));
                            orderModel.setDiscount_amount(order_data.getJSONObject(i).getString("discount_price"));
                            orderModel.setSelling_price(order_data.getJSONObject(i).getString("selling_price"));
                            orderModel.setApplied_coupon_code(order_data.getJSONObject(i).getString("applied_coupon_code"));
                            orderModel.setPayment_mode(order_data.getJSONObject(i).getString("transaction_type"));
                            orderModel.setTransaction_id("");
                            orderModel.setShipping_address(order_data.getJSONObject(i).getString("customer_address") + " " + order_data.getJSONObject(i).getString("landmark")+ " " + order_data.getJSONObject(i).getString("area")+ " " + order_data.getJSONObject(i).getString("city")+ " " + order_data.getJSONObject(i).getString("pincode")+ " " + order_data.getJSONObject(i).getString("state"));
                            orderModel.setCustomer_name(order_data.getJSONObject(i).getString("first_name") + " " + order_data.getJSONObject(i).getString("last_name"));
                            orderModel.setStatus(order_data.getJSONObject(i).getString("order_status"));
                            orderModel.setDelivery_date(order_data.getJSONObject(i).getString("status_time"));
                            orderModel.setCreated_date(order_data.getJSONObject(i).getString("created_at"));
                            JSONArray sub_order = order_data.getJSONObject(i).getJSONArray("sub_order");
                            ArrayList<OrderProductModel> orderProductModels = new ArrayList<>();
                            for(int j = 0;j < sub_order.length();j++){
                                OrderProductModel orderProductModel = new OrderProductModel();
                                orderProductModel.setProduct_id(sub_order.getJSONObject(j).getString("product_id"));
                                orderProductModel.setSub_order_id(sub_order.getJSONObject(j).getString("sub_order_id"));
                                orderProductModel.setProduct_name(sub_order.getJSONObject(j).getString("product_name"));
                                orderProductModel.setProduct_image(sub_order.getJSONObject(j).getString("product_image"));
                                orderProductModel.setProduct_quantity(sub_order.getJSONObject(j).getString("product_quantity"));
                                orderProductModel.setSelling_price(sub_order.getJSONObject(j).getString("selling_price"));
                                orderProductModel.setStatus(sub_order.getJSONObject(j).getString("order_status"));
                                orderProductModel.setDelivery_date(sub_order.getJSONObject(j).getString("status_time"));
                                orderProductModels.add(orderProductModel);
                            }
                            orderModel.setProductModels(orderProductModels);
                            orderModel.setOrder_id(order_data.getJSONObject(i).getString("order_id"));
                            orderModel.setTax("20");
                            list.add(orderModel);
                        }
                        callBackResult.onSuccess(list);
                    } else {
                        callBackResult.onError(object.getString("message"));
                    }
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBackResult.onError("Something went wrong.");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", new HomeController().getAccessToken(context));
                params.put("seller_id", new HomeController().getSellerId(context));
                params.put("customer_id", MyApplication.setting.getString(SH_USER_ID, ""));
                //params.put("customer_id", "44");

                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "create_order");
    }

    public void deleteSubOrder(final Context context, final String order_id, final String sub_order_id, final CallBackResult<String> callBackResult) {
        String url = MyApplication.ApiUrl + "customer_sub_order_delete";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        callBackResult.onSuccess("success");
                    } else {
                        callBackResult.onError(object.getString("message"));
                    }
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBackResult.onError("Something went wrong.");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", new HomeController().getAccessToken(context));
                params.put("order_id", order_id);
                params.put("sub_order_id", sub_order_id);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "customer_sub_order_delete");
    }
    public void deleteOrder(final Context context, final String order_id, final CallBackResult<String> callBackResult) {
        String url = MyApplication.ApiUrl + "customer_order_delete";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        callBackResult.onSuccess("success");
                    } else {
                        callBackResult.onError(object.getString("message"));
                    }
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBackResult.onError("Something went wrong.");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", new HomeController().getAccessToken(context));
                params.put("order_id", order_id);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "customer_sub_order_delete");
    }
}
