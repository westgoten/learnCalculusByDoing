package com.daedalusacademy.learncalculusbydoing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int score = 0;
    private static int globalQuestionNumber;
    private static final int questionsTotal = 10;
    private static Question[] questionsList = new Question[questionsTotal];

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        globalQuestionNumber = 1;
        MultipleAnswerQuestion.resetNumberOfQuestions();
        SingleAnswerQuestion.resetNumberOfQuestions();
        TextAnswerQuestion.resetNumberOfQuestions();

        String[] questionsTypes = getResources().getStringArray(R.array.quiz_types);
        for (int i = 0; i < 6; i++) { // i < questionsTotal
            switch (questionsTypes[i]) {
                case "MAQ":
                    questionsList[i] = new MultipleAnswerQuestion(this, QuizAnswers.getObjectiveAnswer(i));
                    break;
                case "SAQ":
                    questionsList[i] = new SingleAnswerQuestion(this, QuizAnswers.getObjectiveAnswer(i));
                    break;
                case "TAQ":
                    questionsList[i] = new TextAnswerQuestion(this, QuizAnswers.getTextAnswer(i));
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
                Question currentQuestion = questionsList[globalQuestionNumber-1];

                if (currentButtonState.equals(buttonStateList[0])) {
                    if (currentQuestion.hasUserInput()) {
                        if (currentQuestion.isAnswerCorrect())
                            score++;
                        currentQuestion.highlightAnswer();
                        if (globalQuestionNumber < questionsTotal)
                            button.setText(R.string.button_text_next);
                        else
                            button.setText(R.string.button_text_finish);
                    }
                } else if (currentButtonState.equals(buttonStateList[1])) {
                    globalQuestionNumber++;
                    setUpNextQuestionText();
                    currentQuestion.resetInputViewsState();
                    button.setText(R.string.button_text_submit);
                    currentQuestion.setInputViewsVisibility(false);
                    questionsList[globalQuestionNumber-1].setInputViewsVisibility(true);
                } else if (currentButtonState.equals(buttonStateList[2])) {
                    // TO DO (Intent to EndScreen Activity)
                }
            }
        });
    }

    private void setUpNextQuestionText() {
        TextView questionNumberTextView = findViewById(R.id.question_number);
        questionNumberTextView.setText(getString(R.string.question_number, globalQuestionNumber));

        questionsList[globalQuestionNumber-1].setViewsText();
    }
}