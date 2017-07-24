package com.binaryic.customerapp.orbymart.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.binaryic.customerapp.orbymart.Activity.HomeActivity;
import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Controller.CallBackResult;
import com.binaryic.customerapp.orbymart.Controller.HomeController;
import com.binaryic.customerapp.orbymart.Model.BannerModel;
import com.binaryic.customerapp.orbymart.R;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by HP on 03-Apr-17.
 */

public class FragmentHome extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    LinearLayout ll_main;
    ArrayList<BannerModel> listPromotional;
    JSONArray arrayCollection;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ll_main = (LinearLayout) view.findViewById(R.id.ll_main);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(FragmentHome.this);
        getSlider();
        getPromotional();
        getCollection();
        setCollectionBanner();
        return view;
    }

    private void getSlider() {
        HomeController homeController = new HomeController();
        ArrayList<BannerModel> list = homeController.getSlider(getActivity());
        if (list.size() > 0) {
            Utils.addFragment(ll_main.getId(), new FragmentHomeBannerTop(), getActivity());
        }
    }

    private void getPromotional() {
        HomeController homeController = new HomeController();
        listPromotional = homeController.getPromotional(getActivity());
    }

    private void getCollection() {
        HomeController homeController = new HomeController();
        arrayCollection = homeController.getCollection(getActivity());
    }

    private void setCollectionBanner() {
        int total = listPromotional.size() + arrayCollection.length();
        for (int i = 0; i < total; i++) {
            if (i < arrayCollection.length()) {
                try {
                    FragmentHomeCollection fragmentHomeCollection = new FragmentHomeCollection();
                    Bundle bundle = new Bundle();
                    bundle.putString("json", arrayCollection.getJSONObject(i).toString());
                    fragmentHomeCollection.setArguments(bundle);
                    Utils.addFragment(ll_main.getId(), fragmentHomeCollection, getActivity());
                } catch (Exception ex) {
                }
            }
            if (i < listPromotional.size()) {
                BannerModel bannerModel = listPromotional.get(i);
                FragmentPromotionalBanner fragmentPromotionalBanner = new FragmentPromotionalBanner();
                Bundle bundle = new Bundle();
                bundle.putString("banner_id", bannerModel.getBanner_id());
                bundle.putString("banner_image", bannerModel.getBanner_image());
                bundle.putString("category_id", bannerModel.getCategory_id());
                bundle.putString("category_name", bannerModel.getCategory_name());
                fragmentPromotionalBanner.setArguments(bundle);
                Utils.addFragment(ll_main.getId(), fragmentPromotionalBanner, getActivity());
            }
        }
    }

    @Override
    public void onRefresh() {
        if (Utils.isConnectingToInternet(getActivity())) {
            HomeController homeController = new HomeController();
            homeController.getCategoryApiCall(getActivity(), new CallBackResult<String>() {
                @Override
                public void onSuccess(String s) {
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                        FragmentHomeDrawer  fragmentHomeDrawer = new FragmentHomeDrawer();
                        Utils.addFragment(HomeActivity.fl_drawer.getId(), fragmentHomeDrawer, getActivity());
                        getBanner();
                    }
                }

                @Override
                public void onError(String error) {
                    Log.e("FragmentHome", "error==" + error.toString());
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                        getBanner();

                    }
                }
            });
        }
    }

    private void getBanner() {
        if (Utils.isConnectingToInternet(getActivity())) {
            HomeController homeController = new HomeController();
            homeController.getBannerDataApiCall(getActivity(), new CallBackResult<String>() {
                @Override
                public void onSuccess(String s) {
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                        ll_main.removeAllViews();
                        getSlider();
                        getPromotional();
                        getCollection();
                        setCollectionBanner();
                    }
                }

                @Override
                public void onError(String error) {
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        }
    }
}
