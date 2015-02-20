package com.example.lisamazzini.train_app.gui.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lisamazzini.train_app.Controller.AchievementController;
import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.gui.activity.StationListActivity;
import com.example.lisamazzini.train_app.model.Constants;
import com.example.lisamazzini.train_app.model.tragitto.PlainSolution;
import com.example.lisamazzini.train_app.Notification.NotificationService;
import com.example.lisamazzini.train_app.R;

import java.util.List;

public class JourneyListAdapter extends RecyclerView.Adapter<JourneyListAdapter.JourneyViewHolder> implements IAdapter<JourneyListAdapter.JourneyViewHolder>{

    private final List<PlainSolution> journeyList;
    private AchievementController achievementController;

    public JourneyListAdapter(List<PlainSolution> list) {
        this.journeyList = list;
    }

    @Override
    public JourneyViewHolder onCreateViewHolder(ViewGroup viewGroup, int index) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_journey, viewGroup, false);
        return new JourneyViewHolder(itemView);
    }

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
                final Intent intent = new Intent(v.getContext(), NotificationService.class);
                final Context ctx = v.getContext();
                Toast.makeText(ctx, "Notifica impostata", Toast.LENGTH_SHORT).show();
                intent.putExtra(Constants.TRAIN_N_EXTRA, journeyTrain.getNumeroTreno());
                intent.putExtra(Constants.ID_ORIGIN_EXTRA, journeyTrain.getIDorigine());
                intent.putExtra(Constants.DEPARTURE_TIME_EXTRA, journeyTrain.getOrarioPartenza());
                ctx.startService(intent);
                achievementController = new AchievementController(ctx);
                try {
                    achievementController.updateAchievements(journeyTrain);
                } catch (AchievementException e) {
                    Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        if (journeyTrain.isLastVehicleOfJourney()) {
            journeyViewHolder.divider.setVisibility(View.VISIBLE);
        } else {
            journeyViewHolder.divider.setVisibility(View.GONE);
        }
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
        protected ImageButton pinButton;
        protected String stationCode;
        protected View divider;

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
            pinButton = (ImageButton)v.findViewById(R.id.bOptions);
            stationCode = "";
            divider = (View)v.findViewById(R.id.iDivider);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), StationListActivity.class);
            i.putExtra(Constants.TRAIN_N_EXTRA, this.number.getText().toString());
            i.putExtra(Constants.ID_ORIGIN_EXTRA, this.stationCode);
            v.getContext().startActivity(i);
        }
    }

}
