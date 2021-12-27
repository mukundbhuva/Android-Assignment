package com.assignment;

import java.io.Serializable;

public class Trips implements Serializable {
    protected String Name, email, place_type, date_from, date_to, travel_via;
    protected int no_of_person, no_of_days;
    protected float price_from, price_to;
    private int trip_id;

    public Trips(String name, String email, String place_type, String date_from, String date_to, String travel_via, int no_of_person, int no_of_days, float price_from, float price_to, int trip_id) {
        Name = name;
        this.email = email;
        this.place_type = place_type;
        this.date_from = date_from;
        this.date_to = date_to;
        this.travel_via = travel_via;
        this.no_of_person = no_of_person;
        this.no_of_days = no_of_days;
        this.price_from = price_from;
        this.price_to = price_to;
        this.trip_id = trip_id;
    }

    public String getTravel_via() {
        return travel_via;
    }

    public void setTravel_via(String travel_via) {
        this.travel_via = travel_via;
    }

    public int getNo_of_days() {
        return no_of_days;
    }

    public void setNo_of_days(int no_of_days) {
        this.no_of_days = no_of_days;
    }

    public int getTrip_id() {
        return this.trip_id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlace_type() {
        return place_type;
    }

    public void setPlace_type(String place_type) {
        this.place_type = place_type;
    }

    public String getDate_from() {
        return date_from;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public String getDate_to() {
        return date_to;
    }

    public void setDate_to(String date_to) {
        this.date_to = date_to;
    }

    public int getNo_of_person() {
        return no_of_person;
    }

    public void setNo_of_person(int no_of_person) {
        this.no_of_person = no_of_person;
    }

    public float getPrice_from() {
        return price_from;
    }

    public void setPrice_from(float price_from) {
        this.price_from = price_from;
    }

    public float getPrice_to() {
        return price_to;
    }

    public void setPrice_to(float price_to) {
        this.price_to = price_to;
    }
}
