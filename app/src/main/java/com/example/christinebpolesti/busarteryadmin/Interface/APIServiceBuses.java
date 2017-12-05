package com.example.christinebpolesti.busarteryadmin.Interface;

import com.example.christinebpolesti.busarteryadmin.Remote.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by christine B. Polesti on 12/1/2017.
 */

public interface APIServiceBuses {

    @POST("/buses/")
    @FormUrlEncoded
    Call<Result> createBuses(@Field("busNumber") String busNumber,
                             @Field("route") String route,
                             @Field("capacity") int capacity,
                             @Field("busType") String busType,
                             @Field("location") String location,
                             @Field("destination") String destination,
                             @Field("companyName") String companyName);
}
