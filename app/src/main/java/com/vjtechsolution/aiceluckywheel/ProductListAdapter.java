package com.vjtechsolution.aiceluckywheel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private List<ProductData> productDataList;
    private Integer n = 0;

    /*
    private View.OnClickListener onItemClickListener;

    public void setItemClickListener(View.OnClickListener clickListener) {
        onItemClickListener = clickListener;
    }
    */

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView plusQty, minQty;
        public TextView qty;

        public MyViewHolder(@NonNull View view) {
            super(view);
            //view.setTag(this);
            //view.setOnClickListener(onItemClickListener);

            textView = view.findViewById(R.id.namaProduk);
            plusQty = view.findViewById(R.id.plusQty);
            minQty = view.findViewById(R.id.minQty);
            qty = view.findViewById(R.id.qty);

            //view.setOnClickListener(this);
        }

        /*
        @Override
        public void onClick(View v) {
            Log.d("CLICK ITEM", "Click click "+String.valueOf(v.getId()));
        }
        */
    }

    public ProductListAdapter(List<ProductData> productDataList) {
        this.productDataList = productDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductData productData = productDataList.get(position);
        holder.textView.setText(productData.getNama());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK ITEM", "Click click "+String.valueOf(v.getId()));
            }
        });

        /*
        holder.plusQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n += 1;
            }
        });

        holder.minQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(n != 0){
                    n -= 1;
                }
            }
        });

        holder.qty.setText(String.valueOf(n));
        */
    }

    @Override
    public int getItemCount() {
        return productDataList.size();
    }
}
