package com.example.lisamazzini.train_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by albertogiunta on 28/12/14.
 */
public class JourneyAdapter extends BaseAdapter {

    private List<Journey> list = new LinkedList<Journey>();
    private LayoutInflater inflater;

    public JourneyAdapter(Context context, List<Journey> list) {
        this.list = list;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_layout, parent, false);
            holder = new ViewHolder();
            holder.trainCategory = (TextView)convertView.findViewById(R.id.tvSetTrainCategory);
            holder.trainNumber = (TextView)convertView.findViewById(R.id.tvSetTrainNumber);
            holder.departure = (TextView)convertView.findViewById(R.id.tvSetDeparture);
            holder.departureTime = (TextView)convertView.findViewById(R.id.tvSetDepartureTime);
            holder.arrival = (TextView)convertView.findViewById(R.id.tvSetArrival);
            holder.arrivalTime = (TextView)convertView.findViewById(R.id.tvSetArrivalTime);
            holder.delay = (TextView)convertView.findViewById(R.id.tvSetDelay);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.trainCategory.setText(list.get(position).getTrainCategory());
        holder.trainNumber.setText(list.get(position).getTrainNumber());
//        holder.duration..setText(list.get(position).getDuration());
        holder.departure.setText(list.get(position).getDepartureStation());
        holder.departureTime.setText(list.get(position).getDepartureTime());
        holder.arrival.setText(list.get(position).getArrivalStation());
        holder.arrivalTime.setText(list.get(position).getArrivalTime());
        holder.delay.setText("" + list.get(position).getDelay());


        return convertView;
    }



    static class ViewHolder {
        TextView trainCategory;
        TextView trainNumber;
        TextView duration;
        TextView departure;
        TextView departureTime;
        TextView arrival;
        TextView arrivalTime;
        TextView delay;
    }

}
