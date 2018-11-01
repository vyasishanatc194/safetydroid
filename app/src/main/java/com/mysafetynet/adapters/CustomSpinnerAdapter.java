package com.mysafetynet.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    private Typeface mTypeface;

    public CustomSpinnerAdapter(Context context, int resource, List<String> items, String fontName) {
        this(context, resource, items);
        if (fontName == null || TextUtils.isEmpty(fontName)) {
            mTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway Light.ttf");
        } else {
            mTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName);
        }
    }

    private CustomSpinnerAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }

    // Affects default (closed) state of the spinner
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setPadding(5, 5, 5, 5);
        view.setTypeface(mTypeface);
        view.setText(getItem(position));
        return view;
    }

    // Affects opened state of the spinner
    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setPadding(5, 5, 5, 5);
        view.setTypeface(mTypeface);
        view.setText(getItem(position));
        return view;
    }
}
