package com.novelnest.cat201project.models;

public class BookInfo {
    private String title;
    private double price;
    private String author;
    private String imagePath;
    private int quantity;
    private String category;

    public BookInfo(String category, int quantity, String imagePath, String author, double price, String title) {
        this.category = category;
        this.quantity = quantity;
        this.imagePath = imagePath;
        this.author = author;
        this.price = price;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}





