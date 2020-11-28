package com.example.cop4331projectdefinitive;

// Imports
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.cop4331projectdefinitive.MenuRecyclerViewAdapter.FOOD_KEY;

/**
 * MenuActivity.java is the menu activity class and the primary driving class of the user functionality
 * of the application.Its matching XML layout file is "activity_menu.xml"
 *
 * This class handles four different RecyclerViews which are filled using the ArrayLists from Utils.java
 */

public class MenuActivity extends AppCompatActivity {

    // Variable Declaration
    private RecyclerView appetizerRecView;
    private RecyclerView entreeRecView;
    private RecyclerView dessertRecView;
    private RecyclerView drinkRecView;
    private EditText searchEditText;
    private MenuRecyclerViewAdapter appetizerRecViewAdapter;
    private MenuRecyclerViewAdapter entreeRecViewAdapter;
    private MenuRecyclerViewAdapter dessertRecViewAdapter;
    private MenuRecyclerViewAdapter drinkRecViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        searchEditText = findViewById(R.id.menuSearchView);

        // Initialization of the RecyclerView Adapters and setting their MenuItem ArrayLists
        appetizerRecViewAdapter = new MenuRecyclerViewAdapter(this);
        entreeRecViewAdapter = new MenuRecyclerViewAdapter(this);
        dessertRecViewAdapter = new MenuRecyclerViewAdapter(this);
        drinkRecViewAdapter = new MenuRecyclerViewAdapter(this);

        appetizerRecView = findViewById(R.id.appetizerRecView);
        appetizerRecView.setAdapter(appetizerRecViewAdapter);
        appetizerRecView.setLayoutManager(new LinearLayoutManager(this));
        appetizerRecViewAdapter.setMenuItems(Utils.getInstance().getAppetizerList());

        entreeRecView = findViewById(R.id.entreeRecView);
        entreeRecView.setAdapter(entreeRecViewAdapter);
        entreeRecView.setLayoutManager(new LinearLayoutManager(this));
        entreeRecViewAdapter.setMenuItems(Utils.getInstance().getEntreeList());

        dessertRecView = findViewById(R.id.dessertsRecView);
        dessertRecView.setAdapter(dessertRecViewAdapter);
        dessertRecView.setLayoutManager(new LinearLayoutManager(this));
        dessertRecViewAdapter.setMenuItems(Utils.getInstance().getDessertList());

        drinkRecView = findViewById(R.id.drinkRecView);
        drinkRecView.setAdapter(drinkRecViewAdapter);
        drinkRecView.setLayoutManager(new LinearLayoutManager(this));
        drinkRecViewAdapter.setMenuItems(Utils.getInstance().getDrinkList());
    }

    // On click function for the search button.
    public void onClickSearchButton (View view) {
        ArrayList<MenuItem> allItems = Utils.getInstance().getAllItems();
        boolean foundItem = false;

        // Searches the Utils allItems ArrayList for a MenuItem that has a name containing the text
        // from searchEditText. If a menuItem is found, directs to MenuItemActivity with the MenuItem
        // ID. Else, displays an Toast message stating no items were found.
        for(MenuItem myItem : allItems) {
            if (myItem.getItemName().toLowerCase().contains(searchEditText.getText().toString().toLowerCase())) {
                foundItem = true;
                Intent intent = new Intent(this, MenuItemActivity.class);
                intent.putExtra(FOOD_KEY, myItem.getId());
                startActivity(intent);
            }
        }

        if( !foundItem ) {
            Toast.makeText(this, "No items found with " + searchEditText.getText().toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // On click function for the log out button
    public void onClickLogOut (View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            // Clears the back stack on logging out, preventing the user from going back to the menu
            // without loggin in again
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MenuActivity.this, LogInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    // On click function that directs user to ViewOrderActivity
    public void onClickViewOrderStatus (View view) {
        Intent intent = new Intent(this, ViewOrderActivity.class);
        startActivity(intent);
    }

    // On click function that directs user to CartActivity
    public void onClickCart (View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

}