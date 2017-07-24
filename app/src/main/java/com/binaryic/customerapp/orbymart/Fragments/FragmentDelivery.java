package com.binaryic.customerapp.orbymart.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.binaryic.customerapp.orbymart.Activity.LoginActivity;
import com.binaryic.customerapp.orbymart.Common.MyApplication;
import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.R;

import static com.binaryic.customerapp.orbymart.Common.Constants.SH_USER_LOGIN;


/**
 * Created by Asd on 16-09-2016.
 */
public class FragmentDelivery extends Fragment implements View.OnClickListener {
    public static FrameLayout layAddress;
    Boolean isClickOn = true;
    String Amount = "0.0";
    LinearLayout ll_logout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deliverydetails, container, false);
        try {
            GetExtra();
            Init(view);
        } catch (Exception e) {
        }
        return view;
    }

    private void Init(View view) {
        try{
            layAddress = (FrameLayout) view.findViewById(R.id.layAddress);
            ll_logout = (LinearLayout) view.findViewById(R.id.ll_logout);
            ll_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
                    intentLogin.putExtra("type", "Login");
                    startActivity(intentLogin);
                }
            });
            SetUI();
        }catch (Exception ex){}
    }
    private void GetExtra() {
        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                Amount = bundle.getString("Amount");
            }
        } catch (Exception ex) {
        }
    }
    public void SetUI(){
        try{
            if(MyApplication.setting.getBoolean(SH_USER_LOGIN,false)) {
                ll_logout.setVisibility(View.GONE);
                layAddress.setVisibility(View.VISIBLE);
                FragmentDeliveryAddress fragmentDeliveryAddress = new FragmentDeliveryAddress();
                Bundle bundle = new Bundle();
                bundle.putString("Amount", Amount);
                fragmentDeliveryAddress.setArguments(bundle);
                Utils.addFragment(layAddress.getId(), fragmentDeliveryAddress, getActivity());
            }else{
                ll_logout.setVisibility(View.VISIBLE);
                layAddress.setVisibility(View.GONE);
            }
        }catch (Exception ex){}
    }
    @Override
    public void onClick(View v) {
        try{
            if(isClickOn){

            }
        }catch (Exception ex){}
    }

    public void checkFragment(){
        Fragment f = getActivity().getSupportFragmentManager().findFragmentById(R.id.layAddress);
        if (f instanceof FragmentDeliveryAddress) {
            ((FragmentDeliveryAddress)f).GetDefaultAddress();
            Log.e("FragmentDeliveryAdd","OnBack");
        }
    }


}
