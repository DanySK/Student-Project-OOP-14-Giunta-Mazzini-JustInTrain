package com.example.lisamazzini.train_app.gui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.model.Utilities;
import com.example.lisamazzini.train_app.model.Constants;
import com.example.lisamazzini.train_app.model.treno.Fermate;

import java.util.List;

/**
 * Adapter per una lista di stazioni.
 *
 * @author lisamazzini
 */
public class StationListAdapter  extends RecyclerView.Adapter<StationListAdapter.StationViewHolder> implements IAdapter<StationListAdapter.StationViewHolder> {

    private final List<Fermate> list;
    /**
     * Costante per assegnare un colore a seconda che la stazione sia stata visitata, cancellata, straordinaria, o da visitare.
     */
    private final int[] rgbVisited = new int[]{196, 230, 255};
    private final int[] rgbCancelled = new int[]{230, 191, 191};
    private final int[] rgbExtra = new int[]{255, 234, 164};


    /**
     * Costruttore.
     * @param pList la lista di Fermate da mostrare a video
     */
    public StationListAdapter(final List<Fermate> pList) {
        this.list = pList;
    }

    @Override
    public final StationViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        final LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        final View itemView = inflater.inflate(R.layout.view_station, viewGroup, false);
        return new StationViewHolder(itemView);
    }

    @Override
    public final void onBindViewHolder(final StationViewHolder viewHolder, final int i) {

        final Fermate f = list.get(i);
        viewHolder.stationName.setText(f.getStazione());
        if (f.getActualFermataType() == Constants.STATION_CANCELLED) {
            viewHolder.extraMessage.setText("CANCELLATA");
            viewHolder.itemView.setBackgroundColor(Color.rgb(rgbCancelled[0], rgbCancelled[1], rgbCancelled[2]));
        } else if (f.getActualFermataType() == Constants.STATION_EXTRA) {
            viewHolder.extraMessage.setText("Fermata Straordinaria");
            viewHolder.itemView.setBackgroundColor(Color.rgb(rgbExtra[0], rgbExtra[1], rgbExtra[2]));
        } else if (f.getActualFermataType() == Constants.STATION_VISITED) {
            viewHolder.extraMessage.setText("");
            viewHolder.itemView.setBackgroundColor(Color.rgb(rgbVisited[0], rgbVisited[1], rgbVisited[2]));
        } else {
            viewHolder.extraMessage.setText("");
            viewHolder.itemView.setBackgroundColor(Color.WHITE);
        }
        viewHolder.timeDifference.setText(Long.toString(f.getRitardo()));
        viewHolder.plannedTime.setText(Utilities.fromMsToTime(f.getProgrammata()));
        viewHolder.plannedPlatform.setText(f.getBinarioEffettivoPartenzaDescrizione());
        viewHolder.actualTime.setText(Utilities.fromMsToTime(f.getEffettiva()));
        viewHolder.actualPlatform.setText(f.getBinarioProgrammatoPartenzaDescrizione());



    }

    @Override
    public final int getItemCount() {
        return list.size();
    }

    /**
     * Viewholder per una stazione.
     *
     * @author lisamazzini
     */
    public static class StationViewHolder extends RecyclerView.ViewHolder {
        private final TextView stationName;
        private final TextView extraMessage;
        private final TextView actualTime;
        private final TextView plannedTime;
        private final TextView timeDifference;
        private final TextView actualPlatform;
        private final TextView plannedPlatform;

        /**
         * Costruttore.
         * @param itemView la view da costruire
         */
        public StationViewHolder(final View itemView) {
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