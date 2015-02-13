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

/**
 * Created by lisamazzini on 09/02/15.
 */
public class AchievementListAdapter  extends RecyclerView.Adapter<AchievementListAdapter.AchievementViewHolder> implements IAdapter<AchievementListAdapter.AchievementViewHolder>{

    List<String> achievements = new LinkedList<>();

    public AchievementListAdapter(List<String> achievements){
        this.achievements = achievements;
    }

    @Override
    public AchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_achievement, parent, false);
        return new AchievementViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AchievementViewHolder holder, int position) {
        holder.text.setText(achievements.get(position));
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    public static class AchievementViewHolder extends RecyclerView.ViewHolder{
        protected TextView text;

        public AchievementViewHolder(View v){
            super(v);
            text = (TextView)v.findViewById(R.id.ach_text);
        }
    }
}
