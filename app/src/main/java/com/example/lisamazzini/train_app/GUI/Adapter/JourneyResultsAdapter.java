package com.example.lisamazzini.train_app.GUI.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.lisamazzini.train_app.Model.JourneyTrain;

import java.util.List;

public class JourneyResultsAdapter extends RecyclerView.Adapter<JourneyResultsAdapter.ViewHolder>{

    private final List<JourneyTrain> list;

    public static class ViewHolder extends RecyclerView.ViewHolder {





        public ViewHolder(View itemView) {
            super(itemView);
            // qui prendo i riferimenti alla view con findviewbyid
        }
    }

    public JourneyResultsAdapter(List<JourneyTrain> list) {
        this.list = list;
    }

    //chiamato quando viene instanziato il viewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    //chiamato quando si collegano i dati al viewholder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
