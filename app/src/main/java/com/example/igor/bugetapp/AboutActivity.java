package com.example.igor.bugetapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.help);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
