package com.example.assignment2.ui.Literature;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
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
import com.example.assignment2.QuizRules;
import com.example.assignment2.R;
import com.example.assignment2.UserQuizScoreDatabase;
import com.example.assignment2.Users;
import com.example.assignment2.UsersQuizMode;
import com.example.assignment2.ui.criticalthinking.CriticalThinkingFragmentModel;
import com.example.assignment2.ui.criticalthinking.DatabaseCriticalThinking;
import com.example.assignment2.ui.criticalthinking.QuizQuestionCriticalThinking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
//comments are similar to the fragment critical thinking quiz file
public class literatureQuiz extends Fragment
{

    private LiteratureQuizModel slideshowViewModel;

    private UsersQuizMode userScoreStore;
    private UserQuizScoreDatabase quizScoreDatabase;
    private LiteratureQuizModel literatureQuizModel;
    private LiteratureDatabase literatureDatabase;
    private Literature literature;
    private ArrayList<Literature> literatureArrayList;
    private Button answer, next;
    private RadioButton answer1,answer2,answer3,answer4,answer5,yourAnswer;
    private RadioGroup answerGroup;
    private TextView questionText, questionFading;
    private View root;
    private HomePage homePage;
    private Users user;
    private int questionCounter = 0, questionTracker = 0, randonNumerCounter = 1, amountOfContacts = 0, correct = 0, wrong = 0, databaseScore = 0;
    private ArrayList<Integer> questionNumbers = new ArrayList<Integer>();
    private ArrayList<UsersQuizMode> usersQuizModeList;
    private Random random;
    private ArrayList<Integer> randomList = new ArrayList<>();
    private ImageView a1,a2,a3,a4, a5;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        literatureQuizModel =
                new ViewModelProvider(this).get(LiteratureQuizModel.class);
        root = inflater.inflate(R.layout.fragment_literature, container, false);

