package com.westgoten.learncalculusbydoing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MenuActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setContentView(R.layout.activity_menu);

        Button button = findViewById(R.id.menu_start_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.westgoten.learncalculusbydoing",
                        "com.westgoten.learncalculusbydoing.QuizActivity");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.TUTORIAL_BEGIN, null);
                startActivity(intent);
                finish();
            }
        });
    }
}
