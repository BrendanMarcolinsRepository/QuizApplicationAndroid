package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class Login extends AppCompatActivity
{
    //all variables needed
    private TextView username,password,reset,newAccount;
    private EditText usernameInput, userpasswordInput;
    private String userString,passWord,typeOfUser,type;
    private Button login;
    private ConstraintLayout loginLayout;
    private UserDatabase userDatabase;
    private DatabaseLoader databaseLoader;
    private Users user;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Login");

        //assigning variables
        usernameInput = findViewById(R.id.editTextUsername);
        userpasswordInput = findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.loginButton);
        login.setOnClickListener(loginAccount);
        newAccount = findViewById(R.id.register);
        newAccount.setOnClickListener(newaccount);
        //used to reset input
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(resetInputs);


        //used to login to the account
        loginLayout = findViewById(R.id.loginLayout);
        userDatabase = new UserDatabase(this);

    }

    private final View.OnClickListener loginAccount = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            //retrieves the entered inputs
            userString = usernameInput.getText().toString();
            passWord = userpasswordInput.getText().toString();

            //checks and sets errors that don't meant the appropriate inputs
            if (userString.isEmpty()) {
                usernameInput.setError("Please enter a valid email");
                usernameInput.requestFocus();
                return;

            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(userString).matches()) {
                usernameInput.setError("Please enter a valid email");
                usernameInput.requestFocus();
                return;
            }
            else if (passWord.isEmpty()) {
                userpasswordInput.setError("Please enter your password");
                userpasswordInput.requestFocus();
                return;
            }
            else
            {
                //checks if the password and email match in the database
                if(userDatabase.checkUser(userString,passWord) == true)
                {
                    //starts the new activity to the homepage
                    Intent loggedIn = new Intent(Login.this, HomePage.class);
                    //sends extra data to the homepage to use
                    loggedIn.putExtra("userEmail", userString);
                    //new activity starts here
                    startActivity(loggedIn);
                }
                else
                {
                    //snack bar to show the user doesnt exist in the database
                    Snackbar.make(loginLayout,"User Doesnt Exist ", Snackbar.LENGTH_LONG).show();
                }
            }

        }
    };

    //method used to start an activity to register a user
    private final View.OnClickListener newaccount = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent register = new Intent(Login.this,Register.class);
            startActivity(register);
        }
    };

    //method used to reset all the input data in the text fields
    private View.OnClickListener resetInputs = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            usernameInput.setText("");
            userpasswordInput.setText("");
        }
    };
}