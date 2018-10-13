package com.daedalusacademy.learncalculusbydoing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int score = 0;
    private int globalQuestionNumber;
    private static final int questionsTotal = 10;
    private Question[] questionsList = new Question[questionsTotal];

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.globalQuestionNumber = 1;
        MultipleAnswerQuestion.resetNumberOfQuestions();

        String[] questionsTypes = getResources().getStringArray(R.array.quiz_types);
        for (int i = 0; i < 2; i++) {
            switch (questionsTypes[i]) {
                case "MAQ":
                    this.questionsList[i] = new MultipleAnswerQuestion(this, QuizAnswers.getObjectiveAnswer(i));
                    break;
                case "SAQ":
                    break;
                case "TAQ":
                    break;
            }
        }

        this.setUpNextQuestionText();

        final String[] buttonStateList = {getString(R.string.button_text_submit),
                getString(R.string.button_text_next),
                getString(R.string.button_text_finish)};

        final Button clickButton = findViewById(R.id.button);
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentButtonState = clickButton.getText().toString();
                Question currentQuestion = questionsList[globalQuestionNumber-1];

                if (currentQuestion instanceof MultipleAnswerQuestion) {
                   if (currentButtonState.equals(buttonStateList[0])) {
                       if (currentQuestion.hasUserInput()) {
                           if (currentQuestion.isAnswerCorrect())
                               score++;
                           currentQuestion.highlightAnswer();
                           if (globalQuestionNumber < questionsTotal)
                               clickButton.setText(R.string.button_text_next);
                           else
                               clickButton.setText(R.string.button_text_finish);
                           }
                   } else if (currentButtonState.equals(buttonStateList[1])) {
                       globalQuestionNumber++;
                       setUpNextQuestionText();
                       ((MultipleAnswerQuestion) currentQuestion).resetButtonsState();
                       clickButton.setText(R.string.button_text_submit);
                   } else if (currentButtonState.equals(buttonStateList[2])) {
                       // TO DO (Intent to EndScreen Activity)
                   }
                } //TO DO (Other question types cases)
            }
        });
    }

    private void setUpNextQuestionText() {
        TextView questionNumberTextView = findViewById(R.id.question_number);
        questionNumberTextView.setText(getString(R.string.question_number, this.globalQuestionNumber));

        this.questionsList[this.globalQuestionNumber-1].setViewsText();
    }
}