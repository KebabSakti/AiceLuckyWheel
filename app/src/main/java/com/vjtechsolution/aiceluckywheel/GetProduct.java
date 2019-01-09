package com.vjtechsolution.aiceluckywheel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GetProduct {

    @POST("api/product/all")
    Call<ProductModel> productModel (@Body ProductModel productModel);

}
