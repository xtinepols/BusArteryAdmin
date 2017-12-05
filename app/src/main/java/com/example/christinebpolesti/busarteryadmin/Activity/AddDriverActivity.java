package com.example.christinebpolesti.busarteryadmin.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.christinebpolesti.busarteryadmin.Config.Config;
import com.example.christinebpolesti.busarteryadmin.Interface.APIServiceBusNumSpnr;
import com.example.christinebpolesti.busarteryadmin.Interface.APIServiceBuses;
import com.example.christinebpolesti.busarteryadmin.Interface.APIServiceDrivers;
import com.example.christinebpolesti.busarteryadmin.Pojo.Buses;
import com.example.christinebpolesti.busarteryadmin.R;
import com.example.christinebpolesti.busarteryadmin.Pojo.Driver;
import com.example.christinebpolesti.busarteryadmin.Remote.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddDriverActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mEdtName;
    private EditText mEdtEmployeeID;
    private Spinner mSpinBusNum;
    private EditText mEdtAge;
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private TextView mTxtDetails;
    private Button mBtnAddDriver;

    private Retrofit retrofit = null;

    String busNumber;
    private String mName, mEmployeeID, mBusNumber, mUsername, mPassword = "123456";
    int mAge, check = 0;

    List<String> busnum = new ArrayList<String>();

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = getApplicationContext().getSharedPreferences("driverData", MODE_PRIVATE);
        editor = pref.edit();

        mEdtName = (EditText) findViewById(R.id.edtName);
        mEdtEmployeeID = (EditText) findViewById(R.id.edtEmployeeID);
        mSpinBusNum = (Spinner) findViewById(R.id.spinnerBusNum);
        mEdtAge = (EditText) findViewById(R.id.edtAge);
        mEdtUsername = (EditText) findViewById(R.id.edtUsername);
        mEdtPassword = (EditText) findViewById(R.id.edtPassword);
        mSpinBusNum.setOnItemSelectedListener(this);
        mTxtDetails = (TextView) findViewById(R.id.txtDetails);
        mBtnAddDriver = (Button) findViewById(R.id.btnAddDriver);

        busnum = new ArrayList<String>();

        retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIServiceBusNumSpnr serviceBusNumSpnr = retrofit.create(APIServiceBusNumSpnr.class);
        Call<Buses> call = serviceBusNumSpnr.getBusNumbers();
        call.enqueue(new Callback<Buses>() {
            @Override
            public void onResponse(Call<Buses> call, Response<Buses> response) {
                try {
                    busnum.add(response.body().getBusNumber());
                    Toast.makeText(AddDriverActivity.this, busnum.size() + "", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AddDriverActivity.this, "There was an error.", Toast.LENGTH_SHORT).show();
                    Log.e("OnResponse", "There was an error. " + e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Buses> call, Throwable t) {

            }
        });
//        Call<Result> call = serviceBusNumSpnr.getBusNumbers(busNumber);
//        call.enqueue(new Callback<Result>() {
//            @Override
//            public void onResponse(Call<Result> call, Response<Result> response) {
//                Result resultObject = response.body();
//
//                for (int i = 0; i < busList.size(); i++) {
//                    busnum.add(busList.get(i).getBusNumber());
//                    Toast.makeText(AddDriverActivity.this, "bus number: " +busList.get(i).getBusNumber(), Toast.LENGTH_SHORT).show();
//                }
////                setAdapter();
//            }
//
//            @Override
//            public void onFailure(Call<Result> call, Throwable t) {
//                Toast.makeText(AddDriverActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });

        mBtnAddDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDriver();

//                if (bus.equals(busNum) && empId.equals(username)) {
//                    mEdtUsername.setText("");
//                    mEdtUsername.setError("Bus number not available and employee ID already exists.");
//                    check++;
//                }
//                if (!bus.equals(busNum) && empId.equals(username)) {
//                    mEdtUsername.setText("");
//                    mEdtUsername.setError("Employee ID already exists.");
//                    check++;
//                }
//                if (bus.equals(busNum) && !empId.equals(username)) {
//                    Toast.makeText(AddDriverActivity.this, "Bus number already assigned to driver " + driversName, Toast.LENGTH_SHORT).show();
//                    check++;
//                    setDialog();
//                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mBusNumber = parent.getItemAtPosition(position).toString();

//        busRef = new Firebase(Config.FIREBASE_URL_BUSNUMBER);
//        busRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    String busnumber = postSnapshot.child("busNum").getValue().toString();
//                    String route = postSnapshot.child("route").getValue().toString();
//                    String companyName = postSnapshot.child("companyName").getValue().toString();
//
//                    if (busnumber.equals(busNum)) {
//                        mTxtDetails.setText("Bus Number Information\nRoute: " + route + "\nCompanyName: " + companyName);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Toast.makeText(AddDriverActivity.this, "Display route: " + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Select bus number", Toast.LENGTH_SHORT).show();
    }

    public void setAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, busnum);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinBusNum.setAdapter(adapter);
    }

    public void setDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Assigning of Bus Number");
        builder.setMessage("Bus number already assigned to " + ".\nDo you want " + " also be assigned to this bus?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        driver = new Driver();
//                        driver.setDriversName(name);
//                        driver.setBusNum(busNum);
//                        driver.setAge(age);
//                        driver.setEmpId(username);
//                        driver.setUsername(username);
//                        driver.setPassword(password);
//                        saveRef.push().setValue(driver);
//                        mEdtName.setText("");
//                        mEdtAge.setText("");
//                        mEdtUsername.setText("");
//                        Toast.makeText(AddDriverActivity.this, "Bus number " + busNum + " is also assigned to " + name, Toast.LENGTH_LONG).show();
//
//                        editor.putString("driverName", mName);
//                        editor.putString("username", mUsername);
//                        editor.putString("age", mAge);
//                        editor.putString("busNumber", mBusNumber);
//                        editor.commit();
//
//                        Intent intent = new Intent(AddDriverActivity.this, DriverviewActivity.class);
//                        startActivity(intent);
//
//                        driverRef = new Firebase(Config.FIREBASE_URL_DRIVER);
//                        Query pendingBus = driverRef.orderByChild("busNum").equalTo(busNum);
//                        pendingBus.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                    String busnumber = snapshot.child("busNum").getValue().toString();
//                                    if (busnumber.equals(busNum)) {
//
//                                        snapshot.getRef().child("age").removeValue();
//                                        snapshot.getRef().child("availableSeat").removeValue();
//                                        snapshot.getRef().child("busNum").removeValue();
//                                        snapshot.getRef().child("driversName").removeValue();
//                                        snapshot.getRef().child("empId").removeValue();
//                                        snapshot.getRef().child("password").removeValue();
//                                        snapshot.getRef().child("username").removeValue();
//
//                                        driver = new Driver();
//                                        driver.setDriversName(name);
//                                        driver.setBusNum(busNum);
//                                        driver.setAge(age);
//                                        driver.setEmpId(username);
//                                        driver.setUsername(username);
//                                        driver.setPassword(password);
//                                        saveRef.push().setValue(driver);
//                                        mEdtName.setText("");
//                                        mEdtAge.setText("");
//                                        mEdtUsername.setText("");
//                                        Toast.makeText(AddDriverActivity.this, "Bus number " + busNum + " is also assigned to " + name, Toast.LENGTH_LONG).show();
//                                        Intent intent = new Intent(AddDriverActivity.this, DriverListActivity.class);
//                                        startActivity(intent);
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(FirebaseError firebaseError) {
//                                Toast.makeText(AddDriverActivity.this, "onAssigning new Bus number: " + firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.veryLightBlue);
        alertDialog.show();
    }

    private void addDriver() {
        mName = mEdtName.getText().toString().trim();
        mEmployeeID = mEdtEmployeeID.getText().toString().trim();
        mAge = Integer.valueOf(mEdtAge.getText().toString().trim());
        mUsername = mEdtUsername.getText().toString().trim();
        mPassword = mEdtPassword.getText().toString().trim();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        APIServiceDrivers serviceDrivers = retrofit.create(APIServiceDrivers.class);

        Driver driver = new Driver(mName, mEmployeeID, mBusNumber, mAge, mUsername, mPassword);

        if (!mName.isEmpty() && !mEmployeeID.isEmpty() && !busnum.isEmpty() && !mEdtAge.getText().toString().trim().isEmpty()
                && mUsername.isEmpty()) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Adding Driver");
            progressDialog.show();

            //for as long as the bus number is not yet assigned by another driver
            Call<Result> call = serviceDrivers.addDriver(
                    driver.getUsername(),
                    driver.getPassword(),
                    driver.getDriversName(),
                    driver.getAge(),
                    driver.getBusNum(),
                    driver.getEmployeeId());
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    progressDialog.dismiss();
                    Toast.makeText(AddDriverActivity.this, "Successfully added new driver account.", Toast.LENGTH_SHORT).show();

                    editor.putString("driverName", mName);
                    editor.putString("username", mUsername);
                    editor.putString("employeeId", mEmployeeID);
                    editor.putString("age", String.valueOf(mAge));
                    editor.putString("busNumber", mBusNumber);
                    editor.commit();

                    Intent intent = new Intent(AddDriverActivity.this, DriverviewActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {

                }
            });
        }
    }
}