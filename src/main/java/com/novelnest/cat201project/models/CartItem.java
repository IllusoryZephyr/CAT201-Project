package com.novelnest.cat201project.models;

public class CartItem {
    private BookInfo book;
    private int quantity;

    public CartItem(BookInfo book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public BookInfo getBook() {
        return book;
    }
    public void setBook(BookInfo book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double    getTotalPrice() {
        return book.getPrice()*quantity;
    }
}
