package com.example.igor.bugetapp;

import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    ArrayList<element> categories = new ArrayList<element>();
    ListAdapter listAdapter;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Intent intent = getIntent();
        int catID = intent.getIntExtra("cat", 0)+1;
        String token = intent.getStringExtra("token");
        String server = getSharedPreferences("UserInfo", 0).getString("Server", "");

        // получаем номер текущего месяц
        Calendar c = Calendar.getInstance();
        int month = Integer.parseInt(new SimpleDateFormat("M").format(c.getTime()));
        int year =  Integer.parseInt(new SimpleDateFormat("YYYY").format(c.getTime()));
        try {
            apiClient myclient = new apiClient();
            JSONObject answer = myclient.items(server, token, month, year, catID);
            if (answer.getInt("code") == 400) {
                // if error
                throw new Exception(answer.getString("data"));
            }else {
                // в противном слуачае извлекаем данные
                JSONArray items = answer.getJSONArray("data");
                // создаем адаптер списка
                for (int i = 0; i < items.length(); i++)
                    categories.add(new element(items.getJSONObject(i).getString("title"), items.getJSONObject(i).getInt("price")));
                listAdapter = new ListAdapter(this, categories);
                // настраиваем список
                ListView lvItmes = (ListView) findViewById(R.id.itemsList);
                lvItmes.setAdapter(listAdapter);
            }
        }catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
