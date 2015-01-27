package com.example.lisamazzini.train_app.GUI.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lisamazzini.train_app.GUI.StationListActivity;
import com.example.lisamazzini.train_app.Model.JourneyTrain;
import com.example.lisamazzini.train_app.R;

import java.util.List;

public class JourneyResultsAdapter extends RecyclerView.Adapter<JourneyResultsAdapter.JourneyViewHolder>{

    private final List<JourneyTrain> journeyList;

    public JourneyResultsAdapter(List<JourneyTrain> list) {
        this.journeyList = list;
    }

    private int pos;

    //chiamato quando viene instanziato il viewHolder
    @Override
    public JourneyViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new JourneyViewHolder(itemView);
    }


    //chiamato quando si collegano i dati al viewholder
    @Override
    public void onBindViewHolder(JourneyViewHolder journeyViewHolder, int position) {
        JourneyTrain journeyTrain = journeyList.get(position);
        journeyViewHolder.category.setText(journeyTrain.getCategory());
        journeyViewHolder.number.setText(journeyTrain.getNumber());
        journeyViewHolder.duration.setText(journeyTrain.getDuration());
        journeyViewHolder.departureStation.setText(journeyTrain.getDepartureStation());
        journeyViewHolder.departureTime.setText(journeyTrain.getDepartureTime());
        journeyViewHolder.arrivalStation.setText(journeyTrain.getArrivalStation());
        journeyViewHolder.arrivalTime.setText(journeyTrain.getArrivalTime());
//        journeyViewHolder.delay.setText(journeyTrain.getDelay());

    }

    @Override
    public int getItemCount() {
        return journeyList.size();
    }


    public static class JourneyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView category;
        protected TextView number;
        protected TextView duration;
        protected TextView departureStation;
        protected TextView departureTime;
        protected TextView arrivalStation;
        protected TextView arrivalTime;
//        protected TextView delay;



        public JourneyViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            category = (TextView)v.findViewById(R.id.tvSetTrainCategory);
            number = (TextView) v.findViewById(R.id.tvSetTrainNumber);
            duration = (TextView) v.findViewById(R.id.tvSetDuration);
            departureStation = (TextView) v.findViewById(R.id.tvSetDeparture);
            departureTime = (TextView) v.findViewById(R.id.tvSetDepartureTime);
            arrivalStation = (TextView) v.findViewById(R.id.tvSetArrival);
            arrivalTime = (TextView) v.findViewById(R.id.tvSetArrivalTime);
//            delay = (TextView) v.findViewById(R.id.tvSetDelay);
            // qui prendo i riferimenti alla view con findviewbyid
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), StationListActivity.class);
            i.putExtra("trainNumber", this.number.getText().toString());
            i.putExtra("departureSTation", this.departureStation.getText().toString());
            v.getContext().startActivity(i);


        }
    }

}
