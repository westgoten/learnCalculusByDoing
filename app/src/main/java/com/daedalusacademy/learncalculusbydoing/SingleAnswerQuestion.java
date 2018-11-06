package com.daedalusacademy.learncalculusbydoing;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
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

    private static final String TAG = "SingleAnswerQuestion";

    public SingleAnswerQuestion(Context context, boolean[] answer) {
        this.activity = (Activity) context;
        this.answer = answer;

        this.questionNumber = numberOfQuestions++;

        if (this.questionNumber == 0) {
            initializeViews();
        }
    }

    @Override
    public void setViewsText() {
        Resources resources = this.activity.getResources();
        TypedArray SAQArray = resources.obtainTypedArray(R.array.SAQ_array1);
        int SAQStringArrayId = SAQArray.getResourceId(this.questionNumber, 0);

        String[] SAQStringArray;
        if (SAQStringArrayId > 0) {
            SAQStringArray = resources.getStringArray(SAQStringArrayId);

            questionTitle.setText(SAQStringArray[0]);

            for (int i = 0; i < numberOfOptions; i++) {
                questionOptions[i].setText(SAQStringArray[i+1]);
            }
        } else {
            Log.v(TAG, "SAQStringArrayId doesn't exist.");
        }

        SAQArray.recycle();
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
            if (this.answer[i]) {
                ((LinearLayout) questionButtons[i].getParent()).setBackgroundResource(R.color.correctAnswer);
            } else if (questionButtons[i].isChecked()) {
                ((LinearLayout) questionButtons[i].getParent()).setBackgroundResource(R.color.wrongAnswer);
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
    public void resetInputViewsState(){
        for (int i = 0; i < numberOfOptions; i++) {
            if (questionButtons[i].isChecked())
                questionButtons[i].setChecked(false);

            ((LinearLayout) questionButtons[i].getParent()).setBackgroundResource(R.color.noAnswer);
            questionButtons[i].setClickable(true);
        }
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

    public static void resetNumberOfQuestions() {
        numberOfQuestions = 0;
    }

    private void initializeViews() {
        questionTitle = this.activity.findViewById(R.id.question_title);

        Resources resources = this.activity.getResources();
        TypedArray optionsArray = resources.obtainTypedArray(R.array.options_views_IDs);
        TypedArray radioButtonsArray = resources.obtainTypedArray(R.array.radioButtons_IDs);

        for (int i = 0; i < numberOfOptions; i++) {
            int optionId = optionsArray.getResourceId(i, 0);
            int radioButtonId = radioButtonsArray.getResourceId(i, 0);

            if (optionId != 0)
                questionOptions[i] = this.activity.findViewById(optionId);
            else
                Log.v(TAG, "optionId doesn't exist");

            if (radioButtonId != 0)
                questionButtons[i] = this.activity.findViewById(radioButtonId);
            else
                Log.v(TAG, "radioButtonId doesn't exist");
        }

        optionsArray.recycle();
        radioButtonsArray.recycle();
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
