package com.example.cop4331projectdefinitive;

// Imports

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

/**
 * LogInActivity.java is the main activity class, due it being the first screen a user
 * will encounter when opening the application. Its matching XML layout file is
 * "activity_main.xml"
 *
 * This class handles the process of logging users in by querying the database and registering
 * users to the database.
 */

public class LogInActivity extends AppCompatActivity {

    // Variable Declaration
    EditText emailTxt;
    EditText passwordTxt;
    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set variables
        emailTxt = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        // Create employee account using hardcoded values for username and password
        CreateEmployeeAccount createEmployeeAccount = new CreateEmployeeAccount();
        createEmployeeAccount.execute();
    }

    // OnClick function for loginButton
    public void loginButtonOnClick (View view) {
        String userEmail = emailTxt.getText().toString();
        String userPassword = passwordTxt.getText().toString();

        // Checks email validity (contains @ sign) and ends function early with Toast message on failure
        if(validEmail(userEmail) == false) {
            Toast.makeText(this, "Login Failed: Please enter a valid email address", Toast.LENGTH_LONG).show();
            return;
        }
        // Checks password validity (length between 8 and 32 characters inclusive) and ends function
        // early with Toast message on failure
        else if(validPassword(userPassword) == false) {
            Toast.makeText(this, "Login Failed: Please enter a password between 8 and 32 characters.", Toast.LENGTH_LONG).show();
            return;
        }

        // Initiates CheckLoginEmail AsyncTask
        CheckLoginEmail checkLoginEmail = new CheckLoginEmail();
        checkLoginEmail.execute(userEmail);
    }

    // OnClick function for registerButton
    public void registerButtonOnClick (View view) {
        String userEmail = emailTxt.getText().toString();
        String userPassword = passwordTxt.getText().toString();

        // Checks email validity (contains @ sign) and ends function early with Toast message on failure
        if(validEmail(userEmail) == false) {
            Toast.makeText(this, "Registration Failed: Please enter a valid email address", Toast.LENGTH_LONG).show();
            return;
        }
        // Checks password validity (length between 8 and 32 characters inclusive) and ends function
        // early with Toast message on failure
        else if(validPassword(userPassword) == false) {
            Toast.makeText(this, "Registration Failed: Please enter a password between 8 and 32 characters.", Toast.LENGTH_LONG).show();
            return;
        }

        // Initiates CheckRegisterEmail AsyncTask
        CheckRegisterEmail checkRegisterEmail = new CheckRegisterEmail();
        checkRegisterEmail.execute(userEmail);
    }

    // Simple hash function that reads password as a string and sets a double equal to the ascii
    // values of the characters multiplied together
    public double passwordHash (String password) {
        char[] passwordCharArr = password.toCharArray();
        double hashedPass = passwordCharArr[0];
        for(int i = 1; i < password.length(); i++)
        {
            hashedPass = hashedPass * passwordCharArr[i];
        }

        return hashedPass;
    }

    public boolean validEmail(String email) {
        if(email.contains("@")) return true;
        else return false;
    }

    public boolean validPassword(String password) {
        if(password.length() >= 8 && password.length() <= 32) return true;
        else return false;
    }

    // AsyncTask that calls a singleton instance of the AppDatabase and checks if the email is already
    // registered under a different account
    private class CheckRegisterEmail extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "user-database").build();
            User foundUser = db.userDao().findByUsername(strings[0]);
            if(foundUser == null) return false;
            else return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean == true)
                Toast.makeText(LogInActivity.this,
                        "This email is already taken, please use another email.", Toast.LENGTH_LONG).show();
            else {
                RegisterNewUser registerUser = new RegisterNewUser();
                registerUser.execute();
            }
        }
    }

    // AsyncTask that calls a singleton instance of the AppDatabase and registers a new user
    private class RegisterNewUser extends AsyncTask<Void, Void, Void> {
        private String userName;
        private double password;

        @Override
        protected void onPreExecute() {
            userName = emailTxt.getText().toString();
            password = passwordHash(passwordTxt.getText().toString());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(LogInActivity.this,
                    "Registration Successful! Please log in using your email and password.",
                    Toast.LENGTH_LONG).show();
        }

        // Creates a new user and registers them to the database using a hard coded "customer"
        // type to improve security.
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "user-database").build();

            User newUser = new User(userName, password, "customer");
            db.userDao().insertAll(newUser);

            return null;
        }
    }

    // AsyncTask that calls a singleton instance of the AppDatabase and checks if the email is
    // associated with an existing account
    private class CheckLoginEmail extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean == true) {
                InitLogIn initLogIn = new InitLogIn();
                initLogIn.execute();
            }
            // Toast message displaying log in failure due to mismatched emails
            else {
                Toast.makeText(LogInActivity.this,
                        "Log In Failed: No users found with this email.",
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "user-database").build();
            User foundUser = db.userDao().findByUsername(strings[0]);
            Utils.getInstance().setCurrentUser(foundUser);
            if(foundUser == null) return false;
            else return true;
        }
    }

    // AsyncTask that calls a singleton instance of the AppDatabase and checks if the hashed password
    // from the passwordTxt matches the user found using userName
    private class InitLogIn extends AsyncTask<Void, Void, Boolean> {
        private String userName;
        private double password;

        @Override
        protected void onPreExecute() {
            userName = emailTxt.getText().toString();
            password = passwordHash(passwordTxt.getText().toString());
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                // If the found user has userType equal to "customer" they are directed to the MenuActivity
                if(Utils.getInstance().getCurrentUser().userType.compareTo("customer") == 0) {
                    Intent intent = new Intent(LogInActivity.this, MenuActivity.class);
                    startActivity(intent);
                }
                // If the found user has a userType that does not equal "customer", it must be an employee
                // and is directed to EmployeeActivity
                else {
                    Intent intent = new Intent(LogInActivity.this, EmployeeActivity.class);
                    startActivity(intent);
                }
            }
            // Toast message displaying log in failure due to mismatched passwords
            else {
                Toast.makeText(LogInActivity.this,
                        "Log In Failed: Passwords do not match.", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "user-database").build();
            User foundUser = db.userDao().findByUsername(userName);
            if(foundUser.password == password) {
                Utils.getInstance().setCurrentUser(foundUser);
                return true;
            }
            else return false;
        }
    }

    // AsyncTask called at application initialization that calls a singleton instance of the
    // AppDatabase and registers a hard coded employee account for improved security.
    public class CreateEmployeeAccount extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "user-database").build();
            User foundEmployee = db.userDao().findByUsername("employee@business.com");
            if (foundEmployee == null) {
                db.userDao().insertAll(new User("employee@business.com",
                        passwordHash("businesspassword"), "employee"));
            }

            return null;
        }
    }
}