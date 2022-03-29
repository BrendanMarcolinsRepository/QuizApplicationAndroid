package com.example.assignment2.ui.Numeracy;
//similar to the Users class comments on the operation of this java file
public class Numeracy
{
    int id;
    String question;
    int answer;

    public Numeracy() { }

    public Numeracy(int id, String question, int answer)
    {
        this.id = id;
        this.question = question;
        this.answer = answer;

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    @Override
    public String toString() {
        return "QuizQuestionCriticalThinking{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +

                '}';
    }
}

