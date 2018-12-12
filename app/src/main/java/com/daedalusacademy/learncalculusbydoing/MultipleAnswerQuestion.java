package com.daedalusacademy.learncalculusbydoing;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import io.github.kexanie.library.MathView;

public class MultipleAnswerQuestion implements ObjectiveQuestion {
    private static final int numberOfOptions = 4;
    private static int numberOfQuestions = 0;

    private int questionNumber;
    private Activity activity;
    private static MathView questionTitle;
    private static MathView[] questionOptions = new MathView[numberOfOptions];
    private static CheckBox[] questionButtons = new CheckBox[numberOfOptions];
    private boolean[] answer;

    private static boolean isContentViewVisible = true;
    private static boolean areButtonsVisible = true;
    private static boolean buttonsClickable = true;
    private static int[] optionsBackground = {R.color.noAnswer, R.color.noAnswer, R.color.noAnswer, R.color.noAnswer};

    private static final String TAG = "MultipleAnswerQuestion";

    public MultipleAnswerQuestion(Context context, boolean[] answer) {
        this.activity = (Activity) context;
        this.answer = answer;

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
                optionsBackground[i] = R.color.wrongAnswer;
                ((LinearLayout) questionButtons[i].getParent()).setBackgroundResource(R.color.wrongAnswer);
            } else {
                optionsBackground[i] = R.color.correctAnswer;
                ((LinearLayout) questionButtons[i].getParent()).setBackgroundResource(R.color.correctAnswer);
            }

            questionButtons[i].setClickable(false);
            ((LinearLayout) questionButtons[i].getParent()).setClickable(false);
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
            if (isVisible)
                questionButtons[i].setVisibility(View.VISIBLE);
            else
                questionButtons[i].setVisibility(View.GONE);
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
            ((LinearLayout) questionButtons[i].getParent()).setClickable(true);
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

    public static void resetNumberOfQuestions() {
        numberOfQuestions = 0;
    }

    public static void initializeViews(Activity activity) {
        questionTitle = activity.findViewById(R.id.question_title);

        Resources resources = activity.getResources();
        TypedArray optionsArray = resources.obtainTypedArray(R.array.options_views_IDs);
        TypedArray checkBoxesArray = resources.obtainTypedArray(R.array.checkBoxes_IDs);

        for (int i = 0; i < numberOfOptions; i++) {
            int optionId = optionsArray.getResourceId(i, 0);
            int checkBoxId = checkBoxesArray.getResourceId(i, 0);

            if (optionId != 0) {
                questionOptions[i] = activity.findViewById(optionId);
            } else
                Log.v(TAG, "optionId doesn't exist");

            if (checkBoxId != 0) {
                questionButtons[i] = activity.findViewById(checkBoxId);
                questionButtons[i].setClickable(buttonsClickable);

                if (areButtonsVisible) {
                    questionButtons[i].setVisibility(View.VISIBLE);
                    ((LinearLayout) questionButtons[i].getParent()).setBackgroundResource(optionsBackground[i]);
                } else
                    questionButtons[i].setVisibility(View.GONE);
            } else
                Log.v(TAG, "checkBoxId doesn't exist");
        }

        LinearLayout parent = (LinearLayout) questionButtons[0].getParent().getParent();
        if (isContentViewVisible)
            parent.setVisibility(View.VISIBLE);
        else
            parent.setVisibility(View.GONE);

        for (CheckBox checkBox : questionButtons) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    LinearLayout parent = (LinearLayout) buttonView.getParent();
                    int buttonIndex = findButtonIndexInArray(buttonView);
                    if (isChecked) {
                        Button button = activity.findViewById(R.id.button);
                        if (!button.isEnabled()) {
                            button.setEnabled(true);
                        }

                        if (optionsBackground[buttonIndex] == R.color.noAnswer) {
                            parent.setBackgroundResource(R.color.selected);
                            optionsBackground[buttonIndex] = R.color.selected;
                        }
                    } else {
                        int checkedBoxes = 0;
                        for (CheckBox checkBox1 : questionButtons)
                            if (checkBox1.isChecked())
                                checkedBoxes++;

                        if (checkedBoxes == 0) {
                            activity.findViewById(R.id.button).setEnabled(false);
                        }

                        if (optionsBackground[buttonIndex] == R.color.selected) {
                            parent.setBackgroundResource(R.color.noAnswer);
                            optionsBackground[buttonIndex] = R.color.noAnswer;
                        }
                    }
                }
            });
        }

        optionsArray.recycle();
        checkBoxesArray.recycle();
    }

    public static void resetViews() {
        isContentViewVisible = true;
        areButtonsVisible = true;
        buttonsClickable = true;
        optionsBackground = new int[]{R.color.noAnswer, R.color.noAnswer, R.color.noAnswer, R.color.noAnswer};
    }

    private static int findButtonIndexInArray(CompoundButton button) {
        int i;
        for (i = 0; i < numberOfOptions; i++)
            if (questionButtons[i] == button)
                return i;

        return -1;
    }

    public Activity getActivity() { return activity; }

    public static MathView getQuestionTitle() {
        return questionTitle;
    }

    public static MathView[] getQuestionOptions() {
        return questionOptions;
    }

    public static CheckBox[] getQuestionButtons() {
        return questionButtons;
    }

    public boolean[] getAnswer() {
        return answer;
    }

    public static int getNumberOfOptions() {
        return numberOfOptions;
    }

    public static boolean isButtonsClickable() {
        return buttonsClickable;
    }
}
