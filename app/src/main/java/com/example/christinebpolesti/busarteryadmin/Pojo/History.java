package com.example.christinebpolesti.busarteryadmin.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by christine B. Polesti on 8/9/2016.
 */

public class History {

    @SerializedName("driversName")
    @Expose
    private String driversName;
    @SerializedName("busNumber")
    @Expose
    private String busNumber;
    @SerializedName("route")
    @Expose
    private String route;
    @SerializedName("earnings")
    @Expose
    private int earnings;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("timeStarted")
    @Expose
    private String timeSarted;
    @SerializedName("timeEnded")
    @Expose
    private String timeEnded;
    @SerializedName("totalPassenger")
    @Expose
    private String totalPassenger;
    @SerializedName("tripCount")
    @Expose
    private int tripCount;

    public History() {}

    public History(String driversName, String busNumber, String route, int earnings, String distance, String date,
                   String timeStarted, String timeEnded, String totalPassenger, int tripCount) {
        this.driversName = driversName;
        this.busNumber = busNumber;
        this.route = route;
        this.earnings = earnings;
        this.distance = distance;
        this.date = date;
        this.timeSarted = timeStarted;
        this.timeEnded = timeEnded;
        this.totalPassenger = totalPassenger;
        this.tripCount = tripCount;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public String getTimeSarted() {
        return timeSarted;
    }

    public String getTimeEnded() {
        return timeEnded;
    }

    public String getDriversName() {
        return driversName;
    }

    public String getRoute() {
        return route;
    }

    public int getEarnings() {
        return earnings;
    }

    public String getDistance() {
        return distance;
    }

    public String getDate() {
        return date;
    }

    public String getTotalPassenger() {
        return totalPassenger;
    }

    public int getTripCount() {
        return tripCount;
    }
}
