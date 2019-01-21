package com.vjtechsolution.aiceluckywheel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GetPrize {

    @POST("api/prize/get")
    Call<PrizeModel> prizeModel (@Body PrizeModel prizeModel);
}
