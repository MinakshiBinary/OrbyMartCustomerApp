package com.binaryic.customerapp.orbymart.Controller;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.customerapp.orbymart.Activity.LoginActivity;
import com.binaryic.customerapp.orbymart.Common.MyApplication;
import com.binaryic.customerapp.orbymart.Model.Address;
import com.binaryic.customerapp.orbymart.Model.Customer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.binaryic.customerapp.orbymart.Common.Constants.COLUMN_USER_PICTURE;
import static com.binaryic.customerapp.orbymart.Common.Constants.SH_USER_ID;
import static com.binaryic.customerapp.orbymart.Common.Constants.USER_FN;
import static com.binaryic.customerapp.orbymart.Common.Constants.USER_ID;
import static com.binaryic.customerapp.orbymart.Common.Constants.USER_LN;
import static com.binaryic.customerapp.orbymart.Common.Constants.*;


/**
 * Created by Asd on 23-09-2016.
 */
public class CustomerController {

    public void mobileVerificationSignUp(final Context context,final String mobile_no, final CallBackResult<String> callBackResult) {
        String url = MyApplication.ApiUrl + "customer_signup_details";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BannerResponse", response);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        callBackResult.onSuccess(object.getString("OTP"));
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

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", new HomeController().getAccessToken(context));
                params.put("seller_id", new HomeController().getSellerId(context));
                params.put("mobile_number", mobile_no);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "customer_signup_details");
    }
    public void mobileSignUp(final Context context, final String otp, final String firstName, final String lastName, final String email, final String mobileNo, final String address, final String landmark, final String area, final String pincode, final String city, final String state, final CallBackResult<String> callBackResult) {
        String url = MyApplication.ApiUrl + "customer_signup_otp";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BannerResponse", response);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        callBackResult.onSuccess(object.getString("customer_id"));
                    }else{
                        callBackResult.onError(object.getString("message"));
                    }
                } catch (JSONException e) {
                }
                callBackResult.onSuccess("success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBackResult.onError("Error");

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", new HomeController().getAccessToken(context));
                params.put("seller_id", new HomeController().getSellerId(context));
                params.put("otp",otp);
                params.put("mobile_number", mobileNo);
                params.put("first_name", firstName);
                params.put("last_name", lastName);
                params.put("customer_email_id", email);
                params.put("customer_address", address);
                params.put("landmark", landmark);
                params.put("area", area);
                params.put("pincode", pincode);
                params.put("city", city);
                params.put("state", state);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "customer_signup_details");
    }
    public void mobileVerificationSignIn(final Context context, final String mobile_no, final CallBackResult<String> callBackResult) {
        String url = MyApplication.ApiUrl + "customer_register";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BannerResponse", response);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        JSONObject Details = object.getJSONObject("Details");
                        callBackResult.onSuccess(Details.toString());
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
                params.put("mobile_number", mobile_no);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "login");
    }

    public void getAddress(final Context context, final CallBackResult<String> callBackResult) {
        String url = MyApplication.ApiUrl + "get_addresses";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        JSONArray Addresses = object.getJSONArray("Addresses");
                        addAddresses(context,Addresses);
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
                params.put("customer_id", MyApplication.setting.getString(SH_USER_ID,""));
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "login");
    }
    public void addAddress(final Context context,final String mobile_no,final String f_name,final String l_name,final String address,final String landmark,final String area,final String pincode,final String city,final String state, final CallBackResult<String> callBackResult) {
        String url = MyApplication.ApiUrl + "customer_addresses";
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
                params.put("customer_id", MyApplication.setting.getString(SH_USER_ID,""));
                params.put("mobile_number", mobile_no);
                params.put("first_name", f_name);
                params.put("last_name", l_name);
                params.put("customer_address", address);
                params.put("landmark", landmark);
                params.put("area", area);
                params.put("pincode",pincode);
                params.put("city", city);
                params.put("state", state);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "login");
    }
    public void editAddress(final Context context,final String address_id,final String mobile_no,final String f_name,final String l_name,final String address,final String landmark,final String area,final String pincode,final String city,final String state, final CallBackResult<String> callBackResult) {
        String url = MyApplication.ApiUrl + "edit_address";
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
                params.put("customer_id", MyApplication.setting.getString(SH_USER_ID,""));
                params.put("address_id", address_id);
                params.put("mobile_number", mobile_no);
                params.put("first_name", f_name);
                params.put("last_name", l_name);
                params.put("customer_address", address);
                params.put("landmark", landmark);
                params.put("area", area);
                params.put("pincode",pincode);
                params.put("city", city);
                params.put("state", state);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "login");
    }
    public void removeAddress(final Context context,final String address_id, final CallBackResult<String> callBackResult) {
        String url = MyApplication.ApiUrl + "delete_address";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        callBackResult.onSuccess("success");
                        RemoveAddress(context,address_id);
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
                params.put("customer_id", MyApplication.setting.getString(SH_USER_ID,""));
                params.put("address_id", address_id);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "login");
    }
    public void setDefaultAddress(final Context context,final String address_id, final CallBackResult<String> callBackResult) {
        String url = MyApplication.ApiUrl + "update_address_status";
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
                params.put("customer_id", MyApplication.setting.getString(SH_USER_ID,""));
                params.put("address_id", address_id);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "login");
    }
    public static void AddCustomerData(Context context, Customer customer) {
        try {
            ContentValues values = new ContentValues();
            values.put(USER_EMAIL, customer.getEmail());
            values.put(USER_FN, customer.getFirstName());
            values.put(USER_LN, customer.getLastName());
            values.put(USER_ID, customer.getUser_id());
            values.put(USER_MOBILE, customer.getMobile_no());
            values.put(ADDRESS, customer.getAddress());
            values.put(LANDMARK, customer.getLandmark());
            values.put(CITY, customer.getCity());
            values.put(AREA, customer.getArea());
            values.put(PINCODE, customer.getZip());
            values.put(STATE, customer.getState());
            values.put(COLUMN_USER_PICTURE, "");
            context.getContentResolver().delete(CONTENT_USER, null, null);
            context.getContentResolver().insert(CONTENT_USER, values);
            MyApplication.setting.edit().putBoolean(SH_USER_LOGIN, true).commit();
            MyApplication.setting.edit().putString(SH_USER_ID, customer.getUser_id().toString()).commit();
        } catch (Exception ex) {
            Log.e("EX", ex.getMessage());
        }
    }

    private void addAddresses(Context context, JSONArray array) {
        if (array.length() > 0) {
            context.getContentResolver().delete(CONTENT_ADDRESS,null,null);
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject object = array.getJSONObject(i);
                    addAddress(context,object.getString("address_id"),object.getString("customer_address"),object.getString("first_name"),object.getString("last_name"),object.getString("mobile_number"),object.getString("landmark"),object.getString("area"),object.getString("pincode"),object.getString("city"),object.getString("state"),object.getString("default_address"));
                }catch (Exception ex){}
            }
        }
    }

    public static void addAddress(Context context, String address_id, String address, String f_name, String l_name, String mobile_no, String landmark, String area, String pincode, String city, String state, String isDefault) {
        ContentValues values = new ContentValues();
        values.put(ADDRESS_ID, address_id);
        values.put(ADDRESS, address);
        values.put(ADDRESS_LNAME, l_name);
        values.put(ADDRESS_FNAME, f_name);
        values.put(ADDRESS_MOBILE, mobile_no);
        values.put(LANDMARK, landmark);
        values.put(AREA, area);
        values.put(PINCODE, pincode);
        values.put(CITY, city);
        values.put(STATE, state);
        values.put(IS_DEFAULT, isDefault);
        context.getContentResolver().insert(CONTENT_ADDRESS, values);
    }

    public static void signOut(Activity context) {
        context.getContentResolver().delete(CONTENT_USER, null, null);
        context.getContentResolver().delete(CONTENT_WISHLIST, null, null);
        context.getContentResolver().delete(CONTENT_CART, null, null);
        context.getContentResolver().delete(CONTENT_RECENT, null, null);
        context.getContentResolver().delete(CONTENT_ADDRESS, null, null);
        context.finishAffinity();
        MyApplication.setting.edit().clear().commit();
        Intent intent = new Intent(context,LoginActivity.class);
        intent.putExtra("type","");
        context.startActivity(intent);

    }

    public static Customer GetCustomerData(Context context) {
        Customer customer = null;
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_USER, null, null, null, null);
            if (cursor.getCount() > 0) {
                customer = new Customer();
                cursor.moveToLast();
                customer.setEmail(cursor.getString(cursor.getColumnIndex(USER_EMAIL)));
                customer.setFirstName(cursor.getString(cursor.getColumnIndex(USER_FN)));
                customer.setLastName(cursor.getString(cursor.getColumnIndex(USER_LN)));
                customer.setMobile_no(cursor.getString(cursor.getColumnIndex(USER_MOBILE)));
            }
        } catch (Exception ex) {
        }
        return customer;
    }


    public static void RemoveAddress(Context context, String address_id) {
        try {
            String sel = ADDRESS_ID + " = '" + address_id + "'";
            context.getContentResolver().delete(CONTENT_ADDRESS, sel, null);
        } catch (Exception ex) {
            Log.e("RemoveAdd", ex.getMessage());
        }
    }


    public static Address GetDefaultAddress(Context context) {
        Address address = null;
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_ADDRESS, null, null, null, null);
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    if (cursor.getString(cursor.getColumnIndex(IS_DEFAULT)).equals("1")) {
                        address = new Address();
                        address.setState(cursor.getString(cursor.getColumnIndex(STATE)));
                        address.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
                        address.setArea(cursor.getString(cursor.getColumnIndex(AREA)));
                        address.setCity(cursor.getString(cursor.getColumnIndex(CITY)));
                        address.setFirstName(cursor.getString(cursor.getColumnIndex(ADDRESS_FNAME)));
                        address.setId(cursor.getString(cursor.getColumnIndex(ADDRESS_ID)));
                        address.setLandmark(cursor.getString(cursor.getColumnIndex(LANDMARK)));
                        address.setLastName(cursor.getString(cursor.getColumnIndex(ADDRESS_LNAME)));
                        address.setPhone(cursor.getString(cursor.getColumnIndex(ADDRESS_MOBILE)));
                        address.setZip(cursor.getString(cursor.getColumnIndex(PINCODE)));
                        address.setDefaultAddress(true);
                    }
                }
            }
        } catch (Exception ex) {
        }
        return address;
    }

    public static ArrayList<Address> GetAddresses(Context context) {
        ArrayList<Address> addresses = null;
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_ADDRESS, null, null, null, null);
            if (cursor.getCount() > 0) {
                addresses = new ArrayList<>();
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    Address address = new Address();
                    address.setState(cursor.getString(cursor.getColumnIndex(STATE)));
                    address.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
                    address.setArea(cursor.getString(cursor.getColumnIndex(AREA)));
                    address.setCity(cursor.getString(cursor.getColumnIndex(CITY)));
                    address.setFirstName(cursor.getString(cursor.getColumnIndex(ADDRESS_FNAME)));
                    address.setId(cursor.getString(cursor.getColumnIndex(ADDRESS_ID)));
                    address.setLandmark(cursor.getString(cursor.getColumnIndex(LANDMARK)));
                    address.setLastName(cursor.getString(cursor.getColumnIndex(ADDRESS_LNAME)));
                    address.setPhone(cursor.getString(cursor.getColumnIndex(ADDRESS_MOBILE)));
                    address.setZip(cursor.getString(cursor.getColumnIndex(PINCODE)));
                    if (cursor.getString(cursor.getColumnIndex(IS_DEFAULT)).equals("1"))
                        address.setDefaultAddress(true);
                    else
                        address.setDefaultAddress(false);
                    addresses.add(address);
                }
            }
        } catch (Exception ex) {
        }
        return addresses;
    }


}
