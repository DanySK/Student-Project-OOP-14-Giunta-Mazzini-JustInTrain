package com.example.lisamazzini.train_app.GUI.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.*;

import com.example.lisamazzini.train_app.Model.Station;
import com.example.lisamazzini.train_app.*;

import java.util.List;

/**
 * Created by lisamazzini on 23/01/15.
 */
public class StationListAdapter  extends RecyclerView.Adapter<StationListAdapter.RecyclerViewHolder>{

    List<Station> list;

    public StationListAdapter(List<Station> list){
        this.list = list;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.station_adapter, viewGroup, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int i) {
        viewHolder.stationName.setText(list.get(i).getName());
        viewHolder.visited.setText("" + list.get(i).isVisited());
        viewHolder.expectedArrival.setText(list.get(i).getExpectedArrival());
        viewHolder.scheduledArrival.setText(list.get(i).getScheduledArrival());
        viewHolder.timeDifference.setText("" + list.get(i).getTimeDifference());
        viewHolder.expectedPlatform.setText(list.get(i).getExpectedPlatform());
        viewHolder.scheduledPlatform.setText(list.get(i).getScheduledPlatform());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(List<Station> data) {
        list = data;
        notifyDataSetChanged();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        protected TextView stationName;
        TextView visited;
        TextView expectedArrival;
        TextView scheduledArrival;
        TextView timeDifference;
        TextView expectedPlatform;
        TextView scheduledPlatform;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            stationName = (TextView) itemView.findViewById(R.id.statName);
            visited = (TextView) itemView.findViewById(R.id.visited);
            expectedArrival = (TextView) itemView.findViewById(R.id.expArr);
            scheduledArrival = (TextView) itemView.findViewById(R.id.planArr);
            timeDifference = (TextView) itemView.findViewById(R.id.delay);
            expectedPlatform = (TextView) itemView.findViewById(R.id.expPlat);
            scheduledPlatform = (TextView) itemView.findViewById(R.id.schPlat);
        }
    }

}

/*

public class CustomRecyclerAdapter
        extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<Data> mData = Collections.emptyList();

    public CustomRecyclerAdapter() {
        // Pass context or other static stuff that will be needed.
    }

    public void updateList(List<Data> data) {
        mData = data;
        notifyDataSetChanged();
    }

    ...
}*/