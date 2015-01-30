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
import com.example.lisamazzini.train_app.Model.Train;
import com.example.lisamazzini.train_app.Notification.NotificationService;
import com.example.lisamazzini.train_app.R;

import java.util.List;

/**
 * This is the Adapter for the
 *
 * Created by lisamazzini on 25/01/15.
 */
public class FavTrainAdapter extends RecyclerView.Adapter<FavTrainAdapter.Holder> {

    List<Train> list;

    public FavTrainAdapter(List<Train> list){
        this.list = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.train_adapter, viewGroup, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final Train train = list.get(position);

        holder.trainCategory.setText(train.getCategory());
        holder.trainNumber.setText(train.getNumber());
        holder.delay.setText(Integer.toString(train.getDelay()));
        holder.lastSeenStation.setText(train.getLastSeenStation());
        holder.lastSeemTime.setText(train.getLastSeenTime());
        holder.isMoving.setText("" + train.isMoving());

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("oooooooooooooooooo----------------", "Son qua!");
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                final FavouriteTrainController favCtrl = new FavouriteTrainController(v.getContext());
                popupMenu.getMenuInflater().inflate(R.menu.menu_fav_train, popupMenu.getMenu());
                final Intent intent = new Intent(v.getContext(), NotificationService.class);
                final View view = v;

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete:
                                favCtrl.removeFavourite(train.getNumber());
                                return true;
                            case R.id.pin:
                                intent.putExtra("number", train.getNumber());
                                view.getContext().startService(intent);
                                return true;
                            case R.id.unpin:
                                view.getContext().stopService(intent);
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

    public static class Holder extends RecyclerView.ViewHolder {

        TextView trainCategory;
        TextView trainNumber;
        TextView delay;
        TextView lastSeenStation;
        TextView lastSeemTime;
        TextView isMoving;
        Button menu;

        public Holder(View itemView) {
            super(itemView);
            menu =(Button) itemView.findViewById(R.id.options);
            trainCategory = (TextView) itemView.findViewById(R.id.trainCat);
            trainNumber = (TextView) itemView.findViewById(R.id.trainNum);
            delay = (TextView) itemView.findViewById(R.id.delaaay);
            lastSeenStation = (TextView) itemView.findViewById(R.id.lastseen);
            lastSeemTime = (TextView) itemView.findViewById(R.id.timelastseen);
            isMoving = (TextView) itemView.findViewById(R.id.moving);
        }
    }

}
