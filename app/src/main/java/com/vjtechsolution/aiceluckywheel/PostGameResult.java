package com.vjtechsolution.aiceluckywheel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostGameResult {

    @FormUrlEncoded
    @POST("api/game/play")
    Call<GameResultData> postGameResult(
            @Field("username") String username,
            @Field("api_token") String api_token,
            @Field("kode_asset") String kode_asset,
            @Field("session") String session,
            @Field("no_telp") String no_telp,
            @Field("beli[]") ArrayList<Integer> beli,
            @Field("drawn[]") ArrayList<Integer> drawn,
            @Field("menang[]") ArrayList<Integer> menang,
            @Field("kalah[]") ArrayList<Integer> kalah,
            @Field("hadiah[]") ArrayList<String> hadiah
    );

}
