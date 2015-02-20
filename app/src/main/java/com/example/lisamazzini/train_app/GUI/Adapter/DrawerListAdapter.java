package com.example.lisamazzini.train_app.gui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lisamazzini.train_app.controller.favourites.FavouriteTrainController;
import com.example.lisamazzini.train_app.controller.favourites.IFavouriteController;
import com.example.lisamazzini.train_app.gui.activity.AchievementListActivity;
import com.example.lisamazzini.train_app.gui.activity.FavouriteTrainListActivity;
import com.example.lisamazzini.train_app.R;

import java.util.Arrays;


/**
 * Adapter per la lista di opzioni del navigation drawer.
 *
 * @author albertogiunta
 */
public class DrawerListAdapter extends RecyclerView.Adapter<DrawerListAdapter.DrawerListViewHolder> implements IAdapter<DrawerListAdapter.DrawerListViewHolder> {

    private final String[] titles;

    /**
     * Costruttore.
     * @param pTitles l'array di stringhe corrispondenti ognuna a un elemento della lista del navigation drawer.
     */
    public DrawerListAdapter(final String[] pTitles) {

        this.titles = Arrays.copyOf(pTitles, pTitles.length);
    }

    @Override
    public final DrawerListAdapter.DrawerListViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_drawer_list_item, parent, false);
        return new DrawerListViewHolder(itemView);
    }

    @Override
    public final void onBindViewHolder(final DrawerListAdapter.DrawerListViewHolder holder, final int position) {
        holder.navItem.setText(titles[position]);
    }

    @Override
    public final int getItemCount() {
        return titles.length;
    }

    /**
     * Viewholder di stringhe.
     *
     * @author albertogiunta
     */
    public class DrawerListViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView navItem;

        /**
         * Costruttore.
         * @param v la view da costruire
         */
        public DrawerListViewHolder(final View v) {
            super(v);
            v.setClickable(true);
            v.setOnClickListener(this);
            navItem = (TextView) v.findViewById(R.id.tDrawerListItem);
        }

        @Override
        public final void onClick(final View v) {
            switch (getPosition()) {
                case 0 :
                    final Intent i = new Intent(v.getContext(), FavouriteTrainListActivity.class);
                    v.getContext().startActivity(i);
                    break;
                case 1:
                    final IFavouriteController fc = FavouriteTrainController.getInstance();
                    fc.setContext(v.getContext());
                    fc.removeFavourites();
                    break;
                case 2:
                    final Intent intent = new Intent(v.getContext(), AchievementListActivity.class);
                    v.getContext().startActivity(intent);
                default:
                    break;
            }
        }
    }
}
