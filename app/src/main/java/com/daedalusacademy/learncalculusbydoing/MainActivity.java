package com.daedalusacademy.learncalculusbydoing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import io.github.kexanie.library.MathView;

public class MainActivity extends AppCompatActivity {
    private int globalQuestionNumber = 1;
    private static final int questionsTotal = 10;
    private Question[] questionsList = new Question[questionsTotal];

    private static final boolean[] questionAnswer1 = {false, false, true, true};

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView questionNumberTextView = findViewById(R.id.question_number);
        questionNumberTextView.setText(getString(R.string.question_number, this.globalQuestionNumber));

        for (int i = 0; i < 2; i++) {
            this.questionsList[i] = new MultipleAnswerQuestion(this, questionAnswer1);
        }

        this.questionsList[this.globalQuestionNumber-1].setViewsText();
    }
}