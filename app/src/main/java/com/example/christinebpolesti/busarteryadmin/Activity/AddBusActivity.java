package com.example.christinebpolesti.busarteryadmin.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.christinebpolesti.busarteryadmin.Config.Config;
import com.example.christinebpolesti.busarteryadmin.Interface.APIServiceBuses;
import com.example.christinebpolesti.busarteryadmin.R;
import com.example.christinebpolesti.busarteryadmin.Pojo.Buses;
import com.example.christinebpolesti.busarteryadmin.Remote.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddBusActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mEdtBus;
    private Spinner mSpinRoute;
    private EditText mEdtCapacity;
    private Spinner mSpinBusType;
    private EditText mEdtCompanyName;
    private Button mBtnAddBus;

    private Retrofit retrofit = null;

    private String mBusNumber, mRoute, mBusType, mCompanyName, mDestination, mLocation;
    private int mCapacity;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = getApplicationContext().getSharedPreferences("busData", MODE_PRIVATE);
        editor = pref.edit();

        mEdtBus = (EditText) findViewById(R.id.edtBusNum);
        mSpinRoute = (Spinner) findViewById(R.id.spinnerRoute);
        mEdtCapacity = (EditText) findViewById(R.id.edtCapacity);
        mSpinBusType = (Spinner) findViewById(R.id.spinnerBusType);
        mEdtCompanyName = (EditText) findViewById(R.id.edtCompanyName);
        mSpinRoute.setOnItemSelectedListener(this);
        mSpinBusType.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.route_name, android.R.layout.simple_spinner_dropdown_item);
        mSpinRoute.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this, R.array.bus_type, android.R.layout.simple_spinner_dropdown_item);
        mSpinBusType.setAdapter(adapterType);

        mBtnAddBus = (Button) findViewById(R.id.btnAddBus);
        mBtnAddBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBus();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerRoute:
                mRoute = parent.getItemAtPosition(position).toString();
                if (mRoute.equals("Cebu-Bato via Barili")) {
                    mDestination = "Tangbo, Samboan, Cebu";
                } else if (mRoute.equals("Cebu-Bato via Oslob")) {
                    mDestination = "Tangbo, Samboan, Cebu";
                } else if (mRoute.equals("Cebu-Moalboal")) {
                    mDestination = "Tunga Elementary School, Moalboal";
                } else if (mRoute.equals("Cebu-Alcoy")) {
                    mDestination = "San Agustin Primary School, San Agustin, Alcoy, Cebu";
                } else if (mRoute.equals("Cebu-Dalaguete")) {
                    mDestination = "Tuba, Dalaguete, Cebu";
                } else if (mRoute.equals("Cebu-Barili")) {
                    mDestination = "Sayaw Primary School, Sayaw, Barili, Cebu";
                } else if (mRoute.equals("Cebu-Argao")) {
                    mDestination = "Tulic, Argao, Cebu";
                } else if (mRoute.equals("Cebu-Carcar")) {
                    mDestination = "Bolinawan, Carcar City, Cebu";
                }
                break;
            case R.id.spinnerBusType:
                mBusType = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Select bus number", Toast.LENGTH_SHORT).show();
    }

    private void addBus() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding Bus");
        progressDialog.show();

        mBusNumber = mEdtBus.getText().toString().trim();
        mCapacity = Integer.valueOf(mEdtCapacity.getText().toString().trim());
        mCompanyName = mEdtCompanyName.getText().toString().trim();
        mLocation = "Cebu South Bus Terminal, Cebu City";

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        APIServiceBuses serviceBuses = retrofit.create(APIServiceBuses.class);

        Buses buses = new Buses(mBusNumber, mRoute, mCapacity, mBusType, mLocation, mDestination, mCompanyName);

        if (!mBusNumber.isEmpty() && !mRoute.isEmpty() &&
                !mEdtCapacity.getText().toString().equals("")
                && !mBusType.isEmpty() && !mCompanyName.isEmpty()) {
            //for as long as the bus number does not exists
            Call<Result> call = serviceBuses.createBuses(
                    buses.getBusNumber(),
                    buses.getRoute(),
                    buses.getCapacity(),
                    buses.getBusType(),
                    buses.getLocation(),
                    buses.getDestination(),
                    buses.getCompanyName());
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(AddBusActivity.this, "Successfully added new bus.", Toast.LENGTH_LONG).show();
                        editor.putString("busNumber", mBusNumber);
                        editor.putString("route", mRoute);
                        editor.putString("capacity", String.valueOf(mCapacity));
                        editor.putString("busType", mBusType);
                        editor.commit();

                        Intent intent = new Intent(AddBusActivity.this, BusviewActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AddBusActivity.this, response.body().getError().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(AddBusActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            if (mBusNumber.isEmpty())
                mEdtBus.setError("REQUIRED!");
            if (mEdtCapacity.getText().toString().isEmpty())
                mEdtCapacity.setError("REQUIRED!");
            if (mCompanyName.isEmpty())
                mEdtCompanyName.setError("REQUIRED!");
        }
    }
}