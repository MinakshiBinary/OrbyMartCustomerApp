package com.binaryic.customerapp.orbymart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.binaryic.customerapp.orbymart.Common.MyApplication;
import com.binaryic.customerapp.orbymart.Controller.CallBackResult;
import com.binaryic.customerapp.orbymart.Controller.HomeController;
import com.binaryic.customerapp.orbymart.R;

import static com.binaryic.customerapp.orbymart.Common.Constants.SH_USER_ID;
import static com.binaryic.customerapp.orbymart.Common.Constants.SH_USER_LOGIN;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setSettings();
        getHomeDetail();
        Log.e("user_id", MyApplication.setting.getString(SH_USER_ID,""));
    }
    private void setSettings(){
        HomeController homeController = new HomeController();
        homeController.setSettingData(SplashActivity.this,"50","4c10c1f41e2cfddb146fd54e871890e2");
    }
    private void getHomeDetail(){
        HomeController homeController = new HomeController();
        homeController.getBannerDataApiCall(this, new CallBackResult<String>() {
            @Override
            public void onSuccess(String s) {
                getCategoryDetail();
            }

            @Override
            public void onError(String error) {
                getCategoryDetail();
            }
        });
    }
    private void getCategoryDetail(){
        HomeController homeController = new HomeController();
        homeController.getCategoryApiCall(this, new CallBackResult<String>() {
            @Override
            public void onSuccess(String s) {
                if(MyApplication.setting.getBoolean(SH_USER_LOGIN,false)){
                    startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                    finish();
                }else{
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    intent.putExtra("type","");
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(String error) {
                if(MyApplication.setting.getBoolean(SH_USER_LOGIN,false)){
                    startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                    finish();
                }else{
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    intent.putExtra("type","");
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
