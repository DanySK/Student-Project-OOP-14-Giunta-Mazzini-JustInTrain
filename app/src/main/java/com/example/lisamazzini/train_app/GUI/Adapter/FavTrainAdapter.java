package com.example.lisamazzini.train_app.GUI.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteTrainController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.GUI.Activity.StationListActivity;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Treno.Train;
import com.example.lisamazzini.train_app.R;

import java.util.List;

/**
 * This is the Adapter for the
 *
 * Created by lisamazzini on 25/01/15.
 */
public class FavTrainAdapter extends RecyclerView.Adapter<FavTrainAdapter.Holder> implements IAdapter<FavTrainAdapter.Holder> {

    List<Train> list;
    private IFavouriteController favouriteController = FavouriteTrainController.getInstance();

    public FavTrainAdapter(List<Train> list){
        this.list = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.view_favourite_train, viewGroup, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {

        final Train train = list.get(position);
        holder.trainCategory.setText(train.getCategoria());
        holder.trainNumber.setText("" + train.getNumeroTreno());

        if(train.getFermate().size() == 0  || !train.getSubTitle().equals("")){
            holder.extra.setText(train.getSubTitle());
        }else{
            if (train.getRitardo() > 0) {
                holder.delay.setText("  •  " + train.getRitardo() + "'  DELAY");
            } else if (train.getRitardo() < 0) {
                holder.delay.setText("  •  " + train.getRitardo()*(-1) + "'  ADVANCE");
            } else {
                holder.delay.setText("  •  " + "ON TIME");
            }
            holder.lastSeemTime.setText(train.getCompOraUltimoRilevamento());
            holder.lastSeenStation.setText(train.getStazioneUltimoRilevamento());
        }
        holder.stationCode = train.getIdOrigine();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView trainCategory;
        TextView trainNumber;
        TextView delay;
        TextView lastSeemTime;
        TextView lastSeenStation;
        TextView progress;
        TextView extra;
        String stationCode;

        public Holder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);
            trainCategory = (TextView) itemView.findViewById(R.id.tFavTrainCategory);
            trainNumber = (TextView) itemView.findViewById(R.id.tFavTrainNumber);
            delay = (TextView) itemView.findViewById(R.id.tFavDelay);
            lastSeemTime = (TextView) itemView.findViewById(R.id.tFavLastSeenTime);
            lastSeenStation = (TextView) itemView.findViewById(R.id.tFavLastSeenStation);
            progress = (TextView) itemView.findViewById(R.id.tFavProgress);
            extra = (TextView) itemView.findViewById(R.id.tFavExtraMessage);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), StationListActivity.class);
            i.putExtra(Constants.ID_ORIGIN_EXTRA, this.stationCode);
            i.putExtra(Constants.TRAIN_N_EXTRA, this.trainNumber.getText().toString());
            v.getContext().startActivity(i);
        }
    }
}


