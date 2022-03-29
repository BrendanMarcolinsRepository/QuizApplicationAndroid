package com.example.assignment2.ui.Literature;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.assignment2.ui.criticalthinking.QuizQuestionCriticalThinking;
//database methods are similar to the Userdatabase with adjust inputs to return or update particular case
public class LiteratureDatabase extends SQLiteOpenHelper
{


    public static final String DATABASE_NAME = "LiteratureQuestionDatabase";
    public static final String USERS_TABLE_NAME = "literatureQuestionTable";
    public static final String USERS_COLUMN_ID = "id";
    public static final String USERS_COLUMN_QUESTION = "question";
    public static final String USERS_COLUMN_ANSWER = "answer";
    public static final String USERS_COLUMN_WRONGANSWERONE = "wronganswerone";
    public static final String USERS_COLUMN_WRONGANSWERTWO = "wronganswertwo";
    public static final String USERS_COLUMN_WRONGANSWERTHREE = "wronganswerthree";
    public static final String USERS_COLUMN_WRONGANSWERFOUR = "wronganswerfour";


    public LiteratureDatabase(Context context)
    {
        super(context,DATABASE_NAME,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + USERS_TABLE_NAME + "("
                + USERS_COLUMN_ID + " INTEGER  PRIMARY KEY," + USERS_COLUMN_QUESTION + " TEXT," + USERS_COLUMN_ANSWER + " TEXT,"
                + USERS_COLUMN_WRONGANSWERONE + " TEXT," + USERS_COLUMN_WRONGANSWERTWO + " TEXT," + USERS_COLUMN_WRONGANSWERTHREE + " TEXT,"
                + USERS_COLUMN_WRONGANSWERFOUR + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        onCreate(db);

    }

    public String getUsersTableName()
    {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor c= database.rawQuery("SELECT name FROM sqlite_master WHERE type = 'literatureQuestionTable'",null);
        c.moveToFirst();
        return c.getString(0);

    }


    public void addQuestion(Literature question)
    {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_ID, question.getId());
        contentValues.put(USERS_COLUMN_QUESTION, question.getQuestion());
        contentValues.put(USERS_COLUMN_ANSWER, question.getAnswer());
        contentValues.put(USERS_COLUMN_WRONGANSWERONE, question.getWronganswerone());
        contentValues.put(USERS_COLUMN_WRONGANSWERTWO, question.getWronganswertwo());
        contentValues.put(USERS_COLUMN_WRONGANSWERTHREE, question.getWronganswerthree());
        contentValues.put(USERS_COLUMN_WRONGANSWERFOUR, question.getWronganswerfour());
        database.insert(USERS_TABLE_NAME, null, contentValues);


    }

    public Literature getQuestion(int id)
    {
        Literature question;
        SQLiteDatabase database = this.getWritableDatabase();


        Cursor cursor = database.query(USERS_TABLE_NAME, new String[] { USERS_COLUMN_ID,
                        USERS_COLUMN_QUESTION,USERS_COLUMN_ANSWER,USERS_COLUMN_WRONGANSWERONE,USERS_COLUMN_WRONGANSWERTWO,USERS_COLUMN_WRONGANSWERTHREE,USERS_COLUMN_WRONGANSWERFOUR }, USERS_COLUMN_ID + "=?",
                new String[] { String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        question = new Literature(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
                cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));


        cursor.close();
        database.close();
        return question;

    }

    public String getAnswer(String answer, int questionNumber)
    {
        SQLiteDatabase database = this.getWritableDatabase();

        String nm = String.valueOf(questionNumber);
        String[] columns = {USERS_COLUMN_ID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = USERS_COLUMN_ID + "=?" + " and " + USERS_COLUMN_ANSWER + "=?";
        String[] selectionArgs = {nm, answer};
        Cursor cursor = db.query(USERS_TABLE_NAME, columns,selection,selectionArgs,null,null,null);
        String dataAnswer = cursor.getString(2);
        cursor.close();
        database.close();
        return dataAnswer;
    }
}
