package com.example.christinebpolesti.busarteryadmin.Interface;

import com.example.christinebpolesti.busarteryadmin.Pojo.Owner;
import com.example.christinebpolesti.busarteryadmin.Remote.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by christine B. Polesti on 11/30/2017.
 */

public interface APIServiceOwner {

    @POST("/owners/")
    @FormUrlEncoded
    Call<Result> createOwner(@Field("username") String username,
                             @Field("password") String password,
                             @Field("name") String name,
                             @Field("email") String email);

    @POST("/sessions/")
    @FormUrlEncoded
    Call<Result> ownerLogin(@Field("username") String username,
                            @Field("password") String password,
                            @Field("type") String type);

    @GET("user")
    Call<Result> getUser(@Header("Authorization") String authorization);
}
