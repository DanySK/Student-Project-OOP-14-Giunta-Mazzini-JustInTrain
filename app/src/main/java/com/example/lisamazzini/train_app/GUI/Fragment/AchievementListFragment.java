package com.example.lisamazzini.train_app.gui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lisamazzini.train_app.controller.AchievementListController;
import com.example.lisamazzini.train_app.gui.adapter.AchievementListAdapter;
import com.example.lisamazzini.train_app.R;

/**
 * Fragment che mostra la lista di achievements sbloccati.
 *
 * @author lisamazzini
 */
public class AchievementListFragment extends Fragment implements IBaseFragment {

    /**
     * Metodo che restituisce una nuova istanza del fragment.
     * @return fragment
     */
    public static AchievementListFragment newInstance() {
        return new AchievementListFragment();
    }

    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                   final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_achievement_list, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_list);
        final AchievementListController achievementListController = new AchievementListController(getActivity());
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        final AchievementListAdapter adapter = new AchievementListAdapter(achievementListController.computeAchievement());

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();

        return view;
    }

}
