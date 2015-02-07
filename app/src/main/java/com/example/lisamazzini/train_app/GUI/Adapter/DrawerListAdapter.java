package com.example.lisamazzini.train_app.GUI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteTrainController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.GUI.FavouriteTrainListActivity;
import com.example.lisamazzini.train_app.R;

public class DrawerListAdapter extends RecyclerView.Adapter<DrawerListAdapter.DrawerListViewHolder> {


    String TITLES[];
    Context context;


    public DrawerListAdapter(String[] TITLES, Context context) {
        this.TITLES = TITLES;
        this.context = context;
    }

    @Override
    public DrawerListAdapter.DrawerListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_drawer_list_item, parent, false);
        return new DrawerListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DrawerListAdapter.DrawerListViewHolder holder, int position) {
        holder.navItem.setText(TITLES[position]);
    }

    @Override
    public int getItemCount() {
        return TITLES.length;
    }

    public class DrawerListViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected TextView navItem;

        public DrawerListViewHolder(View v) {
            super(v);
            v.setClickable(true);
            v.setOnClickListener(this);
            navItem = (TextView) v.findViewById(R.id.tDrawerListItem);
        }

        @Override
        public void onClick(View v) {
            switch (getPosition()) {
                case 0 :
                    Intent i = new Intent(v.getContext(), FavouriteTrainListActivity.class);
                    v.getContext().startActivity(i);
                    break;

                case 1:
                    IFavouriteController fc = FavouriteTrainController.getInstance();
                    fc.setContext(v.getContext());
                    fc.removeFavourites();
            }
        }
    }
}
