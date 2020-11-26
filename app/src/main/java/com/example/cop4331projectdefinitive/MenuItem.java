package com.example.cop4331projectdefinitive;

import java.util.ArrayList;

public class MenuItem {
    private int id;
    private String itemName;
    private String itemDescription;
    private ArrayList<String> itemDetail;
    public ArrayList<String> appliedDetail;
    private double itemPrice;

    public MenuItem(int id, String itemName, String itemDescription, ArrayList<String> itemDetail, double itemPrice) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemDetail = itemDetail;
        this.appliedDetail = new ArrayList<String>();
        this.itemPrice = itemPrice;
    }

    public MenuItem(int id, String itemName, String itemDescription, ArrayList<String> itemDetail, ArrayList<String> appliedDetail, double itemPrice) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemDetail = itemDetail;
        this.appliedDetail = appliedDetail;
        this.itemPrice = itemPrice;
    }

    public void addToAppliedDetail(String detail) {
        appliedDetail.add(detail);
    }

    public void removeAppliedDetail(String detail) {
        appliedDetail.remove(detail);
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
