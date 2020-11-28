package com.example.cop4331projectdefinitive;

// Imports
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

/**
 * CartActivity class that handles the user's cart functionality. Contains constants for the order statuses
 * that are visible to all types of users. In addition, CartActivity grabs the Utils cart and dynamically
 * populates nested RecyclerViews to display all items with customizations, the tax, and the total cost.
 * Associated XML file is activity_cart.xml.
 */

public class CartActivity extends AppCompatActivity {

    // Order status constants
    public static final String RECEIVED_TEXT = "received";
    public static final String PREPARING_TEXT = "prep";
    public static final String READY_TEXT = "ready";

    // Variable Declaration
    RecyclerView cartRecView;
    CartParentRecAdapter cartRecViewAdapter;
    TextView cartTaxText, cartTotalCostText;
    Button cartSubmitOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Variable Initialization
        cartTaxText = findViewById(R.id.cartTaxText);
        cartTotalCostText = findViewById(R.id.cartTotalCostText);
        cartSubmitOrder = findViewById(R.id.cartSubmitButton);

        cartRecViewAdapter = new CartParentRecAdapter(this);
        cartRecView = findViewById(R.id.cartRecView);
        cartRecView.setAdapter(cartRecViewAdapter);
        cartRecView.setLayoutManager(new LinearLayoutManager(this));
        cartRecViewAdapter.setCartItems(Utils.getInstance().getCart().getCartContents());

        // Calculation of tax and total cost individually using 6.5% sales tax
        double cartTax = Utils.getInstance().getCart().getTotalCartCost() * 0.065;
        double totalCost = Utils.getInstance().getCart().getTotalCartCost() * 1.065;

        // Set tax and total cost texts to the cartTax and totalCost respectively, limited to 2 decimal places
        cartTaxText.setText(String.format("Tax: $ %.2f",cartTax));
        cartTotalCostText.setText(String.format("Total Cost: $ %.2f",totalCost));

        // Disable sending order functionality of cart has no items or if user already has a standing order
        if(Utils.getInstance().getCart().getCartContents().size() < 1) {
            cartSubmitOrder.setEnabled(false);
        }
        if(Utils.getInstance().getCurrentUser().order != null) {
            cartSubmitOrder.setEnabled(false);
            Toast.makeText(this,
                    "You already have a standing order! If you want to make a change, please call 123-456-7890.",
                    Toast.LENGTH_LONG).show();
        }

        // On click function for cartSubmitOrder button, which in turn calls SubmitOrder AsyncTask
        cartSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setMessage("Submit Order?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SubmitOrder submitOrder = new SubmitOrder();
                        submitOrder.execute();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });
    }

    // AsyncTask that calls a singleton instance of the AppDatabase and updates the current user's
    // order field to contain the order created in onPreExecute
    private class SubmitOrder extends AsyncTask<Void, Void, Void> {
        // Sets Utils currentUser order to a new Order containing the cart and RECEIVED_TEXT constant
        @Override
        protected void onPreExecute() {
            Utils.getInstance().getCurrentUser().order = new Order(Utils.getInstance().getCart(), RECEIVED_TEXT);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "user-database").build();
            db.userDao().updateUser(Utils.getInstance().getCurrentUser());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(CartActivity.this, ViewOrderActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}