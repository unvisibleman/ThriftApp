package com.example.igor.bugetapp;

/**
 * Created by igor on 28.12.16.
 */

public class item {
    int id;
    String title, date;
    int price;

    item(int _id, String _t, String _d, int _p) {
        id = _id;
        title = _t; date = _d;
        price = _p;
    }
}
