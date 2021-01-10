package com.caturindo.models;

import java.util.ArrayList;

public class TaskModel {
    private String id;
    private String title;
    private String date;
    private ArrayList<String> participants;
    private String description;
    private CommentModel comment;
    private String type;

    public TaskModel(String id, String title, String date, ArrayList<String> participants, String description, CommentModel comment, String type) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.participants = participants;
        this.description = description;
        this.comment = comment;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CommentModel getComment() {
        return comment;
    }

    public void setComment(CommentModel comment) {
        this.comment = comment;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }
}
