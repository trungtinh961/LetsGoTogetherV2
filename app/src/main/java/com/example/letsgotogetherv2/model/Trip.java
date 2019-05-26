package com.example.letsgotogetherv2.model;

import java.io.Serializable;

/**
 * Created by Trung Tinh on 4/25/2019.
 */
public class Trip implements Serializable {
    private String userID, partnerID;
    private String tripID;
    private String from, to, date, time;
    private Boolean driver, isChoose;

    public Trip() {}

    public Trip(String userID, String tripID, String from, String to, String date, String time, Boolean driver) {
        this.userID = userID;
        this.tripID = tripID;
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.driver = driver;
        this.isChoose = false;
        this.partnerID = "";
    }

    public Boolean getChoose() {
        return isChoose;
    }

    public void setChoose(Boolean choose) {
        isChoose = choose;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPartnerID() {
        return partnerID;
    }

    public void setPartnerID(String partnerID) {
        this.partnerID = partnerID;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
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

