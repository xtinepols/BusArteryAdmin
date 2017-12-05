package com.example.christinebpolesti.busarteryadmin.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.christinebpolesti.busarteryadmin.Config.Config;
import com.example.christinebpolesti.busarteryadmin.Interface.APIServiceOwner;
import com.example.christinebpolesti.busarteryadmin.R;
import com.example.christinebpolesti.busarteryadmin.Remote.Result;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText mEdtUsername, mEdtPassword;
    private Button mBtnLogin, mBtnSignup;

    private String username, password;

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    private Retrofit retrofit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
        editor = pref.edit();

        mEdtUsername = (EditText) findViewById(R.id.edtUsername);
        mEdtPassword = (EditText) findViewById(R.id.edtPassword);

        if (pref.contains("username") && pref.contains("password")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        mBtnLogin = (Button) findViewById(R.id.btnSignin);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ownerLogin();

//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });

        mBtnSignup = (Button) findViewById(R.id.btnRegister);
        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ownerLogin() {
        username = mEdtUsername.getText().toString().trim();
        password = mEdtPassword.getText().toString().trim();
        String type = "owner";

        //building retrofit object
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        final APIServiceOwner serviceOwner = retrofit.create(APIServiceOwner.class);
//        String json = retrofitService().getInfo().execute().body().string();
        if (!username.isEmpty() && !password.isEmpty()) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Signing Up...");
            progressDialog.show();

            Call<Result> call = serviceOwner.ownerLogin(username, password, type);
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.isSuccessful()) {
                        JsonObject object = new JsonObject().get(response.body().toString()).getAsJsonObject();
                        String accessToken = object.get("accessToken").getAsString();
                        Toast.makeText(LoginActivity.this, "accessToken: " + accessToken, Toast.LENGTH_LONG).show();

                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "SUCCESS!\n" + response.body().getMessage().toString(), Toast.LENGTH_LONG).show();

                        //String authorization = accessToken(response server)
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "ERROR\n" + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            if (username.isEmpty())
                mEdtUsername.setError("REQUIRED!");
            if (password.isEmpty())
                mEdtPassword.setError("REQUIRED!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}