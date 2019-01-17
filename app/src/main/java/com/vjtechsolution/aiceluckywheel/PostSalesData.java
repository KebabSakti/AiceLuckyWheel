package com.vjtechsolution.aiceluckywheel;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface PostSalesData {

   @Multipart
   @POST("api/game/register")
   Call<SalesData> postIt(
           @Part("username") RequestBody username,
           @Part("api_token") RequestBody api_token,
           @Part("kode_asset") RequestBody kode_asset,
           @Part MultipartBody.Part foto,
           @Part("nama") RequestBody nama,
           @Part("no_telp") RequestBody no_telp,
           @Part("session") RequestBody session,
           @Query("products[]") ArrayList<String> products,
           @Query("qty_products[]") ArrayList<Integer> qtyProducts
   );

}
