package com.caturindo.models;

public class HomeItemModel {

    private int id;
    private String text;
    private int drawable;

    public HomeItemModel(int id, String text, int drawable){
        this.id = id;
        this.text = text;
        this.drawable = drawable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

}