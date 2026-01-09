package com.novelnest.cat201project.models;

import java.sql.Timestamp;

public class Reviews {
    private int userId;
    private int bookId;
    private String title;
    private int rating;
    private String description;
    private java.sql.Timestamp createDate;

    public Reviews() {}

    public Reviews(int userId, int bookId, String title, int rating, String description, Timestamp createDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.createDate = createDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}