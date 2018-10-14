package com.daedalusacademy.learncalculusbydoing;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import io.github.kexanie.library.MathView;

public class SingleAnswerQuestion implements Question {
    private static final int numberOfOptions = 4;
    private static int numberOfQuestions = 0;

    private int questionNumber;
    private Activity activity;
    private static MathView questionTitle;
    private static MathView[] questionOptions = new MathView[numberOfOptions];
    private static RadioButton[] questionButtons = new RadioButton[numberOfOptions];
    private boolean[] answer;

    private static final int titleId = R.id.question_title;
    private static final int[] optionsId = {R.id.question_op1, R.id.question_op2, R.id.question_op3,
            R.id.question_op4};
    private static final int[] buttonsId = {R.id.radiobutton1, R.id.radiobutton2, R.id.radiobutton3,
            R.id.radiobutton4};

    private static final String TAG = "SingleAnswerQuestion";

    public SingleAnswerQuestion(Context context, boolean[] answer) {
        activity = (Activity) context;
        this.answer = answer;

        this.questionNumber = numberOfQuestions++;

        if (this.questionNumber == 0) {
            questionTitle = activity.findViewById(titleId);

            for (int i = 0; i < numberOfOptions; i++) {
                questionOptions[i] = activity.findViewById(optionsId[i]);
                questionButtons[i] = activity.findViewById(buttonsId[i]);
            }
        }
    }

    @Override
    public void setViewsText() {

    }

    @Override
    public boolean isAnswerCorrect() {
        return false;
    }

    @Override
    public void highlightAnswer() {

    }

    @Override
    public boolean hasUserInput() {
        return false;
    }

    @Override
    public void setInputViewsVisibility(boolean isVisible) {
        for (int i = 0; i < numberOfOptions; i++) {
            if (isVisible) {
                questionButtons[i].setVisibility(View.VISIBLE);
            } else {
                questionButtons[i].setVisibility(View.GONE);
            }
        }

        LinearLayout parent = (LinearLayout) questionButtons[0].getParent().getParent();
        if (isVisible)
            parent.setVisibility(View.VISIBLE);
        else
            parent.setVisibility(View.GONE);
    }

    @Override
    public void resetInputViewsState() {

    }

    public static void setUpRadioButtons() {
        for (int i = 0; i < numberOfOptions; i++) {
            questionButtons[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    RadioButton radioButton = (RadioButton) buttonView;
                    if (isChecked) {
                        for (int i = 0; i < numberOfOptions; i++) {
                            if (questionButtons[i] != radioButton && questionButtons[i].isChecked())
                                questionButtons[i].setChecked(false);
                        }
                    }
                }
            });
        }
    }

    public static int getNumberOfOptions() {
        return numberOfOptions;
    }

    public int getQuestionNumber() {
        return questionNumber;
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

    public RadioButton[] getQuestionButtons() {
        return questionButtons;
    }

    public boolean[] getAnswer() {
        return answer;
    }
}
