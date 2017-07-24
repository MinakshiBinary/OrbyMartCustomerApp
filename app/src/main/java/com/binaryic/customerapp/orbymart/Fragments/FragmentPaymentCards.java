package com.binaryic.customerapp.orbymart.Fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.binaryic.customerapp.orbymart.Activity.PaymentActivity;
import com.binaryic.customerapp.orbymart.Activity.PaymentsActivity;
import com.binaryic.customerapp.orbymart.R;
import com.payu.india.Model.PaymentParams;
import com.payu.india.Model.PostData;
import com.payu.india.Payu.PayuConstants;
import com.payu.india.Payu.PayuErrors;
import com.payu.india.Payu.PayuUtils;
import com.payu.india.PostParams.PaymentPostParams;

/**
 * Created by Asd on 12-10-2016.
 */
public class FragmentPaymentCards extends Fragment {
    private PayuUtils payuUtils;
    EditText cardNameEditText, cardNumberEditText, cardCvvEditText, cardExpiryMonthEditText, cardExpiryYearEditText, saveCardCheckBox;
    Button btnPay;
    ImageView imageViewIssuer;
    LinearLayout layMonth,layCVV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paymentcards, container, false);
        try {
            cardNameEditText = (EditText) view.findViewById(R.id.etCardName);
            cardNumberEditText = (EditText) view.findViewById(R.id.etCardNumber);
            cardCvvEditText = (EditText) view.findViewById(R.id.etCVV);
            cardExpiryMonthEditText = (EditText) view.findViewById(R.id.etMonth);
            cardExpiryYearEditText = (EditText) view.findViewById(R.id.etYear);
            btnPay = (Button) view.findViewById(R.id.btnPay);
            imageViewIssuer = (ImageView) view.findViewById(R.id.imageViewIssuer);
            layMonth = (LinearLayout) view.findViewById(R.id.layMonth);
            layCVV = (LinearLayout) view.findViewById(R.id.layCVV);
            payuUtils = new PayuUtils();
            cardNumberEditText.addTextChangedListener(new TextWatcher() {
                String issuer;
                Drawable issuerDrawable;

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() > 5) {
                     // to confirm rupay card we need min 6 digit.
                        try {
                            if (null == issuer)
                                issuer = payuUtils.getIssuer(charSequence.toString());
                            if (issuer != null && issuer.length() > 1 && issuerDrawable == null) {
                                issuerDrawable = getIssuerDrawable(issuer);
                                if (issuer.contentEquals(PayuConstants.SMAE)) { // hide cvv and expiry
                                    layMonth.setVisibility(View.GONE);
                                    layCVV.setVisibility(View.GONE);
                                } else { //show cvv and expiry
                                    layMonth.setVisibility(View.VISIBLE);
                                    layCVV.setVisibility(View.VISIBLE);
                                }
                            }
                        }catch (Exception ex){
                            String temp = ex.getMessage();
                        }
                    } else {
                        layMonth.setVisibility(View.GONE);
                        layCVV.setVisibility(View.GONE);
                        issuer = null;
                        issuerDrawable = null;
                    }
                    imageViewIssuer.setImageDrawable(issuerDrawable);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
//                        Intent intent = new Intent();
//                        intent.putExtra("result", "success");
//                        getActivity().setResult(getActivity().RESULT_OK, intent);
//                        getActivity().finish();
                        Log.e("pay","pay");
                        PaymentParams paymentParams = new PaymentParams();
                        paymentParams = ((PaymentActivity) getActivity()).mPaymentParams;
                        ((PaymentActivity) getActivity()).mPaymentParams.setHash(((PaymentActivity) getActivity()).payuHashes.getPaymentHash());
                        // lets try to get the post params
                        PostData postData = null;
                        // lets get the current card number;
                        String cardNumber = String.valueOf(cardNumberEditText.getText());
                        String cardName = cardNameEditText.getText().toString();
                        String expiryMonth = cardExpiryMonthEditText.getText().toString();
                        String expiryYear = cardExpiryYearEditText.getText().toString();
                        String cvv = cardCvvEditText.getText().toString();
                        // lets not worry about ui validations.
                        paymentParams.setCardNumber(cardNumber);
                        paymentParams.setCardName(cardName);
                        paymentParams.setNameOnCard(cardName);
                        paymentParams.setExpiryMonth(expiryMonth);
                        paymentParams.setExpiryYear(expiryYear);
                        paymentParams.setCvv(cvv);
                        postData = new PaymentPostParams(paymentParams, PayuConstants.CC).getPaymentPostParams();
                        if (postData.getCode() == PayuErrors.NO_ERROR) {
                            ((PaymentActivity) getActivity()).payuConfig.setData(postData.getResult());
                            Intent intent = new Intent(getActivity(), PaymentsActivity.class);
                            intent.putExtra(PayuConstants.PAYU_CONFIG, ((PaymentActivity) getActivity()).payuConfig);
                            startActivityForResult(intent, PayuConstants.PAYU_REQUEST_CODE);
                        } else {
                            Toast.makeText(getActivity(), postData.getResult(), Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception ex){
                        String temp = ex.getMessage();
                    }
                }
            });
        } catch (Exception e) {
        }
        return view;
    }

    private Drawable getIssuerDrawable(String issuer) {

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            switch (issuer) {
                case PayuConstants.VISA:
                    return getResources().getDrawable(R.drawable.visa);
                case PayuConstants.LASER:
                    return getResources().getDrawable(R.drawable.laser);
                case PayuConstants.DISCOVER:
                    return getResources().getDrawable(R.drawable.discover);
                case PayuConstants.MAES:
                    return getResources().getDrawable(R.drawable.maestro);
                case PayuConstants.MAST:
                    return getResources().getDrawable(R.drawable.master);
                case PayuConstants.AMEX:
                    return getResources().getDrawable(R.drawable.amex);
                case PayuConstants.DINR:
                    return getResources().getDrawable(R.drawable.diner);
                case PayuConstants.JCB:
                    return getResources().getDrawable(R.drawable.jcb);
                case PayuConstants.SMAE:
                    return getResources().getDrawable(R.drawable.maestro);
                case PayuConstants.RUPAY:
                    return getResources().getDrawable(R.drawable.rupay);
            }
            return null;
        } else {

            switch (issuer) {
                case PayuConstants.VISA:
                    return getResources().getDrawable(R.drawable.visa, null);
                case PayuConstants.LASER:
                    return getResources().getDrawable(R.drawable.laser, null);
                case PayuConstants.DISCOVER:
                    return getResources().getDrawable(R.drawable.discover, null);
                case PayuConstants.MAES:
                    return getResources().getDrawable(R.drawable.maestro, null);
                case PayuConstants.MAST:
                    return getResources().getDrawable(R.drawable.master, null);
                case PayuConstants.AMEX:
                    return getResources().getDrawable(R.drawable.amex, null);
                case PayuConstants.DINR:
                    return getResources().getDrawable(R.drawable.diner, null);
                case PayuConstants.JCB:
                    return getResources().getDrawable(R.drawable.jcb, null);
                case PayuConstants.SMAE:
                    return getResources().getDrawable(R.drawable.maestro, null);
                case PayuConstants.RUPAY:
                    return getResources().getDrawable(R.drawable.rupay, null);
            }
            return null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PayuConstants.PAYU_REQUEST_CODE) {
            if(resultCode == getActivity().RESULT_OK) {
                getActivity().setResult(resultCode, data);
                Log.e("result", data.getStringExtra(getString(R.string.cb_payu_response)));
                getActivity().finish();
            }
        }
    }
}
