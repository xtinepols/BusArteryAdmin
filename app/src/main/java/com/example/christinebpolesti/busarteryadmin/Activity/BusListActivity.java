package com.example.christinebpolesti.busarteryadmin.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.christinebpolesti.busarteryadmin.Adapter.BusAdapter;
import com.example.christinebpolesti.busarteryadmin.Config.Config;
import com.example.christinebpolesti.busarteryadmin.Interface.ItemClickListener;
import com.example.christinebpolesti.busarteryadmin.Pojo.Buses;
import com.example.christinebpolesti.busarteryadmin.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BusListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ItemClickListener {

    private String busnum, route, bustype, capacity;
    private RecyclerView recyclerView;
    private BusAdapter adapter;
    private List<Buses> busNumberList = new ArrayList<>();

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_bus);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        pref = getApplicationContext().getSharedPreferences("busData", MODE_PRIVATE);
        editor = pref.edit();

        fill();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bus_list:
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

    public void fill() {
        busNumberList = new ArrayList<>();
//        busref = new Firebase(Config.FIREBASE_URL_BUSNUMBER);
//        busref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (busNumberList.size() > 0) {
//                    busNumberList.clear();
//                }
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    busnum = snapshot.child("busNum").getValue().toString().trim();
//                    route = snapshot.child("route").getValue().toString().trim();
//                    bustype = snapshot.child("busType").getValue().toString().trim();
//                    capacity = snapshot.child("capacity").getValue().toString().trim();
//                    busNumberList.add(new Buses(busnum, route, bustype, capacity));
//                }
//                Collections.reverse(busNumberList);
//
//                adapter = new BusAdapter(busNumberList);
//                recyclerView.setAdapter(adapter);
//                adapter.setClickListener(BusListActivity.this);
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Toast.makeText(BusListActivity.this, firebaseError.getMessage()+"error", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void onClick(View view, int position) {
        Buses busNumber = busNumberList.get(position);
//        Toast.makeText(this, ""+busNumber.getBusNum(), Toast.LENGTH_SHORT).show();
//
//        editor.putString("busNumber", busNumber.getBusNum());
//        editor.putString("route", busNumber.getRoute());
//        editor.putString("busType", busNumber.getBusType());
//        editor.putString("capacity", busNumber.getCapacity());
//        editor.commit();

        Intent i = new Intent(this, BusviewActivity.class);
        startActivity(i);
    }
}