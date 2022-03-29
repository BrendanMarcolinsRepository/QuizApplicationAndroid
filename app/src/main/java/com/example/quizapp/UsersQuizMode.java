package com.example.quizapp;

//similar to the Users class comments on the operation of this java file
public class UsersQuizMode
{
    private int id;
    private String userName;
    private String email;
    private String quizName;
    private int score;
    private String date;


    public UsersQuizMode(int id, String userName, String email, String quizName, int score, String date) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.quizName = quizName;
        this.score = score;
        this.date = date;

    }

    public UsersQuizMode() {

    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    //returns a to string of method on default call on a system.out.print for example
    public String toString() {
        return String.format("%s Area - attempt started on %s - points earned %s", getQuizName(),getDate(), getScore());
    }
}
