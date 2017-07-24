package com.binaryic.customerapp.orbymart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.binaryic.customerapp.orbymart.Activity.ProductListActivity;
import com.binaryic.customerapp.orbymart.Model.BannerModel;
import com.binaryic.customerapp.orbymart.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;


/**
 * Created by User on 01-03-2016.
 */
public class BannerAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    List<BannerModel> mResources;

    public BannerAdapter(Context context, List<BannerModel> banners) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResources = banners;
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.banner_list_item, container, false);
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        itemView.setTag(mResources.get(position).getCategory_id());
        Glide.with(mContext).load(mResources.get(position).getBanner_image()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_launcher).thumbnail(0.1f).into(imageView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.e("Category_id", v.getTag().toString());
                    for (BannerModel bannerModel : mResources) {
                        if (bannerModel.getCategory_id().equals(v.getTag().toString())) {
                            Intent intent = new Intent(mContext, ProductListActivity.class);
                            intent.putExtra("collection", bannerModel.getCategory_name());
                            intent.putExtra("collection_id", bannerModel.getCategory_id());
                            mContext.startActivity(intent);
                            break;
                        }
                    }
                } catch (Exception ex) {
                }
            }
        });

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}
