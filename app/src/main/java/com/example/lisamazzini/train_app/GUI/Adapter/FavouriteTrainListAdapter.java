package com.example.lisamazzini.train_app.gui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lisamazzini.train_app.Utilities;
import com.example.lisamazzini.train_app.gui.activity.StationListActivity;
import com.example.lisamazzini.train_app.model.Constants;
import com.example.lisamazzini.train_app.model.treno.Treno;
import com.example.lisamazzini.train_app.R;

import java.util.List;

/**
 * Adapter per una lista di treni preferiti.
 *
 * @author lisamazzini
 */
public class FavouriteTrainListAdapter extends RecyclerView.Adapter<FavouriteTrainListAdapter.FavouriteTrainsViewHolder> implements IAdapter<FavouriteTrainListAdapter.FavouriteTrainsViewHolder> {

    private final List<Treno> list;

    /**
     * Costruttore.
     * @param pList la lista di elementi da mostrare a video
     */
    public FavouriteTrainListAdapter(final List<Treno> pList) {
        this.list = pList;
    }

    @Override
    public final FavouriteTrainsViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        final View itemView = inflater.inflate(R.layout.view_favourite_train, viewGroup, false);
        return new FavouriteTrainsViewHolder(itemView);
    }

    @Override
    public final void onBindViewHolder(final FavouriteTrainsViewHolder holder, final int position) {

        final Treno train = list.get(position);
        holder.trainCategory.setText(train.getCategoria());
        holder.trainNumber.setText(Long.toString(train.getNumeroTreno()));

        if (train.getFermate().isEmpty()  || !train.getSubTitle().equals("")) {
            holder.extra.setText(train.getSubTitle());
        } else {
            if (train.getRitardo() > Constants.ON_TIME) {
                holder.delay.setText("  •  " + train.getRitardo() + "'  RITARDO");
            } else if (train.getRitardo() < Constants.ON_TIME) {
                holder.delay.setText("  •  " + train.getRitardo() * (-1) + "'  ANTICIPO");
            } else {
                holder.delay.setText("  • IN ORARIO");
            }
            holder.progress.setText(Utilities.getProgress(train));
            holder.lastSeemTime.setText(train.getCompOraUltimoRilevamento());
            holder.lastSeenStation.setText(train.getStazioneUltimoRilevamento());
        }
        holder.stationCode = train.getIdOrigine();
    }

    @Override
    public final int getItemCount() {
        return list.size();
    }

    /**
     *
     */
    public static class FavouriteTrainsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView trainCategory;
        private final TextView trainNumber;
        private final TextView delay;
        private final TextView lastSeemTime;
        private final TextView lastSeenStation;
        private final TextView extra;
        private final TextView progress;
        private String stationCode;

        /**
         * Costruttore.
         * @param itemView la view da costruire
         */
        public FavouriteTrainsViewHolder(final View itemView) {

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
        public final void onClick(final View v) {
            final Intent i = new Intent(v.getContext(), StationListActivity.class);
            i.putExtra(Constants.ID_ORIGIN_EXTRA, this.stationCode);
            i.putExtra(Constants.TRAIN_N_EXTRA, this.trainNumber.getText().toString());
            v.getContext().startActivity(i);
        }
    }
}


