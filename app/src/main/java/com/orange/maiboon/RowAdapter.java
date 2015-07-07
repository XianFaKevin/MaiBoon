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

import java.util.ArrayList;

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

            convertView.setTag(holder);
        } else {
			/*
			 * just get tags from convertView instead
			 */
            holder = (ViewHolder) convertView.getTag();
        }

        holder.date.setText(list.get(position).getDate());
        holder.contact.setText(Integer.toString(list.get(position).getContact()));
        holder.people.setText(Integer.toString(list.get(position).getPeople()));
        holder.room.setText(list.get(position).getRoom());
        holder.duration.setText(Integer.toString(list.get(position).getDuration()));
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
    }
}
