package com.a_liutarovich.nyblesofttest.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a_liutarovich.nyblesofttest.DB.QueriesDB;
import com.a_liutarovich.nyblesofttest.DB.WeatherDB;
import com.a_liutarovich.nyblesofttest.ListAdapter;
import com.a_liutarovich.nyblesofttest.R;

import java.sql.SQLException;
import java.util.List;

public class ListFragment extends Fragment {

    private static final String TAG = ListFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;
    private QueriesDB mQueriesDB = new QueriesDB();

    public static ListFragment createInstance(FragmentManager fragmentManager) {
        ListFragment myFragment = (ListFragment) fragmentManager.findFragmentByTag(ListFragment.TAG);
        if (myFragment == null) {
            myFragment = new ListFragment();
        }
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        initView();
        return view;
    }

    public void initView() {
        try {
            final List<WeatherDB> weatherDBlist = mQueriesDB.getWeatherDB(getActivity());
            if (weatherDBlist != null) {
                mListAdapter = new ListAdapter(getActivity(), weatherDBlist);
                mRecyclerView.setAdapter(mListAdapter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            initView();
        } else {
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        setHasOptionsMenu(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}