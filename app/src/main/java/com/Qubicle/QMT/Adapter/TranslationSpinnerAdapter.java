package com.Qubicle.QMT.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.Qubicle.QMT.R;

/**
 * Created by Anju on 29-08-2019.
 */
public class TranslationSpinnerAdapter extends BaseAdapter {
    Context context;
    int images[];
    String[] fruit;
    LayoutInflater inflter;

    public TranslationSpinnerAdapter(Context applicationContext,  String[] fruit) {
        this.context = applicationContext;

        this.fruit = fruit;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return fruit.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.list_item_spinner, null);

        TextView names = (TextView) view.findViewById(R.id.textHint);

        names.setText(fruit[i]);
        return view;
    }
}