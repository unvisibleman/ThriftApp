package com.example.igor.bugetapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;

public class AnalyticsActivity extends AppCompatActivity {

    ArrayList<element> parts = new ArrayList<element>();
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.analytics);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        // создаем адаптер
        fillData();
        listAdapter = new ListAdapter(this, parts);

        // настраиваем список
        ListView lvMain = (ListView) findViewById(R.id.valuesList);
        lvMain.setAdapter(listAdapter);
    }

    void fillData() {
        for (String elem : getResources().getStringArray(R.array.analytics)) {
            parts.add(new element(elem, 0));
        }
    }
}
