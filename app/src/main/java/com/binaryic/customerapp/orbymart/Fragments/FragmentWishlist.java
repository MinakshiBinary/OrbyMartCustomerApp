package com.binaryic.customerapp.orbymart.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.binaryic.customerapp.orbymart.Activity.HomeActivity;
import com.binaryic.customerapp.orbymart.Activity.ProductDetailActivity;
import com.binaryic.customerapp.orbymart.Activity.ProductListActivity;
import com.binaryic.customerapp.orbymart.Adapter.ProductAdapter;
import com.binaryic.customerapp.orbymart.Common.MyApplication;
import com.binaryic.customerapp.orbymart.Controller.WishListController;
import com.binaryic.customerapp.orbymart.Model.ProductModel;
import com.binaryic.customerapp.orbymart.R;

import java.util.ArrayList;

/**
 * Created by User on 08-09-2016.
 */
public class FragmentWishlist extends Fragment implements ProductAdapter.ClickListener {
    RecyclerView recycler;
    View layMessage;
    ArrayList<ProductModel> list;
    ProductAdapter adapter;
    TextView tvMessage, tvBtnMessage;
    LinearLayout btnRetry;
    RelativeLayout layWishMain;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        try {
            layWishMain = (RelativeLayout) view.findViewById(R.id.layWishMain);
            recycler = (RecyclerView) view.findViewById(R.id.recycler);
            layMessage = view.findViewById(R.id.layMessage);
            tvMessage = (TextView) view.findViewById(R.id.tvMessage);
            tvBtnMessage = (TextView) view.findViewById(R.id.tvBtnMessage);
            btnRetry = (LinearLayout) view.findViewById(R.id.btnRetry);
            layMessage.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);

            GetList();
            SetUpRecyclerView();
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity() instanceof HomeActivity) {
                        ((HomeActivity) getActivity()).onBackPressed();
                    } else if (getActivity() instanceof ProductListActivity) {
                        ((ProductListActivity) getActivity()).onBackPressed();
                    } else if (getActivity() instanceof ProductDetailActivity) {
                        ((ProductDetailActivity) getActivity()).onBackPressed();
                    }
                }
            });
            SetMargin();
        } catch (Exception e) {
        }
        return view;
    }

    private void SetUpRecyclerView() {
        try {
            if (list.size() > 0) {
                layMessage.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
                recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                adapter = new ProductAdapter(getActivity(), list);
                adapter.setClickListener(this);
                recycler.setAdapter(adapter);
            } else {
                layMessage.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.GONE);
                tvMessage.setText("Make a wish \nAnd it would appear here.");
                tvBtnMessage.setText("Browse collection");
            }
        } catch (Exception ex) {
        }
    }

    private void SetMargin() {
        try {
            int px = 0;
            if (getActivity() instanceof HomeActivity) {
                px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getActivity().getResources().getDisplayMetrics());
            }
            layWishMain.setPadding(0,0,0,px);
        } catch (Exception ex) {
        }
    }

    private void GetList() {
        try {
            list = WishListController.GetFavProducts(getActivity());
            SetUpRecyclerView();
        } catch (Exception ex) {
        }
    }
    public void ResetList(){
        try{
            list = WishListController.GetFavProducts(getActivity());
            adapter.list = list;
            adapter.notifyDataSetChanged();
        }catch (Exception ex){}
    }

    @Override
    public void ItemClicked(View view, int position) {
        try {
            if (view.getId() == R.id.imgfav) {
                if (list.get(position).getIsFav()) {
                    WishListController.DeleteProduct(getActivity(), list.get(position));
                    list.get(position).setIsFav(false);
                    list.remove(position);
                }
                adapter.list = list;
                adapter.notifyDataSetChanged();
                if (list.size() > 0) {
                    layMessage.setVisibility(View.GONE);
                    recycler.setVisibility(View.VISIBLE);
                } else {
                    layMessage.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.GONE);
                }
                if (getActivity() instanceof HomeActivity) {
                    int countWish = WishListController.GetWishCount(getActivity());
                    if (countWish == 0) {
                        ((HomeActivity)getActivity()).tvCountWish.setVisibility(View.GONE);
                    } else {
                        ((HomeActivity)getActivity()).tvCountWish.setText(countWish + "");
                        ((HomeActivity)getActivity()).tvCountWish.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                MyApplication.productModel = list.get(position);
                startActivity(new Intent(getActivity(), ProductDetailActivity.class));
            }
        } catch (Exception ex) {
        }
    }


}
