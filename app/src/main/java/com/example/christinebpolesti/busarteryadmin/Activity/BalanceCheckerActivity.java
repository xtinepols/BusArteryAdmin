package com.example.christinebpolesti.busarteryadmin.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
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

import com.example.christinebpolesti.busarteryadmin.Config.Config;
import com.example.christinebpolesti.busarteryadmin.R;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class BalanceCheckerActivity extends AppCompatActivity {

    private EditText medtCheckerId;
    private EditText medtCheckerName;
    private EditText medtCheckerBalance;

    private String customerName, id;

    private static final String MIME_TEXT_PLAIN = "text/plain";
    private NfcAdapter mnfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_checker);

        medtCheckerId = (EditText) findViewById(R.id.edtCheckerID);
        medtCheckerName = (EditText) findViewById(R.id.edtCheckerName);
        medtCheckerBalance = (EditText) findViewById(R.id.edtCheckerBalance);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mnfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //check if the smartphone has NFC
        if (mnfcAdapter == null) {
            Toast.makeText(this, "NFC not supported.", Toast.LENGTH_SHORT).show();
            finish();
        }
        //check if NFC is enabled
        if (!mnfcAdapter.isEnabled()) {
            Toast.makeText(this, "Enable NFC before loading.", Toast.LENGTH_LONG).show();
        }

        Toast.makeText(this, "Place tag against back of phone to read", Toast.LENGTH_LONG).show();

        medtCheckerId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                firebase = new Firebase(Config.FIREBASE_URL_PASSENGER);
//                firebase.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            //null cusID
//                            if (snapshot.child("tagId").exists() && snapshot.child("balance").exists()) {
//                                String cusID = snapshot.child("tagId").getValue().toString().trim();
//                                customerName = snapshot.child("name").getValue().toString().trim();
//
//                                if (cusID.equals(id)) {
//                                    medtCheckerName.setText(customerName);
//                                    medtCheckerBalance.setText(snapshot.child("balance").getValue().toString().trim());
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//                        Toast.makeText(BalanceCheckerActivity.this, "failed", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new BalanceCheckerActivity.NdefReaderTask().execute(tag);
            } else {
                Toast.makeText(this, "Wrong mime type", Toast.LENGTH_SHORT).show();
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            //in case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techlist = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techlist) {
                if (searchedTech.equals(tech)) {
                    new BalanceCheckerActivity.NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupForegroundDispatch(this, mnfcAdapter);
    }

    @Override
    protected void onPause() {
        stopForegroundDispatch(this, mnfcAdapter);
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    public static void setupForegroundDispatch(final Activity activity, NfcAdapter nfcAdapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techlist = new String[][]{};

        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        nfcAdapter.enableForegroundDispatch(activity, pendingIntent, filters, techlist);
    }

    public static void stopForegroundDispatch(final Activity activity, NfcAdapter nfcAdapter) {
        nfcAdapter.disableForegroundDispatch(activity);
    }

    private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                return null;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        Log.e("TAG", "Unsupported Encoding", e);
                    }
                }
            }
            return null;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {
            byte[] id = record.getPayload();
            //get the text encoding
            String textEncoding = ((id[0] & 128) == 0) ? "UTF-8" : "UTF-16";

            //get the language code
            int languageCodeLength = id[0] & 0063;

            //String languagecode = new String(id, 1, languageCodeLength, "US-ASCII");

            //get the text
            return new String(id, languageCodeLength + 1, id.length - languageCodeLength - 1, textEncoding);
        }

        @Override
        protected void onPostExecute(final String tagId) {
            if (tagId != null) {
                id = tagId;
                medtCheckerId.setText(tagId);
            }
        }
    }
}
