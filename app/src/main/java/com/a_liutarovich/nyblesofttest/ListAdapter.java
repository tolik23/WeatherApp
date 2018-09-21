package com.a_liutarovich.nyblesofttest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a_liutarovich.nyblesofttest.DB.WeatherDB;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>  {

    private Context mContext;
    List<WeatherDB> list;

    public ListAdapter(Context mContext, List<WeatherDB> list){
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.mDate.setText(new SimpleDateFormat("d MMMM, yyyy  k:mm", new Locale("ru")).format(list.get(i).getDate()));
        holder.mCity.setText(list.get(i).getAdress() + "    " + list.get(i).getLatitude() + ", " + list.get(i).getLongitude());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mDate;
        private TextView mCity;
        public ViewHolder(View view) {
            super(view);
            mDate = (TextView)view.findViewById(R.id.tv_date);
            mCity = (TextView)view.findViewById(R.id.tv_city);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, DescriptionActivity.class);
            intent.putExtra("id", list.get(getAdapterPosition()).getId());
            mContext.startActivity(intent);
        }
    }
}