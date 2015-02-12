package com.example.lisamazzini.train_app.GUI.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lisamazzini.train_app.Controller.AchievementController;
import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.GUI.Activity.StationListActivity;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import com.example.lisamazzini.train_app.Notification.NotificationService;
import com.example.lisamazzini.train_app.R;

import java.util.List;

public class JourneyResultsAdapter extends RecyclerView.Adapter<JourneyResultsAdapter.JourneyViewHolder> implements IAdapter<JourneyResultsAdapter.JourneyViewHolder>{

    private final List<PlainSolution> journeyList;
    private AchievementController achievementController;

    public JourneyResultsAdapter(List<PlainSolution> list) {
        this.journeyList = list;
    }


    //chiamato quando viene istanziato il viewHolder
    @Override
    public JourneyViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_journey, viewGroup, false);
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
        journeyViewHolder.delay.setText(journeyTrain.getDelay());
        journeyViewHolder.stationCode = journeyTrain.getIDorigine();
        journeyViewHolder.pinButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
//                final Intent intent = new Intent(v.getContext(), NotificationService.class);
//                final Context ctx = v.getContext();
//                int id = v.getVisibility();
//                switch (id) {
//                    //usa la visibility oppure il nome della src se la sostituisce del tutto
//                    case notpinned:
//                        //rendi visibile l'image button preferito
//                        intent.putExtra("number", journeyTrain.getNumeroTreno());
//                        intent.putExtra("idOrigine", journeyTrain.getIDorigine());
//                        intent.putExtra("oraPartenza", journeyTrain.getOrarioPartenza());
//                        ctx.startService(intent);
//                        achievementController = new AchievementController(ctx);
//                        try {
//                            Log.d("i looooooog", "sto per aggiornare");
//                            achievementController.updateAchievements(journeyTrain);
//                        } catch (AchievementException e) {
//                            Log.d("i looooooog", "ORDUNQUE? " + e.getMessage());
//                            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    case pinned:
//                        //elimina il service e cambia icona
//                }

                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_fav_journey, popupMenu.getMenu());
                final Intent intent = new Intent(v.getContext(), NotificationService.class);
                final Context ctx = v.getContext();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.pin:
                                Log.d("----OHI--", "Son qui");
                                intent.putExtra("number", journeyTrain.getNumeroTreno());
                                intent.putExtra("idOrigine", journeyTrain.getIDorigine());
                                intent.putExtra("oraPartenza", journeyTrain.getOrarioPartenza());
                                ctx.startService(intent);
                                achievementController = new AchievementController(ctx);
                                try {
                                    Log.d("i looooooog", "sto per aggiornare");
                                    achievementController.updateAchievements(journeyTrain);
                                } catch (AchievementException e) {
                                    Log.d("i looooooog", "ORDUNQUE? " + e.getMessage());
                                    Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
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
        protected TextView delay;
//        protected ImageButton pinButton;
        protected Button pinButton;
        protected String stationCode;

        public JourneyViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            category = (TextView)v.findViewById(R.id.tTrainCategory);
            number = (TextView) v.findViewById(R.id.tTrainNumber);
            duration = (TextView) v.findViewById(R.id.tDuration);
            departureStation = (TextView) v.findViewById(R.id.tDepartureStation);
            departureTime = (TextView) v.findViewById(R.id.tDepartureTime);
            arrivalStation = (TextView) v.findViewById(R.id.tArrivalStation);
            arrivalTime = (TextView) v.findViewById(R.id.tArrivalTime);
            delay = (TextView) v.findViewById(R.id.tTimeDifference);
//            pinButton = (ImageButton)v.findViewById(R.id.bOptions);
            pinButton = (Button)v.findViewById(R.id.bOptions);
            stationCode = "";
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), StationListActivity.class);
            i.putExtra("trainNumber", this.number.getText().toString());
            i.putExtra("stationCode", this.stationCode);
            v.getContext().startActivity(i);
        }
    }

}
