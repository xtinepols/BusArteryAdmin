package com.example.christinebpolesti.busarteryadmin.Activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.example.christinebpolesti.busarteryadmin.R;

public class DriverviewActivity extends AppCompatActivity {

    private EditText driversName;
    private EditText employeeId;
    private EditText age;
    private EditText username;
    private EditText busNumber;

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverview);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = getApplicationContext().getSharedPreferences("driverData", MODE_PRIVATE);
        editor = pref.edit();

        driversName = (EditText) findViewById(R.id.edtDriverName);
        employeeId = (EditText) findViewById(R.id.edtEmployeeID);
        age = (EditText) findViewById(R.id.edtDriverAge);
        username = (EditText) findViewById(R.id.edtDriverUsername);
        busNumber = (EditText) findViewById(R.id.edtDriverBusnum);

        driversName.setText(pref.getString("driverName", ""));
        employeeId.setText(pref.getString("employeeId", ""));
        age.setText(pref.getString("age", ""));
        username.setText(pref.getString("username", ""));
        busNumber.setText(pref.getString("busNumber", ""));

        editor.clear();
        editor.commit();
    }
}
