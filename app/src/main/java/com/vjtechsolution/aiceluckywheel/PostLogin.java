package com.vjtechsolution.aiceluckywheel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostLogin {

    @POST("api/auth")
    Call<UserAuth> loginUser(@Body UserAuth userAuth);

}
