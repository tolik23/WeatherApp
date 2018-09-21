package com.a_liutarovich.nyblesofttest.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a_liutarovich.nyblesofttest.DB.ElementDBSet;
import com.a_liutarovich.nyblesofttest.Models.WeatherDay;
import com.a_liutarovich.nyblesofttest.R;
import com.a_liutarovich.nyblesofttest.Net.Response;
import com.a_liutarovich.nyblesofttest.Net.WeatherAPI;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class WeatherFragment extends Fragment {

    private TextView mTvCity, mTvCoord, mTvDescription, mTvTemp, mTvPressure, mTvHumidity;
    private ImageView mIvWeatherIcon;
    private LocationManager locationManager;
    private static final String TAG = ListFragment.class.getSimpleName();
    Response mResponse = new Response();
    private ElementDBSet mElementDBSet = new ElementDBSet();

    public static WeatherFragment createInstance(FragmentManager fragmentManager) {
        WeatherFragment myFragment = (WeatherFragment) fragmentManager.findFragmentByTag(WeatherFragment.TAG);
        if (myFragment == null) {
            myFragment = new WeatherFragment();
        }
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mTvCoord = (TextView) view.findViewById(R.id.tv_coord);
        mTvCity = (TextView) view.findViewById(R.id.tv_city);
        mTvDescription = (TextView) view.findViewById(R.id.tv_description);
        mTvTemp = (TextView) view.findViewById(R.id.tv_temp);
        mTvPressure = (TextView) view.findViewById(R.id.tv_pressure);
        mTvHumidity = (TextView) view.findViewById(R.id.tv_humidity);
        mIvWeatherIcon = (ImageView) view.findViewById(R.id.iv_weather_image);
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,locationListener,null);
    }

    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            mTvCoord.setText(formatLocation(location) + "    " + new SimpleDateFormat("d.MM.yyyy  k:mm", new Locale("ru")).format(new Date().getTime()));
        }
        Geocoder gCoder = new Geocoder(getActivity());
        List<Address> addresses = null;
        try {
            addresses = gCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && addresses.size() > 0) {
                mTvCity.setText(addresses.get(0).getLocality()+ "  " + addresses.get(0).getCountryName() );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        WeatherDay weatherDay = mResponse.callWeather(getActivity(), location.getLatitude(), location.getLongitude(), "metric", WeatherAPI.KEY);
        if (weatherDay != null){
            mElementDBSet.setWeatherElement(getActivity(), addresses.get(0).getLocality()+ "  " + addresses.get(0).getCountryName(), new Date().getTime(), weatherDay.getWeather().get(0).getDescription(),
                    new BigDecimal(location.getLatitude()).setScale(4, RoundingMode.UP).doubleValue(),  new BigDecimal(location.getLongitude()).setScale(4, RoundingMode.UP).doubleValue(),
                    weatherDay.getMain().getTemp(), weatherDay.getMain().getPressure(), weatherDay.getMain().getHumidity());
            Picasso.with(getActivity())
                    .load("http://openweathermap.org/img/w/" + weatherDay.getWeather().get(0).getIcon() + ".png")
                    .placeholder(R.drawable.ic__download_blue_150dp)
                    .error(R.drawable.ic__download_blue_150dp)
                    .into(mIvWeatherIcon);
            mTvTemp.setText(weatherDay.getMain().getTemp() + "  ℃");
            mTvDescription.setText(weatherDay.getWeather().get(0).getDescription());
            mTvPressure.setText("Pressure: " + weatherDay.getMain().getPressure() + " hPa");
            mTvHumidity.setText("Humidity: " + weatherDay.getMain().getHumidity() + " %");
        }
    }

    private String formatLocation(Location location) {
        if (location == null)
            return "";
        return String.format(
                "%1$.4f     %2$.4f",
                location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
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

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            locationManager.removeUpdates(this);
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
            }
        }
    };
}