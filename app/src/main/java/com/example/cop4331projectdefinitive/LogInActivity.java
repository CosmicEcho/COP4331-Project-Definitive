package com.example.cop4331projectdefinitive;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * LogInActivity.java is considered the main activity class, due it being the first screen a user
 * will encounter when first opening the application. As such, its matching XML layout file is
 * "activity_main.xml"
 *
 * This class handles the process of logging users in by querying the database and registering
 * users to the database.
 */

public class LogInActivity extends AppCompatActivity {

    EditText emailTxt;
    EditText passwordTxt;
    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailTxt = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
    }

    public void loginButtonOnClick (View view) {
        String userEmail = emailTxt.getText().toString();
        String userPassword = passwordTxt.getText().toString();

        if(validEmail(userEmail) == false) {
            Toast.makeText(this, "Login Failed: Please enter a valid email address", Toast.LENGTH_LONG).show();
            return;
        }
        else if(validPassword(userPassword) == false) {
            Toast.makeText(this, "Login Failed: Please enter a password between 8 and 32 characters.", Toast.LENGTH_LONG).show();
            return;
        }

        CheckLoginEmail checkLoginEmail = new CheckLoginEmail();
        checkLoginEmail.execute(userEmail);
    }

    public void registerButtonOnClick (View view) {
        String userEmail = emailTxt.getText().toString();
        String userPassword = passwordTxt.getText().toString();

        if(validEmail(userEmail) == false) {
            Toast.makeText(this, "Registration Failed: Please enter a valid email address", Toast.LENGTH_LONG).show();
            return;
        }
        else if(validPassword(userPassword) == false) {
            Toast.makeText(this, "Registration Failed: Please enter a password between 8 and 32 characters.", Toast.LENGTH_LONG).show();
            return;
        }

        CheckRegisterEmail checkRegisterEmail = new CheckRegisterEmail();
        checkRegisterEmail.execute(userEmail);
    }

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

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "user-database").build();

            User newUser = new User(userName, password, "customer");
            db.userDao().insertAll(newUser);

            return null;
        }
    }

    private class CheckLoginEmail extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean == true) {
                InitLogIn initLogIn = new InitLogIn();
                initLogIn.execute();
            }
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
            if(foundUser == null) return false;
            else return true;
        }
    }

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
                Intent intent = new Intent(LogInActivity.this, MenuActivity.class);
                startActivity(intent);
            }
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
}