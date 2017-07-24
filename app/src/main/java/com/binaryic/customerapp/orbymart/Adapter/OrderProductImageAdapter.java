package com.binaryic.customerapp.orbymart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.binaryic.customerapp.orbymart.Model.OrderProductModel;
import com.binaryic.customerapp.orbymart.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by HP on 31-Mar-17.
 */

public class OrderProductImageAdapter extends RecyclerView.Adapter<OrderProductImageAdapter.ViewHolder> {
    Context context;
    ArrayList<OrderProductModel> images;

    public OrderProductImageAdapter(Context context, ArrayList<OrderProductModel> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_product_image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!images.get(position).getProduct_image().equals(""))
            Glide.with(context).load(images.get(position).getProduct_image()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.logo).thumbnail(0.1f).into(holder.iv_product);

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_product;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_product = (ImageView) itemView.findViewById(R.id.iv_product);
        }
    }
}
