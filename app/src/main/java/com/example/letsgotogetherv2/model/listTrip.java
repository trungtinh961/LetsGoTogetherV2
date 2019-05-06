package com.example.letsgotogetherv2.model;

import java.util.ArrayList;

/**
 * Created by Trung Tinh on 4/25/2019.
 */
public class listTrip {
    private ArrayList<Trip> tripArrayList;

    public listTrip() {
        this.tripArrayList = new ArrayList<>();
    }

    public void addTrip(Trip trip) {
        trip.setTripID((trip.getFrom()+trip.getTo()+trip.getDate()).hashCode());
        tripArrayList.add(trip);
    }

    public void deleteTrip(Trip trip) {
        tripArrayList.remove(trip);
    }

    public Trip searchTrip(String from, String to, String date){
        int ID = (from+to+date).hashCode();
        for (Trip trip : tripArrayList) {
            if (trip.getTripID() == ID) {
                return trip;
            }
        }
        return null;
    }
}
