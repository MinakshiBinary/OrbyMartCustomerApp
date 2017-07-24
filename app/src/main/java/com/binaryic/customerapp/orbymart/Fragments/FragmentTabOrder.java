package com.binaryic.customerapp.orbymart.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.customerapp.orbymart.Activity.OrderActivity;
import com.binaryic.customerapp.orbymart.Adapter.OrderAdapter;
import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Controller.CallBackResult;
import com.binaryic.customerapp.orbymart.Controller.OrderController;
import com.binaryic.customerapp.orbymart.Model.OrderModel;
import com.binaryic.customerapp.orbymart.R;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by HP on 10-May-17.
 */

public class FragmentTabOrder extends Fragment implements FragmentOrderDetail.CloseListner,SwipeRefreshLayout.OnRefreshListener {
    RecyclerView rv_order;
    TextView tv_no_data;
    ArrayList<OrderModel> list;
    OrderAdapter orderAdapter;
    String type = "";
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            if(rv_order != null){
                getExtra();
                getOrder();
            }
        }
    }
    @Override
    public void onRefresh() {
        if(Utils.isConnectingToInternet(getActivity())){
            OrderController orderController = new OrderController();
            orderController.getOrderApiCall(getActivity(), new CallBackResult<ArrayList<OrderModel>>() {
                @Override
                public void onSuccess(ArrayList<OrderModel> orderModels) {
                    ((OrderActivity) getActivity()).list = orderModels;
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                        getOrder();
                    }
                }

                @Override
                public void onError(String error) {
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                        getOrder();
                    }
                }
            });
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_order, container, false);
        inIt(view);
        return view;
    }
    private void inIt(View view) {
        rv_order = (RecyclerView) view.findViewById(R.id.rv_order);
        tv_no_data = (TextView) view.findViewById(R.id.tv_no_data);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(FragmentTabOrder.this);
        getExtra();
        getOrder();
    }
    private void getExtra() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            type = bundle.getString("type");
        }
    }
    private void getOrder(){
        list = new ArrayList<>();
        ArrayList<OrderModel> orderModels = ((OrderActivity)getActivity()).list;
        for (OrderModel orderModel: orderModels) {
            if(orderModel.getStatus().equals(type)){
                list.add(orderModel);
            }
        }
        if(list.size() > 0) {
            setOrders();
            tv_no_data.setVisibility(View.GONE);
            rv_order.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
        }else{
            tv_no_data.setText("You have not " + type + " Orders.");
            tv_no_data.setVisibility(View.VISIBLE);
            rv_order.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.GONE);
        }


    }
    private void setOrders() {
        rv_order.setVisibility(View.VISIBLE);
        orderAdapter = new OrderAdapter(list,getActivity());
        rv_order.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        orderAdapter.setClickListener(new OrderAdapter.ClickListener() {
            @Override
            public void onClicked(View v, int position) {
                if(v.getId() == R.id.tv_view_detail) {
                    if (getActivity() instanceof OrderActivity) {
                        FragmentOrderDetail fragmentOrderDetail = new FragmentOrderDetail();
                        fragmentOrderDetail.setCloseListner(FragmentTabOrder.this);
                        String json = new Gson().toJson(list.get(position));
                        Bundle bundle = new Bundle();
                        bundle.putString("model", json);
                        fragmentOrderDetail.setArguments(bundle);
                        ((OrderActivity) getActivity()).toolbarTitle.setText("Order Details");
                        Utils.AddFragmentBack(((OrderActivity) getActivity()).layMain.getId(), fragmentOrderDetail, getActivity());
                    }
                }else if(v.getId() == R.id.tv_cancel){
                    if(Utils.isConnectingToInternet(getActivity())) {
                        deleteOrder(position);
                    }else{
                        Toast.makeText(getActivity(), "Check your internet.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        rv_order.setAdapter(orderAdapter);
    }

    private void deleteOrder(final int position){
        Utils.showMessageBox("Are you sure, You want to cancel order ?","Yes","No, Thanks",true,getActivity());
        Utils.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.msgDialog.dismiss();
                Utils.showProgress("Cancel Order...",getActivity());
                OrderController orderController = new OrderController();
                orderController.deleteOrder(getActivity(), list.get(position).getOrder_id(), new CallBackResult<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Utils.progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Order Cancel successfully", Toast.LENGTH_SHORT).show();
                        setActivityList(position);
                    }

                    @Override
                    public void onError(String error) {
                        Utils.progressDialog.dismiss();
                        Utils.showMessageBox(error,"OK","",false,getActivity());
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

    private void setActivityList(int position){
        for (OrderModel orderModel:((OrderActivity)getActivity()).list) {
            if(orderModel.getOrder_id().equals(list.get(position).getOrder_id())){
                orderModel.setStatus("Cancelled");
                break;
            }
        }
        orderReset();
    }

    private void orderReset(){
        list = new ArrayList<>();
        ArrayList<OrderModel> orderModels = ((OrderActivity)getActivity()).list;
        for (OrderModel orderModel: orderModels) {
            if(orderModel.getStatus().equals(type)){
                list.add(orderModel);
            }
        }
        if(list.size() > 0) {
            orderAdapter.list = list;
            orderAdapter.notifyDataSetChanged();
            tv_no_data.setVisibility(View.GONE);
            rv_order.setVisibility(View.VISIBLE);
        }else{
            tv_no_data.setText("You have not " + type + " Orders.");
            tv_no_data.setVisibility(View.VISIBLE);
            rv_order.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClose(boolean isRemove, ArrayList<OrderModel> list) {
        ((OrderActivity)getActivity()).list = list;
        orderReset();
    }
}
