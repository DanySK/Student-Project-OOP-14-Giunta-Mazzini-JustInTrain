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

import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteTrainController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.GUI.StationListActivity;
import com.example.lisamazzini.train_app.Model.NewTrain;
import com.example.lisamazzini.train_app.R;

import java.util.LinkedList;
import java.util.List;

/**
 * This is the Adapter for the
 *
 * Created by lisamazzini on 25/01/15.
 */
public class FavTrainAdapter extends RecyclerView.Adapter<FavTrainAdapter.Holder> {

    List<NewTrain> list;
    private IFavouriteController favouriteController = FavouriteTrainController.getInstance();

    public FavTrainAdapter(List<NewTrain> list){

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

        final NewTrain train = list.get(position);
        holder.trainCategory.setText(train.getCategoria());
        holder.trainNumber.setText("" + train.getNumeroTreno());

        if(train.getFermate().size() == 0  || !train.getSubTitle().equals("")){
            holder.extra.setText(train.getSubTitle());
        }else{
            holder.delay.setText("" + train.getRitardo());
            holder.lastSeenStation.setText(train.getStazioneUltimoRilevamento());
            holder.lastSeemTime.setText(train.getCompOraUltimoRilevamento());
        }
        holder.stationCode = train.getIdOrigine();

//        holder.menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
//                popupMenu.getMenuInflater().inflate(R.menu.menu_fav_train, popupMenu.getMenu());
//                final View view = v;
//                favouriteController.setContext(v.getContext());

//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.delete:
//                                favouriteController.removeFavourite(train.getNumeroTreno().toString(), train.getIdOrigine());
//                                if (position == 0 && getItemCount() == 1) {
//                                    list = new LinkedList<>();
//                                } else {
//                                    list.remove(position);
//                                }
//                                notifyItemRemoved(position);
//                                notifyDataSetChanged();
//                                return true;
//                            default:
//                                return false;
//                        }
//                    }
//                });
//                popupMenu.show();
//            }
//        });
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
        TextView extra;
        String stationCode;
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
            extra = (TextView) itemView.findViewById(R.id.extra);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), StationListActivity.class);
            i.putExtra("stationCode", this.stationCode);
            i.putExtra("trainNumber", this.trainNumber.getText().toString());
            v.getContext().startActivity(i);
        }
    }
}


