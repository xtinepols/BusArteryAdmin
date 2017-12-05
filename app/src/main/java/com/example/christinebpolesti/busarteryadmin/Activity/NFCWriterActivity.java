package com.example.christinebpolesti.busarteryadmin.Activity;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.christinebpolesti.busarteryadmin.Adapter.NFCManager;
import com.example.christinebpolesti.busarteryadmin.Config.Config;
import com.example.christinebpolesti.busarteryadmin.R;

public class NFCWriterActivity extends AppCompatActivity {

    private EditText medtUsername;
    private EditText medtName;
    private EditText medtID;
    private Button mbtnWriteData;

    private NFCManager nfcManager;
    private NdefMessage message = null;
    private ProgressDialog dialog;
    Tag currentTag;

    private String name, username, tagID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcwriter);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        medtUsername = (EditText) findViewById(R.id.edtCusUsername);
        medtName = (EditText) findViewById(R.id.edtCusName);
        medtID = (EditText) findViewById(R.id.edtCusID);

        nfcManager = new NFCManager(this);
        dialog = new ProgressDialog(NFCWriterActivity.this);

        medtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                pass = new Firebase(Config.FIREBASE_URL_PASSENGER);
//                pass.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                            name = postSnapshot.child("name").getValue().toString();
//                            username = postSnapshot.child("username").getValue().toString();
//                            if (postSnapshot.child("tagId").exists()) {
//                                tagID = postSnapshot.child("tagId").getValue().toString();
//                                if (medtUsername.getText().toString().trim().equals(username)) {
//                                    medtID.setText(tagID);
//                                    medtName.setText(name);
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//                    }
//                });
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mbtnWriteData = (Button) findViewById(R.id.btnWriteData);
        mbtnWriteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!medtUsername.getText().toString().trim().equals("") ||
                        !medtName.getText().toString().trim().equals("") ||
                        !medtID.getText().toString().trim().equals("")) {
                    message = nfcManager.createTextMessage(medtID.getText().toString().trim());
                }

                if (message != null) {
                    dialog.setMessage("Place tag against back of phone to write");
                    dialog.show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            nfcManager.verifyNFC();

            Intent nfcIntent = new Intent(this, getClass());
            nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, nfcIntent, 0);
            IntentFilter[] intentFiltersArray = new IntentFilter[]{};
            String[][] techlist = new String[][] {{android.nfc.tech.Ndef.class.getName()}, {android.nfc.tech.NdefFormatable.class.getName()}};
            NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techlist);
        }
        catch (NFCManager.NFCNotSupported nfcNotSupported) {
            Toast.makeText(NFCWriterActivity.this, "NFC not supported", Toast.LENGTH_SHORT).show();
        }
        catch (NFCManager.NFCNotEnabled nfcNotEnabled) {
            Toast.makeText(NFCWriterActivity.this, "NFC not enabled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        nfcManager.disableDispatch();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("Nfc", "New Intent");
        //time to write the tag
        currentTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (message != null) {
            nfcManager.writeTag(currentTag, message);
            //balance
//            pass = new Firebase(Config.FIREBASE_URL_PASSENGER);
//            Query Username = pass.orderByChild("username").equalTo(medtUsername.getText().toString().trim());
//            Username.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
////                        snapshot.getRef().child("tagId").setValue(medtID.getText().toString().trim());
//                        snapshot.getRef().child("balance").setValue("0");
//                    }
//                }
//
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//                }
//            });
            dialog.dismiss();
            Toast.makeText(this, "Tag written", Toast.LENGTH_SHORT).show();

            medtID.setText("");
            medtName.setText("");
            medtUsername.setText("");
        } else {
            Toast.makeText(this, "Failed to write tag", Toast.LENGTH_SHORT).show();
        }
    }
}
