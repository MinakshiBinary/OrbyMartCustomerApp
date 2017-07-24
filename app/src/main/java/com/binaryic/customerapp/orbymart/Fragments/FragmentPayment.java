package com.binaryic.customerapp.orbymart.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.binaryic.customerapp.orbymart.Activity.PaymentActivity;
import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Controller.CallBackResult;
import com.binaryic.customerapp.orbymart.Controller.CartController;
import com.binaryic.customerapp.orbymart.Controller.CouponController;
import com.binaryic.customerapp.orbymart.Controller.CustomerController;
import com.binaryic.customerapp.orbymart.Controller.OrderController;
import com.binaryic.customerapp.orbymart.Custom.AnimatedEditText;
import com.binaryic.customerapp.orbymart.Model.Address;
import com.binaryic.customerapp.orbymart.Model.Customer;
import com.binaryic.customerapp.orbymart.Model.ProductModelQty;
import com.binaryic.customerapp.orbymart.R;
import com.payu.india.Payu.PayuConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by Asd on 16-09-2016.
 */
public class FragmentPayment extends Fragment implements View.OnClickListener {
    Boolean isClickOn = true;
    float Amount = 0;
    int Shipping = 0;
    float SubTotal = 0;
    float Total = 0;
    float Discount = 0;
    int Discount_per = 0;
    String transaction_type = "";
    String transaction_id = "";
    String coupon = "";
    RelativeLayout layCOD, layOnline;
    TextView tvPrice, tvSubTotal, tvShippingCharges, tvVatTotal, tvProductAmount, tvDiscount;
    RadioButton rbOptionCOD, rbOptionCard;
    AnimatedEditText et_coupon_code;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        try {
            GetExtra();
            Init(view);
        } catch (Exception e) {
        }
        return view;
    }

    private void Init(View view) {
        try {
            LinearLayout btnPlaceOrder = (LinearLayout) view.findViewById(R.id.btnPlaceOrder);
            LinearLayout btnApply = (LinearLayout) view.findViewById(R.id.btnApply);
            layCOD = (RelativeLayout) view.findViewById(R.id.layCOD);
            layOnline = (RelativeLayout) view.findViewById(R.id.layOnline);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            tvProductAmount = (TextView) view.findViewById(R.id.tvProductAmount);
            tvSubTotal = (TextView) view.findViewById(R.id.tvSubTotal);
            tvVatTotal = (TextView) view.findViewById(R.id.tvVatTotal);
            tvDiscount = (TextView) view.findViewById(R.id.tvDiscount);
            tvShippingCharges = (TextView) view.findViewById(R.id.tvShippingCharges);
            rbOptionCOD = (RadioButton) view.findViewById(R.id.rbOptionCOD);
            rbOptionCard = (RadioButton) view.findViewById(R.id.rbOptionCard);
            et_coupon_code = (AnimatedEditText) view.findViewById(R.id.et_coupon_code);
            btnPlaceOrder.setOnClickListener(this);
            btnApply.setOnClickListener(this);
            layCOD.setOnClickListener(this);
            layOnline.setOnClickListener(this);
            SetUI();
        } catch (Exception ex) {
        }
    }

    private void SetUI() {
        if (Shipping == 0)
            tvShippingCharges.setText("FREE");
        else
            tvShippingCharges.setText(Shipping);
        Discount = (Amount * Discount_per)/100;
        tvProductAmount.setText(new DecimalFormat("##.##").format(Amount));
        tvDiscount.setText(new DecimalFormat("##.##").format(Discount));
        SubTotal = Amount + Shipping - Discount;
        tvSubTotal.setText(new DecimalFormat("##.##").format(SubTotal));
        float vat = (float) (((SubTotal) * 1.2) / 100);
        tvVatTotal.setText(new DecimalFormat("##.##").format(vat));
        Total = SubTotal + vat;
        tvPrice.setText(new DecimalFormat("##.##").format(Total));
    }

    private void GetExtra() {
        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                Amount = Float.parseFloat(bundle.getString("Amount"));
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (isClickOn) {
                if (v.getId() == R.id.btnPlaceOrder) {
                    if (rbOptionCOD.isChecked()) {
                        SetOrder(false,"");
                    } else {
                        Customer customer = CustomerController.GetCustomerData(getActivity());
                        Intent intent = new Intent(getActivity(), PaymentActivity.class);
                        intent.putExtra("amount", new DecimalFormat("##.##").format(Total));
                        intent.putExtra("name", customer.getFirstName() + " " + customer.getLastName());
                        intent.putExtra("email", customer.getEmail());
                        intent.putExtra("product_info", CartController.GetProductInfo(getActivity()));
                        startActivityForResult(intent, PayuConstants.PAYU_REQUEST_CODE);
                    }
                } else if (v.getId() == R.id.layCOD) {
                    rbOptionCOD.setChecked(true);
                    rbOptionCard.setChecked(false);
                } else if (v.getId() == R.id.layOnline) {
                    rbOptionCOD.setChecked(false);
                    rbOptionCard.setChecked(true);
                } else if(v.getId() == R.id.btnApply){
                    if(!et_coupon_code.et.getText().toString().trim().equals("")){
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        applyCoupon();
                    }else{
                        et_coupon_code.SetError("Enter Coupon Code");
                    }
                }
            }
        } catch (Exception ex) {
        }
    }

    private void applyCoupon(){
        if(Utils.isConnectingToInternet(getActivity())){
            Utils.showProgress("Apply Coupon...",getActivity());
            CouponController controller = new CouponController();
            controller.applyCouponCall(getActivity(), et_coupon_code.et.getText().toString().trim(), new CallBackResult<Integer>() {
                @Override
                public void onSuccess(Integer integer) {
                    Utils.progressDialog.dismiss();
                    Discount_per = integer;
                    SetUI();
                    coupon = et_coupon_code.et.getText().toString().trim();
                }

                @Override
                public void onError(String error) {
                    Utils.progressDialog.dismiss();
                    Utils.showMessageBox(error,"OK","",false,getActivity());
                    Discount_per = 0;
                    SetUI();
                    coupon = "";

                }
            });
        }else{
            Toast.makeText(getActivity(), "Check your internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void SetOrder(boolean isPaid,String tran_id) {
        try {
            ArrayList<ProductModelQty> productModelQties = CartController.GetCartProducts(getActivity());
            JSONArray order_data = new JSONArray();
            for (ProductModelQty productModelQty : productModelQties) {
                JSONObject lineItemObj = new JSONObject();
                lineItemObj.put("product_id", productModelQty.getProductModel().getProduct_id());
                lineItemObj.put("product_quantity", productModelQty.getQty());
                lineItemObj.put("selling_price", productModelQty.getProductModel().getDiscount_price());
                order_data.put(lineItemObj);
            }
            JSONObject other_detail = new JSONObject();
            Address address = CustomerController.GetDefaultAddress(getActivity());
            other_detail.put("first_name", address.getFirstName());
            other_detail.put("last_name", address.getLastName());
            other_detail.put("customer_address", address.getAddress());
            other_detail.put("landmark", address.getLandmark());
            other_detail.put("area", address.getArea());
            other_detail.put("pincode", address.getZip());
            other_detail.put("city", address.getCity());
            other_detail.put("state", address.getState());
            other_detail.put("mobile_number", address.getPhone());
            if (isPaid) {
                transaction_type = "online payment";
                transaction_id = tran_id;
            }else{
                transaction_type = "cod";
                transaction_id = tran_id;
            }
            CreateOrder(order_data.toString(), other_detail.toString());
        } catch (Exception ex) {
        }
    }

    public void CreateOrder(final String order_data, final String other_detail) {
        try {
            Utils.showProgress("Create Order...", getActivity());
            OrderController orderController = new OrderController();
            orderController.createOrder(getActivity(), order_data, coupon, transaction_type, transaction_id, other_detail, Total + "", Discount + "", Amount + "", new CallBackResult<String>() {
                @Override
                public void onSuccess(String s) {
                    Utils.progressDialog.dismiss();
                    Utils.showMessageBox("Order Create Successfully","OK","",false,getActivity());
                    Utils.btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().finish();
                        }
                    });
                    CartController.RemoveCart(getActivity());
                }

                @Override
                public void onError(String error) {
                    Utils.progressDialog.dismiss();
                    Utils.showMessageBox(error,"OK","",false,getActivity());
                }
            });

        } catch (Exception ex) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PayuConstants.PAYU_REQUEST_CODE) {
            if(resultCode == getActivity().RESULT_OK) {
                Log.e("result", data.getStringExtra("result"));
                //getActivity().finish();

            }
        }
    }
}
