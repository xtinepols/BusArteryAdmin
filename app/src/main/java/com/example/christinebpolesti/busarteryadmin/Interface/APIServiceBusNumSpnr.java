package com.example.christinebpolesti.busarteryadmin.Interface;

import com.example.christinebpolesti.busarteryadmin.Pojo.Buses;
import com.example.christinebpolesti.busarteryadmin.Remote.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by christine B. Polesti on 12/5/2017.
 */

public interface APIServiceBusNumSpnr {

    public static final String BASE_URL = "http://192.168.0.120:1337";

    @GET(BASE_URL+"/buses/")
    Call<Buses> getBusNumbers();
}
