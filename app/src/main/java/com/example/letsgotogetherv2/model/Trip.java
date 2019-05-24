package com.example.letsgotogetherv2.model;

/**
 * Created by Trung Tinh on 4/25/2019.
 */
public class Trip {
    private String userID, partnerID;
    private String tripID;
    private String from, to, date, time;
    private Boolean driver;

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public Trip() {}

    public Trip(String userID, String tripID, String from, String to, String date, String time, Boolean driver) {
        this.userID = userID;
        this.tripID = tripID;
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.driver = driver;
    }

    public Trip(String from, String to, String date, String time, Boolean driver) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.driver = driver;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Boolean getDriver() {
        return driver;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDriver(Boolean driver) {
        this.driver = driver;
    }

}

