package com.example.igor.bugetapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        // add elements into main listView
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //click on elements in list
    public void catListClick(View v){
        // do smth
        startActivityFromChild(this, new Intent(this, CategoryActivity.class), 0);
    }

    // add waste floating button click
    public void fabClick(View v){
        startActivityFromChild(this, new Intent(this, EditActivity.class), 0);
    }

    // add waste menu click
    public void menuAddWasteClick(MenuItem v){
        startActivityFromChild(this, new Intent(this, EditActivity.class), 0);
    }

    // add profit menu click
    public void menuAddProfitClick(MenuItem v){
        startActivityFromChild(this, new Intent(this, EditActivity.class), 0);
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

}
