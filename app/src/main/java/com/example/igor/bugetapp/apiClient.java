package com.example.igor.bugetapp;

import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.entity.UrlEncodedFormEntity;

/**
 * Created by igor on 22.12.16.
 */

public class apiClient {

    public JSONObject auth (String server, String username, String password){
        JSONObject res = new JSONObject();
        try {
            HttpClient client = new DefaultHttpClient();
            String request = "http://" + server + "/api/user/?login=" + username + "&password=" + password;
            HttpGet httpget = new HttpGet(request);
            HttpResponse response = client.execute(httpget);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            res = new JSONObject(sb.toString());
            rd.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public JSONObject categoryList(String server, String token){
        JSONObject res = new JSONObject();
        try {
            HttpClient client = new DefaultHttpClient();
            String request = "http://" + server + "/api/categories/?token=" + token;
            HttpGet httpget = new HttpGet(request);
            HttpResponse response = client.execute(httpget);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            res = new JSONObject(sb.toString());
            rd.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public JSONObject items(String server, String token, int month, int year, int catID){
        JSONObject res = new JSONObject();
        try {
            HttpClient client = new DefaultHttpClient();
            String request = "http://" + server + "/api/item/?token=" + token + "&m=" + month + "&y=" + year + "&cat=" + catID;

            HttpGet httpget = new HttpGet(request);
            HttpResponse response = client.execute(httpget);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            res = new JSONObject(sb.toString());
            rd.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public JSONObject addItem(String server, String token, int price, String date, String title, int gID, int catID){
        JSONObject res = new JSONObject();
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://" + server + "/api/item/");
            // подготавливаем аргументы
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("token", token));
            nameValuePairs.add(new BasicNameValuePair("price", Integer.toString(price)));
            nameValuePairs.add(new BasicNameValuePair("date", date));
            nameValuePairs.add(new BasicNameValuePair("title", title));
            nameValuePairs.add(new BasicNameValuePair("group", Integer.toString(gID)));
            nameValuePairs.add(new BasicNameValuePair("cat", Integer.toString(catID)));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(httppost);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            res = new JSONObject(sb.toString());
            rd.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

}
