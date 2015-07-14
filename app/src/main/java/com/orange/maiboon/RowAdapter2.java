package com.orange.maiboon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class RowAdapter2 extends BaseAdapter {

    private static ArrayList<AcProfile> list;
    private LayoutInflater l_Inflater;

    public RowAdapter2(Context context, ArrayList<AcProfile> results) {
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
            convertView = l_Inflater.inflate(R.layout.activity_row_adapter2, null);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.note = (TextView) convertView.findViewById(R.id.note);
            holder.rev = (TextView) convertView.findViewById(R.id.rev);
            holder.cost = (TextView) convertView.findViewById(R.id.cost);
            holder.profit = (TextView) convertView.findViewById(R.id.profit);

            convertView.setTag(holder);
        } else {
			/*
			 * just get tags from convertView instead
			 */
            holder = (ViewHolder) convertView.getTag();
        }

        holder.date.setText(list.get(position).getDate());
        holder.note.setText(list.get(position).getNote());
        holder.rev.setText(Double.toString(list.get(position).getRev()));
        holder.cost.setText(Double.toString(list.get(position).getCost()));
        holder.profit.setText(Double.toString(list.get(position).getProfit()));
        return convertView;
    }

    static class ViewHolder {
        TextView date;
        TextView note;
        TextView rev;
        TextView cost;
        TextView profit;
    }
}
