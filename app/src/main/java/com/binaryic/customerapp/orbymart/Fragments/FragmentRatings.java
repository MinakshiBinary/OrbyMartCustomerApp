package com.binaryic.customerapp.orbymart.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.binaryic.customerapp.orbymart.Activity.ProductDetailActivity;
import com.binaryic.customerapp.orbymart.Activity.ReviewActivity;
import com.binaryic.customerapp.orbymart.Adapter.ReviewAdapter;
import com.binaryic.customerapp.orbymart.Common.MyApplication;
import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Controller.CallBackResult;
import com.binaryic.customerapp.orbymart.Controller.ProductController;
import com.binaryic.customerapp.orbymart.Model.RatingModel;
import com.binaryic.customerapp.orbymart.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;



/**
 * Created by HP on 12-Mar-17.
 */

public class FragmentRatings extends Fragment implements FragmentWriteReview.CloseListner {

    private RecyclerView rv_review;
    TextView tv_rating_point, tv_review,tv_sort_order;
    ProgressBar progress_star5, progress_star4, progress_star3, progress_star2, progress_star1;
    ArrayList<RatingModel> list;
    LinearLayout ll_data, ll_rating;
    ProgressBar progress;
    RatingBar rating_bar;
    LinearLayout ll_sort;
    String order = "latest_reviews";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ratings, container, false);
        inIt(view);
        return view;
    }

    private void inIt(View view) {
        ll_data = (LinearLayout) view.findViewById(R.id.ll_data);
        ll_sort = (LinearLayout) view.findViewById(R.id.ll_sort);
        ll_rating = (LinearLayout) view.findViewById(R.id.ll_rating);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        tv_rating_point = (TextView) view.findViewById(R.id.tv_rating_point);
        tv_review = (TextView) view.findViewById(R.id.tv_review);
        rv_review = (RecyclerView) view.findViewById(R.id.rv_review);
        tv_sort_order = (TextView) view.findViewById(R.id.tv_sort_order);
        rating_bar = (RatingBar) view.findViewById(R.id.rating_bar);
        progress_star5 = (ProgressBar) view.findViewById(R.id.progress_star5);
        progress_star4 = (ProgressBar) view.findViewById(R.id.progress_star4);
        progress_star3 = (ProgressBar) view.findViewById(R.id.progress_star3);
        progress_star2 = (ProgressBar) view.findViewById(R.id.progress_star2);
        progress_star1 = (ProgressBar) view.findViewById(R.id.progress_star1);
        TextView tv_add_review = (TextView) view.findViewById(R.id.tv_add_review);
        tv_add_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ReviewActivity) getActivity()).tvHeader.setText(MyApplication.productModel.getProduct_name());
                FragmentWriteReview fragmentWriteReview = new FragmentWriteReview();
                fragmentWriteReview.setCloseListner(FragmentRatings.this);
                Utils.AddFragmentBack(((ReviewActivity) getActivity()).layMain.getId(), fragmentWriteReview, getActivity());
            }
        });
        ll_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup = new PopupMenu(getActivity(), v);
                popup.getMenuInflater().inflate(R.menu.sort_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.latest) {
                            order = "latest_reviews";
                        }else if(item.getItemId() == R.id.oldest){
                            order = "oldest_reviews";
                        }else if(item.getItemId() == R.id.least_rated){
                            order = "least_rated";
                        }else if(item.getItemId() == R.id.top_rated){
                            order = "top_rated";
                        }
                        tv_sort_order.setText(item.getTitle());
                        getReviews();
                        return true;
                    }
                });
                popup.show();
            }
        });
        getReviews();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("resume", "fragment");
    }

    private void setRv_review() {
        ReviewAdapter reviewAdapter = new ReviewAdapter(list,getActivity());
        rv_review.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_review.setAdapter(reviewAdapter);
    }

    private void setRatingStar(Float rating) {
        try {
            rating_bar.setRating(rating);
        } catch (Exception ex) {
        }
    }

    private void getReviews() {
        progress.setVisibility(View.VISIBLE);
        ll_data.setVisibility(View.GONE);
        if(Utils.isConnectingToInternet(getActivity())) {
            Log.e("product_id",MyApplication.productModel.getProduct_id());
            ProductController productController = new ProductController();
            productController.getReviewCall(getActivity(), MyApplication.productModel.getProduct_id(), order, new CallBackResult<String>() {
                @Override
                public void onSuccess(String s) {
                    try {
                        progress.setVisibility(View.GONE);
                        ll_data.setVisibility(View.VISIBLE);
                        JSONObject object = new JSONObject(s);
                        String average_rating = object.getString("average_rating");
                        String stars5 = object.getString("5_stars_%");
                        String stars4 = object.getString("4_stars_%");
                        String stars3 = object.getString("3_stars_%");
                        String stars2 = object.getString("2_stars_%");
                        String stars1 = object.getString("1_star_%");
                        JSONArray result = object.getJSONArray("result");
                        list = new ArrayList<RatingModel>();
                        for (int i = 0; i < result.length(); i++) {
                            list.add(new RatingModel(result.getJSONObject(i).getString("id"), result.getJSONObject(i).getString("customer_id"), result.getJSONObject(i).getString("customer_name"), result.getJSONObject(i).getString("stars"), result.getJSONObject(i).getString("title"), result.getJSONObject(i).getString("review_message"), result.getJSONObject(i).getString("created_at")));
                        }
                        setRatingData(average_rating, stars5, stars4, stars3, stars2, stars1);
                    } catch (Exception ex) {
                        progress.setVisibility(View.GONE);
                        ll_data.setVisibility(View.VISIBLE);
                        list = new ArrayList<RatingModel>();
                        setRatingData("0", "0", "0", "0", "0", "0");
                    }
                }

                @Override
                public void onError(String error) {
                    progress.setVisibility(View.GONE);
                    ll_data.setVisibility(View.VISIBLE);
                    list = new ArrayList<RatingModel>();
                    setRatingData("0", "0", "0", "0", "0", "0");
                }
            });
        }else{
            progress.setVisibility(View.GONE);
            ll_data.setVisibility(View.VISIBLE);
            list = new ArrayList<RatingModel>();
            setRatingData("0", "0", "0", "0", "0", "0");
            Toast.makeText(getActivity(), "Please check your internet.", Toast.LENGTH_SHORT).show();
        }

    }

    private void setRatingData(String average_rating,String stars5,String stars4,String stars3,String stars2,String stars1) {
        tv_review.setText(list.size() + " Reviews");
        tv_rating_point.setText(String.format("%.1f", new BigDecimal(Float.parseFloat(average_rating))) + "/5");
        setRatingStar(Float.parseFloat(average_rating));
        progress_star1.setProgress((int) Double.parseDouble(stars1));
        progress_star2.setProgress((int) Double.parseDouble(stars2));
        progress_star3.setProgress((int) Double.parseDouble(stars3));
        progress_star4.setProgress((int) Double.parseDouble(stars4));
        progress_star5.setProgress((int) Double.parseDouble(stars5));
        MyApplication.productModel.setAverage_rating(average_rating);
        MyApplication.productModel.setTotal_review(list.size()+"");
        setRv_review();
    }

    @Override
    public void onClose() {
        ((ReviewActivity) getActivity()).tvHeader.setText("Rating and Reviews");
        getReviews();
    }
}
