package com.example.christinebpolesti.busarteryadmin.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.christinebpolesti.busarteryadmin.Adapter.DriverAdapter;
import com.example.christinebpolesti.busarteryadmin.Config.Config;
import com.example.christinebpolesti.busarteryadmin.Interface.ItemClickListener;
import com.example.christinebpolesti.busarteryadmin.Pojo.Driver;
import com.example.christinebpolesti.busarteryadmin.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ItemClickListener {

    private FloatingActionButton fab, fab_add_driver, fab_add_bus;
    private LinearLayout layoutBus, layoutDriver;

    private SharedPreferences pref, sharedPreferences;
    private SharedPreferences.Editor editor, sharedEditor;

    private String name, age, username, busnum;
    private RecyclerView recyclerView;
    private DriverAdapter adapter;
    private List<Driver> driverList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav);

        pref = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
        editor = pref.edit();

        sharedPreferences = getApplicationContext().getSharedPreferences("driverData", MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();

        setUpNavView();
        setUpToolbarDrawer();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//        fill();

        fab = (FloatingActionButton) findViewById(R.id.fab_showMore);
        fab_add_bus = (FloatingActionButton) findViewById(R.id.fab_add_bus);
        fab_add_driver = (FloatingActionButton) findViewById(R.id.fab_add_driver);
        layoutBus = (LinearLayout) findViewById(R.id.layoutBus);
        layoutDriver = (LinearLayout) findViewById(R.id.layoutDriver);

        fab.setTag(1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int status = (Integer) view.getTag();
                if (status == 1) {
                    layoutBus.setVisibility(View.VISIBLE);
                    layoutDriver.setVisibility(View.VISIBLE);
                    view.setTag(0);
                } else {
                    layoutBus.setVisibility(View.INVISIBLE);
                    layoutDriver.setVisibility(View.INVISIBLE);
                    view.setTag(1);
                }
            }
        });

        fab_add_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddBusActivity.class);
                startActivity(intent);
            }
        });

        fab_add_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddDriverActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.bus_list) {
            Intent intent = new Intent(this, BusListActivity.class);
            startActivity(intent);
        } else if (id == R.id.trackBus) {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        } else if (id == R.id.history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.loader) {
            Intent intent = new Intent(this, LoaderActivity.class);
            startActivity(intent);
        } else if (id == R.id.balance_checker) {
            Intent intent = new Intent(this, BalanceCheckerActivity.class);
            startActivity(intent);
        } else if (id == R.id.logout) {
            logoutDialog();
        }
//        else if (id == R.id.driver_list) {
//            Intent intent = new Intent(this, DriverListActivity.class);
//            startActivity(intent);
//        }

        item.setChecked(true);
        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logoutDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setUpToolbarDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    public void setUpNavView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void logoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.veryLightBlue);
        alertDialog.show();
    }

    public void fill() {
//        driverList = new ArrayList<>();
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
//
//                adapter = new DriverAdapter(driverList);
//                recyclerView.setAdapter(adapter);
//                adapter.setClickListener(MainActivity.this);
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Toast.makeText(MainActivity.this, firebaseError.getMessage() + "error", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void onClick(View view, int position) {
//        Driver driver = driverList.get(position);
//        Toast.makeText(this, "" + driver.getDriversName(), Toast.LENGTH_SHORT).show();
//
//        sharedEditor.putString("driverName", driver.getDriversName());
//        sharedEditor.putString("age", driver.getAge());
//        sharedEditor.putString("username", driver.getUsername());
//        sharedEditor.putString("busNumber", driver.getBusNum());
//        sharedEditor.commit();
//
//        Intent i = new Intent(this, DriverviewActivity.class);
//        startActivity(i);
    }
}
