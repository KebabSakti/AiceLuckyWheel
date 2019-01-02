package com.vjtechsolution.aiceluckywheel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostDaftarUser {

    @POST("api/register")
    Call<RegisterUser> registerUser(@Body RegisterUser registerUser);

}
