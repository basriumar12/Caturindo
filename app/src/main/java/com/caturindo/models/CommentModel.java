package com.caturindo.models;

public class CommentModel {
    private String id;
    private UserModel sender;
    private String comment;

    public CommentModel(String id, UserModel sender, String comment) {
        this.id = id;
        this.sender = sender;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserModel getSender() {
        return sender;
    }

    public void setSender(UserModel sender) {
        this.sender = sender;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
