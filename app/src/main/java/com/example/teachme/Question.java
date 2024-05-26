package com.example.teachme;

public class Question {
    private String questionText;

    public Question() {
        // Empty constructor needed for Firestore
    }

    public Question(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
