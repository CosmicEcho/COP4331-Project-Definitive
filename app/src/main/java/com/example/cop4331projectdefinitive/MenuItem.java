package com.example.cop4331projectdefinitive;

// Imports
import java.util.ArrayList;

/**
 * MenuItem class that is used to represent items on the restaurant menu. Includes a unique int id,
 * as well as two different constructors. The primary constructor is used for Utils initialization while
 * the secondary constructor is used to populate the Utils cart.
 */

public class MenuItem {
    private int id;
    private String itemName;
    private String itemDescription;
    private ArrayList<String> itemDetail;
    public ArrayList<String> appliedDetail;
    private double itemPrice;

    // Primary constructor for creating the restaurant MenuItem ArrayLists
    public MenuItem(int id, String itemName, String itemDescription, ArrayList<String> itemDetail, double itemPrice) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemDetail = itemDetail;
        this.appliedDetail = new ArrayList<>();
        this.itemPrice = itemPrice;
    }

    // Secondary constructor for creating the user cart contents
    public MenuItem(int id, String itemName, String itemDescription, ArrayList<String> itemDetail, ArrayList<String> appliedDetail, double itemPrice) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemDetail = itemDetail;
        this.appliedDetail = appliedDetail;
        this.itemPrice = itemPrice;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public ArrayList<String> getItemDetail() {
        return itemDetail;
    }

    public int getId() {
        return id;
    }
}
