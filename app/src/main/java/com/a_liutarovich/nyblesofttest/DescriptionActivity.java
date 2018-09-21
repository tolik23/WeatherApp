package com.a_liutarovich.nyblesofttest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.a_liutarovich.nyblesofttest.Fragments.RequestDescriptionFragment;

import static com.a_liutarovich.nyblesofttest.Fragments.RequestDescriptionFragment.ARG_USER_ID;

public class DescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);

        RequestDescriptionFragment fragment = new RequestDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_USER_ID, id);
        fragment.setArguments(bundle);
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.conteiner, fragment);
        transaction.commit();
    }
}
