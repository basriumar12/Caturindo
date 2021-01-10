package com.caturindo.models;

public class TransportItemModel {

    private int carCode;
    private String carName;
    private int availability;
    private int capacity;
    private int drawable;

    public TransportItemModel(int id, String text, int available, int capacity, int drawable){
        this.carCode = id;
        this.carName = text;
        this.availability = available;
        this.capacity = capacity;
        this.drawable = drawable;
    }

    public int getCarCode() {
        return carCode;
    }

    public void setCarCode(int carCode) {
        this.carCode = carCode;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}