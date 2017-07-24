package com.binaryic.customerapp.orbymart.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.customerapp.orbymart.Common.MyApplication;
import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Controller.CallBackResult;
import com.binaryic.customerapp.orbymart.Controller.CustomerController;
import com.binaryic.customerapp.orbymart.Custom.AnimatedAutoEditText;
import com.binaryic.customerapp.orbymart.Custom.AnimatedEditText;
import com.binaryic.customerapp.orbymart.Model.Customer;
import com.binaryic.customerapp.orbymart.R;

import org.json.JSONObject;

import java.util.regex.Pattern;

import static com.binaryic.customerapp.orbymart.Common.Constants.SH_USER_LOGIN;


/**
 * Created by Asd on 16-09-2016.
 */
public class LoginActivity extends AppCompatActivity {
    Customer customer = null;
    public String otp = "";
    String type = "";
    Dialog msgDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getExtra();
        final LinearLayout btnSignIn = (LinearLayout) findViewById(R.id.btnSignIn);
        final TextView lblSignUp = (TextView) findViewById(R.id.lblSignUp);
        final TextView lblSkip = (TextView) findViewById(R.id.lblSkip);
        final AnimatedEditText etMobile = (AnimatedEditText) findViewById(R.id.etMobile);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(10);
        etMobile.et.setFilters(filterArray);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Utils.isConnectingToInternet(LoginActivity.this)) {
                        Boolean isValid = true;
                        if (etMobile.et.getText().toString().trim().equals("")) {
                            isValid = false;
                            etMobile.getInstatnce().SetError("Enter Mobile No");
                        } else {
                            if (etMobile.et.getText().toString().trim().length() != 10) {
                                isValid = false;
                                etMobile.getInstatnce().SetError("Enter Valid Mobile No");
                            } else
                                etMobile.getInstatnce().RemoveError();
                        }
                        if (isValid) {
                            SignIn(etMobile.et.getText().toString().trim());
                        }
                    } else {
                        Utils.showMessageBox("Please Check your internet connection.", "OK", "", false, LoginActivity.this);
                    }
                } catch (Exception ex) {
                }
            }
        });
        lblSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpDialog();
            }
        });
        lblSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.setting.edit().putBoolean(SH_USER_LOGIN, false).commit();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }
        });

    }

    private void getExtra() {
        try {
            Intent intent = getIntent();
            type = intent.getStringExtra("type");
        } catch (Exception ex) {
            type = "";
        }
    }

    public void SignUpDialog() {
        try {
            msgDialog = new Dialog(LoginActivity.this);
            msgDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            msgDialog.setContentView(R.layout.fragment_signup);
            WindowManager.LayoutParams wmlp = msgDialog.getWindow().getAttributes();
            wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
            wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            msgDialog.setCanceledOnTouchOutside(false);
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            final AnimatedEditText etEmail = (AnimatedEditText) msgDialog.findViewById(R.id.etEmail);
            final AnimatedEditText etfirstname = (AnimatedEditText) msgDialog.findViewById(R.id.etfirstname);
            final AnimatedEditText etlastname = (AnimatedEditText) msgDialog.findViewById(R.id.etlastname);
            final AnimatedEditText etMobile = (AnimatedEditText) msgDialog.findViewById(R.id.etMobile);
            final AnimatedEditText etAddress = (AnimatedEditText) msgDialog.findViewById(R.id.etAddress);
            final AnimatedEditText etLandmark = (AnimatedEditText) msgDialog.findViewById(R.id.etLandmark);
            final AnimatedEditText etCity = (AnimatedEditText) msgDialog.findViewById(R.id.etCity);
            etCity.et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            final AnimatedEditText etArea = (AnimatedEditText) msgDialog.findViewById(R.id.etArea);
            final AnimatedEditText etPincode = (AnimatedEditText) msgDialog.findViewById(R.id.etPincode);
            final AnimatedAutoEditText etState = (AnimatedAutoEditText) msgDialog.findViewById(R.id.etState);
            etState.et.setImeOptions(EditorInfo.IME_ACTION_DONE);
            ArrayAdapter adapter = new ArrayAdapter(LoginActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.state));
            etState.et.setAdapter(adapter);
            etState.et.setThreshold(1);
            etState.et.setAdapter(adapter);
            InputFilter[] filterArray = new InputFilter[1];
            filterArray[0] = new InputFilter.LengthFilter(10);
            etMobile.et.setFilters(filterArray);
            filterArray[0] = new InputFilter.LengthFilter(6);
            etPincode.et.setFilters(filterArray);
            LinearLayout btnSignup = (LinearLayout) msgDialog.findViewById(R.id.btnSignup);
            etfirstname.et.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        etlastname.et.requestFocus();
                    }
                    return false;
                }
            });
            etlastname.et.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        etEmail.et.requestFocus();
                    }
                    return false;
                }
            });
