package com.binaryic.customerapp.orbymart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Controller.CartController;
import com.binaryic.customerapp.orbymart.Fragments.FragmentProductDetail;
import com.binaryic.customerapp.orbymart.Fragments.FragmentProductList;
import com.binaryic.customerapp.orbymart.Fragments.FragmentWishlist;
import com.binaryic.customerapp.orbymart.R;

import static android.R.attr.duration;
import static com.binaryic.customerapp.orbymart.R.id.tv_date;


/**
 * Created by Asd on 19-09-2016.
 */
public class ProductDetailActivity extends AppCompatActivity {
    TextView toolbarTitle;
    FrameLayout layMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        SetMenu();
    }

    private void SetMenu() {
        try {
            Init();
        } catch (Exception ex) {
        }
    }

    private void Init() {
        try {
            layMain = (FrameLayout) findViewById(R.id.layMain);
            AddDetailFragment();
        } catch (Exception ex) {
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.layMain);
            if (f instanceof FragmentProductList) {
                toolbarTitle.setText("");
            } else if (f instanceof FragmentWishlist) {
                toolbarTitle.setText("Wishlist");
            }
        } catch (Exception ex) {
        }
    }

    private void AddDetailFragment() {
        try {
            FragmentProductDetail fragmentProductDetail = new FragmentProductDetail();
            Utils.addFragment(layMain.getId(), fragmentProductDetail, this);
        } catch (Exception ex) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.layMain);
            if (f instanceof FragmentProductDetail) {
                ((FragmentProductDetail)f).SetRatingData();
                if (CartController.GetCartCount(ProductDetailActivity.this) == 0) {
                    ((FragmentProductDetail)f).tv_Count.setVisibility(View.GONE);
                } else {
                    ((FragmentProductDetail)f).tv_Count.setVisibility(View.VISIBLE);
                    ((FragmentProductDetail)f).tv_Count.setText(""+CartController.GetCartCount(ProductDetailActivity.this));
                }
            }
        }catch (Exception ex){}
    }

}
