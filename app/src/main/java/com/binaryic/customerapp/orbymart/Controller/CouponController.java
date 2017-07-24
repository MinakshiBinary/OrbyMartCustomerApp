package com.binaryic.customerapp.orbymart.Controller;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.customerapp.orbymart.Common.MyApplication;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by minakshi on 10/4/17.
 */

public class CouponController {

    public void applyCouponCall(final Activity context, final String couponName, final CallBackResult<Integer> apiCallBack) {
        String url = MyApplication.ApiUrl + "coupon_apply";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("applyCouponCall", "response =" + response);

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getInt("success") == 1) {
                        apiCallBack.onSuccess(object.getInt("coupon_percentage"));

                    } else {
                        apiCallBack.onError(object.getString("message"));
                    }

                } catch (Exception e) {
                    apiCallBack.onError(e.getMessage());
                    Log.e("errrorrrr", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("applyCouponCall", error.toString());
                apiCallBack.onError(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("access_token", HomeController.getAccessToken(context));
                params.put("seller_id", HomeController.getSellerId(context));
                params.put("coupon_name", couponName);
                Log.e("applyCouponCall", "params" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "applyCouponCall");
    }


}
