package com.binaryic.customerapp.orbymart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.binaryic.customerapp.orbymart.Fragments.FragmentProductList;
import com.binaryic.customerapp.orbymart.Fragments.FragmentWishlist;
import com.binaryic.customerapp.orbymart.R;


public class ProductListActivity extends AppCompatActivity implements View.OnClickListener {
    public TextView toolbarTitle, tvCount, tvCountWish;
    FrameLayout layMain;
    private String collection;
    private String collection_id;
    RelativeLayout btnCart, btnWish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        SetMenu();
    }
    private void SetMenu() {
        try {
            Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            GetExtra();
            Init();
        } catch (Exception ex) {
        }
    }
    private void Init() {
        try {
            toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
            layMain = (FrameLayout) findViewById(R.id.layMain);
            toolbarTitle.setText(collection);
            btnCart = (RelativeLayout) findViewById(R.id.btnCart);
            btnWish = (RelativeLayout) findViewById(R.id.btnWish);
            tvCount = (TextView) findViewById(R.id.tvCount);
            tvCountWish = (TextView) findViewById(R.id.tvCountWish);
            btnWish.setOnClickListener(this);
            btnCart.setOnClickListener(this);
            AddListFragment();
        } catch (Exception ex) {
        }
    }
    private void GetExtra() {
        try {
            Intent intent = getIntent();
            collection = intent.getStringExtra("collection");
            collection_id = intent.getStringExtra("collection_id");
        } catch (Exception ex) {
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnCart.getId()){
            startActivity(new Intent(this, CartActivity.class));
        }else if(v.getId() == btnWish.getId()){
            toolbarTitle.setText("Wishlist");
            btnWish.setVisibility(View.GONE);
            FragmentWishlist fragmentWishlist = new FragmentWishlist();
            Utils.AddFragmentBack(layMain.getId(), fragmentWishlist, this);
        }
    }
    private void AddListFragment() {
        try {
            FragmentProductList fragmentProductList = new FragmentProductList();
            Bundle bundle = new Bundle();
            bundle.putString("collection", collection);
            bundle.putString("collection_id", collection_id);
            fragmentProductList.setArguments(bundle);
            Utils.addFragment(layMain.getId(), fragmentProductList, this);
        } catch (Exception ex) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCount();
    }

    private void setCount(){
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
            if (f instanceof FragmentProductList) {
                ((FragmentProductList) f).SetLikeList();
            }else if (f instanceof FragmentWishlist) {
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
        super.onBackPressed();
        try {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.layMain);
            if (f instanceof FragmentProductList) {
                toolbarTitle.setText(collection);
                btnWish.setVisibility(View.VISIBLE);
                onResume();
            } else if (f instanceof FragmentWishlist) {
                toolbarTitle.setText("Wishlist");
            }
        } catch (Exception ex) {
        }
    }
}
