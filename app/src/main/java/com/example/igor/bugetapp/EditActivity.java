package com.example.igor.bugetapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import java.util.*;

public class EditActivity extends AppCompatActivity {

    Spinner spCats;
    EditText edPrice, edTitle;
    CalendarView calendar;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.addElement);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        spCats = (Spinner) findViewById(R.id.spCats);
        edPrice = (EditText) findViewById(R.id.edPrice);
        edTitle = (EditText) findViewById(R.id.edTitle);
        calendar = (CalendarView) findViewById(R.id.calendarView);

        Intent intent = getIntent();
        ArrayList<String> categories = intent.getStringArrayListExtra("cats");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        spCats.setAdapter(adapter);

        token = intent.getStringExtra("token");
    }

    public void btnCancel(View v){
        finish();
    }

    public void btnSave(View v){
        int catID = spCats.getSelectedItemPosition()+1;
        String sPrice = edPrice.getText().toString();
        int price = 0;
        if(!sPrice.isEmpty()) price = Integer.parseInt(sPrice);

        String server = getSharedPreferences("UserInfo", 0).getString("Server", "");
        String title = edTitle.getText().toString();
        Calendar c = Calendar.getInstance();
        String selectedDate = Integer.toString(c.get(c.YEAR)) + "." + Integer.toString(c.get(c.MONTH)+1) + "." + Integer.toString(c.get(c.DAY_OF_MONTH));
        //String selectedDate = new SimpleDateFormat("yyyy.MM.dd").format(calendar.getDate());
        Toast.makeText(this, selectedDate, Toast.LENGTH_SHORT).show();
        try {
            apiClient myclient = new apiClient();
            // TODO: unfix 0 group
            JSONObject res = myclient.addItem(server, token, price, selectedDate, title, 0, catID);
            if(res.getInt("code") != 200)
                Toast.makeText(this, "Ошибка: "+res.getString("data"), Toast.LENGTH_SHORT).show();
            finish();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}
