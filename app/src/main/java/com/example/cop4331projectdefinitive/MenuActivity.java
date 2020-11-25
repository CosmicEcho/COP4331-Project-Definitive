package com.example.cop4331projectdefinitive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    ArrayList<MenuItem> appetizerList;
    ArrayList<MenuItem> entreeList;
    ArrayList<MenuItem> dessertList;
    ArrayList<MenuItem> drinkList;
    Cart userCart;

    private RecyclerView appetizerRecView;
    private RecyclerView entreeRecView;
    private RecyclerView dessertRecView;
    private RecyclerView drinkRecView;
    private MenuRecyclerViewAdapter appetizerRecViewAdapter;
    private MenuRecyclerViewAdapter entreeRecViewAdapter;
    private MenuRecyclerViewAdapter dessertRecViewAdapter;
    private MenuRecyclerViewAdapter drinkRecViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        appetizerRecViewAdapter = new MenuRecyclerViewAdapter(this);
        entreeRecViewAdapter = new MenuRecyclerViewAdapter(this);
        dessertRecViewAdapter = new MenuRecyclerViewAdapter(this);
        drinkRecViewAdapter = new MenuRecyclerViewAdapter(this);
        initMenu();

        appetizerRecView = findViewById(R.id.appetizerRecView);
        appetizerRecView.setAdapter(appetizerRecViewAdapter);
        appetizerRecView.setLayoutManager(new LinearLayoutManager(this));
        appetizerRecViewAdapter.setMenuItems(appetizerList);

        entreeRecView = findViewById(R.id.entreeRecView);
        entreeRecView.setAdapter(entreeRecViewAdapter);
        entreeRecView.setLayoutManager(new LinearLayoutManager(this));
        entreeRecViewAdapter.setMenuItems(entreeList);

        dessertRecView = findViewById(R.id.dessertsRecView);
        dessertRecView.setAdapter(dessertRecViewAdapter);
        dessertRecView.setLayoutManager(new LinearLayoutManager(this));
        dessertRecViewAdapter.setMenuItems(dessertList);

        drinkRecView = findViewById(R.id.drinkRecView);
        drinkRecView.setAdapter(drinkRecViewAdapter);
        drinkRecView.setLayoutManager(new LinearLayoutManager(this));
        drinkRecViewAdapter.setMenuItems(drinkList);
    }

    public void initMenu() {
        String nameContainer;
        String descriptionContainer;
        ArrayList<String> detailContainer = new ArrayList<>();
        double priceContainer;

        appetizerList = new ArrayList<>();

        nameContainer = "Mozzarella Sticks";
        descriptionContainer = "Delicious fried mozzarella sticks served with our signature marinara sauce.";
        detailContainer.add("No marinara");
        priceContainer = 4.99;
        appetizerList.add(new MenuItem(nameContainer, descriptionContainer, detailContainer, priceContainer));
        detailContainer.clear();

        nameContainer = "Cheese Curds";
        descriptionContainer = "Delicious fried cheddar cheese curds served with our signature marinara sauce.";
        detailContainer.add("No marinara");
        priceContainer = 3.99;
        appetizerList.add(new MenuItem(nameContainer, descriptionContainer, detailContainer, priceContainer));
        detailContainer.clear();


        entreeList = new ArrayList<>();

        nameContainer = "Sample Spaghetti";
        descriptionContainer = "Perfectly nondescript spaghetti with your favorite generic flavoring!";
        detailContainer.add("Light sauce");
        detailContainer.add("Heavy sauce");
        detailContainer.add("No sauce");
        detailContainer.add("No noodles");
        priceContainer = 9.99;
        entreeList.add(new MenuItem(nameContainer, descriptionContainer, detailContainer, priceContainer));
        detailContainer.clear();

        nameContainer = "Deluxe Grilled Cheese";
        descriptionContainer = "A new spin on an old classic. Served with crinkle cut french fries.";
        detailContainer.add("Extra cheese");
        detailContainer.add("No fries");
        priceContainer = 7.99;
        entreeList.add(new MenuItem(nameContainer, descriptionContainer, detailContainer, priceContainer));
        detailContainer.clear();


        dessertList = new ArrayList<>();

        nameContainer = "Strawberry Cheesecake";
        descriptionContainer = "Classic cheesecake topped with strawberry glaze.";
        detailContainer.add("No glaze");
        priceContainer = 4.99;
        dessertList.add(new MenuItem(nameContainer, descriptionContainer, detailContainer, priceContainer));
        detailContainer.clear();

        nameContainer = "Chocolate Cake";
        descriptionContainer = "Rich and flavorful chocolate cake. Served with vanilla ice cream.";
        detailContainer.add("No ice cream");
        priceContainer = 5.99;
        dessertList.add(new MenuItem(nameContainer, descriptionContainer, detailContainer, priceContainer));
        detailContainer.clear();


        drinkList = new ArrayList<>();

        nameContainer = "Water";
        descriptionContainer = "Plain old water, straight from the tap.";
        detailContainer.add("No ice");
        priceContainer = 0.00;
        drinkList.add(new MenuItem(nameContainer, descriptionContainer, detailContainer, priceContainer));
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
        drinkList.add(new MenuItem(nameContainer, descriptionContainer, detailContainer, priceContainer));
        detailContainer.clear();

    }
}