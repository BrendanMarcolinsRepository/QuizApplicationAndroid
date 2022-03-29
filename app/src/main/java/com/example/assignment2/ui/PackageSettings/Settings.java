package com.example.assignment2.ui.PackageSettings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.assignment2.ChangePassword;
import com.example.assignment2.HomePage;
import com.example.assignment2.Login;
import com.example.assignment2.R;
import com.example.assignment2.Register;
import com.example.assignment2.UserDatabase;
import com.example.assignment2.Users;
import com.google.android.material.snackbar.Snackbar;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class Settings extends Fragment
{
    //Variables need for this fragment
    private View root;
    private HomePage homePage;
    private String name,email,password;
    private TextView n,e,p, changep;
    private ConstraintLayout constraintLayout;
    private Users users;
    private UserDatabase userDatabase;
    private Intent changeIntent;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_settings, container, false);


        //assigning variables
        homePage = (HomePage) getActivity();
        users = homePage.getUser();
        n = root.findViewById(R.id.namesettings);
        e = root.findViewById(R.id.emailsettings);
        p = root.findViewById(R.id.passwordsettings);
        changep = root.findViewById(R.id.changePw);
        name = users.getFullName();
        email = users.getEmail();
        password = users.getPassword();
        constraintLayout = root.findViewById(R.id.settingsFragment);

        //setting text output
        n.setText("Name: " +name);
        System.out.println(name);
        e.setText("Email: " + email);
        p.setText("Password: " +password);

        //on clicking changing password button calls changepassword method
        changep.setOnClickListener(changePassword);


        return root;
    }

    //starts an activity changepassword, that puts extra data of the user to the next activity
    private final View.OnClickListener changePassword = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            changeIntent = new Intent(homePage, ChangePassword.class);
            changeIntent.putExtra("name", name);
            changeIntent.putExtra("email", email);
            changeIntent.putExtra("password", password);
            //used to return data that matches this request code
            startActivityForResult(changeIntent, 1);

        }
    };

    //on returning to this fragment from the activity it will updates the password displayed
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        //if the request code matchs
        if (requestCode == 1)
        {

            //if the match result code has been set to okay in the activity returned from
            if(resultCode == RESULT_OK)
            {
                //updates the users password to display
                p.setText("Password: " + data.getStringExtra("password"));
                Snackbar.make(constraintLayout,"Password Update Has Been Successful ", Snackbar.LENGTH_LONG).show();
            }
            if (resultCode == RESULT_CANCELED)
            {
                Snackbar.make(constraintLayout,"Password Update Has Been Canceled ", Snackbar.LENGTH_LONG).show();
            }
        }
    }


}
