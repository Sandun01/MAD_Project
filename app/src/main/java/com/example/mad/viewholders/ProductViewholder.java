package com.example.mad.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad.R;
import com.example.mad.interfaces.ItemClicklistner;

public class ProductViewholder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView pnameTxt, pdesTxt, priceTxt;
    public ImageView imageView;
    public ItemClicklistner listner;

    public ProductViewholder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        pnameTxt = (TextView) itemView.findViewById(R.id.product_name);
        pdesTxt = (TextView) itemView.findViewById(R.id.product_description);
        priceTxt = (TextView) itemView.findViewById(R.id.product_price);

    }

    public void setItemClickListner(ItemClicklistner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view, getAdapterPosition(), false);

    }
}
