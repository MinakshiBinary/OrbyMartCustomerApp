package com.binaryic.customerapp.orbymart.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.binaryic.customerapp.orbymart.Common.MyApplication;
import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Fragments.FragmentCart;
import com.binaryic.customerapp.orbymart.Fragments.FragmentRatings;
import com.binaryic.customerapp.orbymart.Fragments.FragmentWriteReview;
import com.binaryic.customerapp.orbymart.R;

public class ReviewActivity extends AppCompatActivity {
    public FrameLayout layMain;
    public TextView tvHeader;
    FragmentRatings fragmentRatings;
    FragmentWriteReview fragmentWriteReview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Init();
        AddReviewFragment();
    }
    private void Init() {
        try {
            layMain = (FrameLayout) findViewById(R.id.layMain);
            tvHeader = (TextView) findViewById(R.id.tvHeader);
            ImageView btnClose = (ImageView) findViewById(R.id.btnClose);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        } catch (Exception ex) {
        }
    }
    private void AddReviewFragment() {
        try {
            tvHeader.setText("Rating and Review");
            fragmentRatings = new FragmentRatings();
            Utils.addFragment(layMain.getId(), fragmentRatings, this);
        } catch (Exception ex) {
        }
    }

    public void AddWriteRatingFragment(){
        fragmentWriteReview = new FragmentWriteReview();
        Utils.AddFragmentBack(layMain.getId(),fragmentWriteReview,this);
        tvHeader.setText(MyApplication.productModel.getProduct_name());
    }
}
