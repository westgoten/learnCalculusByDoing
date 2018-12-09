package com.daedalusacademy.learncalculusbydoing;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import io.github.kexanie.library.MathView;

public class SingleAnswerQuestion implements ObjectiveQuestion {
    private static final int numberOfOptions = 4;
    private static int numberOfQuestions = 0;

    private int questionNumber;
    private Activity activity;
    private static MathView questionTitle;
    private static MathView[] questionOptions = new MathView[numberOfOptions];
    private static RadioButton[] questionButtons = new RadioButton[numberOfOptions];
    private boolean[] answer;

    private static boolean isContentViewVisible = true;
    private static boolean areButtonsVisible = false;
    private static boolean buttonsClickable = true;
    private static int[] optionsBackground = {R.color.noAnswer, R.color.noAnswer, R.color.noAnswer, R.color.noAnswer};

    private static final String TAG = "SingleAnswerQuestion";

    public SingleAnswerQuestion(Context context, boolean[] answer) {
        this.activity = (Activity) context;
        this.answer = answer;

        this.questionNumber = numberOfQuestions++;
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
                optionsBackground[i] = R.color.correctAnswer;
                ((LinearLayout) questionButtons[i].getParent()).setBackgroundResource(R.color.correctAnswer);
            } else if (questionButtons[i].isChecked()) {
                optionsBackground[i] = R.color.wrongAnswer;
                ((LinearLayout) questionButtons[i].getParent()).setBackgroundResource(R.color.wrongAnswer);
            }

            questionButtons[i].setClickable(false);
        }

        buttonsClickable = false;
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
        areButtonsVisible = isVisible;

        for (int i = 0; i < numberOfOptions; i++) {
            if (isVisible) {
                questionButtons[i].setVisibility(View.VISIBLE);
            } else {
                questionButtons[i].setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void resetInputViewsState(){
        for (int i = 0; i < numberOfOptions; i++) {
            if (questionButtons[i].isChecked())
                questionButtons[i].setChecked(false);

            optionsBackground[i] = R.color.noAnswer;
            ((LinearLayout) questionButtons[i].getParent()).setBackgroundResource(R.color.noAnswer);
            questionButtons[i].setClickable(true);
        }

        buttonsClickable = true;
    }

    @Override
    public void setContentViewVisibility(boolean isVisible) {
        isContentViewVisible = isVisible;

        LinearLayout parent = (LinearLayout) questionButtons[0].getParent().getParent();
        if (isVisible)
            parent.setVisibility(View.VISIBLE);
        else
            parent.setVisibility(View.GONE);
    }

    public static void setUpRadioButtons(Activity activity) {
        for (int i = 0; i < numberOfOptions; i++) {
            questionButtons[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    RadioButton radioButton = (RadioButton) buttonView;
                    Button button = activity.findViewById(R.id.button);
                    if (isChecked) {
                        for (int i = 0; i < numberOfOptions; i++) {
                            if (questionButtons[i] != radioButton && questionButtons[i].isChecked())
                                questionButtons[i].setChecked(false);
                        }

                        if (!button.isEnabled()) {
                            button.setEnabled(true);
                        }
                    }
                }
            });
        }
    }

    public static void resetNumberOfQuestions() {
        numberOfQuestions = 0;
    }

    public static void initializeViews(Activity activity) {
        questionTitle = activity.findViewById(R.id.question_title);

        Resources resources = activity.getResources();
        TypedArray optionsArray = resources.obtainTypedArray(R.array.options_views_IDs);
        TypedArray radioButtonsArray = resources.obtainTypedArray(R.array.radioButtons_IDs);

        for (int i = 0; i < numberOfOptions; i++) {
            int optionId = optionsArray.getResourceId(i, 0);
            int radioButtonId = radioButtonsArray.getResourceId(i, 0);

            if (optionId != 0)
                questionOptions[i] = activity.findViewById(optionId);
            else
                Log.v(TAG, "optionId doesn't exist");

            if (radioButtonId != 0) {
                questionButtons[i] = activity.findViewById(radioButtonId);
                questionButtons[i].setClickable(buttonsClickable);
                if (areButtonsVisible) {
                    questionButtons[i].setVisibility(View.VISIBLE);
                    ((LinearLayout) questionButtons[i].getParent()).setBackgroundResource(optionsBackground[i]);
                } else
                    questionButtons[i].setVisibility(View.GONE);
            } else
                Log.v(TAG, "radioButtonId doesn't exist");
        }

        LinearLayout parent = (LinearLayout) questionButtons[0].getParent().getParent();
        if (isContentViewVisible)
            parent.setVisibility(View.VISIBLE);
        else
            parent.setVisibility(View.GONE);

        optionsArray.recycle();
        radioButtonsArray.recycle();
    }

    public static void resetViews() {
        isContentViewVisible = true;
        areButtonsVisible = false;
        buttonsClickable = true;
        optionsBackground = new int[]{R.color.noAnswer, R.color.noAnswer, R.color.noAnswer, R.color.noAnswer};
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
