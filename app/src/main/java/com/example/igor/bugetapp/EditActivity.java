package com.example.igor.bugetapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.icu.util.Calendar;
import android.icu.text.SimpleDateFormat;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Button date = (Button) findViewById(R.id.buttonGetDate);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        date.setText(df.format(c.getTime()));
    }
}
