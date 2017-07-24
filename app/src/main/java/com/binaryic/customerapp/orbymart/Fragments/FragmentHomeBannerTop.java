package com.binaryic.customerapp.orbymart.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.binaryic.customerapp.orbymart.Adapter.BannerAdapter;
import com.binaryic.customerapp.orbymart.Controller.HomeController;
import com.binaryic.customerapp.orbymart.Custom.CirclePageIndicator;
import com.binaryic.customerapp.orbymart.Model.BannerModel;
import com.binaryic.customerapp.orbymart.R;

import java.util.ArrayList;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;



/**
 * Created by User on 22-01-16.
 */
public class FragmentHomeBannerTop extends Fragment {
    AutoScrollViewPager mPager;
    CirclePageIndicator mIndicator;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_banner_top,container,false);
        mPager = (AutoScrollViewPager)view.findViewById(R.id.pager);
        mIndicator = (CirclePageIndicator)view.findViewById(R.id.indicator);
        SetList();
        return view;
    }
    public void SetList(){
        try{
            HomeController homeController = new HomeController();
            ArrayList<BannerModel> list = homeController.getSlider(getActivity());
            BannerAdapter mAdapter = new BannerAdapter(getActivity(),list);
            mPager.setAdapter(mAdapter);
            if(list.size() > 1){
                mIndicator.setViewPager(mPager);
                mPager.setInterval(3000);
                mPager.startAutoScroll();
            }
        }catch (Exception ex){}
    }
}
