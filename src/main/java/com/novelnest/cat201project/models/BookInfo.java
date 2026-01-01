package com.novelnest.cat201project.models;

public class BookInfo {
    private int id;
    private String title;
    private String synopsis;

    public BookInfo(int id, String title, String synopsis) {
        this.id = id;
        this.title = title;
        this.synopsis = synopsis;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getSynopsis() { return synopsis; }
}
