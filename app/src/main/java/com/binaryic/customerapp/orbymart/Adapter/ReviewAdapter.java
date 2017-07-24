package com.binaryic.customerapp.orbymart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.binaryic.customerapp.orbymart.Model.RatingModel;
import com.binaryic.customerapp.orbymart.R;

import java.util.ArrayList;

/**
 * Created by HP on 12-Mar-17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    ArrayList<RatingModel> models;
    Context context;

    public ReviewAdapter(ArrayList<RatingModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_author.setText(models.get(position).getCustomer_name());
        holder.tv_comment.setText(models.get(position).getTitle());
        holder.tv_date.setText(models.get(position).getCreated_at());
        holder.tv_message.setText(models.get(position).getReview_message());
        if (models.get(position).getStars().equals("null"))
            holder.tv_rating.setText("0");
        else
            holder.tv_rating.setText(models.get(position).getStars());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_comment, tv_author, tv_date, tv_rating,tv_message;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_rating = (TextView) itemView.findViewById(R.id.tv_rating);
            tv_message = (TextView) itemView.findViewById(R.id.tv_message);
        }
    }
}
