package com.example.mad.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad.R;
import com.example.mad.interfaces.ItemClicklistner;

public class UserOrderItemViewHolder extends RecyclerView.ViewHolder{

    public TextView txtOrdItmName, txtOrdItmPrice, txtOrdItmQty;

    public UserOrderItemViewHolder(@NonNull View itemView) {
        super(itemView);

        txtOrdItmName = itemView.findViewById(R.id.user_orders_productname);
        txtOrdItmPrice = itemView.findViewById(R.id.user_orders_qty);
        txtOrdItmQty = itemView.findViewById(R.id.user_orders_price);

    }

}
