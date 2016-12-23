package com.example.igor.bugetapp;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by igor on 14.10.16.
 */

public class ListAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<element> objects;

    ListAdapter(Context context, ArrayList<element> products) {
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

        element p = getProduct(position);

        // заполняем View в пункте списка данными
        ((TextView) view.findViewById(R.id.tvDescr)).setText(p.name);
        ((TextView) view.findViewById(R.id.tvCost)).setText(p.cost + "");

        return view;
    }

    // пункт по позиции
    element getProduct(int position) {
        return ((element) getItem(position));
    }


}
