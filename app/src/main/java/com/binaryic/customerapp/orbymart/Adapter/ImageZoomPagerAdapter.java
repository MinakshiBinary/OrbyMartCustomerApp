package com.binaryic.customerapp.orbymart.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.binaryic.customerapp.orbymart.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.List;

/**
 * Created by Asd on 20-09-2016.
 */
public class ImageZoomPagerAdapter extends PagerAdapter {
    Context mContext;
    public static SubsamplingScaleImageView imgDisplay;
    LayoutInflater mLayoutInflater;
    List<String> mResources;

    public ImageZoomPagerAdapter(Context context, List<String> images) {
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
        View itemView = mLayoutInflater.inflate(R.layout.image_zoom_layout, container, false);
        imgDisplay = (SubsamplingScaleImageView) itemView.findViewById(R.id.pinchImage);
        Log.e("url", mResources.get(position));
        try {
            Glide.with(mContext.getApplicationContext())
                    .load(mResources.get(position))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            Bitmap resized = Bitmap.createScaledBitmap(resource, 500, 700, true);
                            imgDisplay.setImage(ImageSource.bitmap(resized));
                        }
                    });


        } catch (Exception ex) {
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
