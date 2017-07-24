package com.binaryic.customerapp.orbymart.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.customerapp.orbymart.Activity.CartActivity;
import com.binaryic.customerapp.orbymart.Activity.ReviewActivity;
import com.binaryic.customerapp.orbymart.Adapter.ProductImagePagerAdapter;
import com.binaryic.customerapp.orbymart.Common.MyApplication;
import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Controller.CallBackResult;
import com.binaryic.customerapp.orbymart.Controller.CartController;
import com.binaryic.customerapp.orbymart.Controller.ProductController;
import com.binaryic.customerapp.orbymart.Controller.WishListController;
import com.binaryic.customerapp.orbymart.Custom.AnimatedEditText;
import com.binaryic.customerapp.orbymart.Custom.CirclePageIndicator;
import com.binaryic.customerapp.orbymart.R;

import java.util.ArrayList;


/**
 * Created by Asd on 19-09-2016.
 */
public class FragmentProductDetail extends Fragment implements View.OnClickListener {
    Boolean isClickOn = true;
    ViewPager product_detail_viewPager;
    CirclePageIndicator indicator;
    ArrayList<String> images;
    TextView tvOldPrice, tvSellingPrice,tv_total_rating;
    ImageView imgfav;
    private TextView tvAddtoCart;
    public TextView tv_Count;
    private TextView tv_product_detail, tv_product_name;

