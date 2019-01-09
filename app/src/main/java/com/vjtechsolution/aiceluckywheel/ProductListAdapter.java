package com.vjtechsolution.aiceluckywheel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ProductData> productDataList;
    private Context context;

    public ProductListAdapter(List<ProductData> productDataList, Context context) {
        this.productDataList = productDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_layout, null);

        return RecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        viewHolder
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
