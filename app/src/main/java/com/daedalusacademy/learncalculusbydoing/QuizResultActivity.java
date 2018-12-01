package com.daedalusacademy.learncalculusbydoing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class QuizResultActivity extends AppCompatActivity {
    private static final String EXTRA_SCORE = "com.daedalusacademy.learncalculusbydoing.EXTRA_SCORE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        Intent resultIntent = getIntent();
        int score = resultIntent.getIntExtra(EXTRA_SCORE, 0);

        TextView scoreView = findViewById(R.id.score_view);
        scoreView.setText(String.valueOf(score));
    }
}
