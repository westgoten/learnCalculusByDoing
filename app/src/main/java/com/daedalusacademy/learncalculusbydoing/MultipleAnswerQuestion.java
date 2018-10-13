package com.daedalusacademy.learncalculusbydoing;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import io.github.kexanie.library.MathView;

public class MultipleAnswerQuestion implements Question {
    private static final int numberOfOptions = 4;
    private static int numberOfQuestions = 0;

    private int questionNumber;
    private Activity activity;
    private MathView questionTitle;
    private MathView[] questionOptions = new MathView[numberOfOptions];
    private CheckBox[] questionButtons = new CheckBox[numberOfOptions];
    private boolean[] answer;

    private static final int titleId = R.id.question_title;
    private static final int[] optionsId = {R.id.question_op1, R.id.question_op2, R.id.question_op3,
            R.id.question_op4};
    private final static int[] buttonsId = {R.id.checkbox1, R.id.checkbox2, R.id.checkbox3,
            R.id.checkbox4};

    private static final String TAG = "MultipleAnswerQuestion";

    public MultipleAnswerQuestion(Context context, boolean[] answer) {
        this.activity = (Activity) context;
        this.answer = answer;

        this.questionTitle = this.activity.findViewById(titleId);

        for (int i = 0; i < numberOfOptions; i++) {
            this.questionOptions[i] = this.activity.findViewById(optionsId[i]);
            this.questionButtons[i] = this.activity.findViewById(buttonsId[i]);
        }

        this.questionNumber = numberOfQuestions++;
    }

    @Override
    public void setViewsText() {
        Resources resources = this.activity.getResources();
        TypedArray MAQArray = resources.obtainTypedArray(R.array.MAQ_array1);
        int MAQStringArrayId = MAQArray.getResourceId(this.questionNumber, 0);

        String[] MAQStringArray;
        if (MAQStringArrayId > 0) {
            MAQStringArray = resources.getStringArray(MAQStringArrayId);

            this.questionTitle.setText(MAQStringArray[0]);

            for (int i = 0; i < numberOfOptions; i++)
                this.questionOptions[i].setText(MAQStringArray[i+1]);
        } else {
            Log.v(TAG, "MAQStringArrayId doesn't exist");
        }

        MAQArray.recycle();
    }

    @Override
    public boolean isAnswerCorrect() {
        for (int i = 0; i < numberOfOptions; i++)
            if (this.questionButtons[i].isChecked() != this.answer[i])
                return false;
        return true;
    }

    @Override
    public void highlightAnswer() {
        for (int i = 0; i < numberOfOptions; i++) {
            if (this.questionButtons[i].isChecked() != this.answer[i])
                ((LinearLayout) this.questionButtons[i].getParent()).setBackgroundResource(R.color.wrongAnswer);
            else
                ((LinearLayout) this.questionButtons[i].getParent()).setBackgroundResource(R.color.correctAnswer);

            this.questionButtons[i].setClickable(false);
        }
    }

    @Override
    public boolean hasUserInput() {
        for (int i = 0; i < numberOfOptions; i++) {
            if (this.questionButtons[i].isChecked())
                return true;
        }
        return false;
    }

    public static void resetNumberOfQuestions() {
        numberOfQuestions = 0;
    }

    public void resetButtonsState(){
        for (int i = 0; i < numberOfOptions; i++) {
            if (this.questionButtons[i].isChecked())
                this.questionButtons[i].setChecked(false);

            ((LinearLayout) this.questionButtons[i].getParent()).setBackgroundResource(R.color.noAnswer);
            this.questionButtons[i].setClickable(true);
        }
    }

    public Activity getActivity() { return activity; }

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

    public static int getNumberOfOptions() {
        return numberOfOptions;
    }
}
