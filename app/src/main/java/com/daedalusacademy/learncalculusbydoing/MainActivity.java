package com.daedalusacademy.learncalculusbydoing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import io.github.kexanie.library.MathView;

public class MainActivity extends AppCompatActivity {
    private TextView questionNumberText;
    private MathView questionTitle;
    private MathView[] questionOptions = new MathView[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.questionNumberText = findViewById(R.id.question_number);
        this.questionNumberText.setText(String.format(getString(R.string.question_number), 1));

        this.questionTitle = findViewById(R.id.question_title);
        this.questionTitle.setText(getString(R.string.question1_title));

        this.initializeQuestionView(1, R.id.question_op1, R.string.question1_option1);
        this.initializeQuestionView(2, R.id.question_op2, R.string.question1_option2);
        this.initializeQuestionView(3, R.id.question_op3, R.string.question1_option3);
        this.initializeQuestionView(4, R.id.question_op4, R.string.question1_option4);
    }

    private void initializeQuestionView(int questionNumber, int viewId, int viewStringId) {
        this.questionOptions[questionNumber-1] = findViewById(viewId);
        this.questionOptions[questionNumber-1].setText(getString(viewStringId));
    }

}
