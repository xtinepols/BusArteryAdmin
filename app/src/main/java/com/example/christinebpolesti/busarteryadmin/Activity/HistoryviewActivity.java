package com.example.christinebpolesti.busarteryadmin.Activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.example.christinebpolesti.busarteryadmin.R;

public class HistoryviewActivity extends AppCompatActivity {

    private EditText driversName;
    private EditText busNumber;
    private EditText route;
    private EditText earning;
    private EditText date;
    private EditText timeStarted;
    private EditText timeEnded;
    private EditText distance;
    private EditText passenger;
    private EditText tripCount;

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyview);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = getApplicationContext().getSharedPreferences("historyData", MODE_PRIVATE);
        editor = pref.edit();

        driversName = (EditText) findViewById(R.id.edtDriversName);
        busNumber = (EditText) findViewById(R.id.edtBusnum);
        route = (EditText) findViewById(R.id.edtRoute);
        earning = (EditText) findViewById(R.id.edtEarning);
        date = (EditText) findViewById(R.id.edtDate);
        timeStarted = (EditText) findViewById(R.id.edtTimeStarted);
        timeEnded = (EditText) findViewById(R.id.edtTimeEnded);
        distance = (EditText) findViewById(R.id.edtDistance);
        passenger = (EditText) findViewById(R.id.edtTotalPass);
        tripCount = (EditText) findViewById(R.id.edtTripCount);

        driversName.setText(pref.getString("driversName", ""));
        busNumber.setText(pref.getString("busNumber", ""));
        route.setText(pref.getString("route", ""));
        earning.setText(pref.getString("earnings", ""));
        date.setText(pref.getString("date", ""));
        timeStarted.setText(pref.getString("timeStarted", ""));
        timeEnded.setText(pref.getString("timeEnded", ""));
        distance.setText(pref.getString("distance", ""));
        passenger.setText(pref.getString("passenger", ""));
        tripCount.setText(pref.getString("tripCount", ""));

        editor.clear();
        editor.commit();
    }
}