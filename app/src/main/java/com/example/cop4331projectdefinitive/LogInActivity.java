package com.example.cop4331projectdefinitive;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * LogInActivity.java is considered the main activity class, due it being the first screen a user
 * will encounter when first opening the application. As such, its matching XML layout file is
 * "activity_main.xml"
 *
 * This class handles the process of logging users in by querying the database and resgistering
 * users to the database.
 */

public class LogInActivity extends AppCompatActivity {

    EditText emailTxt;
    EditText passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailTxt = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
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

        int hashedPass = passwordHash(userPassword);
        //TODO: Actually make a working database query lmao
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

        int hashedPass = passwordHash(userPassword);

        //TODO: Actually make a working database register action lmao!!!!

        Toast.makeText(this, "Registration Successful! Please log in using your email and password.", Toast.LENGTH_LONG).show();
    }

    public int passwordHash (String password) {
        char[] passwordCharArr = password.toCharArray();
        int hashedPass = passwordCharArr[0];
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
}