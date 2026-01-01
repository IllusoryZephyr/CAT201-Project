package com.novelnest.cat201project.models;

public class BookInfo {
    private int id;
    private String title;
    private String synopsis;
    private double price;


    public BookInfo(int id, String title, String synopsis, double price) {
        this.id = id;
        this.title = title;
        this.synopsis = synopsis;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getSynopsis() { return synopsis; }
}
