package com.example.cop4331projectdefinitive;

/**
 * Order class that represents the user's order to the restaurant. Contains a Cart and a String that
 * holds the order's status.
 */

public class Order {
    public Cart storedCart;
    public String orderStatus;

    public Order(Cart storedCart, String orderStatus) {
        this.storedCart = storedCart;
        this.orderStatus = orderStatus;
    }

    public Cart getStoredCart() {
        return storedCart;
    }

    public void setStoredCart(Cart storedCart) {
        this.storedCart = storedCart;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
