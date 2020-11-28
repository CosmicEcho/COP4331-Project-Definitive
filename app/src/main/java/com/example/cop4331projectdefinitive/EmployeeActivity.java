package com.example.cop4331projectdefinitive;

// Imports
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

/**
 * EmployeeActivity serves as the driving function for all of the employee functionality in the application.
 * Employees that log in using the designated login information are directed to this activity and can only
 * access this activity. Employees can view all incoming orders on initialization of the view, as well
 * as advance the order status using a button on the individual CardViews. Upon advancing an order to the
 * Picked Up status, the order is removed from the list and the user is updated to having a null order.
 * The associated XML file is activity_employee.
 */

// EmployeeActivity utilizes a custom interface AsyncResponse in order to successfully populate the RecyclerView
public class EmployeeActivity extends AppCompatActivity implements AsyncResponse {

    // Variable Declaration
    RecyclerView empRecView;
    EmployeeParentRecAdapter empParentRecAdapter;
    Button empLogOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        // Variable initialization
        empRecView = findViewById(R.id.employeeRecView);
        empLogOutButton = findViewById(R.id.empLogButton);

        InitRecView initRecView = new InitRecView();
        initRecView.delegate = this;
        initRecView.execute();

    }

    // On click function that logs the employee out and ensures that they cannot access the activity
    // without logging in again.
    public void onClickEmpLogOutButton(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EmployeeActivity.this, LogInActivity.class);
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

    // AsyncTask that pulls users that have active orders from the AppDatabase and passes them to onTaskCompleted
    public class InitRecView extends AsyncTask<Void, Void, List<User>> {
        public AsyncResponse delegate = null;

        @Override
        protected void onPostExecute(List<User> users) {
            delegate.onTaskCompleted(users);
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "user-database").build();
            return db.userDao().findAllWithOrders("null");
        }
    }

    // AsyncResponse override that creates the EmployeeParentRecAdapter and sets its userList to
    // the pulled list from AppDatabase
    @Override
    public void onTaskCompleted(List<User> userList) {
        empParentRecAdapter = new EmployeeParentRecAdapter(EmployeeActivity.this);
        empRecView.setAdapter(empParentRecAdapter);
        empRecView.setLayoutManager(new LinearLayoutManager(EmployeeActivity.this));
        empParentRecAdapter.setUserList(userList);
    }
}