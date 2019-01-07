package com.vjtechsolution.aiceluckywheel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DashboardUser {

    @POST("api/dashboard")
    Call<DashboardModel> dashboardModel(@Body DashboardModel dashboardModel);
}
