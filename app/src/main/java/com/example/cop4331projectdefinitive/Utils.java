package com.example.cop4331projectdefinitive;

// Imports

import java.util.ArrayList;

/** Utils.java is a supporting class that contains static ArrayLists of individual sections of the
 * restaurant menu, as well as an ArrayList that contains all of the items together. In addition,
 * the class supports a static currentUser and a static cart.
 *
 * The static variables are used to emulate global variables across the application, though
 * only the cart and currentUser are altered during runtime.
 */

public class Utils {
    private static Utils instance;
    private static ArrayList<MenuItem> appetizerList;
    private static ArrayList<MenuItem> entreeList;
    private static ArrayList<MenuItem> dessertList;
    private static ArrayList<MenuItem> drinkList;
    private static ArrayList<MenuItem> allItems;
    private static User currentUser;
    private static Cart cart;

    private Utils() {
        initMenu();
        cart = new Cart();
    }

    public static synchronized Utils getInstance() {
        if (null !=instance) return instance;
        else {
            instance = new Utils();
            return instance;
        }
    }

    public static ArrayList<MenuItem> getAppetizerList() {
        return appetizerList;
    }

    public static ArrayList<MenuItem> getEntreeList() {
        return entreeList;
    }

    public static ArrayList<MenuItem> getDessertList() {
        return dessertList;
    }

    public static ArrayList<MenuItem> getDrinkList() {
        return drinkList;
    }

    public static ArrayList<MenuItem> getAllItems() {
        return allItems;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Utils.currentUser = currentUser;
    }

    public static Cart getCart() {
        return cart;
    }

    public static MenuItem findByID(int id) {
        for (MenuItem myItem : allItems) {
            if(myItem.getId() == id) return myItem;
        }
        return null;
    }

    // Large initialization of the menu items
    private void initMenu() {
        String nameContainer;
        String descriptionContainer;
        ArrayList<String> detailContainer = new ArrayList<>();
        double priceContainer;

        allItems = new ArrayList<>();

        appetizerList = new ArrayList<>();

        nameContainer = "Mozzarella Sticks";
        descriptionContainer = "Delicious fried mozzarella sticks served with our signature marinara sauce.";
        detailContainer.add("No marinara");
        priceContainer = 4.99;
        appetizerList.add(new MenuItem(0, nameContainer, descriptionContainer, new ArrayList<String>(detailContainer), priceContainer));
        allItems.add(new MenuItem(0, nameContainer, descriptionContainer, new ArrayList<String>(detailContainer), priceContainer));
        detailContainer.clear();

        nameContainer = "Cheese Curds";
        descriptionContainer = "Delicious fried cheddar cheese curds served with our signature marinara sauce.";
        detailContainer.add("No marinara");
        priceContainer = 3.99;
        appetizerList.add(new MenuItem(1, nameContainer, descriptionContainer, new ArrayList<String>(detailContainer), priceContainer));
        allItems.add(new MenuItem(1, nameContainer, descriptionContainer, new ArrayList<String>(detailContainer), priceContainer));
        detailContainer.clear();


        entreeList = new ArrayList<>();

        nameContainer = "Sample Spaghetti";
        descriptionContainer = "Perfectly nondescript spaghetti with your favorite generic flavoring!";
        detailContainer.add("Light sauce");
        detailContainer.add("Heavy sauce");
        detailContainer.add("No sauce");
        detailContainer.add("No noodles");
        priceContainer = 9.99;
        entreeList.add(new MenuItem(2, nameContainer, descriptionContainer, new ArrayList<String>(detailContainer), priceContainer));
        allItems.add(new MenuItem(2, nameContainer, descriptionContainer, new ArrayList<String>(detailContainer), priceContainer));
        detailContainer.clear();

        nameContainer = "Deluxe Grilled Cheese";
        descriptionContainer = "A new spin on an old classic. Served with crinkle cut french fries.";
        detailContainer.add("Extra cheese");
        detailContainer.add("No fries");
        priceContainer = 7.99;
        entreeList.add(new MenuItem(3, nameContainer, descriptionContainer, new ArrayList<String>(detailContainer), priceContainer));
        allItems.add(new MenuItem(3, nameContainer, descriptionContainer, new ArrayList<String>(detailContainer), priceContainer));
        detailContainer.clear();


        dessertList = new ArrayList<>();

        nameContainer = "Strawberry Cheesecake";
        descriptionContainer = "Classic cheesecake topped with strawberry glaze.";
        detailContainer.add("No glaze");
        priceContainer = 4.99;
        dessertList.add(new MenuItem(4, nameContainer, descriptionContainer, new ArrayList<String>(detailContainer), priceContainer));
        allItems.add(new MenuItem(4, nameContainer, descriptionContainer, new ArrayList<String>(detailContainer), priceContainer));
        detailContainer.clear();

        nameContainer = "Chocolate Cake";
        descriptionContainer = "Rich and flavorful chocolate cake. Served with vanilla ice cream.";
        detailContainer.add("No ice cream");
        priceContainer = 5.99;
        dessertList.add(new MenuItem(5, nameContainer, descriptionContainer, new ArrayList<String>(detailContainer), priceContainer));
        allItems.add(new MenuItem(5, nameContainer, descriptionContainer, new ArrayList<String>(detailContainer), priceContainer));
        detailContainer.clear();


        drinkList = new ArrayList<>();

        nameContainer = "Water";
        descriptionContainer = "Plain old water, straight from the tap.";
        detailContainer.add("No ice");
        priceContainer = 0.00;
        drinkList.add(new MenuItem(6, nameContainer, descriptionContainer, new ArrayList<String>(detailContainer), priceContainer));
        allItems.add(new MenuItem(6, nameContainer, descriptionContainer, new ArrayList<String>(detailContainer), priceContainer));
        detailContainer.clear();

        nameContainer = "Soft Drink";
        descriptionContainer = "Choose from a wide lineup of Not Coca Cola products! Please select only one drink, multiple selections will default to Not Coca Cola Classic";
        detailContainer.add("Not Coca Cola Classic");
        detailContainer.add("Not Coca Cola Diet");
        detailContainer.add("Not Sprite");
        detailContainer.add("Not Sprite Diet");
        detailContainer.add("Generic Root Beer");
        detailContainer.add("No ice");
        priceContainer = 2.49;
        drinkList.add(new MenuItem(7, nameContainer, descriptionContainer, new ArrayList<String>(detailContainer), priceContainer));
        allItems.add(new MenuItem(7, nameContainer, descriptionContainer, new ArrayList<String>(detailContainer), priceContainer));
        detailContainer.clear();

    }
}
