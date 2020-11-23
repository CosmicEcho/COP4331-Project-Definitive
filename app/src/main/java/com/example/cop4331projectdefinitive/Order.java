package com.example.cop4331projectdefinitive;

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
