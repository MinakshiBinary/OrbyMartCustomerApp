package com.binaryic.customerapp.orbymart.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.binaryic.customerapp.orbymart.Activity.ImageZoomActivity;
import com.binaryic.customerapp.orbymart.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by User on 25-01-16.
 */
public class ProductImagePagerAdapter extends PagerAdapter {
    Context mContext;
    public static ImageView imgDisplay;
    LayoutInflater mLayoutInflater;
    ArrayList<String> mResources;

    public ProductImagePagerAdapter(Context context, ArrayList<String> images) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResources = images;
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_product_image, container, false);
        imgDisplay = (ImageView) itemView.findViewById(R.id.imgDisplay);

        Log.e("url", mResources.get(position));
        Glide.with(mContext).load(mResources.get(position)).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.1f).placeholder(R.drawable.logo).thumbnail(0.1f).into(imgDisplay);
        imgDisplay.setTag(position + "");

        imgDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageZoomActivity.class);
                intent.putExtra("pos", Integer.parseInt(((View) v).getTag().toString()));
                intent.putStringArrayListExtra("images", mResources);
                mContext.startActivity(intent);
                ((Activity)mContext).overridePendingTransition(R.anim.zoom_in, R.anim.no_animation);
            }
        });
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
