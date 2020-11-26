package com.example.cop4331projectdefinitive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewOrderActivity extends AppCompatActivity {

    Button orderRecButton, orderPrepButton, orderRedButton;
    TextView orderExistText;
    boolean orderExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        orderRecButton = findViewById(R.id.orderRecButton);
        orderPrepButton = findViewById(R.id.orderPrepButton);
        orderRedButton = findViewById(R.id.orderRedButton);
        orderExistText = findViewById(R.id.orderExistText);

        CheckStatus initialCheckStatus = new CheckStatus();
        initialCheckStatus.execute();

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

        //TODO: Clean up this crazy while loop by stuffing it into a background thread
        while(orderExists) {
            if (Utils.getInstance().getCurrentUser().order.getOrderStatus().compareTo("complete") == 0) {
                orderExistText.setVisibility(View.VISIBLE);
                orderExists = false;
                break;
            }

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
            break;
        }
    }

    private class CheckStatus extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (!aBoolean) {
                orderExistText.setVisibility(View.VISIBLE);
                orderExists = aBoolean;
            }
            else {
                orderExists = aBoolean;
            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "user-database").build();
            User foundUser = db.userDao().findByUsername(Utils.getInstance().getCurrentUser().userName);
            Utils.getInstance().setCurrentUser(foundUser);

            if(Utils.getInstance().getCurrentUser().order != null) return true;
            else return false;
        }
    }
}