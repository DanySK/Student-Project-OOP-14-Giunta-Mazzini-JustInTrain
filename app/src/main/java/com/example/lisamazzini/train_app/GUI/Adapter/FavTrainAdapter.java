package com.example.lisamazzini.train_app.GUI.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.lisamazzini.train_app.GUI.FavouriteTrainListActivity;
import com.example.lisamazzini.train_app.Model.Train;
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
        holder.trainCategory.setText(list.get(position).getCategory());
        holder.trainNumber.setText(list.get(position).getNumber());
        holder.delay.setText(list.get(position).getDelay());
        holder.lastSeenStation.setText(list.get(position).getLastSeenStation());
        holder.lastSeemTime.setText(list.get(position).getLastSeenTime());
        holder.isMoving.setText("" + list.get(position).isMoving());

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);

                popupMenu.inflate(R.menu.menu_fav_train);


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
            delay = (TextView) itemView.findViewById(R.id.delay);
            lastSeenStation = (TextView) itemView.findViewById(R.id.lastseen);
            lastSeemTime = (TextView) itemView.findViewById(R.id.timelastseen);
            isMoving = (TextView) itemView.findViewById(R.id.moving);
        }
    }

}
