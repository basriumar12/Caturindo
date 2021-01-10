package com.caturindo.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MeetingModel implements Parcelable {
    private String id;
    private String title;
    private String desc;
    private String date;
    private String time;
    private int participant;
    private String location;
    private int type;

    public MeetingModel(String id, String title, String desc, String date, String time, int participant, String location, int type) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.time = time;
        this.participant = participant;
        this.location = location;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getParticipant() {
        return participant;
    }

    public void setParticipant(int participant) {
        this.participant = participant;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeString(this.date);
        dest.writeString(this.time);
        dest.writeInt(this.participant);
        dest.writeString(this.location);
        dest.writeInt(this.type);
    }

    protected MeetingModel(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.desc = in.readString();
        this.date = in.readString();
        this.time = in.readString();
        this.participant = in.readInt();
        this.location = in.readString();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<MeetingModel> CREATOR = new Parcelable.Creator<MeetingModel>() {
        @Override
        public MeetingModel createFromParcel(Parcel source) {
            return new MeetingModel(source);
        }

        @Override
        public MeetingModel[] newArray(int size) {
            return new MeetingModel[size];
        }
    };
}
