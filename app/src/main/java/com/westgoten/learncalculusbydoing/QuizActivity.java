package com.westgoten.learncalculusbydoing;

import android.content.Intent;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProviders;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.github.kexanie.library.MathView;

public class QuizActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;

    private QuizActivityViewModel viewModel;

    private static final int QUESTIONS_TOTAL = 10;
    private static final String EXTRA_SCORE = "com.westgoten.learncalculusbydoing.EXTRA_SCORE";
    private static final String TAG = "QuizActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setContentView(R.layout.activity_quiz);

        viewModel = ViewModelProviders.of(this).get(QuizActivityViewModel.class);

        if (savedInstanceState == null)
            resetViews();

        initializeViews();

        // Fix unsolicited focus request to EditText view on TextAnswerQuestions
        ScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusableInTouchMode(true);

        Button clickButton = findViewById(R.id.button);

        if (savedInstanceState == null) {
            resetNumberOfQuestions();

            String[] questionsTypes = getResources().getStringArray(R.array.quiz_types);
            for (int i = 0; i < QUESTIONS_TOTAL; i++) {
                switch (questionsTypes[i]) {
                    case "MAQ":
                        viewModel.getQuestionsList()[i] = new MultipleAnswerQuestion(this, QuizAnswers.getObjectiveAnswer(i));
                        break;
                    case "SAQ":
                        viewModel.getQuestionsList()[i] = new SingleAnswerQuestion(this, QuizAnswers.getObjectiveAnswer(i));
                        break;
                    case "TAQ":
                        viewModel.getQuestionsList()[i] = new TextAnswerQuestion(this, QuizAnswers.getTextAnswer(i));
                        break;
                }
            }

            viewModel.setCurrentButtonState(getString(R.string.button_text_submit));
        } else {
            clickButton.setText(viewModel.getCurrentButtonState());
        }

        SingleAnswerQuestion.setUpRadioButtons(this);
        setUpNextQuestionText();

        final String[] buttonStateList = {getString(R.string.button_text_submit),
                getString(R.string.button_text_next),
                getString(R.string.button_text_finish)};

        clickButton.setOnClickListener(v -> {
                Button button = (Button) v;
                String currentButtonState = button.getText().toString();
                Question currentQuestion = viewModel.getQuestionsList()[viewModel.getQuestionNumber()-1];

                if (currentButtonState.equals(buttonStateList[0])) {
                    if (currentQuestion.hasUserInput()) {
                        if (currentQuestion.isAnswerCorrect())
                            viewModel.setScore(viewModel.getScore() + 1);
                        currentQuestion.highlightAnswer();
                        if (viewModel.getQuestionNumber() < QUESTIONS_TOTAL) {
                            button.setText(R.string.button_text_next);
                            viewModel.setCurrentButtonState(getString(R.string.button_text_next));
                        } else {
                            button.setText(R.string.button_text_finish);
                            viewModel.setCurrentButtonState(getString(R.string.button_text_finish));
                        }
                    }
                } else if (currentButtonState.equals(buttonStateList[1])) {
                    viewModel.setQuestionNumber(viewModel.getQuestionNumber() + 1);
                    setUpNextQuestionText();
                    button.setText(R.string.button_text_submit);
                    button.setEnabled(false);
                    viewModel.setCurrentButtonState(getString(R.string.button_text_submit));

                    currentQuestion.resetInputViewsState();
                    currentQuestion.setInputViewsVisibility(false);
                    if (currentQuestion instanceof ObjectiveQuestion)
                        ((ObjectiveQuestion) currentQuestion).setContentViewVisibility(false);

                    Question nextQuestion = viewModel.getQuestionsList()[viewModel.getQuestionNumber()-1];
                    nextQuestion.setInputViewsVisibility(true);
                    if (nextQuestion instanceof ObjectiveQuestion)
                        ((ObjectiveQuestion) nextQuestion).setContentViewVisibility(true);
                } else if (currentButtonState.equals(buttonStateList[2])) {
                    Intent resultIntent = new Intent();
                    resultIntent.setClassName("com.westgoten.learncalculusbydoing",
                            "com.westgoten.learncalculusbydoing.QuizResultActivity");
                    resultIntent.putExtra(EXTRA_SCORE, viewModel.getScore());
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.TUTORIAL_COMPLETE, null);
                    startActivity(resultIntent);
                    finish();
                }
            });
    }

    private void setUpNextQuestionText() {
        TextView questionNumberTextView = findViewById(R.id.question_number);
        questionNumberTextView.setText(getString(R.string.question_number, viewModel.getQuestionNumber(), QUESTIONS_TOTAL));

        viewModel.getQuestionsList()[viewModel.getQuestionNumber()-1].setViewsText();
    }

    private void initializeViews() {
        MultipleAnswerQuestion.initializeViews(this);
        SingleAnswerQuestion.initializeViews(this);
        TextAnswerQuestion.initializeViews(this);

        // Set up interactive text on ObjectiveQuestions' options
        for (MathView mathView : MultipleAnswerQuestion.getQuestionOptions()) {
            LinearLayout parent = (LinearLayout) mathView.getParent();
            parent.setOnClickListener(v -> {
                    Question currentQuestion = viewModel.getQuestionsList()[viewModel.getQuestionNumber() - 1];
                    if (currentQuestion instanceof MultipleAnswerQuestion) {
                        CheckBox checkBox = (CheckBox) parent.getChildAt(0);
                        if (checkBox.isChecked())
                            checkBox.setChecked(false);
                        else
                            checkBox.setChecked(true);
                    } else if (currentQuestion instanceof SingleAnswerQuestion) {
                        RadioButton radioButton = (RadioButton) parent.getChildAt(1);
                        if (!radioButton.isChecked())
                            radioButton.setChecked(true);
                    }
                });

            // Fix state change of the LinearLayout caused by setOnClickListener() method
            Question currentQuestion = viewModel.getQuestionsList()[viewModel.getQuestionNumber() - 1];
            if (currentQuestion instanceof MultipleAnswerQuestion)
                parent.setClickable(MultipleAnswerQuestion.isButtonsClickable());
            else if (currentQuestion instanceof  SingleAnswerQuestion)
                parent.setClickable(SingleAnswerQuestion.isButtonsClickable());
        }
    }

    private void resetViews() {
        MultipleAnswerQuestion.resetViews();
        SingleAnswerQuestion.resetViews();
        TextAnswerQuestion.resetViews();
    }

    private void resetNumberOfQuestions() {
        MultipleAnswerQuestion.resetNumberOfQuestions();
        SingleAnswerQuestion.resetNumberOfQuestions();
        TextAnswerQuestion.resetNumberOfQuestions();
    }

    public static int getQuestionsTotal() {
        return QUESTIONS_TOTAL;
    }
}