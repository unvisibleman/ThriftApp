package com.example.igor.bugetapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.concurrent.Exchanger;

import android.content.SharedPreferences;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ArrayList<element> categories = new ArrayList<element>();
    ArrayList<String> pureCategories = new ArrayList<String>();
    ListAdapter listAdapter;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.app_name);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        final ListView list = (ListView)findViewById(R.id.catList);
        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                startActivityFromChild(MainActivity.this, new Intent(
                        MainActivity.this, CategoryActivity.class
                ).putExtra("cat", position).putExtra("catName", pureCategories.get(position)).putExtra("token", token), 0);
            }
        });

        token = "";
        letUseInternet();
        refresh();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) { return true; }
        return super.onOptionsItemSelected(item);
    }

    public void refresh(){
        // проверяем аккаунт
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        // если ни одного нет, то вывести уведомление
        if(settings.getString("Username", "").isEmpty()){
            Toast.makeText(this, "Аккаунт не создан! Зайдите в настройки", Toast.LENGTH_SHORT).show();
        }else{
            new AsyncTask<Void, Void, JSONArray>() {
                private String err;
                @Override protected JSONArray doInBackground(Void... params) {
                    SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                    apiClient myclient = new apiClient();
                    JSONObject res = myclient.auth(
                            settings.getString("Server", ""),
                            settings.getString("Username", ""),
                            settings.getString("Password", "")
                    );
                    try{
                        if (res.getInt("code") == 400) {
                            throw new Exception(res.getString("data"));
                        } else {
                            // токен получен
                            token = res.getString("data");
                            return myclient.categoryList(settings.getString("Server", ""), token).getJSONArray("data");
                        }
                    }catch(Exception e){
                        err = e.getMessage();
                        return null;
                    }
                }

                @Override protected void onPostExecute(JSONArray cats){
                    if(cats!=null) {
                        // выводим данные из джейсона в лист вью
                        categories.clear();
                        pureCategories.clear();
                        try {
                            for (int i = 0; i < cats.length(); i++) {
                                categories.add(new element(cats.getJSONObject(i).getString("name"), cats.getJSONObject(i).getInt("sum")));
                                pureCategories.add(cats.getJSONObject(i).getString("name"));
                            }
                        }catch(Exception e){Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();}

                        listAdapter = new ListAdapter(MainActivity.this, categories);
                        ListView lvMain = (ListView) findViewById(R.id.catList);
                        lvMain.setAdapter(listAdapter);
                    }else{
                        Toast.makeText(MainActivity.this, err, Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        }
    }

    //click on elements in list
    public void catListClick(View v){
        // do smth
        startActivityFromChild(this, new Intent(this, CategoryActivity.class), 0);
    }

    // add waste floating button click
    public void fabClick(View v){
        startActivityFromChild(this,
                new Intent(this, EditActivity.class).putExtra("cats", pureCategories).putExtra("token", token)
                , 0);
    }

    public void fabRefresh(View v){
        refresh();
    }

    // add waste menu click
    public void menuAddWasteClick(MenuItem v){
        startActivityFromChild(this, new Intent(this, EditActivity.class).putExtra("cats", pureCategories).putExtra("token", token), 0);
    }

    // add profit menu click
    public void menuAddProfitClick(MenuItem v){
        startActivityFromChild(this, new Intent(this, EditActivity.class).putExtra("cats", pureCategories).putExtra("token", token), 0);
    }

    // Analytics menu click
    public void menuAnalyticsClick(MenuItem v){
        startActivityFromChild(this, new Intent(this, AnalyticsActivity.class), 0);
    }

    // Settings menu click
    public void menuSettingsClick(MenuItem v){
        startActivityFromChild(this, new Intent(this, SettingsActivity.class), 0);
    }

    // help/about menu click
    public void menuHelpClick(MenuItem v){
        startActivityFromChild(this, new Intent(this, AboutActivity.class), 0);
    }

    public void letUseInternet(){
        StrictMode.ThreadPolicy pol = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(pol);
    }

}
