package com.binaryic.customerapp.orbymart.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.binaryic.customerapp.orbymart.Activity.ProductDetailActivity;
import com.binaryic.customerapp.orbymart.Activity.ProductListActivity;
import com.binaryic.customerapp.orbymart.Adapter.ProductAdapter;
import com.binaryic.customerapp.orbymart.Common.MyApplication;
import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Controller.CallBackResult;
import com.binaryic.customerapp.orbymart.Controller.ProductController;
import com.binaryic.customerapp.orbymart.Controller.WishListController;
import com.binaryic.customerapp.orbymart.Model.ProductModel;
import com.binaryic.customerapp.orbymart.R;

import java.util.ArrayList;

/**
 * Created by Asd on 17-09-2016.
 */
public class FragmentProductList extends Fragment implements ProductAdapter.ClickListener, View.OnClickListener, ProductAdapter.ScrollListener,SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recycler;
    boolean isClickOn = true;
    LinearLayout progress;
    public ArrayList<ProductModel> list;
    TextView tvMessage, tvBtnMessage;
    LinearLayout btnRetry;
    ImageView imgMessage;
    View infoView;
    boolean isScrolled = true;
    int page = 1;
    int productCount = 0;
    ProductAdapter adapter;
    String collection = "";
    String collection_id = "";
    boolean isFilter = false;
    String sortOrder = "";
    private SwipeRefreshLayout swipeRefreshLayout;

    ArrayList<String> listFilter = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productlisting, container, false);
        try {
            list = new ArrayList<>();
            Init(view);
            GetProductList();
        } catch (Exception e) {
        }
        return view;
    }

    private void Init(View view) {
        try {
            recycler = (RecyclerView) view.findViewById(R.id.recycler);
            progress = (LinearLayout) view.findViewById(R.id.progress);
            infoView = view.findViewById(R.id.infoView);
            tvMessage = (TextView) view.findViewById(R.id.tvMessage);
            tvBtnMessage = (TextView) view.findViewById(R.id.tvBtnMessage);
            btnRetry = (LinearLayout) view.findViewById(R.id.btnRetry);
            btnRetry.setOnClickListener(this);
            imgMessage = (ImageView) view.findViewById(R.id.imgMessage);
            tvMessage.setText("Product is not available for this collection");
            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
            swipeRefreshLayout.setOnRefreshListener(FragmentProductList.this);
            GetExtra();
        } catch (Exception ex) {
        }
    }

    private void GetExtra() {
        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                collection = bundle.getString("collection");
                collection_id = bundle.getString("collection_id");
            }
        } catch (Exception ex) {
        }
    }

    private void SetUpRecyclerView() {
        try {
            if (list.size() == 0) {
                infoView.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.GONE);
            } else {
                infoView.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
                recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                adapter = new ProductAdapter(getActivity(), list);
                adapter.setClickListener(this);
                adapter.setScrollListener(this);
                //adapter.list = list;
                recycler.setAdapter(adapter);
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void ItemClicked(View view, int position) {
        try {
            if (view.getId() == R.id.imgfav) {
                if (list.get(position).getIsFav()) {
                    WishListController.DeleteProduct(getActivity(), list.get(position));
                    list.get(position).setIsFav(false);
                } else {
                    WishListController.AddProduct(getActivity(), list.get(position));
                    list.get(position).setIsFav(true);
                }
                adapter.list = list;
                adapter.notifyDataSetChanged();
                int countWish = WishListController.GetWishCount(getActivity());
                if (countWish == 0) {
                    ((ProductListActivity)getActivity()).tvCountWish.setVisibility(View.GONE);
                } else {
                    ((ProductListActivity)getActivity()).tvCountWish.setText(countWish + "");
                    ((ProductListActivity)getActivity()).tvCountWish.setVisibility(View.VISIBLE);
                }
            } else {
                MyApplication.productModel = list.get(position);
                startActivity(new Intent(getActivity(), ProductDetailActivity.class));
            }
        } catch (Exception ex) {
        }

    }

    @Override
    public void onClick(View v) {
        try {
            if (isClickOn) {
                if (v.getId() == R.id.btnRetry) {
                    getActivity().finish();
                }
            }
        } catch (Exception ex) {
        }
    }


    private void GetProductList() {
        try {
            if (Utils.isConnectingToInternet(getActivity())) {
                progress.setVisibility(View.VISIBLE);
                ProductController productController = new ProductController();
                productController.getProductApiCall(getActivity(), collection_id, page, new CallBackResult<ArrayList<ProductModel>>() {
                    @Override
                    public void onSuccess(ArrayList<ProductModel> productModels) {
                        if (productModels.size() == 0)
                            isScrolled = false;
                        else
                            isScrolled = true;
                        for (ProductModel product : productModels) {
                            list.add(product);
                        }
                        SetLike();
                        SetUpList();
                        if (productModels.size() == 0)
                            isScrolled = false;
                        progress.setVisibility(View.GONE);
                        if (swipeRefreshLayout != null) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        if (swipeRefreshLayout != null) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
            } else {
                Utils.showMessageBox("Internet is not working now.", "OK", "", false, getActivity());
            }
        } catch (Exception ex) {
        }
    }


    private void SetUpList() {
        try {
            if (productCount == list.size()) {
                if (isScrolled) {
                    page++;
                    GetProductList();
                } else {
                    if (list.size() == 0) {
                        infoView.setVisibility(View.VISIBLE);
                        recycler.setVisibility(View.GONE);
                    }
                }
            } else {
                productCount = list.size();
                if (recycler.getVisibility() == View.VISIBLE) {
                    Log.e("notify", list.toString());
                    adapter.list = list;
                    adapter.notifyDataSetChanged();
                } else
                    SetUpRecyclerView();
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void Scrolled(int position) {
        try {
            if (position >= productCount) {
                if (isScrolled) {
                    page++;
                    GetProductList();
                }
            }
        } catch (Exception ex) {
        }
    }

    public void SetLike() {
        try {
            String ids = WishListController.GetFavProductId(getActivity());
            for (ProductModel productModel : list) {
                if (ids.contains(productModel.getProduct_id()))
                    productModel.setIsFav(true);
                else
                    productModel.setIsFav(false);
            }
        } catch (Exception ex) {
        }
    }
    public void SetLikeList() {
        try {
            String ids = WishListController.GetFavProductId(getActivity());
            for (ProductModel productModel : list) {
                if (ids.contains(productModel.getProduct_id()))
                    productModel.setIsFav(true);
                else
                    productModel.setIsFav(false);
            }
            adapter.list = list;
            adapter.notifyDataSetChanged();
        } catch (Exception ex) {
        }
    }

    @Override
    public void onRefresh() {
        GetProductList();
    }
}
