package com.example.lisamazzini.train_app.gui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lisamazzini.train_app.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Adapter per la lista di achievements.
 *
 * @author lisamazzini
 */
public class AchievementListAdapter  extends RecyclerView.Adapter<AchievementListAdapter.AchievementViewHolder> implements IAdapter<AchievementListAdapter.AchievementViewHolder> {

    private final List<String> achievements = new LinkedList<>();

    /**
     * Costruttore.
     * @param pAchievements lista di achievements
     */
    public AchievementListAdapter(final List<String> pAchievements) {
        this.achievements.addAll(pAchievements);
    }

    @Override
    public final AchievementViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_achievement, parent, false);
        return new AchievementViewHolder(itemView);
    }

    @Override
    public final void onBindViewHolder(final AchievementViewHolder holder, final int position) {
        holder.text.setText(achievements.get(position));
    }

    @Override
    public final int getItemCount() {
        return achievements.size();
    }


    /**
     * ViewHolder di achievements.
     *
     * @author albertogiunta
     */
    public static class AchievementViewHolder extends RecyclerView.ViewHolder {
        private final TextView text;

        /**
         * Viewholder per gli elementi della view di una lista di achievemnents.
         * @param v la view da costruire
         */
        public AchievementViewHolder(final View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.ach_text);
        }
    }
}
