package com.example.igor.bugetapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    EditText edLogin, edPassword, edServer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        edLogin = (EditText) findViewById(R.id.edLogin);
        edPassword = (EditText) findViewById(R.id.edPassword);
        edServer = (EditText) findViewById(R.id.edServer);

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        // загрузить логин-пароль из настроек
        edLogin.setText(settings.getString("Username", "").toString());
        edPassword.setText(settings.getString("Password", "").toString());
        edServer.setText(settings.getString("Server", "").toString());

    }

    public void saveClick(View v){
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        // сохранить логин-пароль в настройках
        editor.putString("Username",edLogin.getText().toString());
        editor.putString("Password",edPassword.getText().toString());
        editor.putString("Server",edServer.getText().toString());
        editor.commit();

        Toast.makeText(this, "Настройки сохранены", Toast.LENGTH_SHORT).show();
        finish();
    }
}
