package com.daedalusacademy.learncalculusbydoing;

import java.io.Serializable;

public interface Question extends Serializable {
    void setViewsText();
    boolean isAnswerCorrect();
    void highlightAnswer();
    boolean hasUserInput();
    void setInputViewsVisibility(boolean isVisible);
    void resetInputViewsState();
}
