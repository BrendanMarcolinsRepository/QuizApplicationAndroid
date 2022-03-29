package com.example.quizapp.ui.criticalthinking;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.quizapp.HomePage;
import com.example.quizapp.R;
import com.example.quizapp.UserQuizScoreDatabase;
import com.example.quizapp.Users;
import com.example.quizapp.UsersQuizMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class CriticalThinkingFragment extends Fragment
{
    //variables needed for this fragment
    private UsersQuizMode userScoreStore;
    private UserQuizScoreDatabase quizScoreDatabase;
    private CriticalThinkingFragmentModel criticalThinkingFragmentModel;
    private DatabaseCriticalThinking databaseCriticalThinking;
    private QuizQuestionCriticalThinking questions;
    private ArrayList<QuizQuestionCriticalThinking> questionsList;
    private Button answer, next;
    private RadioButton answer1,answer2,answer3,answer4, yourAnswer, answer5;
    private RadioGroup answerGroup;
    private TextView questionText,questionFading;
    private View root;
    private HomePage homePage;
    private Users user;
    private int questionCounter = 0, questionTracker = 0, randonNumerCounter = 1, amountOfContacts = 0, correct = 0, wrong = 0, databaseScore = 0;
    private ArrayList<Integer> questionNumbers = new ArrayList<Integer>();
    private ArrayList<UsersQuizMode> usersQuizModeList;
    private ImageView a1,a2,a3,a4, a5;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        //inflater to get the layout and container
        root = inflater.inflate(R.layout.fragment_criticalthinking, container, false);
        //gets the activity content
        homePage = (HomePage) getActivity();
        //assigns variables
        databaseCriticalThinking = new DatabaseCriticalThinking(homePage);
        questions = new QuizQuestionCriticalThinking();
        questionsList = new ArrayList<QuizQuestionCriticalThinking>();
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

        //used to get the user to update the database of scores
        //loops through the quizscore database
        for(UsersQuizMode q : usersQuizModeList)
        {
            //if the names match
            if(q.getUserName().matches(user.getFullName()))
            {
                //if email matches
                if(q.getEmail().matches(user.getEmail()))
                {
                    //the databasename matches the quiz name
                    if(q.getQuizName() == "CriticalThingQuestionDatabase")
                    {
                        //gets the current overall score for the user in this quiz
                        databaseScore = q.getScore();
                    }

                }
            }
        }

        //methods used to randomise the questions
        randomMethod();

        //assigns variables
        answer = root.findViewById(R.id.answerCritical);
        next = root.findViewById(R.id.nextCritical);
        questionText = root.findViewById(R.id.questionCritical);
        answerGroup = root.findViewById(R.id.criticalAnswerGroup);
        answer1 = root.findViewById(R.id.criticalAnswer1);
        answer2 = root.findViewById(R.id.criticalAnswer2);
        answer3 = root.findViewById(R.id.criticalAnswer3);
        answer4 = root.findViewById(R.id.criticalAnswer4);
        answer5 = root.findViewById(R.id.criticalAnswer5);
        a1 = root.findViewById(R.id.imageanswerCritic1);
        a2 = root.findViewById(R.id.imageanswerCritic2);
        a3 = root.findViewById(R.id.imageanswerCritic3);
        a4 = root.findViewById(R.id.imageanswerCritic4);
        a5 = root.findViewById(R.id.imageanswerCritic5);
        questionFading = root.findViewById(R.id.questionCriticalFade);
        questionFading.setText("Current Question: " + (questionTracker + 1));
        //method used for animation fade
        fadeIn();

        //sets the questions, with the questiontrack that is randomised from the arraylist
        questionText.setText(questionsList.get(questionTracker).getQuestion());
        answer1.setText(questionsList.get(questionTracker).getAnswer());
        answer2.setText(questionsList.get(questionTracker).getWronganswerone());
        answer3.setText(questionsList.get(questionTracker).getWronganswertwo());
        answer4.setText(questionsList.get(questionTracker).getWronganswerthree());
        answer5.setText(questionsList.get(questionTracker).getWronganswerfour());

        //on click answer button and next button
        answer.setOnClickListener(buttonOnClickListener);
        next.setOnClickListener(buttonOnClickListener);

        criticalThinkingFragmentModel =
                new ViewModelProvider(this).get(CriticalThinkingFragmentModel.class);

        final TextView textView = root.findViewById(R.id.typeOfQuiz);
        criticalThinkingFragmentModel.getText().observe(getViewLifecycleOwner(), new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)
            {
                textView.setText("Critical Thinking Quiz");
            }
        });
        return root;


    }

    //used to randomise the questions
    private void randomMethod()
    {
        //clears anything previous
        questionNumbers.clear();
        questionsList.clear();
        for(int i = 1; i <= 15; i++)
        {
            //adds the amount of questions in the database
            questionNumbers.add(i);

        }

        //shuffles the ammount to randomise it
        Collections.shuffle(questionNumbers);

        for(int i = 0; i < 5; i++)
        {
            System.out.println("Number " + i + ": " + questionNumbers.get(i));

        }

        //loops through the first 5, gets those id of those questions from the database using the questionnumbers and add it to the arraylist
        for(int i = 0; i < 5; i++)
        {

            System.out.println("worked");
            questions = databaseCriticalThinking.getQuestion(questionNumbers.get(i));
            questionsList.add(questions);
        }
    }

    private View.OnClickListener buttonOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            //gets the selection id from the radio buttongroup
            int selectedID = answerGroup.getCheckedRadioButtonId();
            switch(v.getId())
            {
                //button answer pressed has no chosen, user gets the question wrong
                case R.id.answerCritical:
                    if(selectedID <= 0)
                    {
                        System.out.println("Please Pick an Answer");
                    }
                    else
                    {
                        //checks the answer
                        answerToQuestion(selectedID);
                    }
                    break;
                    //case used for the next button
                case R.id.nextCritical:
                    if(questionTracker < 5)
                    {
                        //updates the question tracker
                        questionTracker++;
                        if(questionTracker == 5)
                        {
                            //resets after the 5 questions is done and calls reset
                            reset();
                        }
                        //else keeps going through the questions by calling the next question method
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
        //calls the alert method
        alertDialog();

        //used to get the current data and store it in the user object
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        //user object store the updates of the users and pass it to the database
        userScoreStore = new UsersQuizMode(amountOfContacts, user.getFullName(),user.getEmail(),databaseCriticalThinking.getDatabaseName(),correct + databaseScore,formatter.format(date));
        quizScoreDatabase.addUsers(userScoreStore);
        //resets all the variables or updates the variables for the next quiz
        amountOfContacts++;

        questionTracker = 0;
        correct = 0;
        wrong = 0;
        questionCounter = 0;
        answerGroup.clearCheck();
        next.setText("Next");
        //keeps the fading going
        questionFading.setText("Current Question: " + (questionTracker + 1));
        randomMethod();
        nextQuestion();

    }
    private void nextQuestion()
    {

        //sets the answers for each questio, displays it, updates the colour to black and sets the image view to invisible again
        questionText.setText(questionsList.get(questionTracker).getQuestion());
        answer1.setText(questionsList.get(questionTracker).getAnswer());
        answer1.setTextColor(getResources().getColor(R.color.black));
        a1.setVisibility(View.INVISIBLE);
        answer2.setText(questionsList.get(questionTracker).getWronganswerone());
        answer2.setTextColor(getResources().getColor(R.color.black));
        a2.setVisibility(View.INVISIBLE);
        answer3.setText(questionsList.get(questionTracker).getWronganswertwo());
        answer3.setTextColor(getResources().getColor(R.color.black));
        a3.setVisibility(View.INVISIBLE);
        answer4.setText(questionsList.get(questionTracker).getWronganswerthree());
        answer4.setTextColor(getResources().getColor(R.color.black));
        a4.setVisibility(View.INVISIBLE);
        answer5.setText(questionsList.get(questionTracker).getWronganswerfour());
        answer5.setTextColor(getResources().getColor(R.color.black));
        a5.setVisibility(View.INVISIBLE);
        answer.setEnabled(true);
        questionFading.setText("Current Question: " +(questionTracker + 1));
        answerGroup.clearCheck();

        //updates the next button to finish to help quide the user
        if(questionTracker == 5)
        {
            next.setText("Finish");
        }

    }

    private void answerToQuestion(int selectionID)
    {

        //gets the answers the user chosen in the radio button group
        yourAnswer = root.findViewById(selectionID);
        //if the correct answer matches the radio button answer
        if(answer1.getText().equals(yourAnswer.getText()))
        {
            //sets the colour green for correct, sets the tick visible,updates the correct count
            answer.setEnabled(false);
            yourAnswer.setTextColor(getResources().getColor(R.color.Green));
            a1.setVisibility(View.VISIBLE);
            a1.setImageResource(R.drawable.tick);
            correct++;
        }
        else
        {
            //else if the answer was incorrect, sets the text colour to red
            answer.setEnabled(false);
            yourAnswer.setTextColor(getResources().getColor(R.color.Red));
            System.out.println(selectionID);
            //gets the wrong radiobutton, depending on the selection. sets an image of a cross
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
            //increase the wrong count
            wrong++;

        }
    }



    //alert dialog to show the user the count of wrong and right answers
    private void alertDialog()
    {
        //dialog builder objects
        AlertDialog.Builder dialog=new AlertDialog.Builder(homePage);
        //sets the message output
        dialog.setMessage("You Have Completed the Quiz, \nYour Correct Answers:" + correct + " \nYour Wrong Answers: " + wrong);
        //sets the title
        dialog.setTitle("Quiz Finish");
        //creates the alert
        AlertDialog alertDialog=dialog.create();
        //and shows it
        alertDialog.show();
    }

    //used to fade in the textview count of the question
    private void fadeIn()
    {
        //creates object animator, whichs takes the object type, property and values
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(questionFading, "alpha", 0f, 1f);
        //sets the duration of the animation
        objectAnimator.setDuration(2000L);
        //calles the fade out after the duration
        objectAnimator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                fadeOut();
            }
        });
        //starts the animation
        objectAnimator.start();
    }

    //used to fade out the textview count of the question
    private void fadeOut()
    {
        //creates object animator, whichs takes the object type, property and values
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(questionFading, "alpha", 1f, 0f);
        //sets the duration of the animation
        objectAnimator.setDuration(2000L);
        //calles the fade in after the duration
        objectAnimator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                fadeIn();
            }
        });
        //starts the animation
        objectAnimator.start();
    }
}
