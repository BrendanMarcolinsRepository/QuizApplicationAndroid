package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class QuizRules extends AppCompatActivity
{
    private TextView returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_rules);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Quiz Rules");

        returnButton = findViewById(R.id.return1);
        returnButton.setOnClickListener(returnMethod);
    }

    //returns the user back to the home page when clicking the return button
    private View.OnClickListener returnMethod = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            finish();

        }
    };
}