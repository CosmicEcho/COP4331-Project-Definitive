package com.example.cop4331projectdefinitive;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CartActivity extends AppCompatActivity {

    public static final String RECEIVED_TEXT = "received";
    public static final String PREPARING_TEXT = "prep";
    public static final String READY_TEXT = "ready";

    RecyclerView cartRecView;
    CartRecViewAdapter cartRecViewAdapter;
    TextView cartTaxText, cartTotalCostText;
    Button cartSubmitOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartTaxText = findViewById(R.id.cartTaxText);
        cartTotalCostText = findViewById(R.id.cartTotalCostText);
        cartSubmitOrder = findViewById(R.id.cartSubmitButton);

        cartRecViewAdapter = new CartRecViewAdapter(this);
        cartRecView = findViewById(R.id.cartRecView);
        cartRecView.setAdapter(cartRecViewAdapter);
        cartRecView.setLayoutManager(new LinearLayoutManager(this));
        cartRecViewAdapter.setCartItems(Utils.getInstance().getCart().getCartContents());

        double cartTax = Utils.getInstance().getCart().getTotalCartCost() * 0.065;
        double totalCost = Utils.getInstance().getCart().getTotalCartCost() * 1.065;

        cartTaxText.setText(String.format("Tax: $ %.2f",cartTax));
        cartTotalCostText.setText(String.format("Total Cost: $ %.2f",totalCost));

        if(Utils.getInstance().getCart().getCartContents().size() < 1) {
            cartSubmitOrder.setEnabled(false);
        }

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

    private class SubmitOrder extends AsyncTask<Void, Void, Void> {
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