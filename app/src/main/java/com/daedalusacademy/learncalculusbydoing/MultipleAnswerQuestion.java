package com.daedalusacademy.learncalculusbydoing;

import android.app.Activity;
import android.content.Context;
import android.widget.CheckBox;
import io.github.kexanie.library.MathView;

public class MultipleAnswerQuestion implements Question {
    private final static int numberOfOptions = 4;
    private static int numberOfQuestions = 0;

    private int questionNumber;
    private Activity activity;
    private MathView questionTitle;
    private MathView[] questionOptions = new MathView[numberOfOptions];
    private CheckBox[] questionButtons = new CheckBox[numberOfOptions];
    private boolean[] answer;

    private final static int titleId = R.id.question_title;
    private final static int[] optionsId = {R.id.question_op1, R.id.question_op2, R.id.question_op3,
            R.id.question_op4};
    private final static int[] buttonsId = {R.id.checkbox1, R.id.checkbox2, R.id.checkbox3,
            R.id.checkbox4};

    public MultipleAnswerQuestion(Context context, boolean[] answer) {
        this.activity = (Activity) context;
        this.answer = answer;

        this.questionTitle = this.activity.findViewById(titleId);

        for (int i = 0; i < numberOfOptions; i++) {
            this.questionOptions[i] = this.activity.findViewById(optionsId[i]);
            this.questionButtons[i] = this.activity.findViewById(buttonsId[i]);
        }

        this.questionNumber = ++numberOfQuestions;
    }

    @Override
    public void setViewsText() {

    }

    @Override
    public boolean isAnswerCorrect() {
        return false;
    }

    public Activity getActivity() {
        return activity;
    }

    public MathView getQuestionTitle() {
        return questionTitle;
    }

    public MathView[] getQuestionOptions() {
        return questionOptions;
    }

    public CheckBox[] getQuestionButtons() {
        return questionButtons;
    }

    public boolean[] getAnswer() {
        return answer;
    }
}
