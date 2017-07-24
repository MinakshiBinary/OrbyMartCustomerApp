package com.binaryic.customerapp.orbymart.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.binaryic.customerapp.orbymart.Activity.PaymentActivity;
import com.binaryic.customerapp.orbymart.Activity.PaymentsActivity;
import com.binaryic.customerapp.orbymart.Adapter.BanksAdapter;
import com.binaryic.customerapp.orbymart.R;
import com.payu.india.Model.PaymentDetails;
import com.payu.india.Model.PaymentParams;
import com.payu.india.Model.PostData;
import com.payu.india.Payu.PayuConstants;
import com.payu.india.Payu.PayuErrors;
import com.payu.india.PostParams.PaymentPostParams;

import java.util.ArrayList;

/**
 * Created by Asd on 12-10-2016.
 */
public class FragmentNetBanking extends Fragment implements BanksAdapter.ClickListener {
    RecyclerView recycler;
    BanksAdapter adapter;
    ArrayList<PaymentDetails> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_netbanking, container, false);
        try {
            recycler = (RecyclerView) view.findViewById(R.id.recycler);
            //Log.e("banks", ((PaymentActivity) getActivity()).mPayuResponse.getNetBanks().toString());
            list = ((PaymentActivity) getActivity()).mPayuResponse.getNetBanks();
            SetUpRecyclerView();
        } catch (Exception e) {
        }
        return view;
    }

    private void SetUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        adapter = new BanksAdapter(getActivity(), list);
        adapter.setClickListener(this);
        recycler.setAdapter(adapter);

    }

    @Override
    public void ItemClicked(View view, int position) {
        try {
            PostData postData = new PostData();
            PaymentParams paymentParams = new PaymentParams();
            paymentParams = ((PaymentActivity) getActivity()).mPaymentParams;
            paymentParams.setHash(((PaymentActivity) getActivity()).payuHashes.getPaymentHash());
            paymentParams.setBankCode(list.get(position).getBankCode());
            postData = new PaymentPostParams(paymentParams, PayuConstants.NB).getPaymentPostParams();
            if (postData.getCode() == PayuErrors.NO_ERROR) {
                // launch webview
                ((PaymentActivity) getActivity()).payuConfig.setData(postData.getResult());
                Intent intent = new Intent(getActivity(), PaymentsActivity.class);
                intent.putExtra(PayuConstants.PAYU_CONFIG, ((PaymentActivity) getActivity()).payuConfig);
                startActivityForResult(intent, PayuConstants.PAYU_REQUEST_CODE);
            } else {
                Toast.makeText(getActivity(), postData.getResult(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PayuConstants.PAYU_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                getActivity().setResult(resultCode, data);
                Log.e("result", data.getStringExtra("result"));
                getActivity().finish();
            }
        }
    }
}
