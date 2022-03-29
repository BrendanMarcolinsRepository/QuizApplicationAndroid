package com.example.quizapp.ui.Literature;
//similar to the Users class comments on the operation of this java file
public class Literature
{
    int id;
    String question;
    String answer;
    String wronganswerone;
    String wronganswertwo;
    String wronganswerthree;
    String wronganswerfour;

    public Literature() { }

    public Literature(int id, String question, String answer, String wronganswerone, String wronganswertwo, String wronganswerthree,String wronganswerfour)
    {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.wronganswerone = wronganswerone;
        this.wronganswertwo = wronganswertwo;
        this.wronganswerthree = wronganswerthree;
        this.wronganswerfour = wronganswerfour;


    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getWronganswerone() {
        return wronganswerone;
    }

    public void setWronganswerone(String wronganswerone) {
        this.wronganswerone = wronganswerone;
    }

    public String getWronganswerthree() {
        return wronganswerthree;
    }

    public void setWronganswerthree(String wronganswerthree) {
        this.wronganswerthree = wronganswerthree;
    }

    public String getWronganswertwo() {
        return wronganswertwo;
    }

    public void setWronganswertwo(String wronganswertwo) {
        this.wronganswertwo = wronganswertwo;
    }

    public String getWronganswerfour() {
        return wronganswerfour;
    }

    public void setWronganswerfour(String wronganswerfour) {
        this.wronganswerfour = wronganswerfour;
    }
}
