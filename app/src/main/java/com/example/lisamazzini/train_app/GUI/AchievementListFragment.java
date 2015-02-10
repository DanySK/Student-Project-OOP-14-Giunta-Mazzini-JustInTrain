package com.example.lisamazzini.train_app.GUI;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.*;

import com.example.lisamazzini.train_app.Controller.AchievementListController;
import com.example.lisamazzini.train_app.GUI.Adapter.AchievementListAdapter;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.R;

import java.util.*;

/**
 * Created by lisamazzini on 10/02/15.
 */
public class AchievementListFragment extends Fragment{
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private AchievementListAdapter adapter;
    private AchievementListController achievementListController;

    public static AchievementListFragment newInstance() {
        return new AchievementListFragment();
    }

    public AchievementListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_achievement_list, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_list);
        achievementListController = new AchievementListController(getActivity());
        this.manager = new LinearLayoutManager(getActivity());
        adapter = new AchievementListAdapter(achievementListController.computeAchievement());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
