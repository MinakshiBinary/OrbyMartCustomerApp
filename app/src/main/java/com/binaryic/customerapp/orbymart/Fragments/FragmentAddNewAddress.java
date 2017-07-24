package com.binaryic.customerapp.orbymart.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Controller.CallBackResult;
import com.binaryic.customerapp.orbymart.Controller.CustomerController;
import com.binaryic.customerapp.orbymart.Custom.AnimatedAutoEditText;
import com.binaryic.customerapp.orbymart.Custom.AnimatedEditText;
import com.binaryic.customerapp.orbymart.Model.Address;
import com.binaryic.customerapp.orbymart.R;


/**
 * Created by MY PC on 07-Oct-16.
 */
public class FragmentAddNewAddress extends Fragment implements View.OnClickListener {

    AnimatedEditText txtFirstName, txtLastName, txtAddress1, etLandmark, txtPincode, txtMobile, txtCity,et_area;
    CardView btnSave;
    AnimatedAutoEditText txtState;
    AddAddressCloseListener addAddressCloseListener;

    public void setAddAddressCloseListener(AddAddressCloseListener addAddressCloseListener) {
        this.addAddressCloseListener = addAddressCloseListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_delivery_add_address, container, false);
        try {
            initView(view);
        } catch (Exception e) {
        }
        return view;
    }

    private void initView(View view) {
        try {
            txtFirstName = (AnimatedEditText) view.findViewById(R.id.etfirstname);
            txtLastName = (AnimatedEditText) view.findViewById(R.id.etlastname);
            txtAddress1 = (AnimatedEditText) view.findViewById(R.id.etaddress1);
            etLandmark = (AnimatedEditText) view.findViewById(R.id.etLandmark);
            et_area = (AnimatedEditText) view.findViewById(R.id.et_area);
            txtPincode = (AnimatedEditText) view.findViewById(R.id.etpincode);
            txtMobile = (AnimatedEditText) view.findViewById(R.id.etmobile);
            txtMobile.et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
            txtPincode.et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
            txtCity = (AnimatedEditText) view.findViewById(R.id.etcity);
            txtState = (AnimatedAutoEditText) view.findViewById(R.id.etstate);
            ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.state));
            txtState.et.setAdapter(adapter);
            txtState.et.setThreshold(1);
            txtState.et.setAdapter(adapter);
            btnSave = (CardView) view.findViewById(R.id.btnSave);
            btnSave.setOnClickListener(this);
            txtCity.et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            txtState.et.setImeOptions(EditorInfo.IME_ACTION_DONE);
            txtFirstName.et.setOnEditorActionListener(new EditText.OnEditorActionListener(){
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT){
                        txtLastName.et.requestFocus();
                    }
                    return false;
                }
            });
            txtLastName.et.setOnEditorActionListener(new EditText.OnEditorActionListener(){
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT){
                        txtAddress1.et.requestFocus();
                    }
                    return false;
                }
            });
            txtAddress1.et.setOnEditorActionListener(new EditText.OnEditorActionListener(){
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT){
                        etLandmark.et.requestFocus();
                    }
                    return false;
                }
            });
            etLandmark.et.setOnEditorActionListener(new EditText.OnEditorActionListener(){
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT){
                        et_area.et.requestFocus();
                    }
                    return false;
                }
            });
            et_area.et.setOnEditorActionListener(new EditText.OnEditorActionListener(){
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT){
                        txtPincode.et.requestFocus();
                    }
                    return false;
                }
            });
            txtPincode.et.setOnEditorActionListener(new EditText.OnEditorActionListener(){
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT){
                        txtMobile.et.requestFocus();
                    }
                    return false;
                }
            });
            txtMobile.et.setOnEditorActionListener(new EditText.OnEditorActionListener(){
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT){
                        txtCity.et.requestFocus();
                    }
                    return false;
                }
            });
            txtCity.et.setOnEditorActionListener(new EditText.OnEditorActionListener(){
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT){
                        txtState.et.requestFocus();
                    }
                    return false;
                }
            });

        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSave) {
            CheckValid();
        }
    }

    private void CheckValid() {
        try {
            boolean isValid = true;
            if (txtFirstName.et.getText().toString().trim().equals("")) {
                isValid = false;
                txtFirstName.getInstatnce().SetError("Enter First Name");
            } else {
                txtFirstName.getInstatnce().RemoveError();
                isValid = true;
            }
            if (txtLastName.et.getText().toString().trim().equals("")) {
                isValid = false;
                txtLastName.getInstatnce().SetError("Enter Last Name");
            } else {
                txtLastName.getInstatnce().RemoveError();
                isValid = true;
            }
            if (txtAddress1.et.getText().toString().trim().equals("")) {
                isValid = false;
                txtAddress1.getInstatnce().SetError("Enter Address1");
            } else {
                isValid = true;
                txtAddress1.getInstatnce().RemoveError();
            }
            if (etLandmark.et.getText().toString().trim().equals("")) {
                isValid = false;
                etLandmark.getInstatnce().SetError("Enter Landmark");
            } else {
                isValid = true;
                etLandmark.getInstatnce().RemoveError();
            }
            if (et_area.et.getText().toString().trim().equals("")) {
                isValid = false;
                et_area.getInstatnce().SetError("Enter Area");
            } else {
                isValid = true;
                et_area.getInstatnce().RemoveError();
            }
            if (txtPincode.et.getText().toString().trim().equals("")) {
                isValid = false;
                txtPincode.getInstatnce().SetError("Enter Pincode");
            } else {
                if (txtPincode.et.getText().length() == 6) {
                    isValid = true;
                    txtPincode.getInstatnce().RemoveError();
                } else {
                    isValid = false;
                    txtPincode.getInstatnce().SetError("Enter Vaild Pincode");
                }
            }
            if (txtMobile.et.getText().toString().trim().equals("")) {
                isValid = false;
                txtMobile.getInstatnce().SetError("Enter Mobile");
            } else {
                if (txtMobile.et.getText().toString().length() == 10) {
                    isValid = true;
                    txtMobile.getInstatnce().RemoveError();
                } else {
                    isValid = false;
                    txtMobile.getInstatnce().SetError("Enter Valid Mobile");
                }
            }
            if (txtCity.et.getText().toString().trim().equals("")) {
                isValid = false;
                txtCity.getInstatnce().SetError("Enter City");
            } else {
                isValid = true;
                txtCity.getInstatnce().RemoveError();
            }
            if (txtState.et.getText().toString().trim().equals("")) {
                isValid = false;
                txtState.getInstatnce().SetError("Enter State");
            } else {
                isValid = true;
                txtState.getInstatnce().RemoveError();
            }
            if (isValid) {
                AddAddress();
            }
        } catch (Exception e) {
        }
    }

    private void AddAddress() {
        try {
            Address address = new Address();
            address.setAddress(txtAddress1.et.getText().toString().trim());
            address.setLandmark(etLandmark.et.getText().toString().trim());
            address.setArea(et_area.et.getText().toString().trim());
            address.setCity(txtCity.et.getText().toString().trim());
            address.setDefaultAddress(true);
            address.setFirstName(txtFirstName.et.getText().toString().trim());
            address.setLastName(txtLastName.et.getText().toString().trim());
            address.setPhone(txtMobile.et.getText().toString().trim());
            address.setState(txtState.et.getText().toString().trim());
            address.setZip(txtPincode.et.getText().toString().trim());
            CreateAdd(address);
        } catch (Exception ex) {
        }
    }

    public interface AddAddressCloseListener {
        public void AddAddressClose();
    }

    private void CreateAdd(Address address) {
        try {
            if(Utils.isConnectingToInternet(getActivity())) {
                Utils.showProgress("Add Address...", getActivity());
                CustomerController customerController = new CustomerController();
                customerController.addAddress(getActivity(), address.getPhone(), address.getFirstName(), address.getLastName(), address.getAddress(), address.getLandmark(), address.getArea(), address.getZip(), address.getCity(), address.getState(), new CallBackResult<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Utils.progressDialog.dismiss();
                        addAddressCloseListener.AddAddressClose();
                    }

                    @Override
                    public void onError(String error) {
                        Utils.progressDialog.dismiss();
                        Utils.showMessageBox(error, "OK", "", false, getActivity());
                    }
                });
            }else{
                Toast.makeText(getActivity(), "Please check internet.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
        }
    }
}
