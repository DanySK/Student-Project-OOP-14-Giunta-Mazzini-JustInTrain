package com.example.lisamazzini.train_app.GUI;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;

import com.example.lisamazzini.train_app.GUI.Adapter.AchievementListAdapter;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.R;

import java.util.*;

/**
 * Created by lisamazzini on 10/02/15.
 */
public class AchievementListFragment extends Fragment{
    private RecyclerView recyclerView;
    List<String> achievements = new LinkedList<>();
    private final SharedPreferences data;
    private final SharedPreferences.Editor editor;
    private LinearLayoutManager manager;
    private AchievementListAdapter adapter;

    public static AchievementListFragment newInstance() {
        return new AchievementListFragment();
    }

    public AchievementListFragment() {
        data = getActivity().getSharedPreferences("ACHIEVEMENT_STORE", Context.MODE_APPEND);
        editor = data.edit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievement_list, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_list);
        this.manager = new LinearLayoutManager(getActivity());
        adapter = new AchievementListAdapter(achievements);
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        for(String s : data.getAll().keySet()){
            switch (s) {
                case Constants.DELAY_ACH:
                    achievements.add("Ritardatario! (10 minuti)");
                    break;
                case Constants.PIN_ACH:
                    achievements.add("Pinnatore seriale");
                    break;
                default:
                    break;
            }
        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
