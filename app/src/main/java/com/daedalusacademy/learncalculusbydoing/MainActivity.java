package com.daedalusacademy.learncalculusbydoing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import io.github.kexanie.library.MathView;

public class MainActivity extends AppCompatActivity {
    private MathView question1_op1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();

        question1_op1 = findViewById(R.id.question1_op1);
        question1_op1.setText(getString(R.string.question1_option1));
    }
}
