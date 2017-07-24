package com.binaryic.customerapp.orbymart.Adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.binaryic.customerapp.orbymart.Model.Address;
import com.binaryic.customerapp.orbymart.R;

import java.util.ArrayList;

/**
 * Created by Asd on 10-10-2016.
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    Context context;
    public ArrayList<Address> list;

    public AddressAdapter(Context context, ArrayList<Address> list) {
        this.context = context;
        this.list = list;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    ClickListener clickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(list.get(position).getFirstName() + " " + list.get(position).getLastName());
        holder.tvAddress.setText(list.get(position).getAddress() + " " + list.get(position).getLandmark() + " " + list.get(position).getArea() + " " + list.get(position).getCity() + " " +  list.get(position).getState());
        holder.tvPincode.setText(list.get(position).getZip());
        holder.tvContactNo.setText(list.get(position).getPhone());
        holder.chkDefault.setChecked(list.get(position).getDefaultAddress());
        if(list.get(position).getDefaultAddress())
            holder.btnRemove.setVisibility(View.GONE);
        else
            holder.btnRemove.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvAddress, tvPincode, tvContactNo;
        AppCompatCheckBox chkDefault;
        LinearLayout btnRemove,btnEdit;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            tvPincode = (TextView) view.findViewById(R.id.tvPincode);
            tvContactNo = (TextView) view.findViewById(R.id.tvContactNo);
            chkDefault = (AppCompatCheckBox) view.findViewById(R.id.chkDefault);
            btnRemove = (LinearLayout) view.findViewById(R.id.btnRemove);
            btnEdit = (LinearLayout) view.findViewById(R.id.btnEdit);
            chkDefault.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(chkDefault.isChecked()){
                        if (clickListener != null)
                            clickListener.ItemClicked(v, getPosition());
                    }else {
                        chkDefault.setChecked(true);
                    }
                }
            });
            btnRemove.setOnClickListener(this);
            btnEdit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.ItemClicked(v, getPosition());
        }
    }

    public interface ClickListener {
        public void ItemClicked(View view, int position);
    }
}
