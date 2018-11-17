package com.daedalusacademy.learncalculusbydoing;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {
    private int mScore;
    private int mGlobalQuestionNumber;
    private Question[] mQuestionsList;

    //private static final String STATE_SCORE = "userScore";
    //private static final String STATE_QUESTION_NUMBER = "globalQuestionNumber";
    //private static final String STATE_QUESTION_LIST = "questionList";
    private static final int QUESTIONS_TOTAL = 10;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivityViewModel viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        mQuestionsList = new Question[QUESTIONS_TOTAL];

        // Fix unsolicited focus request to EditText view on TextAnswerQuestions
        ScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusableInTouchMode(true);

        mGlobalQuestionNumber = 1;
        MultipleAnswerQuestion.resetNumberOfQuestions();
        SingleAnswerQuestion.resetNumberOfQuestions();
        TextAnswerQuestion.resetNumberOfQuestions();

        String[] questionsTypes = getResources().getStringArray(R.array.quiz_types);
        for (int i = 0; i < QUESTIONS_TOTAL; i++) {
            switch (questionsTypes[i]) {
                case "MAQ":
                    mQuestionsList[i] = new MultipleAnswerQuestion(this, QuizAnswers.getObjectiveAnswer(i));
                    break;
                case "SAQ":
                    mQuestionsList[i] = new SingleAnswerQuestion(this, QuizAnswers.getObjectiveAnswer(i));
                    break;
                case "TAQ":
                    mQuestionsList[i] = new TextAnswerQuestion(this, QuizAnswers.getTextAnswer(i));
                    break;
            }
        }

        SingleAnswerQuestion.setUpRadioButtons();
        this.setUpNextQuestionText();

        final String[] buttonStateList = {getString(R.string.button_text_submit),
                getString(R.string.button_text_next),
                getString(R.string.button_text_finish)};

        Button clickButton = findViewById(R.id.button);
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String currentButtonState = button.getText().toString();
                Question currentQuestion = mQuestionsList[mGlobalQuestionNumber - 1];

                if (currentButtonState.equals(buttonStateList[0])) {
                    if (currentQuestion.hasUserInput()) {
                        if (currentQuestion.isAnswerCorrect())
                            mScore++;
                        currentQuestion.highlightAnswer();
                        if (mGlobalQuestionNumber < QUESTIONS_TOTAL)
                            button.setText(R.string.button_text_next);
                        else
                            button.setText(R.string.button_text_finish);
                    }
                } else if (currentButtonState.equals(buttonStateList[1])) {
                    mGlobalQuestionNumber++;
                    setUpNextQuestionText();
                    currentQuestion.resetInputViewsState();
                    button.setText(R.string.button_text_submit);
                    currentQuestion.setInputViewsVisibility(false);
                    mQuestionsList[mGlobalQuestionNumber - 1].setInputViewsVisibility(true);
                } else if (currentButtonState.equals(buttonStateList[2])) {
                    // TO DO (Intent to EndScreen Activity)
                }
            }
        });
    }

    private void setUpNextQuestionText() {
        TextView questionNumberTextView = findViewById(R.id.question_number);
        questionNumberTextView.setText(getString(R.string.question_number, mGlobalQuestionNumber));

        mQuestionsList[mGlobalQuestionNumber -1].setViewsText();
    }
}