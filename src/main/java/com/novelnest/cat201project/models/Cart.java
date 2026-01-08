package com.novelnest.cat201project.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart(){
        this.items = new ArrayList<>();
    }

    public void addItem(BookInfo book, int qty){
        for(CartItem item : items){
            if(item.getBook().getId() == book.getId()){
                item.setQuantity(item.getQuantity()+qty);
                return;
            }
        }
        items.add(new CartItem(book, qty));
    }
    public List<CartItem> getItems(){return items;}

    public void removeItem(int bookId) {
        // removeIf is a shortcut that removes any item where the condition is true
        items.removeIf(item -> item.getBook().getId() == bookId);
    }

    public double grandTotal(){
        double total = 0;
        for(CartItem item : items){
            total += item.getTotalPrice();
        }
        return total;
    }
    public void clear() { items.clear(); }
}

