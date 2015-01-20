package com.example.lisamazzini.train_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.lisamazzini.train_app.Model.Station;

import java.util.List;

/**
 * Created by lisamazzini on 27/12/14.
 */
public class StationAdapter extends BaseAdapter{

    private List<Station> list;
    private LayoutInflater layoutInf;

    public StationAdapter(Context context, List<Station> list){
        this.list = list;
        layoutInf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if(convertView == null){

            convertView = layoutInf.inflate(R.layout.custom_adapter_layout, parent, false);
            holder = new ViewHolder();
            holder.stationName = (TextView)convertView.findViewById(R.id.statName);
            holder.visited = (TextView)convertView.findViewById(R.id.visited);
            holder.scheduledArrival = (TextView)convertView.findViewById(R.id.planArr);
            holder.expectedArrival = (TextView)convertView.findViewById(R.id.expArr);
            holder.scheduledPlatform = (TextView)convertView.findViewById(R.id.platf1);
            holder.expectedPlatform = (TextView)convertView.findViewById(R.id.platf2);
            holder.timeDifference = (TextView)convertView.findViewById(R.id.delay);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.stationName.setText(list.get(position).getName() + "  ");
        holder.visited.setText("" + list.get(position).isVisited());
        holder.scheduledArrival.setText(list.get(position).getScheduledArrival() + "  ");
        holder.expectedArrival.setText(list.get(position).getExpectedArrival());
        holder.scheduledPlatform.setText(list.get(position).getScheduledPlatform() + "  ");
        holder.expectedPlatform.setText(list.get(position).getExpectedPlatform());
        holder.timeDifference.setText("" + list.get(position).getTimeDifference());
        return convertView;
    }

    static class ViewHolder {
        TextView stationName;
        TextView visited;
        TextView expectedArrival;
        TextView scheduledArrival;
        TextView timeDifference;
        TextView expectedPlatform;
        TextView scheduledPlatform;

    }
}
