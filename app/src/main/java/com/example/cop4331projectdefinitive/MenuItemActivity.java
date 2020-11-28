package com.example.cop4331projectdefinitive;

// Imports

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.cop4331projectdefinitive.MenuRecyclerViewAdapter.FOOD_KEY;

/**
 * MenuItemActivity is one of the only dynamically filled activities in the application. It receives a
 * MenuItem id from either the search functionality in MenuActivity or the onClickListener from MenuRecyclerViewAdapter.
 * The MenuItemActivity is then filled with information from the allItems ArrayList in Utils.
 */

public class MenuItemActivity extends AppCompatActivity {

    // Variable Declaration
    private ItemRecyclerViewAdapter itemRecViewAdapter;
    private MenuItem selectedItem;
    private ArrayList<String> selectedItemDetails;
    private RecyclerView itemDetailRecView;
    private TextView foodNameTextView, foodDescTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        // Creates a receiving intent and checks if it is null. If it is not null,
        // checks the incoming int and sets the selectedItem to the MenuItem with the associated ID
        Intent intent = getIntent();
        if(intent != null) {
            int itemId = intent.getIntExtra(FOOD_KEY, -1);
            if(itemId > -1) {
                selectedItem = Utils.getInstance().findByID(itemId);
                selectedItemDetails = selectedItem.getItemDetail();
            }
            else {
                selectedItem = null;
                selectedItemDetails = null;
            }
        }

        // Initializing the rest of the views based on the selectedItem
        foodNameTextView = findViewById(R.id.foodNameTxtView);
        foodDescTextView = findViewById(R.id.foodDescTxtView);

        foodNameTextView.setText(selectedItem.getItemName());
        foodDescTextView.setText(selectedItem.getItemDescription());

        itemRecViewAdapter = new ItemRecyclerViewAdapter(this);
        itemDetailRecView = findViewById(R.id.itemDetailRecView);
        itemDetailRecView.setAdapter(itemRecViewAdapter);
        itemDetailRecView.setLayoutManager(new LinearLayoutManager(this));
        itemRecViewAdapter.setDetailList(selectedItemDetails);

    }

    // On click function that adds a new item to the Utils cart
    public void onClickAddToCart(View view) {
        ArrayList<String> chosenDetails = itemRecViewAdapter.returnSelected();
        MenuItem newItem = new MenuItem(selectedItem.getId(), selectedItem.getItemName(),
                selectedItem.getItemDescription(), selectedItemDetails, chosenDetails, selectedItem.getItemPrice());

        Utils.getInstance().getCart().addItem(newItem);

        Toast.makeText(this, "Added " + newItem.getItemName() + " to cart.", Toast.LENGTH_SHORT).show();
    }
}