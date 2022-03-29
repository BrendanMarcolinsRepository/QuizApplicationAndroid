package com.example.assignment2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment2.DatabaseLoader;
import com.example.assignment2.R;
import com.example.assignment2.UserDatabase;
import com.example.assignment2.UserQuizScoreDatabase;
import com.example.assignment2.Users;
import com.example.assignment2.UsersQuizMode;
import com.example.assignment2.ui.Numeracy.Numeracy;
import com.example.assignment2.ui.criticalthinking.DatabaseCriticalThinking;

import java.util.ArrayList;

public class HomePageModel extends Fragment
{

    //variables needed for this fragment
    private HomePage homePage;
    private Users user;
    private TextView greeting;
    private UserDatabase userDatabase;
    private DatabaseLoader databaseLoader;
    private DatabaseCriticalThinking databaseCriticalThinking;
    private UserQuizScoreDatabase userQuizScoreDatabase;
    private UsersQuizMode usersQuizMode;
    private ArrayList<UsersQuizMode> usersQuizModeList;
    private ArrayList<UsersQuizMode> usersQuizModeArrayList;
    private ArrayList<UsersQuizMode> searchQuizPeopleArrayList;
    private ArrayAdapter<UsersQuizMode> arrayAdapter;
    private ArrayAdapter<UsersQuizMode> arrayAdapter3;
    private ArrayAdapter<String> arrayAdapter2;
    private ArrayList<UsersQuizMode> message;
    private ArrayList<String> empty;
    private TextView userMessage,searchText;
    private ListView listView;
    private Button searchDate;
    private String dateString;
    private com.example.assignment2.HomePage h1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        com.example.assignment2.HomePage homePage = (com.example.assignment2.HomePage) getActivity();

        h1 =  (com.example.assignment2.HomePage) getActivity();
        this.homePage =
                new ViewModelProvider(this).get(HomePage.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //assigning variables
        userMessage = root.findViewById(R.id.displayName);
        userDatabase = new UserDatabase(homePage);
        user = homePage.getUser();
        userQuizScoreDatabase = new UserQuizScoreDatabase(homePage);
        usersQuizModeArrayList = new ArrayList<UsersQuizMode>();
        usersQuizModeList = userQuizScoreDatabase.getUsersQuiz();
        empty = new ArrayList<String>();
        searchDate = root.findViewById(R.id.searchDateButton);
        searchText = root.findViewById(R.id.searchDate);


        //total used to show users current score
        int total = 0;
        //loops through the arraylist of user scores
        for(UsersQuizMode q : usersQuizModeList)
        {
            //if users name matches in the list
            if(q.getUserName().equals(user.getFullName()))
            {
                //if email matches in the list
                if(q.getEmail().equals(user.getEmail()))
                {
                    //adds the score to the total and the list
                    total += q.getScore();
                    usersQuizModeArrayList.add(q);
                }
            }
        }

        //displays a greeting to the user with the attempts and total
        userMessage.setText(String.format("Hello: %s, you have earned %d in the following attempts" ,user.getFullName() ,total));
        //use a list view, with an adapter to put the arraylist into a listview
        listView = (ListView) root.findViewById(R.id.usersStuff);
        arrayAdapter = new ArrayAdapter<>(homePage,R.layout.userlist,R.id.userList,usersQuizModeArrayList);
        listView.setAdapter(arrayAdapter);



        greeting =  root.findViewById(R.id.greeting);

        //search button to search quiz attempts on a particular day
        searchDate.setOnClickListener(searchPatient);

        this.homePage.getText().observe(getViewLifecycleOwner(), new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)
            {
               // textView.setText(s);
            }
        });
        return root;
    }
    private View.OnClickListener searchPatient = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {

            //holds the search list
            searchQuizPeopleArrayList = new ArrayList<UsersQuizMode>();
            searchQuizPeopleArrayList.clear();
            //gets the dates
            dateString = searchText.getText().toString();


            //searches the arraylist through a loop
            for(UsersQuizMode c : usersQuizModeArrayList)
            {
                //splits the string to get the date without the time
                String[] strParts = c.getDate().split( " " );

                //checks to see any dates match the ones in the list
                if(dateString.matches(strParts[0]))
                {
                    //then adds it the list
                    searchQuizPeopleArrayList.add(c);
                }
            }

            //if the list is not empty is will output the arraylist into an adapter, which a listview will display it to a user the quiz attempts
            if(!searchQuizPeopleArrayList.isEmpty())
            {

                arrayAdapter3 = new ArrayAdapter<UsersQuizMode>(h1,R.layout.userlist,R.id.userList,searchQuizPeopleArrayList);
                listView.setAdapter(arrayAdapter3);
            }
            //else an error will be sets that the quiz attempt doesnt exist on that date
            else
            {
                searchText.setError("You have no completed a quiz on this date");
                searchText.requestFocus();
            }

        }
    };
}