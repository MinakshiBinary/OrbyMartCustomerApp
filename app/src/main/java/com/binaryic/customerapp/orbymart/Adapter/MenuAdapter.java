package com.binaryic.customerapp.orbymart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.binaryic.customerapp.orbymart.Activity.HomeActivity;
import com.binaryic.customerapp.orbymart.Activity.ProductListActivity;
import com.binaryic.customerapp.orbymart.Model.CategoryModel;
import com.binaryic.customerapp.orbymart.R;

import java.util.ArrayList;

/**
 * Created by HP on 05-Apr-17.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    Context context;
    ArrayList<CategoryModel> list;
    public MenuAdapter(Context context, ArrayList<CategoryModel> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_menu.setText(list.get(position).getCategory_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_menu;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_menu = (TextView) itemView.findViewById(R.id.tv_menu);
            tv_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeActivity.drawer.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(context, ProductListActivity.class);
                    intent.putExtra("collection", list.get(getPosition()).getCategory_name());
                    intent.putExtra("collection_id", list.get(getPosition()).getCategory_id());
                    context.startActivity(intent);
                }
            });
        }
    }
}
