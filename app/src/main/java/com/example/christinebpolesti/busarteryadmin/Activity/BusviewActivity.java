package com.example.christinebpolesti.busarteryadmin.Activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.example.christinebpolesti.busarteryadmin.R;

public class BusviewActivity extends AppCompatActivity {

    private EditText busNum;
    private EditText route;
    private EditText busType;
    private EditText capacity;

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busview);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = getApplicationContext().getSharedPreferences("busData", MODE_PRIVATE);
        editor = pref.edit();

        busNum = (EditText) findViewById(R.id.edtBusNumber);
        route = (EditText) findViewById(R.id.edtBusRoute);
        busType = (EditText) findViewById(R.id.edtBusType);
        capacity = (EditText) findViewById(R.id.edtBusCapacity);

        busNum.setText(pref.getString("busNumber", ""));
        route.setText(pref.getString("route", ""));
        busType.setText(pref.getString("busType", ""));
        capacity.setText(pref.getString("capacity", ""));
    }
}