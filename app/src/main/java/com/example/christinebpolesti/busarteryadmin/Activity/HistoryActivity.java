package com.example.christinebpolesti.busarteryadmin.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.christinebpolesti.busarteryadmin.Adapter.AllHistoryAdapter;
import com.example.christinebpolesti.busarteryadmin.Adapter.BusNumHistoryAdapter;
import com.example.christinebpolesti.busarteryadmin.Adapter.DateHistoryAdapter;
import com.example.christinebpolesti.busarteryadmin.Adapter.NameHistoryAdapter;
import com.example.christinebpolesti.busarteryadmin.Config.Config;
import com.example.christinebpolesti.busarteryadmin.Interface.ItemClickListener;
import com.example.christinebpolesti.busarteryadmin.Pojo.History;
import com.example.christinebpolesti.busarteryadmin.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, ItemClickListener {

    private String name, busnum, route, earnings, distance, date, timeStarted, timeEnded, totalPassenger, tripCount, choose, busNumber, driverName, strmonth, strday;
    private RecyclerView recycler;
    private AllHistoryAdapter allAdapter;
    private NameHistoryAdapter nameAdapter;
    private BusNumHistoryAdapter busNumAdapter;
    private DateHistoryAdapter dateAdapter;
    private List<History> historyList = new ArrayList<>();
    private List<String> busnumList = new ArrayList<>();
    private List<String> driverList = new ArrayList<>();

    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    private Spinner mspnrChoose, mspnrBus, mspnrDriver;

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        pref = getApplicationContext().getSharedPreferences("historyData", MODE_PRIVATE);
        editor = pref.edit();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mspnrChoose = (Spinner) findViewById(R.id.spinnerChoose);
        mspnrChoose.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.history_choose, android.R.layout.simple_spinner_dropdown_item);
        mspnrChoose.setAdapter(adapter);

        mspnrBus = (Spinner) findViewById(R.id.spinnerBus);
        mspnrBus.setOnItemSelectedListener(this);

        mspnrDriver = (Spinner) findViewById(R.id.spinnerDriver);
        mspnrDriver.setOnItemSelectedListener(this);

        recycler = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(mLayoutManager);

        dateView = (TextView) findViewById(R.id.dateView);
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        fill();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            showDate(year, monthOfYear + 1, dayOfMonth);
        }
    };

    private void showDate(int year, int month, int day) {
        if (month == 1)
            strmonth = "Jan";
        if (month == 2)
            strmonth = "Feb";
        if (month == 3)
            strmonth = "Mar";
        if (month == 4)
            strmonth = "Apr";
        if (month == 5)
            strmonth = "May";
        if (month == 6)
            strmonth = "Jun";
        if (month == 7)
            strmonth = "Jul";
        if (month == 8)
            strmonth = "Aug";
        if (month == 9)
            strmonth = "Sep";
        if (month == 10)
            strmonth = "Oct";
        if (month == 11)
            strmonth = "Nov";
        if (month == 12)
            strmonth = "Dec";

        if (day < 10) {
            if (day == 1)
                strday = "01";
            if (day == 2)
                strday = "02";
            if (day == 3)
                strday = "03";
            if (day == 4)
                strday = "04";
            if (day == 5)
                strday = "05";
            if (day == 6)
                strday = "06";
            if (day == 7)
                strday = "07";
            if (day == 8)
                strday = "08";
            if (day == 9)
                strday = "09";

            dateView.setText(strmonth + "-" + strday + "-" +year);
        } else {
            dateView.setText(strmonth + "-" + day + "-" +year);
        }

        historyList = new ArrayList<>();
//        ref = new Firebase(Config.FIREBASE_URL_HISTORY_DRIVER);
//        Query dateQuery = ref.orderByChild("date").equalTo(dateView.getText().toString());
//        dateQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (historyList.size() > 0) {
//                    historyList.clear();
//                }
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    name = snapshot.child("driversName").getValue().toString().trim();
//                    busnum = snapshot.child("busNum").getValue().toString().trim();
//                    route = snapshot.child("route").getValue().toString().trim();
//                    earnings = snapshot.child("earnings").getValue().toString().trim();
//                    date = snapshot.child("date").getValue().toString().trim();
//                    timeStarted = snapshot.child("timeStarted").getValue().toString().trim();
//                    timeEnded = snapshot.child("timeEnded").getValue().toString().trim();
//                    distance = snapshot.child("distance").getValue().toString().trim();
//                    totalPassenger = snapshot.child("totalPassenger").getValue().toString().trim();
//                    tripCount = snapshot.child("tripCount").getValue().toString().trim();
//
//                    historyList.add(new History(name, busnum, route, earnings, distance, date, timeStarted, timeEnded, totalPassenger, tripCount));
//                }
//                Collections.reverse(historyList);
//
//                dateAdapter = new DateHistoryAdapter(historyList);
//                recycler.setAdapter(dateAdapter);
//                dateAdapter.setClickListener(HistoryActivity.this);
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Toast.makeText(HistoryActivity.this, firebaseError.getMessage() + "error", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.history:
                Intent mIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, mIntent)) {
                    TaskStackBuilder.create(this).addNextIntentWithParentStack(mIntent).startActivities();
                } else {
                    NavUtils.navigateUpTo(this, mIntent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerChoose:
                choose = parent.getItemAtPosition(position).toString();
                if (choose.equals("All")) {
                    mspnrBus.setVisibility(View.INVISIBLE);
                    mspnrDriver.setVisibility(View.INVISIBLE);
                    dateView.setVisibility(View.INVISIBLE);

                    fill();
                } else if (choose.equals("Bus number")) {
                    mspnrBus.setVisibility(View.VISIBLE);
                    mspnrDriver.setVisibility(View.INVISIBLE);
                    dateView.setVisibility(View.INVISIBLE);

                    fillSpnrBus();
                } else if (choose.equals("Name of Driver")) {
                    mspnrDriver.setVisibility(View.VISIBLE);
                    mspnrBus.setVisibility(View.INVISIBLE);
                    dateView.setVisibility(View.INVISIBLE);

                    fillSpnrDriver();
                }
                if (choose.equals("Date")) {
                    mspnrDriver.setVisibility(View.INVISIBLE);
                    mspnrBus.setVisibility(View.INVISIBLE);
                    dateView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.spinnerBus:
                busNumber = parent.getItemAtPosition(position).toString();

                historyList = new ArrayList<>();
//                ref = new Firebase(Config.FIREBASE_URL_HISTORY_DRIVER);
//                Query equalBusNum = ref.orderByChild("busNum").equalTo(busNumber);
//                equalBusNum.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (historyList.size() > 0) {
//                            historyList.clear();
//                        }
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            name = snapshot.child("driversName").getValue().toString().trim();
//                            busnum = snapshot.child("busNum").getValue().toString().trim();
//                            route = snapshot.child("route").getValue().toString().trim();
//                            earnings = snapshot.child("earnings").getValue().toString().trim();
//                            date = snapshot.child("date").getValue().toString().trim();
//                            timeStarted = snapshot.child("timeStarted").getValue().toString().trim();
//                            timeEnded = snapshot.child("timeEnded").getValue().toString().trim();
//                            distance = snapshot.child("distance").getValue().toString().trim();
//                            totalPassenger = snapshot.child("totalPassenger").getValue().toString().trim();
//                            tripCount = snapshot.child("tripCount").getValue().toString().trim();
//
//                            historyList.add(new History(name, busnum, route, earnings, distance, date, timeStarted, timeEnded, totalPassenger, tripCount));
//                        }
//                        Collections.reverse(historyList);
//
//                        busNumAdapter = new BusNumHistoryAdapter(historyList);
//                        recycler.setAdapter(busNumAdapter);
//                        busNumAdapter.setClickListener(HistoryActivity.this);
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//                        Toast.makeText(HistoryActivity.this, firebaseError.getMessage() + "error", Toast.LENGTH_SHORT).show();
//                    }
//                });
                break;
            case R.id.spinnerDriver:
                driverName = parent.getItemAtPosition(position).toString();

                historyList = new ArrayList<>();
//                ref = new Firebase(Config.FIREBASE_URL_HISTORY_DRIVER);
//                Query equalDriversName = ref.orderByChild("driversName").equalTo(driverName);
//                equalDriversName.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (historyList.size() > 0) {
//                            historyList.clear();
//                        }
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            name = snapshot.child("driversName").getValue().toString().trim();
//                            busnum = snapshot.child("busNum").getValue().toString().trim();
//                            route = snapshot.child("route").getValue().toString().trim();
//                            earnings = snapshot.child("earnings").getValue().toString().trim();
//                            date = snapshot.child("date").getValue().toString().trim();
//                            timeStarted = snapshot.child("timeStarted").getValue().toString().trim();
//                            timeEnded = snapshot.child("timeEnded").getValue().toString().trim();
//                            distance = snapshot.child("distance").getValue().toString().trim();
//                            totalPassenger = snapshot.child("totalPassenger").getValue().toString().trim();
//                            tripCount = snapshot.child("tripCount").getValue().toString().trim();
//
//                            historyList.add(new History(name, busnum, route, earnings, distance, date, timeStarted, timeEnded, totalPassenger, tripCount));
//                        }
//                        Collections.reverse(historyList);
//
//                        nameAdapter = new NameHistoryAdapter(historyList);
//                        recycler.setAdapter(nameAdapter);
//                        nameAdapter.setClickListener(HistoryActivity.this);
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//                        Toast.makeText(HistoryActivity.this, firebaseError.getMessage() + "error", Toast.LENGTH_SHORT).show();
//                    }
//                });
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Choose on how to view history", Toast.LENGTH_SHORT).show();
    }

    public void fill() {
        historyList = new ArrayList<>();
//        ref = new Firebase(Config.FIREBASE_URL_HISTORY_DRIVER);
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (historyList.size() > 0) {
//                    historyList.clear();
//                }
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    name = snapshot.child("driversName").getValue().toString().trim();
//                    busnum = snapshot.child("busNum").getValue().toString().trim();
//                    route = snapshot.child("route").getValue().toString().trim();
//                    earnings = snapshot.child("earnings").getValue().toString().trim();
//                    date = snapshot.child("date").getValue().toString().trim();
//                    timeStarted = snapshot.child("timeStarted").getValue().toString().trim();
//                    timeEnded = snapshot.child("timeEnded").getValue().toString().trim();
//                    distance = snapshot.child("distance").getValue().toString().trim();
//                    totalPassenger = snapshot.child("totalPassenger").getValue().toString().trim();
//                    tripCount = snapshot.child("tripCount").getValue().toString().trim();
//                    historyList.add(new History(name, busnum, route, earnings, distance, date, timeStarted, timeEnded, totalPassenger, tripCount));
//                }
//                Collections.reverse(historyList);
//
//                allAdapter = new AllHistoryAdapter(historyList);
//                recycler.setAdapter(allAdapter);
//                allAdapter.setClickListener(HistoryActivity.this);
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Toast.makeText(HistoryActivity.this, firebaseError.getMessage() + "error", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void fillSpnrBus() {
        busnumList = new ArrayList<>();
//        busRef = new Firebase(Config.FIREBASE_URL_BUSNUMBER);
//        busRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (busnumList.size() > 0) {
//                    busnumList.clear();
//                }
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    busnumList.add(postSnapshot.child("busNum").getValue().toString());
//                }
//                setAdapterBus();
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Toast.makeText(HistoryActivity.this, "Failed to read data.... " + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
    }

    public void fillSpnrDriver() {
        driverList = new ArrayList<>();

//        driverRef = new Firebase(Config.FIREBASE_URL_DRIVER);
//        driverRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (driverList.size() > 0) {
//                    driverList.clear();
//                }
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    driverList.add(postSnapshot.child("driversName").getValue().toString());
//                }
//                setAdapterDriver();
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Toast.makeText(HistoryActivity.this, "Failed to read data.... " + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
    }

    public void setAdapterBus() {
        ArrayAdapter<String> adapterBus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, busnumList);
        adapterBus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspnrBus.setAdapter(adapterBus);
    }

    public void setAdapterDriver() {
        ArrayAdapter<String> adapterDriver = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, driverList);
        adapterDriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspnrDriver.setAdapter(adapterDriver);
    }

    @Override
    public void onClick(View view, int position) {
        History history = historyList.get(position);

        Toast.makeText(this, "" + history.getBusNumber(), Toast.LENGTH_SHORT).show();
        editor.putString("driversName", history.getDriversName());
        editor.putString("busNumber", history.getBusNumber());
        editor.putString("route", history.getRoute());
        editor.putString("earnings", String.valueOf(history.getEarnings()));
        editor.putString("date", history.getDate());
        editor.putString("timeStarted", history.getTimeSarted());
        editor.putString("timeEnded", history.getTimeEnded());
        editor.putString("distance", history.getDistance());
        editor.putString("passenger", history.getTotalPassenger());
        editor.putString("tripCount", String.valueOf(history.getTripCount()));
        editor.commit();

        Intent i = new Intent(this, HistoryviewActivity.class);
        startActivity(i);
    }
}