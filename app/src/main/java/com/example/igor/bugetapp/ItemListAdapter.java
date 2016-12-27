package com.example.igor.bugetapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by igor on 28.12.16.
 */

public class ItemListAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<item> objects;

    ItemListAdapter(Context context, ArrayList<item> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    public long getItemId(int position) { return position; }

    // пункт списка
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        item p = getProduct(position);

        // заполняем View в пункте списка данными
        ((TextView) view.findViewById(R.id.tvDescr)).setText(p.title);
        ((TextView) view.findViewById(R.id.tvDate)).setText(p.date);
        ((TextView) view.findViewById(R.id.tvCost)).setText(p.price + " руб.");

        return view;
    }

    // пункт по позиции
    item getProduct(int position) {
        return ((item) getItem(position));
    }

}