package com.example.assignment2;

import java.io.Serializable;

public class Users implements Serializable
{
    //User variables needed
    private String fullName;
    private String password;
    private String email;
    private int literatureScore;
    private int numeracyScore;
    private int criticalThinkingScore;
    private int id;


    //empty constructor if needed to create an object that doesnt take any variables
    Users()
    {

    }

    //constructor with variables that is usually used oncreating a new user, or updating a using the database
    public Users(int id,String fullName,  String email,String pW, int literatureScore,int numeracyScore,int criticalThinkingScore )
    {
        this.fullName = fullName;
        this.password = pW;
        this.email = email;
        this.id = id;
        this.criticalThinkingScore = criticalThinkingScore;
        this.numeracyScore = numeracyScore;
        this.literatureScore = literatureScore;
    }



    //getters and setters needed to update the user through encapsulation
    public int getIdNumber(){return id;}

    public void setId(int id) { this.id = id;}

    public void setEmail(String email) { this.email = email; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public void setPassword(String password) { this.password = password; }

    public String getPassword()
    {
        return password;
    }

    public String getEmail() { return email; }

    public void setLiteratureScore(int literatureScore) { this.literatureScore = literatureScore; }

    public void setNumeracyScore(int numeracyScore) { this.numeracyScore = numeracyScore; }

    public void setCriticalThinkingScore(int criticalThinkingScore) { this.criticalThinkingScore = criticalThinkingScore; }

    public int getCriticalThinkingScore() { return criticalThinkingScore; }

    public int getLiteratureScore() { return literatureScore; }

    public int getNumeracyScore() { return numeracyScore; }

    public String getFullName() { return fullName; }
}
