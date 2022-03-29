package com.example.quizapp;

import android.content.Context;

import com.example.quizapp.ui.Literature.Literature;
import com.example.quizapp.ui.Literature.LiteratureDatabase;
import com.example.quizapp.ui.Numeracy.Numeracy;
import com.example.quizapp.ui.Numeracy.NumeracyDatabase;
import com.example.quizapp.ui.criticalthinking.DatabaseCriticalThinking;
import com.example.quizapp.ui.criticalthinking.QuizQuestionCriticalThinking;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DatabaseLoader
{
    //methods used to take in the files to the database
    public DatabaseCriticalThinking createThatCritalThinkingDatabase(Context context)
    {
        //creates at new database object
        DatabaseCriticalThinking criticalThinking = new DatabaseCriticalThinking(context);
        //string will hold the data from the buffer
        String data = "";
        //create a new stringbuffer object
        StringBuffer stringBuffer = new StringBuffer();
        //input stream gets the contents from the raw file in the resource files
        InputStream is = context.getResources().openRawResource(R.raw.questions1);
        //readers the new input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        // try and catch for errors in the file reading process
        try
        {
            while((data = reader.readLine()) != null)
            {
                //appends the string data
                stringBuffer.append(data + "n");
                // splits the data into an array with the index count occuring the ","
                String[] strParts = data.split( "," );
                //stores each split array index into the new object
                QuizQuestionCriticalThinking quizQuestionCriticalThinking = new QuizQuestionCriticalThinking(Integer.parseInt(strParts[0]), strParts[1], strParts[2], strParts[3], strParts[4], strParts[5],strParts[6]);
                //then adds the question to the database
                criticalThinking.addQuestion(quizQuestionCriticalThinking);
            }


        }
        //catches any error and shows the stack trace
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //returns the database object
        return criticalThinking;
    }

    //process is replicated from above
    public LiteratureDatabase createThatLiteratureDatabase(Context context)
    {
        LiteratureDatabase literatureDatabase = new LiteratureDatabase(context);
        String data = "";
        StringBuffer stringBuffer = new StringBuffer();
        InputStream is = context.getResources().openRawResource(R.raw.lit);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        try
        {
            while((data = reader.readLine()) != null)
            {
                stringBuffer.append(data + "n");
                String[] strParts = data.split( "," );
                System.out.println(data);
                Literature literature = new Literature(Integer.parseInt(strParts[0]), strParts[1], strParts[2], strParts[3], strParts[4], strParts[5],strParts[6]);
                System.out.println(data);
                literatureDatabase.addQuestion(literature);
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return literatureDatabase;
    }

    //process is replicated from above
    public NumeracyDatabase createThatNumeracyDatabase(Context context)
    {
        NumeracyDatabase numeracyDatabase = new NumeracyDatabase(context);
        String data = "";
        StringBuffer stringBuffer = new StringBuffer();
        InputStream is = context.getResources().openRawResource(R.raw.math);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        try
        {
            while((data = reader.readLine()) != null)
            {
                stringBuffer.append(data + "n");
                String[] strParts = data.split( "," );
                System.out.println(data);
                Numeracy numeracy = new Numeracy(Integer.parseInt(strParts[0]), strParts[1], Integer.parseInt(strParts[2]));
                System.out.println(data);
                numeracyDatabase.addQuestion(numeracy);
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return numeracyDatabase;
    }
}
