package com.novelnest.cat201project.models;


//Jeffry built one for , needed for cart

public class Book {
    private int id;
    private String title;
    private double price;

    public Book(int id, String title, double price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public int getId() {return id;}
    public String getTitle() {return title;}
    public double getPrice() {return price;}
}
