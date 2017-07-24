package com.binaryic.customerapp.orbymart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binaryic.customerapp.orbymart.R;
import com.payu.india.Model.PaymentDetails;

import java.util.ArrayList;

/**
 * Created by Asd on 13-10-2016.
 */
public class BanksAdapter extends RecyclerView.Adapter<BanksAdapter.ViewHolder> {
    Context context;
    ArrayList<PaymentDetails> list;
    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    ClickListener clickListener;
    public BanksAdapter(Context context, ArrayList<PaymentDetails> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvBankName.setText(list.get(position).getBankName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBankName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvBankName = (TextView) itemView.findViewById(R.id.tvBankName);
            tvBankName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListener != null)
                        clickListener.ItemClicked(v,getPosition());
                }
            });
        }
    }
    public interface ClickListener {
        public void ItemClicked(View view, int position);
    }
}
