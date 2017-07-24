package com.binaryic.customerapp.orbymart.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.binaryic.customerapp.orbymart.Activity.OrderActivity;
import com.binaryic.customerapp.orbymart.Adapter.Adapter;
import com.binaryic.customerapp.orbymart.Controller.CallBackResult;
import com.binaryic.customerapp.orbymart.Controller.OrderController;
import com.binaryic.customerapp.orbymart.Model.OrderModel;
import com.binaryic.customerapp.orbymart.R;

import java.util.ArrayList;

/**
 * Created by HP on 05-May-17.
 */

public class FragmentOrders extends Fragment {
    ProgressBar progress_order;
    ViewPager viewpager;
    TabLayout tabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        inIt(view);
        return view;
    }

    private void inIt(View view) {
        progress_order = (ProgressBar) view.findViewById(R.id.progress_order);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        tabs = (TabLayout) view.findViewById(R.id.tabs);
        getOrder();
    }

    private void getOrder() {
        progress_order.setVisibility(View.VISIBLE);
        viewpager.setVisibility(View.GONE);
        OrderController orderController = new OrderController();
        orderController.getOrderApiCall(getActivity(), new CallBackResult<ArrayList<OrderModel>>() {
            @Override
            public void onSuccess(ArrayList<OrderModel> orderModels) {
                ((OrderActivity) getActivity()).list = orderModels;
                progress_order.setVisibility(View.GONE);
                SetFragment();
            }

            @Override
            public void onError(String error) {
                progress_order.setVisibility(View.GONE);
                SetFragment();
            }
        });

    }

    private void SetFragment() {
        try {
            viewpager.setVisibility(View.VISIBLE);
            if (viewpager != null) {
                Adapter adapter = new Adapter(getChildFragmentManager());
                FragmentTabOrder fragmentPending = new FragmentTabOrder();
                Bundle bundle = new Bundle();
                bundle.putString("type", "Pending");
                fragmentPending.setArguments(bundle);
                adapter.addFragment(fragmentPending, "Pending");
                FragmentTabOrder fragmentDelivered = new FragmentTabOrder();
                Bundle bundle1 = new Bundle();
                bundle1.putString("type", "Delivered");
                fragmentDelivered.setArguments(bundle1);
                adapter.addFragment(fragmentDelivered, "Delivered");
                FragmentTabOrder fragmentCancel = new FragmentTabOrder();
                Bundle bundle2 = new Bundle();
                bundle2.putString("type", "Cancelled");
                fragmentCancel.setArguments(bundle2);
                adapter.addFragment(fragmentCancel, "Cancelled");
                viewpager.setAdapter(adapter);
            }
            tabs.setupWithViewPager(viewpager);
            viewpager.setCurrentItem(0, false);
        } catch (Exception e) {
            String x = e.getMessage();
        }
    }

}
