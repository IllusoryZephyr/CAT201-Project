package com.novelnest.cat201project.models;

public class BookInfo {
    private int id;
    private String title;
    private String synopsis;
    private double price;
    private int quantity;
    private String imagePath;

    public BookInfo(int id, String title, String synopsis, double price, int quantity, String imagePath) {
        this.id = id;
        this.title = title;
        this.synopsis = synopsis;
        this.price = price;
        this.quantity = quantity;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
