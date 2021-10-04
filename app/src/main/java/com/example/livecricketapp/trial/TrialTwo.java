package com.example.livecricketapp.trial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.livecricketapp.R;

public class TrialTwo extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_two);

        webView = findViewById(R.id.webView);

        webView.loadUrl("https://webdemo.agora.io/basicVideoCall/index.html");
    }
}