package com.example.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
//database methods are similar to the Userdatabase with adjust inputs to return or update particular case
public class UserQuizScoreDatabase extends SQLiteOpenHelper
{


    public static final String DATABASE_NAME = "QuizScoringDatabase";
    public static final String USERS_TABLE_NAME = "QuizScores";
    public static final String USERS_COLUMN_ID = "id";
    public static final String USERS_COLUMN_NAME = "name";
    public static final String USERS_COLUMN_EMAIL = "email";
    public static final String USERS_COLUMN_QUIZNAME = "quizName";
    public static final String USERS_COLUMN_SCORE = "score";
    public static final String USERS_COLUMN_DATE = "date";

    public UserQuizScoreDatabase(Context context)
    {
        super(context,DATABASE_NAME,null,1);

    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + USERS_TABLE_NAME + "("
                + USERS_COLUMN_ID + " INTEGER  PRIMARY KEY," + USERS_COLUMN_NAME + " TEXT,"
                + USERS_COLUMN_EMAIL + " TEXT," + USERS_COLUMN_QUIZNAME + " TEXT," + USERS_COLUMN_SCORE + " INTEGER,"
                + USERS_COLUMN_DATE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }


    public void addUsers(UsersQuizMode usersQuizMode ) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_ID, usersQuizMode.getId());
        contentValues.put(USERS_COLUMN_NAME, usersQuizMode.getUserName());
        contentValues.put(USERS_COLUMN_EMAIL, usersQuizMode.getEmail());
        contentValues.put(USERS_COLUMN_QUIZNAME, usersQuizMode.getQuizName());
        contentValues.put(USERS_COLUMN_SCORE, usersQuizMode.getScore());
        contentValues.put(USERS_COLUMN_DATE, usersQuizMode.getDate());
        database.insert(USERS_TABLE_NAME, null, contentValues);
        database.close();

    }


    public ArrayList<UsersQuizMode> getUsersQuiz()
    {
        ArrayList<UsersQuizMode> users = new ArrayList<UsersQuizMode>();

        String selectQuery = "SELECT * FROM " + USERS_TABLE_NAME;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery,null);


        while(cursor.moveToNext())
        {
            UsersQuizMode user = new UsersQuizMode();
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setUserName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setQuizName(cursor.getString(3));
            user.setScore(Integer.parseInt(cursor.getString(4)));
            user.setDate(cursor.getString(5));
            users.add(user);
        }


        return users;
    }

    public Users getUser(String email)
    {

        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + USERS_TABLE_NAME
                + " WHERE " + USERS_COLUMN_EMAIL + " = " + email;

        Cursor cursor = database.query(USERS_TABLE_NAME, new String[] { USERS_COLUMN_ID,
                        USERS_COLUMN_NAME, USERS_COLUMN_EMAIL,USERS_COLUMN_QUIZNAME,
                        USERS_COLUMN_SCORE,USERS_COLUMN_DATE}, USERS_COLUMN_EMAIL + "=?",
                new String[] { String.valueOf(email) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Users user = new Users(Integer.parseInt(cursor.getString(0)),cursor.getString(2),cursor.getString(3),
                cursor.getString(4),Integer.parseInt(cursor.getString(5)),Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(6)));

        cursor.close();
        database.close();
        return user;
    }

    public int getContactsCount()
    {
        String countQuery = "SELECT  * FROM " + USERS_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

}
