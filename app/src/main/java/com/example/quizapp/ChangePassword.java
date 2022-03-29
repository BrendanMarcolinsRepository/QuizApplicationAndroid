package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class ChangePassword extends AppCompatActivity
{

    //variables need for this activity
    private EditText currentPw, newPassword1,newPassword2;
    private TextView returnText;
    private String passWord1,passWord2,passWord3, email, pw;
    private Button changePw, reset;
    private Intent intent;
    private ConstraintLayout passwordLayout;
    private UserDatabase userDatabase;
    private Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Change Password");

        //get the intent for previous activity/fragment an assigns the strings pasts through the intent to the new variables
        intent = getIntent();
        pw = intent.getStringExtra("password");
        email = intent.getStringExtra("email");
        setResult(RESULT_OK, null);

        //assigns ids to variables
        currentPw = findViewById(R.id.currentPw);
        newPassword1 = findViewById(R.id.CPW1);
        newPassword2 = findViewById(R.id.CPW2);
        changePw  = findViewById(R.id.changePwButton);
        //method when user clicks the change password button
        changePw.setOnClickListener(changePasswordMethod);
        // reset button when user clicks the button
        reset = findViewById(R.id.resetPw);
        reset.setOnClickListener(changePasswordMethod);
        passwordLayout = findViewById(R.id.passwordFragment);
        //gets the user by the past email from previous activity/fragment
        userDatabase = new UserDatabase(this);
        users = userDatabase.getUser(email);

        returnText = findViewById(R.id.returnPassword);
        returnText.setOnClickListener(returnMethod);


    }

    private final View.OnClickListener resetInputs = new View.OnClickListener()
    {
        @Override
        //resets the inputs
        public void onClick(View v)
        {
            currentPw.setText("");
            newPassword1.setText("");
            newPassword2.setText("");
        }
    };

    private final View.OnClickListener changePasswordMethod = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {

            //gets the text from the edit text
            passWord1 = currentPw.getText().toString();
            passWord2 = newPassword1.getText().toString();
            passWord3 = newPassword2.getText().toString();
            boolean inputGood = true;

            if(currentPw.getText().toString().isEmpty())
            {
                currentPw.setError("Please Enter Your Current Password");
                currentPw.requestFocus();
                inputGood = false;
            }

            if(newPassword1.getText().toString().isEmpty())
            {
                newPassword1.setError("Please Input A New Password");
                newPassword1.requestFocus();
                inputGood = false;
            }

            if(newPassword2.getText().toString().isEmpty())
            {
                newPassword2.setError("Please Input A New Password");
                newPassword2.requestFocus();
                inputGood = false;
            }

            //if the inputgood is true
            if(inputGood)
            {
                //if the password given by the user matches the database password
                if(passWord1.equals(users.getPassword()))
                {
                    //if the two new passwords match
                    if (passWord2.equals(passWord3))
                    {
                        //pass the text into the new users object to update the same user in the database
                        String n, e, p;
                        n = users.getFullName();
                        e = users.getEmail();
                        p = users.getPassword();
                        int id = users.getIdNumber(), l = users.getLiteratureScore(), num = users.getNumeracyScore(), cr = users.getCriticalThinkingScore();
                        Users user = new Users(id,n, e, passWord3, l, num, cr);
                        userDatabase.updateUserCritical(user);
                        //snackbar alert done to show is been completed
                        Snackbar.make(passwordLayout, "Password has been updates ", Snackbar.LENGTH_LONG).show();
                        //returns the password to show its been updated
                        intent.putExtra("password",passWord3);
                        setResult(RESULT_OK,intent);
                        //finishs the activity
                        finish();
                    }
                    else
                    {
                        //sets the errors
                        newPassword1.setError("New Passwords Don't Match");
                        newPassword1.requestFocus();
                        newPassword2.setError("New Passwords Don't Match");
                        newPassword2.requestFocus();
                    }
                }
                else
                {
                    //sets the errors
                    currentPw.setError("Wrong Current Password");
                    currentPw.requestFocus();
                }
            }

        }
    };

    private View.OnClickListener returnMethod = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            setResult(RESULT_CANCELED,intent);
            finish();

        }
    };
}