//            etEmail.et.setOnEditorActionListener(new EditText.OnEditorActionListener(){
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    if (actionId == EditorInfo.IME_ACTION_NEXT){
//                        etMobile.et.requestFocus();
//                    }
//                    return false;
//                }
//            });
            etMobile.et.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        etfirstname.et.requestFocus();
                    }
                    return false;
                }
            });
//            etAddress.et.setOnEditorActionListener(new EditText.OnEditorActionListener(){
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    if (actionId == EditorInfo.IME_ACTION_NEXT){
//                        etLandmark.et.requestFocus();
//                    }
//                    return false;
//                }
//            });
//            etLandmark.et.setOnEditorActionListener(new EditText.OnEditorActionListener(){
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    if (actionId == EditorInfo.IME_ACTION_NEXT){
//                        etArea.et.requestFocus();
//                    }
//                    return false;
//                }
//            });
//            etArea.et.setOnEditorActionListener(new EditText.OnEditorActionListener(){
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    if (actionId == EditorInfo.IME_ACTION_NEXT){
//                        etPincode.et.requestFocus();
//                    }
//                    return false;
//                }
//            });
//            etPincode.et.setOnEditorActionListener(new EditText.OnEditorActionListener(){
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    if (actionId == EditorInfo.IME_ACTION_NEXT){
//                        etCity.et.requestFocus();
//                    }
//                    return false;
//                }
//            });
//            etCity.et.setOnEditorActionListener(new EditText.OnEditorActionListener(){
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    if (actionId == EditorInfo.IME_ACTION_NEXT){
//                        etState.et.requestFocus();
//                    }
//                    return false;
//                }
//            });
            btnSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (Utils.isConnectingToInternet(LoginActivity.this)) {
                            Boolean isValid = true;
                            if (etEmail.et.getText().toString().trim().equals("")) {
                                isValid = false;
                                etEmail.getInstatnce().SetError("Enter Email");
                            } else {
                                final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
                                if (!EMAIL_ADDRESS_PATTERN.matcher(etEmail.et.getText().toString().trim()).matches()) {
                                    isValid = false;
                                    etEmail.getInstatnce().SetError("Enter Valid Email");
                                } else
                                    etEmail.getInstatnce().RemoveError();
                            }
                            if (etfirstname.et.getText().toString().trim().equals("")) {
                                isValid = false;
                                etfirstname.getInstatnce().SetError("Enter First Name");
                            } else
                                etfirstname.getInstatnce().RemoveError();
                            if (etlastname.et.getText().toString().trim().equals("")) {
                                isValid = false;
                                etlastname.getInstatnce().SetError("Enter Last Name");
                            } else
                                etlastname.getInstatnce().RemoveError();
                            if (etMobile.et.getText().toString().trim().equals("")) {
                                isValid = false;
                                etMobile.getInstatnce().SetError("Enter Mobile No");
                            } else if (etMobile.et.getText().toString().trim().length() != 10) {
                                isValid = false;
                                etMobile.getInstatnce().SetError("Enter Valid Mobile No");
                            } else {
                                etMobile.getInstatnce().RemoveError();
                            }
//                            if (etAddress.et.getText().toString().trim().equals("")) {
//                                isValid = false;
//                                etAddress.getInstatnce().SetError("Enter Address");
//                            } else
//                                etAddress.getInstatnce().RemoveError();
//                            if (etLandmark.et.getText().toString().trim().equals("")) {
//                                isValid = false;
//                                etLandmark.getInstatnce().SetError("Enter Landmark");
//                            } else
//                                etLandmark.getInstatnce().RemoveError();
//                            if (etArea.et.getText().toString().trim().equals("")) {
//                                isValid = false;
//                                etArea.getInstatnce().SetError("Enter Area");
//                            } else
//                                etArea.getInstatnce().RemoveError();
//                            if (etState.et.getText().toString().trim().equals("")) {
//                                isValid = false;
//                                etState.getInstatnce().SetError("Enter State");
//                            } else
//                                etState.getInstatnce().RemoveError();
//                            if (etCity.et.getText().toString().trim().equals("")) {
//                                isValid = false;
//                                etCity.getInstatnce().SetError("Enter City");
//                            } else
//                                etCity.getInstatnce().RemoveError();
                            if (isValid) {
                                getOtpForRegistration(etfirstname.et.getText().toString().trim(), etlastname.et.getText().toString().trim(), etEmail.et.getText().toString().trim(), etMobile.et.getText().toString().trim(), etAddress.et.getText().toString().trim(), etLandmark.et.getText().toString().trim(), etArea.et.getText().toString().trim(), etPincode.et.getText().toString().trim(), etCity.et.getText().toString().trim(), etState.et.getText().toString().trim());
                            }
                        } else {
                            Utils.showMessageBox("Please Check your internet connection.", "OK", "", false, LoginActivity.this);
                        }
                    } catch (Exception ex) {
                    }
                }
            });
            msgDialog.show();
        } catch (Exception ex) {
        }
    }

    private void CallRegistration(final String firstName, final String lastName, final String email, final String mobileNo, final String address, final String landmark, final String area, final String pincode, final String city, final String state) {
        try {
            Utils.showProgress("Sign Up...", LoginActivity.this);
            CustomerController customerController = new CustomerController();
            customerController.mobileSignUp(LoginActivity.this, otp, firstName, lastName, email, mobileNo, address, landmark, area, pincode, city, state, new CallBackResult<String>() {
                @Override
                public void onSuccess(String s) {
                    Utils.progressDialog.dismiss();
                    msgDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Registration Successfully.", Toast.LENGTH_SHORT).show();
//                    customer = new Customer();
//                    customer.setArea(area);
//                    customer.setFirstName(firstName);
//                    customer.setState(state);
//                    customer.setUser_id(s);
//                    customer.setLandmark(landmark);
//                    customer.setMobile_no(mobileNo);
//                    customer.setAddress(address);
//                    customer.setCity(city);
//                    customer.setEmail(email);
//                    customer.setLastName(lastName);
//                    customer.setUser_pic("");
//                    customer.setZip(pincode);
//                    new CustomerController().AddCustomerData(LoginActivity.this, customer);
//                    if (type.equals("Login")) {
//                        finish();
//                    } else {
//                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                        finish();
//                    }
                }

                @Override
                public void onError(String error) {
                    Utils.progressDialog.dismiss();
                    Utils.showMessageBox(error, "OK", "", false, LoginActivity.this);
                }
            });
        } catch (Exception ex) {
        }
    }

    private void getOtpForRegistration(final String firstName, final String lastName, final String email, final String mobileNo, final String address, final String landmark, final String area, final String pincode, final String city, final String state) {
        Utils.showProgress("Sign Up...", LoginActivity.this);
        CustomerController customerController = new CustomerController();
        customerController.mobileVerificationSignUp(LoginActivity.this, mobileNo, new CallBackResult<String>() {
            @Override
            public void onSuccess(String s) {
                Utils.progressDialog.dismiss();
                dialogOtpShowRegistration(firstName, lastName, email, mobileNo, address, landmark, area, pincode, city, state);
                otp = s;
                Toast.makeText(LoginActivity.this, "otp :" + otp, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Utils.progressDialog.dismiss();
                Utils.showMessageBox(error, "OK", "", false, LoginActivity.this);
            }
        });
    }

    private void SignIn(String mobile_no) {
        try {
            Utils.showProgress("Sign In...", LoginActivity.this);
            CustomerController customerController = new CustomerController();
            customerController.mobileVerificationSignIn(LoginActivity.this, mobile_no, new CallBackResult<String>() {
                @Override
                public void onSuccess(String s) {
                    try {
                        Utils.progressDialog.dismiss();
                        JSONObject Details = new JSONObject(s);
                        customer = new Customer();
                        customer.setArea(Details.getString("area"));
                        customer.setFirstName(Details.getString("first_name"));
                        customer.setState(Details.getString("state"));
                        customer.setUser_id(Details.getString("customer_id"));
                        customer.setLandmark(Details.getString("landmark"));
                        customer.setMobile_no(Details.getString("mobile_number"));
                        customer.setAddress(Details.getString("customer_address"));
                        customer.setCity(Details.getString("city"));
                        customer.setEmail(Details.getString("customer_email_id"));
                        customer.setLastName(Details.getString("last_name"));
                        customer.setUser_pic("");
                        customer.setZip(Details.getString("pincode"));
                        otp = Details.getString("otp");
                        Toast.makeText(LoginActivity.this, otp, Toast.LENGTH_SHORT).show();
                        dialogOtpShow();
                    } catch (Exception ex) {
                    }
                }

                @Override
                public void onError(String error) {
                    Utils.progressDialog.dismiss();
                    otp = "";
                    customer = null;
                    Utils.showMessageBox(error, "OK", "", false, LoginActivity.this);
                }
            });
        } catch (Exception ex) {
        }
    }


    public void dialogOtpShow() {
        final Dialog otp_dialog = new Dialog(LoginActivity.this);
        otp_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        otp_dialog.setContentView(R.layout.dialog_otp);
        WindowManager.LayoutParams wmlp = otp_dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        otp_dialog.setCanceledOnTouchOutside(false);
        final AnimatedEditText etOtp = (AnimatedEditText) otp_dialog.findViewById(R.id.etOtp);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(4);
        etOtp.et.setFilters(filterArray);
        TextView btnDone = (TextView) otp_dialog.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etOtp.et.getText().toString().trim().equals("")) {
                    etOtp.RemoveError();
                    if (!otp.equals("")) {
                        if (otp.equals(etOtp.et.getText().toString().trim())) {
                            otp_dialog.dismiss();
                            etOtp.RemoveError();
                            new CustomerController().AddCustomerData(LoginActivity.this, customer);
                            if (type.equals("Login")) {
                                finish();
                            } else {
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            }
                        } else {
                            etOtp.SetError("In Correct OTP.");
                        }
                    }
                } else {
                    etOtp.SetError("Enter OTP.");
                }

            }
        });
        otp_dialog.show();
    }

    public void dialogOtpShowRegistration(final String firstName, final String lastName, final String email, final String mobileNo, final String address, final String landmark, final String area, final String pincode, final String city, final String state) {
        final Dialog otp_dialog = new Dialog(LoginActivity.this);
        otp_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        otp_dialog.setContentView(R.layout.dialog_otp);
        WindowManager.LayoutParams wmlp = otp_dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        otp_dialog.setCanceledOnTouchOutside(false);
        final AnimatedEditText etOtp = (AnimatedEditText) otp_dialog.findViewById(R.id.etOtp);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(4);
        etOtp.et.setFilters(filterArray);
        TextView btnDone = (TextView) otp_dialog.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etOtp.et.getText().toString().trim().equals("")) {
                    etOtp.RemoveError();
                    if (!otp.equals("")) {
                        if (otp.equals(etOtp.et.getText().toString().trim())) {
                            otp_dialog.dismiss();
                            CallRegistration(firstName, lastName, email, mobileNo, address, landmark, area, pincode, city, state);
                        } else {
                            etOtp.SetError("In Correct OTP.");
                        }
                    }
                } else {
                    etOtp.SetError("Enter OTP.");
                }

            }
        });
        otp_dialog.show();
    }

}
