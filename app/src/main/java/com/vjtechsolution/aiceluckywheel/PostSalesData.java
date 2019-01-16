package com.vjtechsolution.aiceluckywheel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostSalesData {

   @FormUrlEncoded
   @POST("game/register")
   Call<SLS> salesData(
           @Field("username") String username,
           @Field("api_token") String api_token,
           @Field("kode_asset") String kode_asset,
           @Field("data[]") ArrayList<String> listData
   );
}
