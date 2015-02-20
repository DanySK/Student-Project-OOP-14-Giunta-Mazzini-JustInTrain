package com.example.lisamazzini.train_app.gui.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lisamazzini.train_app.gui.activity.StationListActivity;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Treno.Treno;
import com.example.lisamazzini.train_app.R;

import java.util.List;

public class FavouriteTrainListAdapter extends RecyclerView.Adapter<FavouriteTrainListAdapter.Holder> implements IAdapter<FavouriteTrainListAdapter.Holder> {

    private List<Treno> list;

    public FavouriteTrainListAdapter(final List<Treno> list){
        this.list = list;
    }

    @Override
    public Holder onCreateViewHolder(final ViewGroup viewGroup, final int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.view_favourite_train, viewGroup, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {

        final Treno train = list.get(position);
        holder.trainCategory.setText(train.getCategoria());
        holder.trainNumber.setText("" + train.getNumeroTreno());

        if(train.getFermate().size() == 0  || !train.getSubTitle().equals("")){
            holder.extra.setText(train.getSubTitle());
        }else{
            if (train.getRitardo() > 0) {
                holder.delay.setText("  •  " + train.getRitardo() + "'  RITARDO");
            } else if (train.getRitardo() < 0) {
                holder.delay.setText("  •  " + train.getRitardo()*(-1) + "'  ANTICIPO");
            } else {
                holder.delay.setText("  • IN ORARIO");
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

        protected TextView trainCategory;
        protected TextView trainNumber;
        protected TextView delay;
        protected TextView lastSeemTime;
        protected TextView lastSeenStation;
        protected TextView progress;
        protected TextView extra;
        protected String stationCode;

        public Holder(final View itemView) {

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
        public void onClick(final View v) {
            Intent i = new Intent(v.getContext(), StationListActivity.class);
            i.putExtra(Constants.ID_ORIGIN_EXTRA, this.stationCode);
            i.putExtra(Constants.TRAIN_N_EXTRA, this.trainNumber.getText().toString());
            v.getContext().startActivity(i);
        }
    }
}


