package com.vjtechsolution.aiceluckywheel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AdsInterface {

    @POST("api/promo")
    Call<AdsModel> adsModel(@Body AdsModel adsModel);
}
