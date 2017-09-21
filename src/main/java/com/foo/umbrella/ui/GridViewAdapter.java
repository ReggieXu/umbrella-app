package com.foo.umbrella.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.foo.umbrella.R;

/**
 * Created by Rengui on 2017/9/20.
 */

public class GridViewAdapter extends BaseAdapter {

    private int icons[];
    private String times[];
    private String temperature[];

    private Context context;
    private LayoutInflater inflater;


    public GridViewAdapter(Context context, int icons[], String times[],String temperature[] ){
        this.context = context;
        this.icons = icons;
        this.times = times;
        this.temperature =temperature;
    }

    @Override
    public int getCount() {
        return temperature.length;
    }

    @Override
    public Object getItem(int position) {
        return temperature[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View gridvView = convertView;
        if (convertView == null){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridvView = inflater.inflate(R.layout.list_item,null);
        }

        ImageView icon = (ImageView)gridvView.findViewById(R.id.weather_image);
        TextView current_time = (TextView)gridvView.findViewById(R.id.current_time);
        TextView current_temperature = (TextView)gridvView.findViewById(R.id.temperature_hourly);

        icon.setImageResource(icons[position]);
        current_time.setText(times[position]);
        current_temperature.setText(temperature[position]);

        return gridvView;
    }


}
