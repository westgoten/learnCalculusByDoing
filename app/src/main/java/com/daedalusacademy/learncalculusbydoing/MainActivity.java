package com.daedalusacademy.learncalculusbydoing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
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

        TextView questionNumberTextView = findViewById(R.id.question_number);
        questionNumberTextView.setText(getString(R.string.question_number, this.globalQuestionNumber));

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

        this.questionsList[this.globalQuestionNumber-1].setViewsText();
    }
}