package com.example.assignment2.ui.Numeracy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment2.HomePage;
import com.example.assignment2.R;
import com.example.assignment2.UserQuizScoreDatabase;
import com.example.assignment2.Users;
import com.example.assignment2.UsersQuizMode;
import com.example.assignment2.ui.criticalthinking.CriticalThinkingFragmentModel;
import com.example.assignment2.ui.criticalthinking.DatabaseCriticalThinking;
import com.example.assignment2.ui.criticalthinking.QuizQuestionCriticalThinking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
//comments are similar to the fragment critical thinking quiz file
public class NumeracyQuiz extends Fragment
{

    private UsersQuizMode userScoreStore;
    private UserQuizScoreDatabase quizScoreDatabase;
    private NumeracyModel numeracyModel;
    private NumeracyDatabase numeracyDatabase;
    private Numeracy questions;
    private ArrayList<Numeracy> questionsList;
    private Button answer, next;
    private EditText answer1;
    private int answerFromDatabase, youranswer;
    private TextView questionText,questionFading;
    private View root;
    private HomePage homePage;
    private final String regexStr = "^[0-9]*$";
    private Users user;
    private int questionCounter = 0, questionTracker = 0, randonNumerCounter = 1, amountOfContacts = 0, correct = 0, wrong = 0, databaseScore = 0;
    private ArrayList<Integer> questionNumbers = new ArrayList<Integer>();
    private ArrayList<UsersQuizMode> usersQuizModeList;
    private ImageView a1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_numeracy, container, false);
        homePage = (HomePage) getActivity();
        numeracyDatabase = new NumeracyDatabase(homePage);
        questions = new Numeracy();
        questionsList = new ArrayList<Numeracy>();
        quizScoreDatabase = new UserQuizScoreDatabase(homePage);
        usersQuizModeList = quizScoreDatabase.getUsersQuiz();
        user = homePage.getUser();

        for(int i = 0; i <= usersQuizModeList.size();i++)
        {
            System.out.println("Contacts size  " + usersQuizModeList.size());

            if(i == usersQuizModeList.size() && usersQuizModeList.size() > 0)
            {
                amountOfContacts = usersQuizModeList.get(i - 1).getId() + 1;
                System.out.println("Contacts Number:  " + usersQuizModeList);
            }
            else
            {
                amountOfContacts = 1;
            }
        }

        for(UsersQuizMode q : usersQuizModeList)
        {
            if(q.getUserName().matches(user.getFullName()))
            {
                if(q.getEmail().matches(user.getEmail()))
                {
                    if(q.getQuizName() == "NumeracyQuestionDatabase")
                    {
                        databaseScore = q.getScore();
                    }

                }
            }
        }


        randomMethod();

        answer = root.findViewById(R.id.answerNum);
        next = root.findViewById(R.id.nextNum);
        questionText = root.findViewById(R.id.questionNum);
        answer1 = root.findViewById(R.id.numAnswer);
        a1 = root.findViewById(R.id.imageanswerNum1);
        questionFading = root.findViewById(R.id.questionNumFade);
        questionFading.setText("Current Question: " +  (questionTracker + 1));
        fadeIn();


        questionText.setText(questionsList.get(questionTracker).getQuestion());
        answer.setOnClickListener(buttonOnClickListener);
        next.setOnClickListener(buttonOnClickListener);

        numeracyModel =
                new ViewModelProvider(this).get(NumeracyModel.class);

        final TextView textView = root.findViewById(R.id.typeOfQuiz3);
        numeracyModel.getText().observe(getViewLifecycleOwner(), new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)
            {
                textView.setText("Numeracy Quiz");
            }
        });
        return root;


    }

    private View.OnClickListener buttonOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch(v.getId())
            {
                case R.id.answerNum:
                    if( TextUtils.isEmpty(answer1.getText()))
                    {
                        System.out.println("Please Pick an Answer");
                    }
                    else
                    {
                        youranswer = Integer.parseInt(answer1.getText().toString());
                        answerToQuestion(youranswer);
                    }
                    break;
                case R.id.nextNum:
                    if(questionTracker < 5)
                    {
                        questionTracker++;
                        if(questionTracker == 5)
                        {
                            reset();
                        }
                        else
                        {
                            if(questionTracker == 4)
                            {
                                next.setText("Finish");
                            }
                            nextQuestion();
                        }
                    }

                    break;
            }
        }


    };

    private void reset()
    {
        alertDialog();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));
        System.out.println(amountOfContacts + user.getFullName() + user.getEmail() + numeracyDatabase.getDatabaseName() + correct + formatter.format(date));
        userScoreStore = new UsersQuizMode(amountOfContacts, user.getFullName(),user.getEmail(),numeracyDatabase.getDatabaseName(),correct + databaseScore,formatter.format(date));
        quizScoreDatabase.addUsers(userScoreStore);
        amountOfContacts++;
        randomMethod();
        questionTracker = 0;
        correct = 0;
        wrong = 0;
        questionCounter = 0;
        next.setText("Next");
        questionFading.setText("Current Question: " + (questionTracker + 1));
        nextQuestion();

    }
    private void nextQuestion()
    {

        questionText.setText(questionsList.get(questionTracker).getQuestion());
        answerFromDatabase = questionsList.get(questionTracker).getAnswer();
        answer1.setTextColor(getResources().getColor(R.color.black));
        answer1.setText("");
        a1.setVisibility(View.INVISIBLE);
        answer.setEnabled(true);
        questionFading.setText("Current Question: " + (questionTracker + 1));

    }

    private void answerToQuestion(int youranswer)
    {


        if(youranswer == answerFromDatabase)
        {
            answer.setEnabled(false);
            answer1.setTextColor(getResources().getColor(R.color.Green));
            a1.setVisibility(View.VISIBLE);
            a1.setImageResource(R.drawable.tick);
            System.out.println("correct");
            correct++;
        }
        else
        {
            answer.setEnabled(false);
            answer1.setTextColor(getResources().getColor(R.color.Red));
            a1.setVisibility(View.VISIBLE);
            a1.setImageResource(R.drawable.wrong);
            wrong++;
            System.out.println("wrong");
        }
    }

    private void randomMethod()
    {
        questionNumbers.clear();
        questionsList.clear();
        for(int i = 1; i <= 15; i++)
        {
            questionNumbers.add(i);
        }

        Collections.shuffle(questionNumbers);

        for(int i = 0; i < 5; i++)
        {
            System.out.println(questionNumbers.get(i));
            questions = numeracyDatabase.getQuestion(questionNumbers.get(i));
            questionsList.add(questions);
        }


    }

    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(homePage);
        dialog.setMessage("You Have Completed the Quiz, \nYour Correct Answers:" + correct + " \nYour Wrong Answers: " + wrong);
        dialog.setTitle("Quiz Finish");
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    private void fadeIn()
    {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(questionFading, "alpha", 0f, 1f);
        objectAnimator.setDuration(2000L);
        objectAnimator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                fadeOut();
            }
        });
        objectAnimator.start();
    }

    private void fadeOut()
    {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(questionFading, "alpha", 1f, 0f);
        objectAnimator.setDuration(2000L);
        objectAnimator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                fadeIn();
            }
        });
        objectAnimator.start();
    }
}