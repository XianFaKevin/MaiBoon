package com.orange.maiboon;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Kevin on 27/6/2015.
 */
public class RowAdapter extends BaseAdapter {

    private static ArrayList<Profile> list;
    private LayoutInflater l_Inflater;

    public RowAdapter(Context context, ArrayList<Profile> results) {
        list = results;
        l_Inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
			/*
			 * instantiate viewholder if convertview is null
			 */
            convertView = l_Inflater.inflate(R.layout.row, null);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.dateText);
            holder.contact = (TextView) convertView.findViewById(R.id.contactText);
            holder.people = (TextView) convertView.findViewById(R.id.peoText);
            holder.room = (TextView) convertView.findViewById(R.id.rmText);
            holder.duration = (TextView) convertView.findViewById(R.id.durText);
            holder.period = (TextView) convertView.findViewById(R.id.periodText);
            holder.acc = (TextView) convertView.findViewById(R.id.accText);

            convertView.setTag(holder);
        } else {
			/*
			 * just get tags from convertView instead
			 */
            holder = (ViewHolder) convertView.getTag();
        }

        holder.date.setText(list.get(position).getDate());
        holder.contact.setText(list.get(position).getContact());
        holder.people.setText(Integer.toString(list.get(position).getPeople()));
        holder.room.setText(list.get(position).getRoom());
        holder.duration.setText(Integer.toString(list.get(position).getDuration()));
        String in = simpleDateConverter(list.get(position).getIn());
        String out = simpleDateConverter(list.get(position).getOut());
        holder.period.setText( in + " 到 " + out);
        if (list.get(position).getAccounted() == 1) {
            holder.acc.setText("没交");
            holder.acc.setTextColor(Color.parseColor("#ff0000"));
        } else if (list.get(position).getAccounted() == 2) {
            holder.acc.setText("浸交");
            holder.acc.setTextColor(Color.parseColor("#cdcd00"));
        } else {
            holder.acc.setText("已交");
            holder.acc.setTextColor(Color.parseColor("#00cd00"));
        }
        /*if (list.get(position).getAccounted() == 1) {
            convertView.setBackgroundColor(Color.RED);
        } else if (list.get(position).getAccounted() == 2) {
            convertView.setBackgroundColor(Color.GREEN);
        } else if (list.get(position).getAccounted() == 3) {
            convertView.setBackgroundColor(Color.MAGENTA);
        }*/
        return convertView;
    }

    static class ViewHolder {
        TextView date;
        TextView contact;
        TextView people;
        TextView room;
        TextView duration;
        TextView period;
        TextView acc;
    }

    public String simpleDateConverter(String input) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = originalFormat.parse(input);
        } catch (Exception e) {}
        String output = newFormat.format(date);
        return output;
    }
}
