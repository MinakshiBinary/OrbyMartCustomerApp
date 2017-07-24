package com.binaryic.customerapp.orbymart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Controller.CartController;
import com.binaryic.customerapp.orbymart.Controller.WishListController;
import com.binaryic.customerapp.orbymart.Fragments.FragmentOrderDetail;
import com.binaryic.customerapp.orbymart.Fragments.FragmentOrders;
import com.binaryic.customerapp.orbymart.Fragments.FragmentWishlist;
import com.binaryic.customerapp.orbymart.Model.OrderModel;
import com.binaryic.customerapp.orbymart.R;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {
    public TextView toolbarTitle, tvCount, tvCountWish;
    public FrameLayout layMain;
    RelativeLayout btnCart, btnWish;
    public ArrayList<OrderModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        SetMenu();
    }

    private void SetMenu() {
        try {
            Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Init();
        } catch (Exception ex) {
        }
    }

    private void Init() {
        try {
            toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
            layMain = (FrameLayout) findViewById(R.id.layMain);
            toolbarTitle.setText("My Orders");
            btnCart = (RelativeLayout) findViewById(R.id.btnCart);
            btnWish = (RelativeLayout) findViewById(R.id.btnWish);
            tvCount = (TextView) findViewById(R.id.tvCount);
            tvCountWish = (TextView) findViewById(R.id.tvCountWish);
            btnWish.setOnClickListener(this);
            btnCart.setOnClickListener(this);
            addOrderFragment();
        } catch (Exception ex) {
        }
    }

    private void addOrderFragment() {
        FragmentOrders fragmentOrders = new FragmentOrders();
        Utils.addFragment(layMain.getId(), fragmentOrders, this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnCart.getId()) {
            startActivity(new Intent(this, CartActivity.class));
        } else if (v.getId() == btnWish.getId()) {
            toolbarTitle.setText("Wishlist");
            btnWish.setVisibility(View.GONE);
            FragmentWishlist fragmentWishlist = new FragmentWishlist();
            Utils.AddFragmentBack(layMain.getId(), fragmentWishlist, this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCount();
    }

    private void setCount() {
        try {
            int count = CartController.GetCartCount(this);
            if (count == 0) {
                tvCount.setVisibility(View.GONE);
            } else {
                tvCount.setText(count + "");
                tvCount.setVisibility(View.VISIBLE);
            }
            int countWish = WishListController.GetWishCount(this);
            if (countWish == 0) {
                tvCountWish.setVisibility(View.GONE);
            } else {
                tvCountWish.setText(countWish + "");
                tvCountWish.setVisibility(View.VISIBLE);
            }
            SetLikeList();
        } catch (Exception ex) {
        }
    }

    private void SetLikeList() {
        try {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.layMain);
            if (f instanceof FragmentWishlist) {
                ((FragmentWishlist) f).ResetList();
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.layMain);
            if (f instanceof FragmentOrderDetail) {
                toolbarTitle.setText("Order Detail");
                btnWish.setVisibility(View.VISIBLE);
            }
            if (f instanceof FragmentOrders) {
                toolbarTitle.setText("My Orders");
                btnWish.setVisibility(View.VISIBLE);
            }
        } else {
            finish();
        }
    }
}
