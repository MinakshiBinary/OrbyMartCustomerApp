package com.binaryic.customerapp.orbymart.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.binaryic.customerapp.orbymart.Model.ProductModel;
import com.binaryic.customerapp.orbymart.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by HP on 12-Apr-17.
 */

public class CollectionProductAdapter extends RecyclerView.Adapter<CollectionProductAdapter.ViewHolder> {
    public ArrayList<ProductModel> list;
    Context context;
    ClickListener clickListener;
    public CollectionProductAdapter(Context context, ArrayList<ProductModel> list) {
        this.context = context;
        this.list = list;
    }
    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (list.get(position).getProduct_images().size() > 0)
            Glide.with(context).load(list.get(position).getProduct_images().get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.1f).into(holder.imgProduct1);
        holder.tvOldPrice1.setText("` " + list.get(position).getSelling_price());
        holder.tvSellingPrice1.setText("` " + list.get(position).getDiscount_price());
        holder.tvName1.setText(list.get(position).getProduct_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_product1;
        ImageView imgProduct1;
        TextView tvName1,tvOldPrice1,tvSellingPrice1;
        public ViewHolder(View view) {
            super(view);
            rl_product1 = (RelativeLayout) view.findViewById(R.id.rl_product1);
            imgProduct1 = (ImageView) view.findViewById(R.id.imgProduct1);
            tvName1 = (TextView) view.findViewById(R.id.tvName1);
            tvOldPrice1 = (TextView) view.findViewById(R.id.tvOldPrice1);
            tvSellingPrice1 = (TextView) view.findViewById(R.id.tvSellingPrice1);
            tvOldPrice1.setPaintFlags(tvOldPrice1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            rl_product1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.ItemClicked(v, getPosition());
                    }
                }
            });
        }
    }
    public interface ClickListener {
        public void ItemClicked(View view, int position);
    }
}
