package com.example.lisamazzini.train_app.GUI.Adapter;

import android.view.ViewGroup;

/**
 * Created by lisamazzini on 11/02/15.
 */
public interface IAdapter<X> {

    public X onCreateViewHolder(ViewGroup parent, int viewType);

    public void onBindViewHolder(X holder, int position);

    public int getItemCount();

}
