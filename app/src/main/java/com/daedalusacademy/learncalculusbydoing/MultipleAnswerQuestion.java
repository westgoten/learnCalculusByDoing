package com.daedalusacademy.learncalculusbydoing;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import io.github.kexanie.library.MathView;

public class MultipleAnswerQuestion implements Question {
    private static final int numberOfOptions = 4;
    private static int numberOfQuestions = 0;

    private int questionNumber;
    private Activity activity;
    private static MathView questionTitle;
    private static MathView[] questionOptions = new MathView[numberOfOptions];
    private static CheckBox[] questionButtons = new CheckBox[numberOfOptions];
    private boolean[] answer;

    private static final int titleId = R.id.question_title;
    private static final int[] optionsId = {R.id.question_op1, R.id.question_op2, R.id.question_op3,
            R.id.question_op4};
    private static final int[] buttonsId = {R.id.checkbox1, R.id.checkbox2, R.id.checkbox3,
            R.id.checkbox4};

    private static final String TAG = "MultipleAnswerQuestion";

    public MultipleAnswerQuestion(Context context, boolean[] answer) {
        this.activity = (Activity) context;
        this.answer = answer;

        this.questionNumber = numberOfQuestions++;

        if (this.questionNumber == 0) {
            questionTitle = this.activity.findViewById(titleId);

            for (int i = 0; i < numberOfOptions; i++) {
                questionOptions[i] = this.activity.findViewById(optionsId[i]);
                questionButtons[i] = this.activity.findViewById(buttonsId[i]);
            }
        }
    }

    @Override
    public void setViewsText() {
        Resources resources = this.activity.getResources();
        TypedArray MAQArray = resources.obtainTypedArray(R.array.MAQ_array1);
        int MAQStringArrayId = MAQArray.getResourceId(this.questionNumber, 0);

        String[] MAQStringArray;
        if (MAQStringArrayId > 0) {
            MAQStringArray = resources.getStringArray(MAQStringArrayId);

            questionTitle.setText(MAQStringArray[0]);

            for (int i = 0; i < numberOfOptions; i++)
                questionOptions[i].setText(MAQStringArray[i+1]);
        } else {
            Log.v(TAG, "MAQStringArrayId doesn't exist");
        }

        MAQArray.recycle();
    }

    @Override
    public boolean isAnswerCorrect() {
        for (int i = 0; i < numberOfOptions; i++)
            if (questionButtons[i].isChecked() != this.answer[i])
                return false;
        return true;
    }

    @Override
    public void highlightAnswer() {
        for (int i = 0; i < numberOfOptions; i++) {
            if (questionButtons[i].isChecked() != this.answer[i]) {
                ((LinearLayout) questionButtons[i].getParent()).setBackgroundResource(R.color.wrongAnswer);
            } else {
                ((LinearLayout) questionButtons[i].getParent()).setBackgroundResource(R.color.correctAnswer);
            }

            questionButtons[i].setClickable(false);
        }
    }

    @Override
    public boolean hasUserInput() {
        for (int i = 0; i < numberOfOptions; i++) {
            if (questionButtons[i].isChecked())
                return true;
        }
        return false;
    }

    @Override
    public void setInputViewsVisibility(boolean isVisible) {
        for (int i = 0; i < numberOfOptions; i++) {
            if (isVisible)
                questionButtons[i].setVisibility(View.VISIBLE);
            else
                questionButtons[i].setVisibility(View.GONE);
        }

        LinearLayout parent = (LinearLayout) questionButtons[0].getParent().getParent();
        if (isVisible)
            parent.setVisibility(View.VISIBLE);
        else
            parent.setVisibility(View.GONE);
    }

    @Override
    public void resetInputViewsState(){
        for (int i = 0; i < numberOfOptions; i++) {
            if (questionButtons[i].isChecked())
                questionButtons[i].setChecked(false);

            ((LinearLayout) questionButtons[i].getParent()).setBackgroundResource(R.color.noAnswer);
            questionButtons[i].setClickable(true);
        }
    }

    public static void resetNumberOfQuestions() {
        numberOfQuestions = 0;
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
