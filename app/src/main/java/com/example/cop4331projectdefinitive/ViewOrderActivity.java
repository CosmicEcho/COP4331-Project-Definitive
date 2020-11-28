package com.example.cop4331projectdefinitive;

// Imports

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

/**
 * ViewOrderActivity serves as the customer functionality of viewing orders in the system. Users can
 * only see the order that they have submitted, and order status is displayed as buttons that are
 * only enabled when an order is present in the Utils currentUser variable.
 * Its associated XML file is activity_view_order.
 */
public class ViewOrderActivity extends AppCompatActivity {

    // Variable Declaration
    Button orderRecButton, orderPrepButton, orderRedButton;
    TextView orderExistText;
    boolean orderExists;
    CheckStatus initialCheckStatus;

    // Overrides the onBackPressed functionality to always direct to MenuActivity
    // In addition, sets the AsyncTask loop that checks order status to cancel
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        initialCheckStatus.cancel(true);
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        // Initialize Variables
        orderRecButton = findViewById(R.id.orderRecButton);
        orderPrepButton = findViewById(R.id.orderPrepButton);
        orderRedButton = findViewById(R.id.orderRedButton);
        orderExistText = findViewById(R.id.orderExistText);

        // Initialization of the order status buttons
        // If an order does not exist (null) all buttons are disabled sets orderExistText to visible
        if(Utils.getInstance().getCurrentUser().order != null) {

            switch (Utils.getInstance().getCurrentUser().order.getOrderStatus()) {
                case "received":
                    orderRecButton.setEnabled(true);
                    orderPrepButton.setEnabled(false);
                    orderRedButton.setEnabled(false);
                    break;
                case "prep":
                    orderRecButton.setEnabled(false);
                    orderPrepButton.setEnabled(true);
                    orderRedButton.setEnabled(false);
                    break;
                case "ready":
                    orderRecButton.setEnabled(false);
                    orderPrepButton.setEnabled(false);
                    orderRedButton.setEnabled(true);
                    break;
                default:
                    orderRecButton.setEnabled(false);
                    orderPrepButton.setEnabled(false);
                    orderRedButton.setEnabled(false);
                    break;
            }
        }
        else {
            orderExistText.setVisibility(View.VISIBLE);
            orderRecButton.setEnabled(false);
            orderPrepButton.setEnabled(false);
            orderRedButton.setEnabled(false);
        }

        initialCheckStatus = new CheckStatus();
        initialCheckStatus.execute();

    }

    // AsyncTask that loops until the order status from querying the database changes
    private class CheckStatus extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (!aBoolean) {
                orderExistText.setVisibility(View.VISIBLE);
                orderExists = aBoolean;
                return;
            }
            else {
                orderExists = aBoolean;
                switch (Utils.getInstance().getCurrentUser().order.getOrderStatus()) {
                    case "received":
                        orderRecButton.setEnabled(true);
                        orderPrepButton.setEnabled(false);
                        orderRedButton.setEnabled(false);
                        break;
                    case "prep":
                        orderRecButton.setEnabled(false);
                        orderPrepButton.setEnabled(true);
                        orderRedButton.setEnabled(false);
                        break;
                    case "ready":
                        orderRecButton.setEnabled(false);
                        orderPrepButton.setEnabled(false);
                        orderRedButton.setEnabled(true);
                        break;
                    default:
                        orderRecButton.setEnabled(false);
                        orderPrepButton.setEnabled(false);
                        orderRedButton.setEnabled(false);
                        break;
                }
                CheckStatus checkStatus = new CheckStatus();
                checkStatus.execute();
            }
        }

        // First checks to see if order exists to avoid referencing null, in case logic from
        // the onCreate method fails. Then loops until the stored order status differs from queried
        // order status. If the new order is null, returns false, else returns true and starts loops again via
        // onPostExecute().
        @Override
        protected Boolean doInBackground(Void... voids) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "user-database").build();
            User foundUser = db.userDao().findByUsername(Utils.getInstance().getCurrentUser().userName);

            String currentOrderStatus;
            if(foundUser.order != null) {
                currentOrderStatus = foundUser.order.getOrderStatus();
            }
            else return false;

            while(currentOrderStatus.compareTo(foundUser.order.getOrderStatus()) == 0) {
                foundUser = db.userDao().findByUsername(foundUser.userName);
                Utils.getInstance().setCurrentUser(foundUser);
                if (foundUser.order == null) return false;
                if(isCancelled()) {
                    break;
                }
            }

            if(Utils.getInstance().getCurrentUser().order != null) return true;
            else return false;
        }
    }
}