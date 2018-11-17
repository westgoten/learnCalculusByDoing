package com.daedalusacademy.learncalculusbydoing;

import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    private int score, questionNumber;
    private Question[] questionsList;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public Question[] getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(Question[] questionsList) {
        this.questionsList = questionsList;
    }
}
