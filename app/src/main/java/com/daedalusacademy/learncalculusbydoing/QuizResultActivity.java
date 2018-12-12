package com.daedalusacademy.learncalculusbydoing;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class QuizResultActivity extends AppCompatActivity {
    private static final String EXTRA_SCORE = "com.daedalusacademy.learncalculusbydoing.EXTRA_SCORE";

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        Intent resultIntent = getIntent();
        int score = resultIntent.getIntExtra(EXTRA_SCORE, 0);

        TextView scoreView = findViewById(R.id.result_score_text_view);
        SpannableString string = new SpannableString(String.format(getString(R.string.score), score, 10));
        string.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.primaryColor)), 0, 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        scoreView.setText(string);

        Button button = findViewById(R.id.result_to_menu_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.daedalusacademy.learncalculusbydoing",
                        "com.daedalusacademy.learncalculusbydoing.MenuActivity");
                startActivity(intent);
                finish();
            }
        });
    }
}
