package com.example.christinebpolesti.busarteryadmin.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by christine B. Polesti on 8/9/2016.
 */

public class Driver {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("age")
    @Expose
    private int age;
    @SerializedName("busNumber")
    @Expose
    private String busNumber;
    @SerializedName("employeeId")
    @Expose
    private String employeeId;
    @SerializedName("route")
    @Expose
    private String route;

    public Driver(String fullName, String employeeId, String busNumber, int age, String username, String password) {
        this.fullName = fullName;
        this.employeeId = employeeId;
        this.busNumber = busNumber;
        this.age = age;
        this.username = username;
        this.password = password;
    }

    public String getDriversName() {
        return fullName;
    }

    public void setDriversName(String fullName) {
        this.fullName = fullName;
    }

    public String getBusNum() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
