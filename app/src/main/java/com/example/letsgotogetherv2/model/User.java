package com.example.letsgotogetherv2.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Trung Tinh on 4/25/2019.
 */
public class User implements Serializable {
    private String name, email, phone, address;
    private ArrayList<String> tripArrayList;

    public User(){}

    public User(String name, String email, String phone, String address) {
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

    public ArrayList<String> getTripArrayList() {
        return tripArrayList;
    }

    public void setTripArrayList(ArrayList<String> tripArrayList) {
        this.tripArrayList = tripArrayList;
    }
}

