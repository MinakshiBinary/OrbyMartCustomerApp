package com.binaryic.customerapp.orbymart.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;


import com.binaryic.customerapp.orbymart.Adapter.ImageZoomPagerAdapter;
import com.binaryic.customerapp.orbymart.Custom.CirclePageIndicator;
import com.binaryic.customerapp.orbymart.R;

import java.util.ArrayList;



/**
 * Created by Asd on 20-09-2016.
 */
public class ImageZoomActivity extends Activity {
    ViewPager product_detail_viewPager;
    CirclePageIndicator indicator;
    ArrayList<String> images;
    ImageView imgClose,product_detail_leftArrow,product_detail_rightArrow;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);
        product_detail_viewPager = (ViewPager) findViewById(R.id.product_detail_viewPager);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        imgClose = (ImageView) findViewById(R.id.imgClose);
        product_detail_leftArrow = (ImageView) findViewById(R.id.product_detail_leftArrow);
        product_detail_rightArrow = (ImageView) findViewById(R.id.product_detail_rightArrow);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        product_detail_leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product_detail_viewPager.getCurrentItem() != 0){
                    product_detail_viewPager.setCurrentItem(product_detail_viewPager.getCurrentItem() - 1,true);
                }
            }
        });
        product_detail_rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product_detail_viewPager.getCurrentItem() != (images.size()-1)){
                    product_detail_viewPager.setCurrentItem(product_detail_viewPager.getCurrentItem() + 1,true);
                }
            }
        });
        GetExtra();
        SetImages();
    }
    private void GetExtra(){
        try {
            Intent intent = getIntent();
            images = intent.getStringArrayListExtra("images");
            pos = intent.getIntExtra("pos",0);
        } catch (Exception ex) {
        }
    }
    private void SetImages() {
        try {
            ImageZoomPagerAdapter mAdapter = new ImageZoomPagerAdapter(this, images);
            product_detail_viewPager.setAdapter(mAdapter);
            if (images.size() > 1) {
                indicator.setViewPager(product_detail_viewPager);
            }
            product_detail_viewPager.setCurrentItem(pos);
        } catch (Exception ex) {
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.zoom_out);
    }
}
