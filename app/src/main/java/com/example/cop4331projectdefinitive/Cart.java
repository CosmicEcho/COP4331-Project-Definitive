package com.example.cop4331projectdefinitive;

import java.util.ArrayList;

public class Cart {
    private ArrayList<MenuItem> cartContents;
    private double totalCartCost;

    public Cart() {
        this.cartContents = new ArrayList<MenuItem>();
        this.totalCartCost = 0.0;
    }

    public ArrayList<MenuItem> getCartContents() {
        return cartContents;
    }

    public void addItem(MenuItem menuItem) {
        cartContents.add(menuItem);
        updateCost();
    }

    public void removeItem(MenuItem menuItem) {
        cartContents.remove(menuItem);
        updateCost();
    }

    public void updateCost() {
        double cost = 0;

        for(MenuItem myMenuItem : cartContents) {
            cost += myMenuItem.getItemPrice();
        }

        setTotalCartCost(cost);
    }

    public double getTotalCartCost() {
        return totalCartCost;
    }

    public void setTotalCartCost(double totalCartCost) {
        this.totalCartCost = totalCartCost;
    }
}
