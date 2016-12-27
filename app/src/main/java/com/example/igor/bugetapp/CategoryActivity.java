package com.example.igor.bugetapp;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;;

public class CategoryActivity extends AppCompatActivity {

    ArrayList<item> listItems = new ArrayList<item>();
    ItemListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Intent intent = getIntent();
        int catID = intent.getIntExtra("cat", 0)+1;
        setTitle("Категория: " + intent.getStringExtra("catName") );
        final String token = intent.getStringExtra("token");
        final String server = getSharedPreferences("UserInfo", 0).getString("Server", "");

        // получаем номер текущего месяц
        Calendar c = Calendar.getInstance();
        int month = c.get(c.MONTH)+1; //Integer.parseInt(new SimpleDateFormat("M").format(c.getTime()));
        int year = c.get(c.YEAR); //Integer.parseInt(new SimpleDateFormat("YYYY").format(c.getTime()));

        new AsyncTask<itemsParameters, Void, JSONArray>() {
            private String err;
            @Override protected JSONArray doInBackground(itemsParameters... p) {
                apiClient myclient = new apiClient();
                JSONObject answer = myclient.items(p[0].server, p[0].token, p[0].month, p[0].year, p[0].catID);
                try{
                    if (answer.getInt("code") == 400) {
                        throw new Exception(answer.getString("data"));
                    } else {
                        return answer.getJSONArray("data");
                    }
                }catch(Exception e){
                    err = e.getMessage();
                    return null;
                }
            }

            @Override protected void onPostExecute(JSONArray items){
                if(items!=null) {
                    // выводим данные из джейсона в лист вью
                    listItems.clear();
                    try {
                        for (int i = 0; i < items.length(); i++) {
                            listItems.add(new item(
                                    items.getJSONObject(i).getInt("id"),
                                    items.getJSONObject(i).getString("title"),
                                    items.getJSONObject(i).getString("date"),
                                    items.getJSONObject(i).getInt("price")
                            ));
                        }
                    }catch(Exception e){
                        Toast.makeText(CategoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    listAdapter = new ItemListAdapter(CategoryActivity.this, listItems);
                    ListView lvMain = (ListView) findViewById(R.id.itemsList);
                    lvMain.setAdapter(listAdapter);
                }else{
                    Toast.makeText(CategoryActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(new itemsParameters(server, token, month, year, catID) );

        ListView lv = (ListView) findViewById(R.id.itemsList);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                final int position = pos;
                AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                // delete item
                                new AsyncTask<forDelete, Void, JSONObject>() {
                                    private String err;
                                    @Override protected JSONObject doInBackground(forDelete... p) {
                                        apiClient myclient = new apiClient();
                                        JSONObject answer = myclient.delItem(p[0].server, p[0].token, p[0].id);
                                        try{
                                            if (answer.getInt("code") != 200) {
                                                throw new Exception(answer.getString("data"));
                                            } else {
                                                return answer;
                                            }
                                        }catch(Exception e){
                                            err = e.getMessage();
                                            return null;
                                        }
                                    }

                                    @Override protected void onPostExecute(JSONObject result){
                                        if(result!=null) {
                                            Toast.makeText(CategoryActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(CategoryActivity.this, "Запись удалена", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }.execute(new forDelete(server, token, listItems.get(position).id));

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                builder.setMessage("Удалить элемент").setPositiveButton("Да", dialogClickListener)
                        .setNegativeButton("Нет", dialogClickListener)
                        .show();
                return true;
            }
        });
    }

    private static class forDelete {
        String server, token;
        int id;

        public forDelete(String _s, String _t, int _id){
            server = _s; token = _t;
            id = _id;
        }
    }

    private static class itemsParameters {
        String server, token;
        int month, year, catID;

        public itemsParameters(String _s, String _t, int _m, int _y, int _c){
            server = _s; token = _t;
            month = _m; year = _y; catID = _c;
        }
    }
}

