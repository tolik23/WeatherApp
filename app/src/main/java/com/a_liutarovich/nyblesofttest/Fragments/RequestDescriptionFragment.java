package com.a_liutarovich.nyblesofttest.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a_liutarovich.nyblesofttest.DB.QueriesDB;
import com.a_liutarovich.nyblesofttest.DB.WeatherDB;
import com.a_liutarovich.nyblesofttest.R;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RequestDescriptionFragment extends Fragment {

    private static final String TAG = RequestDescriptionFragment.class.getSimpleName();
    private QueriesDB mQueriesDB = new QueriesDB();
    public static final String ARG_USER_ID = "id";
    private int positionId;
    private TextView mTvCity, mTvCoord, mTvDescription, mTvTemp, mTvPressure, mTvHumidity;
    private ImageView mIvWeatherIcon;

    public static RequestDescriptionFragment createInstance(FragmentManager fragmentManager, int positionId) {
        RequestDescriptionFragment myFragment = (RequestDescriptionFragment) fragmentManager.findFragmentByTag(RequestDescriptionFragment.TAG);
        if (myFragment == null) {
            myFragment = new RequestDescriptionFragment();
        }
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        Bundle bundle = this.getArguments();
        positionId = bundle.getInt(ARG_USER_ID,0);

        mTvCoord = (TextView) view.findViewById(R.id.tv_coord);
        mTvCity = (TextView) view.findViewById(R.id.tv_city);
        mTvDescription = (TextView) view.findViewById(R.id.tv_description);
        mTvTemp = (TextView) view.findViewById(R.id.tv_temp);
        mTvPressure = (TextView) view.findViewById(R.id.tv_pressure);
        mTvHumidity = (TextView) view.findViewById(R.id.tv_humidity);
        mIvWeatherIcon = (ImageView) view.findViewById(R.id.iv_weather_image);
        mIvWeatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_history_blue_150dp));
        initView();
        return view;
    }

    public void initView() {
        try {
            final List<WeatherDB> weatherDBlist = mQueriesDB.getWeatherDBById(getActivity(), positionId);
            if (weatherDBlist != null) {
                mTvCity.setText(weatherDBlist.get(0).getAdress());
                mTvCoord.setText(weatherDBlist.get(0).getLatitude() + ", " + weatherDBlist.get(0).getLongitude() + "   " + new SimpleDateFormat("d MMMM, yyyy  k:mm", new Locale("ru")).format(weatherDBlist.get(0).getDate()));
                mTvDescription.setText(weatherDBlist.get(0).getDescription());
                mTvTemp.setText(weatherDBlist.get(0).getTemp() + "  ℃");
                mTvDescription.setText(weatherDBlist.get(0).getDescription());
                mTvPressure.setText("Pressure: " + weatherDBlist.get(0).getPressure() + " hPa");
                mTvHumidity.setText("Humidity: " + weatherDBlist.get(0).getHumidity() + " %");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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