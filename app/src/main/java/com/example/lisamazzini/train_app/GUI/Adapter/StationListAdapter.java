package com.example.lisamazzini.train_app.GUI.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.*;

import com.example.lisamazzini.train_app.*;
import com.example.lisamazzini.train_app.Model.Treno.Fermate;

import java.util.List;

/**
 * Created by lisamazzini on 23/01/15.
 */
public class StationListAdapter  extends RecyclerView.Adapter<StationListAdapter.RecyclerViewHolder> implements IAdapter<StationListAdapter.RecyclerViewHolder>{

    private final List<Fermate> list;

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
            viewHolder.extraMessage.setText("CANCELLATA");
        }else{
            if(f.getActualFermataType() == 2L) {
            viewHolder.extraMessage.setText("Fermata Straordinaria");
            }
            viewHolder.timeDifference.setText("" + f.getRitardo());
            viewHolder.plannedTime.setText(Utilities.fromMsToTime(f.getProgrammata()));
            viewHolder.plannedPlatform.setText(f.getBinarioEffettivoPartenzaDescrizione());
            viewHolder.actualTime.setText(Utilities.fromMsToTime(f.getEffettiva()));
            viewHolder.actualPlatform.setText(f.getBinarioProgrammatoPartenzaDescrizione());
            if(f.getActualFermataType() == 1L ) {
                viewHolder.itemView.setBackgroundColor(Color.rgb(196,230,255));
            }else{
                viewHolder.itemView.setBackgroundColor(Color.WHITE);
            }

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        protected TextView stationName;
        protected TextView extraMessage;
        protected TextView actualTime;
        protected TextView plannedTime;
        protected TextView timeDifference;
        protected TextView actualPlatform;
        protected TextView plannedPlatform;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            stationName = (TextView) itemView.findViewById(R.id.tStationName);
            timeDifference = (TextView) itemView.findViewById(R.id.tTimeDifference);
            plannedTime = (TextView) itemView.findViewById(R.id.tPlannedTime);
            plannedPlatform = (TextView) itemView.findViewById(R.id.tPlannedPlatform);
            actualTime = (TextView) itemView.findViewById(R.id.tActualTime);
            actualPlatform = (TextView) itemView.findViewById(R.id.tActualPlatform);
            extraMessage = (TextView) itemView.findViewById(R.id.tExtraMessage);
        }
    }
}