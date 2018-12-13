package com.daedalusacademy.learncalculusbydoing;

import androidx.lifecycle.ViewModel;

public class QuizActivityViewModel extends ViewModel {
    private static final int QUESTIONS_TOTAL = 10;

    private int score, questionNumber = 1;
    private Question[] questionsList = new Question[QUESTIONS_TOTAL];
    private String currentButtonState;

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

    public String getCurrentButtonState() {
        return currentButtonState;
    }

    public void setCurrentButtonState(String currentButtonState) {
        this.currentButtonState = currentButtonState;
    }
}
