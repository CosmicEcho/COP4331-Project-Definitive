package com.example.cop4331projectdefinitive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LogInActivity extends AppCompatActivity {

    final Button loginButton = findViewById(R.id.loginButton);
    final EditText emailEditTxt = findViewById(R.id.emailTxt);
    final EditText password = findViewById(R.id.passwordTxt);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}