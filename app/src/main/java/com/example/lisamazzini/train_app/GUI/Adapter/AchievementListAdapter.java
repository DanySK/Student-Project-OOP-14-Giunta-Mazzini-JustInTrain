package com.example.lisamazzini.train_app.GUI.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lisamazzini.train_app.R;

import java.util.LinkedList;
import java.util.List;

public class AchievementListAdapter  extends RecyclerView.Adapter<AchievementListAdapter.AchievementViewHolder> implements IAdapter<AchievementListAdapter.AchievementViewHolder>{

    private List<String> achievements = new LinkedList<>();

    public AchievementListAdapter(final List<String> achievements){
        this.achievements = achievements;
    }

    @Override
    public AchievementViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_achievement, parent, false);
        return new AchievementViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AchievementViewHolder holder, final int position) {
        holder.text.setText(achievements.get(position));
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    public static class AchievementViewHolder extends RecyclerView.ViewHolder{
        protected TextView text;

        public AchievementViewHolder(final View v){
            super(v);
            text = (TextView)v.findViewById(R.id.ach_text);
        }
    }
}
