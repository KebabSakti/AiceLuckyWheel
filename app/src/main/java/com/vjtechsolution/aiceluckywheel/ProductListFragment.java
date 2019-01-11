package com.vjtechsolution.aiceluckywheel;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ProductListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<ProductData> productDataList;

    private ImageView plusQty, minQty;
    private TextView qty;

    private Integer n = 0;

    private Context context;

    private View v;

    public ProductListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_product_list, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = getContext();

        productDataList = new ArrayList<>();

        for(int i=0;i<100;i++){
            productDataList.add(new ProductData(String.valueOf(i), "Product_"+String.valueOf(i)));
        }

        recyclerView = getActivity().findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ProductListAdapter(productDataList);

        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapter);

        //RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) recyclerView.getTag();

        //Toast.makeText(context, String.valueOf(viewHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();

        //Log.d("DBU", String.valueOf(viewHolder));
    }
}
