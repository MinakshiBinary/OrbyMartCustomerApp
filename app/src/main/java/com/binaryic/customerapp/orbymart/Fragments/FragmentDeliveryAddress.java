package com.binaryic.customerapp.orbymart.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.customerapp.orbymart.Activity.CartActivity;
import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Controller.CallBackResult;
import com.binaryic.customerapp.orbymart.Controller.CustomerController;
import com.binaryic.customerapp.orbymart.Model.Address;
import com.binaryic.customerapp.orbymart.R;


/**
 * Created by Asd on 08-10-2016.
 */
public class FragmentDeliveryAddress extends Fragment implements View.OnClickListener, FragmentAddNewAddress.AddAddressCloseListener, FragmentAddresses.AddressesCloseListener {
    TextView tvName, tvAddress, tvPincode, tvContactNo, tvTotalPrice;
    RelativeLayout btnAddAddress;
    LinearLayout btnProceed;
    String Amount = "0.0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery_address, container, false);
        try {
            GetExtra();
            InIt(view);
            getAddressApi();
        } catch (Exception e) {
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Resume","FragmentDeliveryAddress");
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

    private void InIt(View view) {
        try {
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            tvPincode = (TextView) view.findViewById(R.id.tvPincode);
            tvContactNo = (TextView) view.findViewById(R.id.tvContactNo);
            tvTotalPrice = (TextView) view.findViewById(R.id.tvTotalPrice);
            tvTotalPrice.setText(Amount);
            btnAddAddress = (RelativeLayout) view.findViewById(R.id.btnAddAddress);
            btnProceed = (LinearLayout) view.findViewById(R.id.btnProceed);
            btnProceed.setOnClickListener(this);
            btnAddAddress.setOnClickListener(this);
        } catch (Exception ex) {
        }
    }
    private void getAddressApi(){
        if(Utils.isConnectingToInternet(getActivity())){
            Utils.showProgress("Get Address...",getActivity());
            CustomerController customerController = new CustomerController();
            customerController.getAddress(getActivity(), new CallBackResult<String>() {
                @Override
                public void onSuccess(String s) {
                    Utils.progressDialog.dismiss();
                    SetUI();
                }

                @Override
                public void onError(String error) {
                    Utils.progressDialog.dismiss();
                    Utils.showMessageBox(error,"OK","",false,getActivity());
                    SetUI();
                }
            });
        }else{
            Toast.makeText(getActivity(), "Please check internet.", Toast.LENGTH_SHORT).show();
            SetUI();
        }
    }
    private void SetUI() {
        try {
            Address address = new CustomerController().GetDefaultAddress(getActivity());
            if (address != null) {
                GetDefaultAddress();
            } else {
                AddCreateAddressFragment();
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == R.id.btnAddAddress) {
                FragmentAddresses fragmentAddresses = new FragmentAddresses();
                fragmentAddresses.setAddressesCloseListener(FragmentDeliveryAddress.this);
                Utils.AddFragmentBack(FragmentDelivery.layAddress.getId(), fragmentAddresses, getActivity());
            } else if (v.getId() == R.id.btnProceed) {
                Address address = CustomerController.GetDefaultAddress(getActivity());
                if(address != null) {
                    ((CartActivity) getActivity()).AddPaymentFragment(Amount);
                }else{
                    Toast.makeText(getActivity(), "Please add default address", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {
        }
    }

    private void AddCreateAddressFragment() {
        try {
            FragmentAddNewAddress fragmentAddNewAddress = new FragmentAddNewAddress();
            fragmentAddNewAddress.setAddAddressCloseListener(this);
            Utils.AddFragmentBack(FragmentDelivery.layAddress.getId(), fragmentAddNewAddress, getActivity());
        } catch (Exception ex) {
        }
    }

    public void GetDefaultAddress() {
        try {
            Address address = CustomerController.GetDefaultAddress(getActivity());
            tvName.setText(address.getFirstName() + " " + address.getLastName());
            tvAddress.setText(address.getAddress() + " " + address.getLandmark() + " " + address.getArea() + " " + address.getCity() + " " + address.getState());
            tvPincode.setText(address.getZip());
            tvContactNo.setText(address.getPhone());
        } catch (Exception ex) {
        }
    }

    @Override
    public void AddAddressClose() {
        getActivity().onBackPressed();
        GetDefaultAddress();
    }


    @Override
    public void AddressesClose(boolean isClose) {
        if (isClose)
            getActivity().onBackPressed();
        GetDefaultAddress();
    }
}
