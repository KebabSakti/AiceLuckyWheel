package com.vjtechsolution.aiceluckywheel;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<ProductData> productDataList;

    private Integer n = 0;

    private View v;

    private String username, api_token, kode_asset;
    private Context context;
    private SharedPreferences sharedPreferences;

    private SweetAlertDialog pDialog;

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

        sharedPreferences = context.getSharedPreferences(getString(R.string.key_preference), Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username","");
        api_token = sharedPreferences.getString("api_token", "");
        kode_asset = sharedPreferences.getString("kode_asset", "");

        ProductModel productModel = new ProductModel(
                username,
                api_token,
                kode_asset
        );

        getAllProducts(productModel);
    }

    private void getAllProducts(ProductModel productModel) {
        //progress dialog
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorAccent));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        GetProduct getProduct = RetrofitBuilderGenerator.createService(GetProduct.class);
        Call<ProductModel> getProductCall = getProduct.productModel(productModel);

        getProductCall.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                if(response.code() == 200){
                    productDataList = response.body().getProductDataList();

                    recyclerView = getActivity().findViewById(R.id.rv);
                    recyclerView.setHasFixedSize(true);

                    layoutManager = new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(layoutManager);

                    adapter = new ProductListAdapter(productDataList);
                    recyclerView.setAdapter(adapter);

                    pDialog.dismissWithAnimation();
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
