package com.example.lisamazzini.train_app.GUI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.lisamazzini.train_app.Controller.DoubleTrainService;
import com.example.lisamazzini.train_app.Exceptions.DoubleTrainNumberException;
import com.example.lisamazzini.train_app.GUI.StationListActivity;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import com.example.lisamazzini.train_app.Model.Tragitto.Soluzioni;
import com.example.lisamazzini.train_app.Notification.NotificationService;
import com.example.lisamazzini.train_app.R;

import java.util.List;

public class JourneyResultsAdapter extends RecyclerView.Adapter<JourneyResultsAdapter.JourneyViewHolder>{

    private final List<PlainSolution> journeyList;

    public JourneyResultsAdapter(List<PlainSolution> list) {
        this.journeyList = list;
    }


    //chiamato quando viene instanziato il viewHolder
    @Override
    public JourneyViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new JourneyViewHolder(itemView);
    }

    //chiamato quando si collegano i dati al viewholder
    @Override
    public void onBindViewHolder(JourneyViewHolder journeyViewHolder, int position) {
        final PlainSolution journeyTrain = journeyList.get(position);
        journeyViewHolder.category.setText(journeyTrain.getCategoria());
        journeyViewHolder.number.setText(journeyTrain.getNumeroTreno());
        journeyViewHolder.duration.setText(journeyTrain.getDurata());
        journeyViewHolder.departureStation.setText(journeyTrain.getOrigine());
        journeyViewHolder.departureTime.setText(journeyTrain.getOrarioPartenza());
        journeyViewHolder.arrivalStation.setText(journeyTrain.getDestinazione());
        journeyViewHolder.arrivalTime.setText(journeyTrain.getOrarioArrivo());
//        journeyViewHolder.delay.setText("" + journeyTrain.);
        journeyViewHolder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_fav_journey, popupMenu.getMenu());
                final Intent intent = new Intent(v.getContext(), NotificationService.class);
                final Context ctx = v.getContext();

//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()){
//                            case R.id.pin:
//                                Log.d("----OHI--", "Son qui");
//                                intent.putExtra("number", journeyTrain.getNumber());
//                                intent.putExtra("time", journeyTrain.getArrivalTime());
//                                ctx.startService(intent);
//                                return true;
//                            case R.id.unpin:
//                                ctx.startService(intent);
//                                return true;
//                            default:
//                                return false;
//                        }
//                    }
//                });
//
//                popupMenu.show();

            }
        });

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
        protected Button menu;

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
            menu = (Button)v.findViewById(R.id.btnOpt);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), DoubleTrainService.class);
            i.putExtra("trainNumber", this.number.getText().toString());
            i.putExtra("depStation", this.departureStation.getText().toString());
            v.getContext().startService(i);
        }
    }

}