        homePage = (HomePage) getActivity();
        literatureDatabase = new LiteratureDatabase(homePage);
        literature = new Literature();
        literatureArrayList = new ArrayList<Literature>();
        quizScoreDatabase = new UserQuizScoreDatabase(homePage);
        usersQuizModeList = quizScoreDatabase.getUsersQuiz();
        user = homePage.getUser();
        random = new Random();


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
                    if(q.getQuizName() == "LiteratureQuestionDatabase")
                    {
                        databaseScore = q.getScore();
                    }

                }
            }
        }


        randomMethod();


        System.out.println("Arrya list size" +  literatureArrayList.size());

        answer = root.findViewById(R.id.answerLit);
        next = root.findViewById(R.id.nextLit);
        questionText = root.findViewById(R.id.questionlit);
        answerGroup = root.findViewById(R.id.litAnswerGroup);
        answer1 = root.findViewById(R.id.litAnswer1);
        answer2 = root.findViewById(R.id.litAnswer2);
        answer3 = root.findViewById(R.id.litAnswer3);
        answer4 = root.findViewById(R.id.litAnswer4);
        answer5 = root.findViewById(R.id.litAnswer5);
        a1 = root.findViewById(R.id.imageanswerLit1);
        a2 = root.findViewById(R.id.imageanswerLit2);
        a3 = root.findViewById(R.id.imageanswerLit3);
        a4 = root.findViewById(R.id.imageanswerLit4);
        a5  = root.findViewById(R.id.imageanswerLit5);
        questionFading = root.findViewById(R.id.questionLitFade);
        questionFading.setText("Current Question: " + (questionTracker + 1));

        fadeIn();

        questionText.setText(literatureArrayList.get(questionTracker).getQuestion());
        answer1.setText(literatureArrayList.get(questionTracker).getAnswer());
        answer2.setText(literatureArrayList.get(questionTracker).getWronganswerone());
        answer3.setText(literatureArrayList.get(questionTracker).getWronganswertwo());
        answer4.setText(literatureArrayList.get(questionTracker).getWronganswerthree());
        answer5.setText(literatureArrayList.get(questionTracker).getWronganswerfour());
        answer.setOnClickListener(buttonOnClickListener);
        next.setOnClickListener(buttonOnClickListener);


        final TextView textView = root.findViewById(R.id.typeOfQuiz2);
        literatureQuizModel.getText().observe(getViewLifecycleOwner(), new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)
            {
                textView.setText("Literature Quiz");
            }
        });
        return root;


    }

    private void randomMethod()
    {
        questionNumbers.clear();
        literatureArrayList.clear();
        for(int i = 1; i <= 15; i++)
        {
            questionNumbers.add(i);
        }

        Collections.shuffle(questionNumbers);

        for(int i = 0; i < 5; i++)
        {
            System.out.println(questionNumbers.get(i));
            literature = literatureDatabase.getQuestion(questionNumbers.get(i));
            literatureArrayList.add(literature);
        }


    }

    private View.OnClickListener buttonOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int selectedID = answerGroup.getCheckedRadioButtonId();
            switch(v.getId())
            {
                case R.id.answerLit:
                    if(selectedID <= 0)
                    {
                        System.out.println("Please Pick an Answer");
                    }
                    else
                    {
                        answerToQuestion(selectedID);
                    }
                    break;
                case R.id.nextLit:
                    if(questionTracker <= 5)
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
        System.out.println(amountOfContacts + user.getFullName() + user.getEmail() + literatureDatabase.getDatabaseName() + correct + formatter.format(date));
        userScoreStore = new UsersQuizMode(amountOfContacts, user.getFullName(),user.getEmail(),literatureDatabase.getDatabaseName(),correct + databaseScore,formatter.format(date));
        quizScoreDatabase.addUsers(userScoreStore);
        amountOfContacts++;
        randomMethod();
        questionTracker = 0;
        questionFading.setText("Current Question: " + (questionTracker + 1));
        correct = 0;
        wrong = 0;
        questionCounter = 0;
        next.setText("Next");
        nextQuestion();

    }
    private void nextQuestion()
    {

        questionText.setText(literatureArrayList.get(questionTracker).getQuestion());
        answer1.setText(literatureArrayList.get(questionTracker).getAnswer());
        answer1.setTextColor(getResources().getColor(R.color.black));
        a1.setVisibility(View.INVISIBLE);
        answer2.setText(literatureArrayList.get(questionTracker).getWronganswerone());
        answer2.setTextColor(getResources().getColor(R.color.black));
        a2.setVisibility(View.INVISIBLE);
        answer3.setText(literatureArrayList.get(questionTracker).getWronganswertwo());
        answer3.setTextColor(getResources().getColor(R.color.black));
        a3.setVisibility(View.INVISIBLE);
        answer4.setText(literatureArrayList.get(questionTracker).getWronganswerthree());
        answer4.setTextColor(getResources().getColor(R.color.black));
        a4.setVisibility(View.INVISIBLE);
        answer5.setText(literatureArrayList.get(questionTracker).getWronganswerfour());
        answer5.setTextColor(getResources().getColor(R.color.black));
        a5.setVisibility(View.INVISIBLE);
        answer.setEnabled(true);
        questionFading.setText("Current Question: " + (questionTracker + 1));
        answerGroup.clearCheck();

    }

    private void answerToQuestion(int selectionID)
    {

        System.out.println(selectionID);
        yourAnswer = root.findViewById(selectionID);
        if(answer1.getText().equals(yourAnswer.getText()))
        {
            answer.setEnabled(false);
            yourAnswer.setTextColor(getResources().getColor(R.color.Green));
            a1.setVisibility(View.VISIBLE);
            a1.setImageResource(R.drawable.tick);
            System.out.println("correct");
            correct++;
        }
        else
        {
            answer.setEnabled(false);
            yourAnswer.setTextColor(getResources().getColor(R.color.Red));

            System.out.println(selectionID);
            if(selectionID % 10  == 7)
            {
                a2.setVisibility(View.VISIBLE);
                a2.setImageResource(R.drawable.wrong);
            }
            if(selectionID % 10 == 8)
            {
                a3.setVisibility(View.VISIBLE);
                a3.setImageResource(R.drawable.wrong);
            }
            if(selectionID % 10  == 9)
            {
                a4.setVisibility(View.VISIBLE);
                a4.setImageResource(R.drawable.wrong);
            }
            if(selectionID % 10  == 0)
            {
                a5.setVisibility(View.VISIBLE);
                a5.setImageResource(R.drawable.wrong);
            }

            wrong++;
            System.out.println("wrong");
        }
    }


    private void alertDialog()
    {
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