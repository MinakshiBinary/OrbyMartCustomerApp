package com.binaryic.customerapp.orbymart.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.customerapp.orbymart.Activity.OrderActivity;
import com.binaryic.customerapp.orbymart.Adapter.OrderDetailItemAdapter;
import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Controller.CallBackResult;
import com.binaryic.customerapp.orbymart.Controller.OrderController;
import com.binaryic.customerapp.orbymart.Model.OrderModel;
import com.binaryic.customerapp.orbymart.Model.OrderProductModel;
import com.binaryic.customerapp.orbymart.R;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by HP on 14-Mar-17.
 */

public class FragmentOrderDetail extends Fragment implements OrderDetailItemAdapter.ClickListener {
    TextView tv_top_amount, tv_status, tv_items_count, tv_order_no, tv_created_date, tv_total_amount, tv_payble_amount, tv_discount, tv_address_name, tv_payment_mode, tv_address, tv_tax_amount;
    RecyclerView rv_order_item;
    OrderModel orderModel;
    ArrayList<OrderProductModel> list;
    OrderDetailItemAdapter orderDetailItemAdapter;
    CloseListner closeListner;


    public void setCloseListner(CloseListner closeListner) {
        this.closeListner = closeListner;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        inIt(view);
        getExtra();
        SetOderData();
        return view;
    }

    private void inIt(View view) {
        rv_order_item = (RecyclerView) view.findViewById(R.id.rv_order_item);
        tv_top_amount = (TextView) view.findViewById(R.id.tv_top_amount);
        tv_status = (TextView) view.findViewById(R.id.tv_status);
        tv_items_count = (TextView) view.findViewById(R.id.tv_items_count);
        tv_order_no = (TextView) view.findViewById(R.id.tv_order_no);
        tv_created_date = (TextView) view.findViewById(R.id.tv_created_date);
        tv_total_amount = (TextView) view.findViewById(R.id.tv_total_amount);
        tv_payble_amount = (TextView) view.findViewById(R.id.tv_payble_amount);
        tv_discount = (TextView) view.findViewById(R.id.tv_discount);
        tv_address_name = (TextView) view.findViewById(R.id.tv_address_name);
        tv_address = (TextView) view.findViewById(R.id.tv_address);
        tv_payment_mode = (TextView) view.findViewById(R.id.tv_payment_mode);
        tv_tax_amount = (TextView) view.findViewById(R.id.tv_tax_amount);
    }

    private void getExtra() {
        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                String model = bundle.getString("model");
                orderModel = new Gson().fromJson(model, OrderModel.class);
            }
        } catch (Exception ex) {
        }
    }

    private void SetOderData() {
        tv_top_amount.setText(orderModel.getTotal_amount());
        tv_status.setText(orderModel.getStatus());
        tv_items_count.setText(orderModel.getProductModels().size() + "");
        tv_order_no.setText(orderModel.getOrder_no());
        tv_created_date.setText(orderModel.getCreated_date());
        tv_total_amount.setText(orderModel.getTotal_amount());
        //String mrp = String.valueOf(Double.parseDouble(orderModel.getTotal_amount()) + Double.parseDouble(orderModel.getDiscount_amount()));
        tv_payble_amount.setText(orderModel.getSelling_price());
        tv_discount.setText(orderModel.getDiscount_amount());
        tv_tax_amount.setText(orderModel.getTax());
        tv_address_name.setText(orderModel.getCustomer_name());
        tv_address.setText(orderModel.getShipping_address());
        tv_payment_mode.setText(orderModel.getPayment_mode());
        list = orderModel.getProductModels();
        setOrdersItems(list);
    }

    private void setOrdersItems(ArrayList<OrderProductModel> productModels) {
        orderDetailItemAdapter = new OrderDetailItemAdapter(getActivity(), productModels);
        rv_order_item.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        orderDetailItemAdapter.setClickListener(FragmentOrderDetail.this);
        rv_order_item.setAdapter(orderDetailItemAdapter);
    }


    @Override
    public void onClicked(View v, int position) {
        subOrderDelete(position);
    }

    private void subOrderDelete(final int position) {
        Utils.showMessageBox("Are you sure, You want to remove product ?", "Yes", "No, Thanks", true, getActivity());
        Utils.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.msgDialog.dismiss();
                Utils.showProgress("Remove product...", getActivity());
                OrderController orderController = new OrderController();
                Log.e("FragmentOrderDetails","order id=="+ orderModel.getOrder_no());
                Log.e("FragmentOrderDetails","sub order id=="+  list.get(position).getSub_order_id());
                orderController.deleteSubOrder(getActivity(), orderModel.getOrder_no(), list.get(position).getSub_order_id(), new CallBackResult<String>() {
                    @Override
                    public void onSuccess(String s) {
                        getOrder(position);
                    }

                    @Override
                    public void onError(String error) {
                        Utils.progressDialog.dismiss();
                        Utils.showMessageBox(error, "OK", "", false, getActivity());
                    }
                });

            }
        });
        Utils.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.msgDialog.dismiss();
            }
        });
    }

    public interface CloseListner {
        public void onClose(boolean isRemove, ArrayList<OrderModel> list);
    }

    private void getOrder(final int position) {
        OrderController orderController = new OrderController();
        orderController.getOrderApiCall(getActivity(), new CallBackResult<ArrayList<OrderModel>>() {
            @Override
            public void onSuccess(ArrayList<OrderModel> orderModels) {
                for (OrderModel model : orderModels) {
                    if (model.getOrder_id().equals(orderModel.getOrder_id())) {
                        orderModel = model;
                        break;
                    }
                }

                SetOderData();
                Utils.progressDialog.dismiss();
                Toast.makeText(getActivity(), "Order product remove successfully", Toast.LENGTH_SHORT).show();
                if (closeListner != null)
                    closeListner.onClose(true, orderModels);
                if (orderModel.getProductModels().size() == 0) {
                    ((OrderActivity) getActivity()).onBackPressed();
                }
            }

            @Override
            public void onError(String error) {
                list.remove(position);
                orderDetailItemAdapter.list = list;
                orderDetailItemAdapter.notifyDataSetChanged();
                Utils.progressDialog.dismiss();
                Toast.makeText(getActivity(), "Order product remove successfully", Toast.LENGTH_SHORT).show();
                if (list.size() == 0) {
                    ((OrderActivity) getActivity()).onBackPressed();
                }
            }
        });

    }
}
