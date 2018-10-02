package com.daedalusacademy.learncalculusbydoing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import mathjaxwebview.MathJaxWebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MathJaxWebView webView = findViewById(R.id.webView);
        webView.setText(getString(R.string.question1_option1));
    }
}
