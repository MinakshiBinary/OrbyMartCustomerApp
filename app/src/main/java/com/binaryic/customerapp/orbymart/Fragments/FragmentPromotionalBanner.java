package com.binaryic.customerapp.orbymart.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.binaryic.customerapp.orbymart.Activity.ProductListActivity;
import com.binaryic.customerapp.orbymart.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by HP on 04-Apr-17.
 */

public class FragmentPromotionalBanner extends Fragment {
    ImageView iv_banner;
    String banner_id = "";
    String banner_image = "";
    String category_id = "";
    String category_name = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotional_banner, container, false);
        inIt(view);
        getExtra();
        setImage();
        return view;
    }
    private void inIt(View view) {
        iv_banner = (ImageView) view.findViewById(R.id.iv_banner);
        iv_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra("collection", category_name);
                intent.putExtra("collection_id", category_id);
                startActivity(intent);
            }
        });
    }
    private void getExtra(){
        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                banner_id = bundle.getString("banner_id");
                banner_image = bundle.getString("banner_image");
                category_id = bundle.getString("category_id");
                category_name = bundle.getString("category_name");
            }
        } catch (Exception ex) {
        }
    }

    private void setImage(){
        Glide.with(getActivity()).load(banner_image).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_launcher).thumbnail(0.1f).into(iv_banner);
    }
}
