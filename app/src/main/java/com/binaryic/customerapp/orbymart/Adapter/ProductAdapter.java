package com.binaryic.customerapp.orbymart.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Model.ProductModel;
import com.binaryic.customerapp.orbymart.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Asd on 17-09-2016.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    public ArrayList<ProductModel> list;
    Context context;
    ClickListener clickListener;
    private ScrollListener scrollListener;

    public ProductAdapter(Context context, ArrayList<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            Log.e("notify", "notifyAdapter");
            if ((list.size() - 1) == position) {
                if (scrollListener != null)
                    scrollListener.Scrolled(position + 1);
            }
            Glide.with(context).load(list.get(position).getProduct_images().get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.1f).placeholder(R.drawable.logo).into(holder.imgProduct);

            holder.tvOldPrice.setText("` " + list.get(position).getSelling_price());
            Utils.setMargins(holder.tvOldPrice, 0, 0, 50, 0);

            holder.tvSellingPrice.setText("` " + list.get(position).getDiscount_price());

            if (list.get(position).getIsFav())
                holder.imgfav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_detail_selected));
            else
                holder.imgfav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fav_white));
            holder.tvName.setText(list.get(position).getProduct_name());
        } catch (Exception ex) {
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ClickListener {
        public void ItemClicked(View view, int position);
    }

    public interface ScrollListener {
        public void Scrolled(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgfav, imgProduct;
        TextView tvOldPrice;
        TextView tvSellingPrice, tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imgfav = (ImageView) itemView.findViewById(R.id.imgfav);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            tvOldPrice = (TextView) itemView.findViewById(R.id.tvOldPrice);
            tvSellingPrice = (TextView) itemView.findViewById(R.id.tvSellingPrice);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            imgfav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(getPosition()).getIsFav())
                        imgfav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite));
                    else
                        imgfav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_home_selected));
                    if (clickListener != null) {
                        clickListener.ItemClicked(v, getPosition());
                    }
                }
            });
            imgProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.ItemClicked(v, getPosition());
                    }
                }
            });
            tvOldPrice.setPaintFlags(tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.ItemClicked(v, getPosition());
            }
        }
    }
}

