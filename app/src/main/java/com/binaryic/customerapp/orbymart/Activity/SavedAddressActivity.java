package com.binaryic.customerapp.orbymart.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Controller.CallBackResult;
import com.binaryic.customerapp.orbymart.Controller.CustomerController;
import com.binaryic.customerapp.orbymart.Fragments.FragmentAddresses;
import com.binaryic.customerapp.orbymart.R;


public class SavedAddressActivity extends AppCompatActivity {
    TextView tvHeader;
    public FrameLayout layMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_address);
        inIt();
    }

    public void inIt(){
        tvHeader = (TextView) findViewById(R.id.tvHeader);
        layMain = (FrameLayout) findViewById(R.id.layMain);
        ImageView btnClose = (ImageView) findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvHeader.setText("My Addresses");
        getAddressApi();
    }
    private void AddAddressFragment() {
        try {
            FragmentAddresses fragmentAddresses = new FragmentAddresses();
            Utils.addFragment(layMain.getId(), fragmentAddresses, this);
        } catch (Exception ex) {
        }
    }
    private void getAddressApi(){
        if(Utils.isConnectingToInternet(this)){
            Utils.showProgress("Get Address...",this);
            CustomerController customerController = new CustomerController();
            customerController.getAddress(this, new CallBackResult<String>() {
                @Override
                public void onSuccess(String s) {
                    Utils.progressDialog.dismiss();
                    AddAddressFragment();
                }

                @Override
                public void onError(String error) {
                    Utils.progressDialog.dismiss();
                    Utils.showMessageBox(error,"OK","",false,SavedAddressActivity.this);
                    AddAddressFragment();
                }
            });
        }else{
            Toast.makeText(this, "Please check internet.", Toast.LENGTH_SHORT).show();

        }
    }
}
