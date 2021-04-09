package com.example.transflylocal;


import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SpinnerLoadingAdapter extends ArrayAdapter<ResponseLoading> {

    private List<ResponseLoading> items;
    private Activity activity;

    public SpinnerLoadingAdapter(Activity activity, List<ResponseLoading> items) {
        super(activity, R.layout.view_spinner_item, items);
        this.items = items;
        this.activity = activity;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView v = (TextView) super.getView(position, convertView, parent);

        if (v == null) {
            v = new TextView(activity);
        }
        v.setTextColor(Color.BLACK);
        v.setText(items.get(position).getLoadingname());
        return v;
    }

    @Override
    public ResponseLoading getItem(int position) {
        return items.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            v = inflater.inflate(R.layout.view_spinner_item, null);
        }
        TextView lbl = (TextView) v.findViewById(R.id.text1);
        lbl.setTextColor(Color.BLACK);
        lbl.setText(items.get(position).getLoadingname());
        return v;
    }

}


