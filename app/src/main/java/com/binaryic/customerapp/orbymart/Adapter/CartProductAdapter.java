package com.binaryic.customerapp.orbymart.Adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binaryic.customerapp.orbymart.Model.ProductModelQty;
import com.binaryic.customerapp.orbymart.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Asd on 16-09-2016.
 */
public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ViewHolder> {
    Context context;
    public ArrayList<ProductModelQty> list;

    public CartProductAdapter(Context context, ArrayList<ProductModelQty> list) {
        this.context = context;
        this.list = list;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    ClickListener clickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            Glide.with(context).load(list.get(position).getProductModel().getProduct_images().get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.1f).placeholder(R.drawable.logo).into(holder.img);
            holder.tvOldPrice.setText((Double.valueOf(list.get(position).getProductModel().getSelling_price()).intValue() * list.get(position).getQty()) + "");
            holder.tvPrice.setText((Double.valueOf(list.get(position).getProductModel().getDiscount_price()).intValue() * list.get(position).getQty()) + "");
            holder.layOldPrice.setVisibility(View.VISIBLE);
            holder.tvProductName.setText(list.get(position).getProductModel().getProduct_name());
            holder.tvQty.setText(list.get(position).getQty() + "");
            setQty(list.get(position).getProductModel().getRemain_qty(),holder);

        } catch (Exception ex) {
        }
    }

    private void setQty(String qty,ViewHolder holder){
        switch (qty){
            case "1":
                holder.tv1.setVisibility(View.VISIBLE);
                holder.tv2.setVisibility(View.GONE);
                holder.tv3.setVisibility(View.GONE);
                holder.tv4.setVisibility(View.GONE);
                holder.tv5.setVisibility(View.GONE);
                break;
            case "2":
                holder.tv1.setVisibility(View.VISIBLE);
                holder.tv2.setVisibility(View.VISIBLE);
                holder.tv3.setVisibility(View.GONE);
                holder.tv4.setVisibility(View.GONE);
                holder.tv5.setVisibility(View.GONE);
                break;
            case "3":
                holder.tv1.setVisibility(View.VISIBLE);
                holder.tv2.setVisibility(View.VISIBLE);
                holder.tv3.setVisibility(View.VISIBLE);
                holder.tv4.setVisibility(View.GONE);
                holder.tv5.setVisibility(View.GONE);
                break;
            case "4":
                holder.tv1.setVisibility(View.VISIBLE);
                holder.tv2.setVisibility(View.VISIBLE);
                holder.tv3.setVisibility(View.VISIBLE);
                holder.tv4.setVisibility(View.VISIBLE);
                holder.tv5.setVisibility(View.GONE);
                break;
            case "5":
                holder.tv1.setVisibility(View.VISIBLE);
                holder.tv2.setVisibility(View.VISIBLE);
                holder.tv3.setVisibility(View.VISIBLE);
                holder.tv4.setVisibility(View.VISIBLE);
                holder.tv5.setVisibility(View.VISIBLE);
                break;
            default:
                holder.tv1.setVisibility(View.VISIBLE);
                holder.tv2.setVisibility(View.VISIBLE);
                holder.tv3.setVisibility(View.VISIBLE);
                holder.tv4.setVisibility(View.VISIBLE);
                holder.tv5.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img, imgChangeQty;
        LinearLayout llRemove, layOldPrice, layQty;
        TextView tvProductName, tvOldPrice, tvPrice, tvQty;
        TextView tv1, tv2, tv3, tv4, tv5;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            imgChangeQty = (ImageView) itemView.findViewById(R.id.imgChangeQty);
            llRemove = (LinearLayout) itemView.findViewById(R.id.llRemove);
            layQty = (LinearLayout) itemView.findViewById(R.id.layQty);
            layOldPrice = (LinearLayout) itemView.findViewById(R.id.layOldPrice);
            tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvOldPrice = (TextView) itemView.findViewById(R.id.tvOldPrice);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvQty = (TextView) itemView.findViewById(R.id.tvQty);
            tv1 = (TextView) itemView.findViewById(R.id.tv1);
            tv2 = (TextView) itemView.findViewById(R.id.tv2);
            tv3 = (TextView) itemView.findViewById(R.id.tv3);
            tv4 = (TextView) itemView.findViewById(R.id.tv4);
            tv5 = (TextView) itemView.findViewById(R.id.tv5);
            llRemove.setOnClickListener(this);
            tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CloseQty(layQty);
                    tvQty.setText("1");
                    if (clickListener != null)
                        clickListener.ItemClicked(v, getPosition(), 1);
                }
            });
            tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CloseQty(layQty);
                    tvQty.setText("2");
                    if (clickListener != null)
                        clickListener.ItemClicked(v, getPosition(), 2);
                }
            });
            tv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CloseQty(layQty);
                    tvQty.setText("3");
                    if (clickListener != null)
                        clickListener.ItemClicked(v, getPosition(), 3);
                }
            });
            tv4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CloseQty(layQty);
                    tvQty.setText("4");
                    if (clickListener != null)
                        clickListener.ItemClicked(v, getPosition(), 4);
                }
            });
            tv5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CloseQty(layQty);
                    tvQty.setText("5");
                    if (clickListener != null)
                        clickListener.ItemClicked(v, getPosition(), 5);
                }
            });
            imgChangeQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (layQty.getVisibility() == View.VISIBLE) {
                        dropDownAnimationReverse(layQty);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(350);
                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            layQty.setVisibility(View.GONE);
                                        }
                                    });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    } else {
                        layQty.setVisibility(View.VISIBLE);
                        dropDownAnimation(layQty);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.ItemClicked(v, getPosition(), 0);
        }
    }

    public interface ClickListener {
        public void ItemClicked(View view, int position, int Qty);
    }

    private void dropDownAnimation(final View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationY", -40, 0),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1)

        );
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(350);

        animatorSet.start();
    }

    private void dropDownAnimationReverse(final View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationY", 0, -40),
                ObjectAnimator.ofFloat(view, "alpha", 1, 0)

        );
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(350);

        animatorSet.start();
    }

    private void CloseQty(final View layQty) {
        dropDownAnimationReverse(layQty);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(350);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            layQty.setVisibility(View.GONE);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
