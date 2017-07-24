package com.binaryic.customerapp.orbymart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Fragments.FragmentCart;
import com.binaryic.customerapp.orbymart.Fragments.FragmentDelivery;
import com.binaryic.customerapp.orbymart.Fragments.FragmentPayment;
import com.binaryic.customerapp.orbymart.R;
import com.payu.india.Payu.PayuConstants;

import org.json.JSONObject;


/**
 * Created by Asd on 16-09-2016.
 */
public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    FrameLayout layMain;
    FragmentCart fragmentCart;
    FragmentDelivery fragmentDelivery;
    FragmentPayment fragmentPayment;
    TextView tvHeader;
    Boolean isClickOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Init();
        layMain = (FrameLayout) findViewById(R.id.layMain);
        AddCartFragment();
    }

    private void Init() {
        try {
            tvHeader = (TextView) findViewById(R.id.tvHeader);
            ImageView btnClose = (ImageView) findViewById(R.id.btnClose);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        } catch (Exception ex) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment f = getSupportFragmentManager().findFragmentById(layMain.getId());
        if (f instanceof FragmentDelivery) {
            ((FragmentDelivery) f).SetUI();
        }
    }

    private void AddCartFragment() {
        try {
            tvHeader.setText("Your cart");
            fragmentCart = new FragmentCart();
            Utils.addFragment(layMain.getId(), fragmentCart, this);
        } catch (Exception ex) {
        }
    }

    public void AddDeliveryFragment(String Amount) {
        try {
            tvHeader.setText("Delivery Address");
            fragmentDelivery = new FragmentDelivery();
            Bundle bundle = new Bundle();
            bundle.putString("Amount", Amount);
            fragmentDelivery.setArguments(bundle);
            Utils.AddFragmentBack(layMain.getId(), fragmentDelivery, this);
        } catch (Exception ex) {
        }
    }

    public void AddPaymentFragment(String Amount) {
        try {
            tvHeader.setText("Payment Option");
            fragmentPayment = new FragmentPayment();
            Bundle bundle = new Bundle();
            bundle.putString("Amount",Amount);
            fragmentPayment.setArguments(bundle);
            Utils.AddFragmentBack(layMain.getId(), fragmentPayment, this);
        } catch (Exception ex) {
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (isClickOn) {
                if (v.getId() == R.id.btnClose) {
                    finish();
                }
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.layMain);
            if (f instanceof FragmentCart) {
                tvHeader.setText("Your cart");
            }else if(f instanceof FragmentDelivery){
                ((FragmentDelivery)f).checkFragment();
                tvHeader.setText("Delivery Address");
            }else if(f instanceof FragmentPayment){
                tvHeader.setText("Payment Option");
            }
        } catch (Exception ex) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PayuConstants.PAYU_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                setResult(resultCode, data);
                Log.e("result", data.getStringExtra(getString(R.string.cb_payu_response)));
                try {
                    JSONObject obj = new JSONObject(data.getStringExtra(getString(R.string.cb_payu_response)));
                    if (obj.getString("status").equals("success")) {
                        Fragment f = getSupportFragmentManager().findFragmentById(R.id.layMain);
                        if (f instanceof FragmentPayment) {
                            ((FragmentPayment) f).SetOrder(true,obj.getString("txnid"));
                        }
                    }
                }catch (Exception ex){}
            }
        }
    }
}
