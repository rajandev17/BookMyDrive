package com.ggktech.bookmydrive;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rajan.kali on 11/17/2016.
 */
public class TechnologyAdapter extends ArrayAdapter<String> {
    private Activity context;
    private List<String> techList;

    public TechnologyAdapter(Activity context, List<String> techList){
        super(context, R.layout.spinner_item, techList);
        this.techList = techList;
        this.context=context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View convertView =inflater.inflate(R.layout.spinner_item,parent,false);
        TextView locationTV=(TextView)convertView.findViewById(R.id.spinner_value);
        locationTV.setText(techList.get(position));
        return convertView;
    }
}