package com.daedalusacademy.learncalculusbydoing;

public interface Question {
    void setViewsText();
    boolean isAnswerCorrect();
    void highlightAnswer();
    boolean hasUserInput();
    void setInputViewsVisibility(boolean isVisible);
    void resetInputViewsState();
}
