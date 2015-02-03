package com.example.lisamazzini.train_app.GUI.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.lisamazzini.train_app.Controller.FavouriteTrainController;
import com.example.lisamazzini.train_app.GUI.FavouriteTrainListActivity;
import com.example.lisamazzini.train_app.GUI.StationListActivity;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Train;
import com.example.lisamazzini.train_app.Notification.NotificationService;
import com.example.lisamazzini.train_app.Parser.NewTrain;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Utilities;
import com.squareup.okhttp.internal.Util;

import java.util.LinkedList;
import java.util.List;

/**
 * This is the Adapter for the
 *
 * Created by lisamazzini on 25/01/15.
 */
public class FavTrainAdapter extends RecyclerView.Adapter<FavTrainAdapter.Holder> {

    List<NewTrain> list;

    public FavTrainAdapter(List<NewTrain> list, ){
        this.list = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.train_adapter, viewGroup, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        final NewTrain train = list.get(position);

        holder.trainCategory.setText(train.getCategoria());
        holder.trainNumber.setText("" + train.getNumeroTreno());
        holder.delay.setText("" + train.getRitardo());
        holder.lastSeenStation.setText(train.getStazioneUltimoRilevamento());
        holder.lastSeemTime.setText(Utilities.fromMsToTime(train.getOraUltimoRilevamento()));

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("ooooooooooooooo---------------", "Son qua!");
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_fav_train, popupMenu.getMenu());
                final View view = v;

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    FavouriteTrainController favCtrl = new FavouriteTrainController(view.getContext());
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete:
                                Log.d("---------CULO------------", "Prima di rimuovere ce ne sono  " + getItemCount() + "elem n' " + position);
                                favCtrl.removeFavourite(train.getNumeroTreno() + Constants.SEPARATOR + train.getIdOrigine());
                                if(position == 0 && getItemCount() == 1){
                                    list = new LinkedList<>();
                                }else {
                                    list.remove(position);
                                }
                                Log.d("----------CULO-----------", "Dopo aver rimosso e prima di notify "+ getItemCount());
                                notifyItemRemoved(position);
                                notifyDataSetChanged();
                                Log.d("--------CULO-------------", "Dopo notify " + getItemCount());

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
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView trainCategory;
        TextView trainNumber;
        TextView delay;
        TextView lastSeenStation;
        TextView lastSeemTime;
        TextView isMoving;
        Button menu;

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            menu =(Button) itemView.findViewById(R.id.options);
            trainCategory = (TextView) itemView.findViewById(R.id.trainCat);
            trainNumber = (TextView) itemView.findViewById(R.id.trainNum);
            delay = (TextView) itemView.findViewById(R.id.delaaay);
            lastSeenStation = (TextView) itemView.findViewById(R.id.lastseen);
            lastSeemTime = (TextView) itemView.findViewById(R.id.timelastseen);
            isMoving = (TextView) itemView.findViewById(R.id.moving);

        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), StationListActivity.class);
            i.putExtra("trainNumber", this.trainNumber.getText().toString());
            v.getContext().startActivity(i);
        }
    }
}


