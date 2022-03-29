package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Register extends AppCompatActivity
{

    //variables needed for this activity
    private String firstName, lastName, stringPW,stringEmail;
    private Button button;
    private TextView returnLogin;
    private EditText fN, lN, pw, email;
    private int amountOfContacts = 0;
    private ConstraintLayout registerLayout;
    private Users users;
    private ArrayList<Users> usersArrayList;
    private UserDatabase userDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Register");

        //assigning variables to their xml IDs
        fN = findViewById(R.id.newFirstName);
        lN = findViewById(R.id.newLastName);
        pw = findViewById(R.id.regPassword);
        email = findViewById(R.id.regEmail);
        registerLayout = findViewById(R.id.register);
        //assigning the user database and storing all the users in the database to an arraylist
        userDatabase = new UserDatabase(this);
        usersArrayList = userDatabase.getAllUsers();

        //looping through all the users in the arraylist and checking if there is any and then adjust the id count
        for(int i = 0; i <= usersArrayList.size();i++)
        {
            if(i == usersArrayList.size() && usersArrayList.size() > 0)
            {
                amountOfContacts = usersArrayList.get(i - 1).getIdNumber() + 1;
            }
            else
            {
                amountOfContacts = 1;
            }
        }

        returnLogin = findViewById(R.id.returnRegister);
        returnLogin.setOnClickListener(returnMethod);

        //action performed when a user clicks the buttons
        button = findViewById(R.id.createButton);
        button.setOnClickListener(createAccount);
    }

    private final View.OnClickListener createAccount = new View.OnClickListener()
    {
        @Override
        //calls the registerUser method
        public void onClick(View v)
        {
            registerUser();
        }
    };

    private void registerUser()
    {
        //gets the text for each edittext
        firstName = fN.getText().toString();
        lastName = lN.getText().toString();
        stringEmail = email.getText().toString();
        stringPW = pw.getText().toString();


        //checks if the input meets certain requirement to proceed and if not displays an error to that particular edit text
        if (firstName.isEmpty()) {
            fN.setError("First name is requried");
            fN.requestFocus();
        }
        else if(lastName.isEmpty()) {
            lN.setError("Last name is requried");
            lN.requestFocus();
        }
        else if(stringEmail.isEmpty()) {
            lN.setError("Email is requried");
            lN.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(stringEmail).matches()) {
            email.setError("Email is Required");
            email.requestFocus();
        }
        else if (stringPW.isEmpty()) {
            lN.setError("Password is requried");
            lN.requestFocus();
        }
        else
        {
            //assigns a new user object with the details for the constructor
            users = new Users(amountOfContacts,firstName +" " +lastName,stringEmail,stringPW,0,0,0);
            //adds the user to the database
            userDatabase.addUsers(users);
            //snack alert to show that the event has occured
            Snackbar.make(registerLayout,"Contact Has Been Saved ", Snackbar.LENGTH_LONG).show();
            //increase the id count for the database of users
            amountOfContacts++;
            //returns to the main menu
            finish();

        }
    }

    private View.OnClickListener returnMethod = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            finish();

        }
    };

}