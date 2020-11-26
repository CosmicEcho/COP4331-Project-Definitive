package com.example.cop4331projectdefinitive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.cop4331projectdefinitive.MenuRecyclerViewAdapter.FOOD_KEY;

public class MenuItemActivity extends AppCompatActivity {

    private ItemRecyclerViewAdapter itemRecViewAdapter;
    private MenuItem selectedItem;
    private ArrayList<String> selectedItemDetails;
    private RecyclerView itemDetailRecView;
    private TextView foodNameTextView, foodDescTextView;
    private Button addToCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

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

        addToCartButton = findViewById(R.id.toCartButton);
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

    public void onClickAddToCart(View view) {
        ArrayList<String> chosenDetails = itemRecViewAdapter.returnSelected();
        MenuItem newItem = new MenuItem(selectedItem.getId(), selectedItem.getItemName(),
                selectedItem.getItemDescription(), selectedItemDetails, chosenDetails, selectedItem.getItemPrice());

        Utils.getInstance().getCart().addItem(newItem);

        Toast.makeText(this, "Added " + newItem.getItemName() + " to cart.", Toast.LENGTH_SHORT).show();
    }
}