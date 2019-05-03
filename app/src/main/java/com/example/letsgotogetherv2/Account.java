package com.example.letsgotogetherv2;

import java.util.ArrayList;

/**
 * Created by Trung Tinh on 4/25/2019.
 */
public class Account {
    private int accountID;
    private String name, email, phone, adress;
    private ArrayList<Trip> tripArrayList;

    public Account(String name, String email, String phone, String adress) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.adress = adress;
        this.accountID = (name+phone).hashCode();
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public ArrayList<Trip> getTripArrayList() {
        return tripArrayList;
    }

    public int getAccountID() {
        return accountID;
    }

    public void bookTrip(String from, String to, String date, String time, Boolean driver) {
        Trip trip = new Trip(from,to,date,time,driver);
        tripArrayList.add(trip);
    }

    public void deleteTrip(Trip trip){
        tripArrayList.remove(trip);
    }
}

class Admin extends Account{

    public Admin(String name, String email, String phone, String adress) {
        super(name, email, phone, adress);
    }


}
