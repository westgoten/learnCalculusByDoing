package com.daedalusacademy.learncalculusbydoing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import io.github.kexanie.library.MathView;

public class MainActivity extends AppCompatActivity {
    private int globalQuestionNumber = 1;

    private final boolean[] questionAnswer1 = {false, false, true, true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}