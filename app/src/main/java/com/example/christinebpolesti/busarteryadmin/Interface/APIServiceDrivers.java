package com.example.christinebpolesti.busarteryadmin.Interface;

import com.example.christinebpolesti.busarteryadmin.Remote.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by christine B. Polesti on 12/1/2017.
 */

public interface APIServiceDrivers {

    @POST("/drivers/")
    @FormUrlEncoded
    Call<Result> addDriver(@Field("username") String username,
                           @Field("password") String password,
                           @Field("fullName") String fullName,
                           @Field("age") int age,
                           @Field("busNumber") String busNumber,
                           @Field("employeeId") String employeeId);
}
