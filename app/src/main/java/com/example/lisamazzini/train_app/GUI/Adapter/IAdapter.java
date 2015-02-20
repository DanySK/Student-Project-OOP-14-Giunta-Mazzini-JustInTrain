package com.example.lisamazzini.train_app.gui.Adapter;

import android.view.ViewGroup;

public interface IAdapter<X> {

    public X onCreateViewHolder(ViewGroup parent, int viewType);

    public void onBindViewHolder(X holder, int position);

    public int getItemCount();

}
