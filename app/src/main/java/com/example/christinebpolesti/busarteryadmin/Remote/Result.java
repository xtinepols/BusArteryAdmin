package com.example.christinebpolesti.busarteryadmin.Remote;

import com.example.christinebpolesti.busarteryadmin.Pojo.Buses;
import com.example.christinebpolesti.busarteryadmin.Pojo.Driver;
import com.example.christinebpolesti.busarteryadmin.Pojo.Owner;
import com.google.gson.annotations.SerializedName;

/**
 * Created by christine B. Polesti on 12/1/2017.
 */

public class Result {

    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("userOwner")
    private Owner userOwner;

    @SerializedName("bus")
    private Buses buses;

    @SerializedName("driver")
    private Driver driver;

    public Result(Boolean error, String message, Owner userOwner) {
        this.error = error;
        this.message = message;
        this.userOwner = userOwner;
    }

    public Result(Boolean error, String message, Buses buses) {
        this.error = error;
        this.message = message;
        this.buses = buses;
    }

    public Result(Boolean error, String message, Driver driver) {
        this.error = error;
        this.message = message;
        this.driver = driver;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Owner getUserOwner() {
        return userOwner;
    }
}
