package com.binaryic.customerapp.orbymart.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.binaryic.customerapp.orbymart.Activity.CartActivity;
import com.binaryic.customerapp.orbymart.Adapter.CartProductAdapter;
import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Controller.CartController;
import com.binaryic.customerapp.orbymart.Model.ProductModelQty;
import com.binaryic.customerapp.orbymart.R;

import java.util.ArrayList;

/**
 * Created by Asd on 16-09-2016.
 */
public class FragmentCart extends Fragment implements View.OnClickListener, CartProductAdapter.ClickListener {
    RecyclerView recycler;
    TextView tvShippingCharges, tvTotalPrice, tvSubTotal;
    LinearLayout btnPlaceOrder;
    Boolean isClickOn = true;
    ArrayList<ProductModelQty> list;
    View infoView;
    RelativeLayout layMainCart;
    LinearLayout btnRetry;
    Double Amount = 0.0;
    int Shipping = 0;
    CartProductAdapter adapter;
    Boolean isChange = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        try {
            Init(view);
            SetData();
        } catch (Exception e) {
        }
        return view;
    }

    private void Init(View view) {
        try {
            infoView = view.findViewById(R.id.infoView);//if cart is empty than show
            layMainCart = (RelativeLayout) view.findViewById(R.id.layMainCart);
            btnRetry = (LinearLayout) infoView.findViewById(R.id.btnRetry);
            recycler = (RecyclerView) view.findViewById(R.id.recycler);
            tvShippingCharges = (TextView) view.findViewById(R.id.tvShippingCharges);
            tvTotalPrice = (TextView) view.findViewById(R.id.tvTotalPrice);
            tvSubTotal = (TextView) view.findViewById(R.id.tvSubTotal);
            btnPlaceOrder = (LinearLayout) view.findViewById(R.id.btnPlaceOrder);
            btnPlaceOrder.setOnClickListener(this);
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        getActivity().finish();
                    }catch (Exception ex){}
                }
            });
        } catch (Exception ex) {
        }
    }

    private void SetData() {
        try {
            if (CheckCart()) {
                infoView.setVisibility(View.VISIBLE);
                layMainCart.setVisibility(View.GONE);
                TextView tvMessage = (TextView) infoView.findViewById(R.id.tvMessage);
                tvMessage.setText("Add to Cart \nAnd it would appear here.");
            } else {
                infoView.setVisibility(View.GONE);
                layMainCart.setVisibility(View.VISIBLE);
                tvSubTotal.setText(Amount.intValue() + "");
                tvTotalPrice.setText((Amount.intValue() + Shipping) + "");
                if (Shipping == 0)
                    tvShippingCharges.setText("Free");
                else
                    tvShippingCharges.setText(Shipping + "");
                SetUpRecyclerView();
            }
        } catch (Exception ex) {
        }
    }

    private void SetUpRecyclerView() {
        if(isChange){
            adapter.list = list;
            adapter.notifyDataSetChanged();
        }else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recycler.setLayoutManager(layoutManager);
            adapter = new CartProductAdapter(getActivity(), list);
            adapter.setClickListener(this);
            recycler.setAdapter(adapter);
        }
    }

    private boolean CheckCart() {
        boolean isEmpty = false;
        try {
            Amount = 0.0;
            list = new ArrayList<>();
            list = CartController.GetCartProducts(getActivity());
            if (list.size() > 0) {
                isEmpty = false;
                for (ProductModelQty productModelQty : list) {
                    Amount = Amount + (Double.parseDouble(productModelQty.getProductModel().getDiscount_price()) * productModelQty.getQty());
                }
            } else
                isEmpty = true;

        } catch (Exception ex) {
        }
        return isEmpty;
    }

    @Override
    public void onClick(View v) {
        try {
            if (isClickOn) {
                if (v.getId() == R.id.btnPlaceOrder) {
                    ((CartActivity) getActivity()).AddDeliveryFragment(String.valueOf(Amount));
                }
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void ItemClicked(View view, final int position, int Qty) {
        try {
            if (view.getId() == R.id.llRemove) {
                Utils.showMessageBox("Are you sure, Do you want to remove product ?", "Remove", "NO", true, getActivity());
                Utils.btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CartController.DeleteProduct(getActivity(), list.get(position).getProductModel());
                        isChange = true;
                        SetData();
                        Utils.msgDialog.dismiss();
                    }
                });
                Utils.btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.msgDialog.dismiss();
                    }
                });
            } else if (Qty != 0) {
                CartController.ChangeQty(getActivity(), list.get(position).getProductModel(), Qty);
                isChange = true;
                SetData();
            }
        } catch (Exception ex) {
        }
    }
}
