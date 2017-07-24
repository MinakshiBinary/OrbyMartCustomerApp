package com.binaryic.customerapp.orbymart.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.binaryic.customerapp.orbymart.Activity.ProductDetailActivity;
import com.binaryic.customerapp.orbymart.Activity.ProductListActivity;
import com.binaryic.customerapp.orbymart.Adapter.CollectionProductAdapter;
import com.binaryic.customerapp.orbymart.Common.MyApplication;
import com.binaryic.customerapp.orbymart.Controller.ProductController;
import com.binaryic.customerapp.orbymart.Model.ProductModel;
import com.binaryic.customerapp.orbymart.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 04-Apr-17.
 */

public class FragmentHomeCollection extends Fragment implements CollectionProductAdapter.ClickListener {
    TextView tvCollection;
    String json = "";
    String title = "";
    String category_id = "";
    ArrayList<ProductModel> productModels;
    RecyclerView recycler;
    CollectionProductAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_collection, container, false);
        inIt(view);
        getExtra();
        getData();
        return view;
    }

    private void getExtra() {
        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                json = bundle.getString("json");
            }
        } catch (Exception ex) {
        }
    }

    private void inIt(View view) {
        tvCollection = (TextView) view.findViewById(R.id.tvCollection);
        final CardView btnViewAll = (CardView) view.findViewById(R.id.btnViewAll);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra("collection", title);
                intent.putExtra("collection_id", category_id);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        try {
            productModels = new ArrayList<>();
            JSONObject object = new JSONObject(json);
            title = object.getString("collection");
            category_id = object.getString("category_id");
            JSONArray result = object.getJSONArray("result");
            JSONArray products = result.getJSONObject(0).getJSONArray("products");
            ProductController productController = new ProductController();
            productModels = productController.getListFromArrayProduct(products,"4");
            tvCollection.setText(title);
            SetUpRecyclerView();
        } catch (Exception ex) {
        }
    }

    private void SetUpRecyclerView() {
        try {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            gridLayoutManager.setAutoMeasureEnabled(true);
            recycler.setLayoutManager(gridLayoutManager);
            adapter = new CollectionProductAdapter(getActivity(), productModels);
            adapter.setClickListener(this);
            recycler.setAdapter(adapter);
        } catch (Exception ex) {
        }
    }



    @Override
    public void ItemClicked(View view, int position) {
        MyApplication.productModel = productModels.get(position);
        startActivity(new Intent(getActivity(), ProductDetailActivity.class));
    }
}
