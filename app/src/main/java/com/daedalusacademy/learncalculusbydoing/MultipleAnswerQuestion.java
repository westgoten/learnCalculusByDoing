package com.daedalusacademy.learncalculusbydoing;

import android.content.Context;
import android.widget.CheckBox;
import io.github.kexanie.library.MathView;

public class MultipleAnswerQuestion {
    private Context context;
    private int questionNumber;
    private MathView questionTitle;
    private MathView[] questionOptions;
    private CheckBox[] questionButtons;
    private boolean[] answer;

    private final static int titleId = R.id.question_title;
    private final static int[] optionsId = {R.id.question_op1, R.id.question_op2, R.id.question_op3,
            R.id.question_op4};
    private final static int[] buttonsId = {R.id.checkbox1, R.id.checkbox2, R.id.checkbox3,
            R.id.checkbox4};

    public MultipleAnswerQuestion(Context context, int questionNumber, boolean[] answer) {
        this.context = context;
        this.questionNumber = questionNumber;
        this.answer = answer;
    }

    public Context getContext() {
        return context;
    }

    public int getQuestionNumber() {
        return questionNumber;
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
