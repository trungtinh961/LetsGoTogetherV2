package com.example.letsgotogetherv2.model;

import java.util.ArrayList;

/**
 * Created by Trung Tinh on 4/25/2019.
 */
public class Account {
    private String name, email, phone, address;
    private ArrayList<Trip> tripArrayList;

    public Account(){}

    public Account(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.tripArrayList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Trip> getTripArrayList() {
        return tripArrayList;
    }

    public void setTripArrayList(ArrayList<Trip> tripArrayList) {
        this.tripArrayList = tripArrayList;
    }

    public void bookTrip(String from, String to, String date, String time, Boolean driver) {
        Trip trip = new Trip(from,to,date,time,driver);
        tripArrayList.add(trip);
    }

    public void deleteTrip(Trip trip){
        tripArrayList.remove(trip);
    }
}

