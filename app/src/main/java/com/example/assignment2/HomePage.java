package com.example.assignment2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.example.assignment2.ui.Literature.Literature;
import com.example.assignment2.ui.Literature.LiteratureDatabase;
import com.example.assignment2.ui.Numeracy.NumeracyDatabase;
import com.example.assignment2.ui.criticalthinking.DatabaseCriticalThinking;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.util.ArrayList;

public class HomePage extends AppCompatActivity
{

    //variables need for this activty
    private AppBarConfiguration mAppBarConfiguration;
    private String userEmail;
    private Users user;
    private UserDatabase userDatabase;
    private DatabaseLoader databaseLoader;
    private DatabaseCriticalThinking databaseCriticalThinking;
    private LiteratureDatabase literatureDatabase;
    private NumeracyDatabase numarcyDatabase;
    private UserQuizScoreDatabase userQuizScoreDatabase;
    private UsersQuizMode usersQuizMode;
    private ArrayList<UsersQuizMode> usersQuizModeList;
    private TextView headName, headEmail,help;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        databaseChecker();

        //gets the extra data past across from the previous activty
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("userEmail");
        setResult(RESULT_OK, null);
        //assigning variables
        userDatabase = new UserDatabase(this);
        user = userDatabase.getUser(userEmail);
        userQuizScoreDatabase = new UserQuizScoreDatabase(this);
        usersQuizModeList = userQuizScoreDatabase.getUsersQuiz();
        //on click to logout and return to the login page
        Button mButton = findViewById(R.id.loginButton);
        mButton.setOnClickListener(logout);
        //user to call the help method
        help = findViewById(R.id.help);
        help.setOnClickListener(helpMethod);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        //used to create the navigational menu with ids from the menu xml
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_numeracy, R.id.nav_literature, R.id.nav_criticalthinking, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        //creates a new navigation controller, actionbar controller
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View header = navigationView.getHeaderView(0);
        //use to display the users details in the navigation header
        headName = (TextView) header.findViewById(R.id.navhead_name);
        headEmail = (TextView) header.findViewById(R.id.navhead_email);
        headName.setText(user.getFullName());
        headEmail.setText(user.getEmail());


    }




    //method used to log the user out if their account return back to the login page
    private final View.OnClickListener logout = new View.OnClickListener()
    {
        @Override
        public void onClick (View v)
        {
            Intent mainActivity = new Intent(HomePage.this, Login.class);
            startActivity(mainActivity);
        }
    };

    //method used to start the activity page for the help and quiz instructions
    private View.OnClickListener helpMethod = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(HomePage.this, QuizRules.class);
            startActivity(intent);

        }
    };



    @Override
    //used to inflate the menu when the activity is called
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }


    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //returns the currnent user
    public Users getUser()
    {
        return user;
    }

    //returns the currents user email
    public String getUserEmail(){return userEmail;}

    private void databaseChecker()
    {
        //loads in the data from the raw files and them to the database if they dont already exist
        databaseLoader = new DatabaseLoader();
        if(!doesDatabaseExist(this,"CriticalThingQuestionDatabase") && !doesDatabaseExist(this,
                "LiteratureQuestionDatabase") && !doesDatabaseExist(this,"NumeracyQuestionDatabase"))
        {
            //loads in the data from the raw files and them to the database if they dont already exist
            databaseCriticalThinking = databaseLoader.createThatCritalThinkingDatabase(this);
            literatureDatabase = databaseLoader.createThatLiteratureDatabase(this);
            numarcyDatabase = databaseLoader.createThatNumeracyDatabase(this);
        }
        else
        {
            //if they do exist then a new object is made for the databases
            databaseCriticalThinking = new DatabaseCriticalThinking(this);
            literatureDatabase = new LiteratureDatabase(this);
            numarcyDatabase = new NumeracyDatabase(this);

        }

    }

    //checks to see if the database already exist and needs updating
    private boolean doesDatabaseExist(Context context, String dbName)
    {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}