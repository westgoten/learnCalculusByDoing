package com.daedalusacademy.learncalculusbydoing;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import io.github.kexanie.library.MathView;

public class TextAnswerQuestion implements Question {
    private static int numberOfQuestions = 0;

    private int questionNumber;
    private Activity activity;
    private static MathView questionTitle;
    private EditText editTextAnswer;
    private String answer;

    private Drawable editTextBackground;

    // TO DO: Move this IDs to arrays.xml resource
    private static final int titleId = R.id.question_title;
    private static final int editTextId = R.id.TAQ_edit_text;

    private static final String TAG = "TextAnswerQuestion";

    public TextAnswerQuestion(Context context, String answer) {
        this.activity = (Activity) context;
        this.answer = answer;

        this.questionNumber = numberOfQuestions++;

        if (this.questionNumber == 0) {
            questionTitle = this.activity.findViewById(titleId);
        }

        editTextAnswer = this.activity.findViewById(editTextId);
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
        this.editTextBackground = editTextAnswer.getBackground();

        if (isAnswerCorrect())
            editTextAnswer.setBackgroundResource(R.color.correctAnswer);
        else
            editTextAnswer.setBackgroundResource(R.color.wrongAnswer);

        editTextAnswer.setFocusable(false);
    }

    @Override
    public boolean hasUserInput() {
        return (!(editTextAnswer.getText().toString().equals("")));
    }

    @Override
    public void setInputViewsVisibility(boolean isVisible) {
        if (isVisible)
            editTextAnswer.setVisibility(View.VISIBLE);
        else
            editTextAnswer.setVisibility(View.GONE);
    }

    @Override
    public void resetInputViewsState() {
        editTextAnswer.setText(null);
        editTextAnswer.setBackground(this.editTextBackground);
        editTextAnswer.setFocusableInTouchMode(true);
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

    public static int getTitleId() {
        return titleId;
    }

    public static int getEditTextId() {
        return editTextId;
    }
}
