package com.trip.expensemanager.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.trip.expensemanager.R;
import com.trip.expensemanager.database.LocalDB;
import com.trip.expensemanager.fragments.AddTripFragment;
import com.trip.utils.Constants;


public class TripsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }


        if (savedInstanceState == null) {
            long lngUserId = new LocalDB(this).retrieve();
            getSupportFragmentManager().beginTransaction().add(R.id.container, AddTripFragment.newInstance(
                    lngUserId, getIntent().getBooleanExtra(Constants.STR_SYNC_NEEDED, false))).commit();
        }
    }

}
