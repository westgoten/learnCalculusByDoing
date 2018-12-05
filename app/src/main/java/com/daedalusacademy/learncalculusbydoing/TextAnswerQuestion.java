package com.daedalusacademy.learncalculusbydoing;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import io.github.kexanie.library.MathView;

public class TextAnswerQuestion implements Question {
    private static int numberOfQuestions;

    private int questionNumber;
    private Activity activity;
    private static MathView questionTitle;
    private static EditText editTextAnswer;
    private String answer;

    private static Drawable editTextBackground;

    private static boolean isEditTextVisible = false;
    private static boolean isEditTextFocusable = true;
    private static boolean hasBackground = true;
    private static int editTextBackgroundResource = R.color.noAnswer;

    private static final String TAG = "TextAnswerQuestion";

    public TextAnswerQuestion(Context context, String answer) {
        this.activity = (Activity) context;
        this.answer = answer;

        this.questionNumber = numberOfQuestions++;
    }

    @Override
    public void setViewsText() {
        Resources resources = this.activity.getResources();
        String[] TAQStringArray = resources.getStringArray(R.array.TAQ_stringArray);
        questionTitle.setText(TAQStringArray[this.questionNumber]);
    }

    @Override
    public boolean isAnswerCorrect() {
        return (editTextAnswer.getText().toString().toLowerCase().equals(this.answer));
    }

    @Override
    public void highlightAnswer() {
        editTextBackground = editTextAnswer.getBackground();
        hasBackground = false;

        if (isAnswerCorrect()) {
            editTextBackgroundResource = R.color.correctAnswer;
            editTextAnswer.setBackgroundResource(R.color.correctAnswer);
        } else {
            editTextBackgroundResource = R.color.wrongAnswer;
            editTextAnswer.setBackgroundResource(R.color.wrongAnswer);
        }

        editTextAnswer.setFocusable(false);
        isEditTextFocusable = false;
    }

    @Override
    public boolean hasUserInput() {
        return (!(editTextAnswer.getText().toString().equals("")));
    }

    @Override
    public void setInputViewsVisibility(boolean isVisible) {
        isEditTextVisible = isVisible;

        if (isVisible)
            editTextAnswer.setVisibility(View.VISIBLE);
        else
            editTextAnswer.setVisibility(View.GONE);
    }

    @Override
    public void resetInputViewsState() {
        hasBackground = true;
        editTextAnswer.setText(null);
        if (Build.VERSION.SDK_INT >= 16) {
            editTextAnswer.setBackground(editTextBackground);
        } else {
            editTextAnswer.setBackgroundResource(R.color.noAnswer);
        }

        editTextAnswer.setFocusableInTouchMode(true);
        isEditTextFocusable = true;
    }

    public static void initializeViews(Activity activity) {
        questionTitle = activity.findViewById(R.id.question_title);
        editTextAnswer = activity.findViewById(R.id.TAQ_edit_text);
        if (numberOfQuestions == 0)
            editTextBackground = editTextAnswer.getBackground();

        if (isEditTextVisible)
            editTextAnswer.setVisibility(View.VISIBLE);
        else
            editTextAnswer.setVisibility(View.GONE);

        if (hasBackground) {
            if (Build.VERSION.SDK_INT >= 16)
                editTextAnswer.setBackground(editTextBackground);
            else
                editTextAnswer.setBackgroundResource(R.color.noAnswer);
        } else {
            editTextAnswer.setBackgroundResource(editTextBackgroundResource);
        }

        if (isEditTextFocusable)
            editTextAnswer.setFocusableInTouchMode(true);
        else
            editTextAnswer.setFocusable(false);
    }

    public static void resetViews() {
        isEditTextVisible = false;
        isEditTextFocusable = true;
        hasBackground = true;
        editTextBackgroundResource = R.color.noAnswer;
    }

    public static void resetNumberOfQuestions() {
        numberOfQuestions = 0;
    }

    public static int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public Activity getActivity() {
        return activity;
    }

    public static MathView getQuestionTitle() {
        return questionTitle;
    }

    public EditText getEditTextAnswer() {
        return editTextAnswer;
    }

    public String getAnswer() {
        return answer;
    }
}
