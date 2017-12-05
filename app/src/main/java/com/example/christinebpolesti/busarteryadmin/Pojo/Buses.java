package com.example.christinebpolesti.busarteryadmin.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by christine B. Polesti on 8/9/2016.
 */

public class Buses {

    @SerializedName("busNumber")
    @Expose
    private String busNumber;
    @SerializedName("route")
    @Expose
    private String route;
    @SerializedName("capacity")
    @Expose
    private int capacity;
    @SerializedName("busType")
    @Expose
    private String busType;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("companyName")
    @Expose
    private String companyName;

    public Buses(String busNumber, String route, int capacity, String busType,
                 String location, String destination, String companyName) {
        this.busNumber = busNumber;
        this.route = route;
        this.capacity = capacity;
        this.busType = busType;
        this.location = location;
        this.destination = destination;
        this.companyName = companyName;
    }

    public Buses(String busNumber, String route, String companyName) {
        this.busNumber = busNumber;
        this.route = route;
        this.companyName = companyName;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
