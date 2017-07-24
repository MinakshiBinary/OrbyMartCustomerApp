package com.binaryic.customerapp.orbymart.Common;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.binaryic.customerapp.orbymart.Model.ProductModel;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;


/**
 * Created by HP on 10-Mar-17.
 */
@ReportsCrashes(formKey = "ogencencestallysterederh")
public class MyApplication extends Application {
    public static ProductModel productModel = null;
    private static MyApplication mInstance;
    private RequestQueue mRequestQueue;
    public static final String TAG = MyApplication.class.getSimpleName();
    public static String ApiUrl = "http://sellerapp.binaryicdirect.com/v1/";
    public static String SURL = "http://sellerapp.binaryicdirect.com/v1/surl";
    public static String FURL = "http://sellerapp.binaryicdirect.com/v1/furl";
    public static String SALT = "DWkSD2kh";
    public static String KEY = "tr6gBX";
    public static SharedPreferences setting;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        setting = getSharedPreferences("orbymart", MODE_PRIVATE);
        ACRA.init(this);
        ACRAReportSender reportSender = new ACRAReportSender();
        ACRA.getErrorReporter().setReportSender(reportSender);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        RetryPolicy policy = new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);
        getRequestQueue().add(req);
    }
}
