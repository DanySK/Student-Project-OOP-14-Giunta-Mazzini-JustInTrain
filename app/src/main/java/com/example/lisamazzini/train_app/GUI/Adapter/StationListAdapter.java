package com.example.lisamazzini.train_app.GUI.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.example.lisamazzini.train_app.*;
import com.example.lisamazzini.train_app.Model.Fermate;

import java.util.List;

/**
 * Created by lisamazzini on 23/01/15.
 */
public class StationListAdapter  extends RecyclerView.Adapter<StationListAdapter.RecyclerViewHolder>{

    List<Fermate> list;

    public StationListAdapter(List<Fermate> list){
        this.list = list;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.view_station, viewGroup, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int i) {

        Fermate f = list.get(i);
        viewHolder.stationName.setText(f.getStazione());
        if(f.getOrientamento() == (null)){
            viewHolder.visited.setText("CANCELLATA");
        }else{
            if(f.getActualFermataType() == 2L) {
            viewHolder.visited.setText("Fermata Straordinaria");
            }
            viewHolder.expectedArrival.setText(Utilities.fromMsToTime(f.getEffettiva()));
            viewHolder.scheduledArrival.setText(Utilities.fromMsToTime(f.getProgrammata()));
            viewHolder.timeDifference.setText("" + f.getRitardo());
            viewHolder.expectedPlatform.setText(f.getBinarioEffettivoPartenzaDescrizione());
            viewHolder.scheduledPlatform.setText(f.getBinarioProgrammatoPartenzaDescrizione());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(List<Fermate> data) {
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
            timeDifference = (TextView) itemView.findViewById(R.id.delaaay);
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