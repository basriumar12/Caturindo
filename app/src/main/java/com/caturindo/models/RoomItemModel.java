package com.caturindo.models;

import java.util.ArrayList;

public class RoomItemModel {

    private int roomCode;
    private String roomName;
    private int availability;
    private int capacity;
    private int drawable;
    private ArrayList<String> imageUrl;

    public ArrayList<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(ArrayList<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public RoomItemModel(int id, String text, int available, int capacity, int drawable){
        this.roomCode= id;
        this.roomName= text;
        this.availability = available;
        this.capacity = capacity;
        this.drawable = drawable;
    }

    public int getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(int roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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