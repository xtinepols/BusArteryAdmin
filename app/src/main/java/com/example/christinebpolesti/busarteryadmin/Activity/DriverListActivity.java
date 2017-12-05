package com.example.christinebpolesti.busarteryadmin.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.christinebpolesti.busarteryadmin.Adapter.DriverAdapter;
import com.example.christinebpolesti.busarteryadmin.Config.Config;
import com.example.christinebpolesti.busarteryadmin.Interface.ItemClickListener;
import com.example.christinebpolesti.busarteryadmin.Pojo.Driver;
import com.example.christinebpolesti.busarteryadmin.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DriverListActivity extends AppCompatActivity implements ItemClickListener {

    private String name, age, username, busnum;
    private RecyclerView recyclerView;
    private DriverAdapter adapter;
    private List<Driver> driverList = new ArrayList<>();

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_list);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_driver);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        pref = getApplicationContext().getSharedPreferences("driverData", MODE_PRIVATE);
        editor = pref.edit();

        fill();
    }

//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.driver_list:
//                Intent mIntent = NavUtils.getParentActivityIntent(this);
//                if (NavUtils.shouldUpRecreateTask(this, mIntent)) {
//                    TaskStackBuilder.create(this).addNextIntentWithParentStack(mIntent).startActivities();
//                } else {
//                    NavUtils.navigateUpTo(this, mIntent);
//                }
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    public void fill() {
        driverList = new ArrayList<>();
//        driverRef = new Firebase(Config.FIREBASE_URL_DRIVER);
//        driverRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (driverList.size() > 0) {
//                    driverList.clear();
//                }
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    name = snapshot.child("driversName").getValue().toString().trim();
//                    age = snapshot.child("age").getValue().toString().trim();
//                    username = snapshot.child("username").getValue().toString().trim();
//                    busnum = snapshot.child("busNum").getValue().toString().trim();
//                    driverList.add(new Driver(name, age, username, busnum));
//                }
//                Collections.reverse(driverList);
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Toast.makeText(DriverListActivity.this, firebaseError.getMessage()+"error", Toast.LENGTH_SHORT).show();
//            }
//        });
        adapter = new DriverAdapter(driverList);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
    }

    @Override
    public void onClick(View view, int position) {
        Driver driver = driverList.get(position);
        Toast.makeText(this, ""+driver.getDriversName(), Toast.LENGTH_SHORT).show();

//        editor.putString("driverName", driver.getDriversName());
//        editor.putString("age", driver.getAge());
//        editor.putString("username", driver.getUsername());
//        editor.putString("busNumber", driver.getBusNum());
//        editor.commit();

        Intent i = new Intent(this, DriverviewActivity.class);
        startActivity(i);
    }
}