    RatingBar rating_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        Init(view);
        return view;
    }

    private void Init(View view) {
        LinearLayout ll_back = (LinearLayout) view.findViewById(R.id.ll_back);
        LinearLayout ll_ratings = (LinearLayout) view.findViewById(R.id.ll_ratings);
        RelativeLayout rl_fav = (RelativeLayout) view.findViewById(R.id.rl_fav);
        RelativeLayout rl_Cart = (RelativeLayout) view.findViewById(R.id.rl_Cart);
        imgfav = (ImageView) view.findViewById(R.id.imgfav);
        ImageView iv_share = (ImageView) view.findViewById(R.id.iv_share);
        tv_Count = (TextView) view.findViewById(R.id.tv_Count);
        product_detail_viewPager = (ViewPager) view.findViewById(R.id.product_detail_viewPager);
        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        tvOldPrice = (TextView) view.findViewById(R.id.tvOldPrice);
        tvSellingPrice = (TextView) view.findViewById(R.id.tvSellingPrice);
        tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
        tv_product_detail = (TextView) view.findViewById(R.id.tv_product_detail);
        tvAddtoCart = (TextView) view.findViewById(R.id.tvAddtoCart);

        tv_total_rating = (TextView) view.findViewById(R.id.tv_total_rating);
        rating_bar = (RatingBar) view.findViewById(R.id.rating_bar);
        TextView tv_abuse = (TextView) view.findViewById(R.id.tv_abuse);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        rl_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.productModel.getIsFav()) {
                    imgfav.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav_white));
                    WishListController.DeleteProduct(getActivity(), MyApplication.productModel);
                    MyApplication.productModel.setIsFav(false);
                } else {
                    imgfav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_detail_selected));
                    WishListController.AddProduct(getActivity(), MyApplication.productModel);
                    MyApplication.productModel.setIsFav(true);
                }
            }
        });
        tvAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvAddtoCart.getText().toString().trim().matches("APPLY")) {
                    if (CartController.getSingleProduct(getActivity(), MyApplication.productModel.getProduct_id())) {
                        tvAddtoCart.setText("GO TO CART");
                    } else {
                        tvAddtoCart.setText("BUY NOW");
                    }
                } else if (tvAddtoCart.getText().toString().trim().matches("BUY NOW")) {
                    tvAddtoCart.setText("GO TO CART");
                    tv_Count.setVisibility(View.VISIBLE);
                    CartController.AddProduct(getActivity(), MyApplication.productModel);
                    tv_Count.setText("" + CartController.GetCartCount(getActivity()));
                    Utils.showMessageBox("Add to Cart Successfully go to cart for buy.", "OK", "", false, getActivity());
                } else {
                    startActivity(new Intent(getActivity(), CartActivity.class));
                }
            }
        });
        tv_abuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abuseDialog();
            }
        });
        if (CartController.GetCartCount(getActivity()) == 0) {
            tv_Count.setVisibility(View.GONE);
        } else {
            tv_Count.setVisibility(View.VISIBLE);
            tv_Count.setText("" + CartController.GetCartCount(getActivity()));
        }
        ll_ratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ReviewActivity.class);
                startActivity(i);
            }
        });
        SetImages();
        SetData();
        SetRatingData();
        AddSimilarFragment();
        AddToRecent();
        rl_Cart.setOnClickListener(this);

    }

    private void AddToRecent() {
        try {
            //RecentController.AddProduct(getActivity(), MyApplication.productModel);
        } catch (Exception ex) {
        }
    }

    //Set Images and BodyHtml
    private void SetImages() {
        try {
            images = new ArrayList<>();
            images = MyApplication.productModel.getProduct_images();
            ProductImagePagerAdapter mAdapter = new ProductImagePagerAdapter(getActivity(), images);
            product_detail_viewPager.setAdapter(mAdapter);
            if (images.size() > 1) {
                indicator.setViewPager(product_detail_viewPager);
            }

        } catch (Exception ex) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_Cart:
                startActivity(new Intent(getActivity(), CartActivity.class));
                break;
        }

    }

    private void SetData() {
        if (CartController.getSingleProduct(getActivity(), MyApplication.productModel.getProduct_id())) {
            tvAddtoCart.setText("GO TO CART");
        } else {
            tvAddtoCart.setText("BUY NOW");
        }
        tv_product_name.setText(MyApplication.productModel.getProduct_name());
        tv_product_detail.setText(MyApplication.productModel.getProduct_description());
        tvOldPrice.setText("` " + MyApplication.productModel.getSelling_price());
        tvSellingPrice.setText("` " + MyApplication.productModel.getDiscount_price());
        if (MyApplication.productModel.getIsFav())
            imgfav.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_favorite_detail_selected));
        else
            imgfav.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_fav_white));

    }
    public void SetRatingData(){
        try{
            rating_bar.setRating(Float.parseFloat(MyApplication.productModel.getAverage_rating()));
            tv_total_rating.setText(MyApplication.productModel.getTotal_review() + " Reviews");
        }catch (Exception ex){}
    }
    private void AddSimilarFragment() {
        try {
            //FragmentSimilar fragmentSimilar = new FragmentSimilar();
            //Util.AddFragment(LaySimilar.getId(), fragmentSimilar, getActivity());
        } catch (Exception ex) {
        }
    }

    public void abuseDialog() {
        final Dialog abuseDialog = new Dialog(getActivity());
        abuseDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        abuseDialog.setContentView(R.layout.dialog_abuse);
        WindowManager.LayoutParams wmlp = abuseDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        abuseDialog.setCanceledOnTouchOutside(false);
        final Spinner spn_reason = (Spinner) abuseDialog.findViewById(R.id.spn_reason);
        final AnimatedEditText et_message = (AnimatedEditText) abuseDialog.findViewById(R.id.et_message);
        TextView btnCancel = (TextView) abuseDialog.findViewById(R.id.btnCancel);
        TextView btnDone = (TextView) abuseDialog.findViewById(R.id.btnDone);
        setReasonSpinner(spn_reason);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                abuseDialog.dismiss();
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!spn_reason.getSelectedItem().toString().equals("Select Reason")) {
                    if(et_message.et.getText().toString().trim().equals("")){
                        et_message.SetError("Enter Message");
                    }else{
                        et_message.RemoveError();
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        abuseCall(spn_reason.getSelectedItem().toString(),et_message.et.getText().toString(),abuseDialog);
                    }
                }else{
                    Toast.makeText(getActivity(), "Please Select Reason", Toast.LENGTH_SHORT).show();
                }
            }
        });

        abuseDialog.show();
    }

    private void abuseCall(String title, String message, final Dialog abuseDialog){
        if(Utils.isConnectingToInternet(getActivity())){
            Utils.showProgress("Loading",getActivity());
            ProductController controller = new ProductController();
            controller.abuseApiCall(getActivity(), MyApplication.productModel.getProduct_id(), title, message, new CallBackResult<Boolean>() {
                @Override
                public void onSuccess(Boolean s) {
                    Utils.progressDialog.dismiss();
                    if(s){
                        Toast.makeText(getActivity(), "Your abuse report successfully.", Toast.LENGTH_SHORT).show();
                    }
                    abuseDialog.dismiss();
                }

                @Override
                public void onError(String error) {
                    Utils.progressDialog.dismiss();
                    Utils.showMessageBox(error,"OK","",false,getActivity());
                }
            });
        }else{
            Toast.makeText(getActivity(), "Please check internet.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setReasonSpinner(Spinner spn) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_adapter_textview, getResources().getStringArray(R.array.array_reason));
        adapter.setDropDownViewResource(R.layout.spinner_adapter_textview);
        spn.setAdapter(adapter);
    }



}