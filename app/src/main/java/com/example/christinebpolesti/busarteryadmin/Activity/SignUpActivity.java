package com.example.christinebpolesti.busarteryadmin.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.christinebpolesti.busarteryadmin.Config.Config;
import com.example.christinebpolesti.busarteryadmin.Interface.APIServiceOwner;
import com.example.christinebpolesti.busarteryadmin.Pojo.Owner;
import com.example.christinebpolesti.busarteryadmin.R;
import com.example.christinebpolesti.busarteryadmin.Remote.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private EditText mEdtUsername, mEdtPassword, mEdtName, mEdtEmail;
    private Button mBtnSignUp;
    private String mUsername, mPassword, mName, mEmail;
    Retrofit retrofit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEdtUsername = (EditText) findViewById(R.id.edtUsername);
        mEdtPassword = (EditText) findViewById(R.id.edtPassword);
        mEdtName = (EditText) findViewById(R.id.edtName);
        mEdtEmail = (EditText) findViewById(R.id.edtEmail);

        mBtnSignUp = (Button) findViewById(R.id.btnSignup);
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSignUp();
            }
        });
    }

    private void userSignUp() {
        mUsername = mEdtUsername.getText().toString().trim();
        mPassword = mEdtPassword.getText().toString().trim();
        mName = mEdtName.getText().toString().trim();
        mEmail = mEdtEmail.getText().toString().trim();

        //building retrofit object
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        APIServiceOwner serviceOwner = retrofit.create(APIServiceOwner.class);

        Owner userOwner = new Owner(mUsername, mPassword, mName, mEmail);

        if (!mUsername.isEmpty() && !mPassword.isEmpty() && !mName.isEmpty() && !mEmail.isEmpty()) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Signing Up...");
            progressDialog.show();

            //defining the call
            Call<Result> call = serviceOwner.createOwner(
                    userOwner.getUsername(),
                    userOwner.getPassword(),
                    userOwner.getName(),
                    userOwner.getEmail());

            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this, "Success! \n" + response.body().getMessage().toString(), Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignUpActivity.this, "Error: OnResponse " + response.body().getError().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Error: OnFailure " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            if (mUsername.isEmpty())
                mEdtUsername.setError("REQUIRED!");
            if (mName.isEmpty())
                mEdtName.setError("REQUIRED!");
            if (mEmail.isEmpty())
                mEdtEmail.setError("REQUIRED!");
            if (mPassword.isEmpty())
                mEdtPassword.setError("REQUIRED!");
        }
    }
}
