package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.assignment2.ui.Literature.Literature;

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