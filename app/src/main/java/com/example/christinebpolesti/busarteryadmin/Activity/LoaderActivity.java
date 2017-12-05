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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.christinebpolesti.busarteryadmin.Config.Config;
import com.example.christinebpolesti.busarteryadmin.R;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class LoaderActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    private FloatingActionButton fab, fab_write, fab_check_balance;
    private LinearLayout layoutWrite, layoutCheckBalance;

    private EditText medtCustomerID;
    private EditText medtCustomerName;
    private Spinner mspnrLoadAmount;
    private Button mbtnLoad;

    private String amount, id, customerName;
    private int amountLoad, balance, newLoad = 0;

    private NfcAdapter nfcAdapter;

    private static final String MIME_TEXT_PLAIN = "text/plain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        medtCustomerID = (EditText) findViewById(R.id.edtCustomerID);
        medtCustomerName = (EditText) findViewById(R.id.edtCustomerName);
        mspnrLoadAmount = (Spinner) findViewById(R.id.spinnerLoadAmount);
        mspnrLoadAmount.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.load_amount, android.R.layout.simple_spinner_dropdown_item);
        mspnrLoadAmount.setAdapter(adapter);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //check if the smartphone has NFC
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC not supported.", Toast.LENGTH_SHORT).show();
            finish();
        }
        //check if NFC is enabled
        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "Enable NFC before loading.", Toast.LENGTH_LONG).show();
        }

        Toast.makeText(this, "Place tag against back of phone to read", Toast.LENGTH_LONG).show();

        fab = (FloatingActionButton) findViewById(R.id.fab_show_more);
        fab_write = (FloatingActionButton) findViewById(R.id.fab_writer);
        fab_check_balance = (FloatingActionButton) findViewById(R.id.fab_balance_checker);
        layoutWrite = (LinearLayout) findViewById(R.id.layoutWrite);
        layoutCheckBalance = (LinearLayout) findViewById(R.id.layoutCheckBalance);

        fab.setTag(1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int status = (Integer) view.getTag();
                if (status == 1) {
                    layoutWrite.setVisibility(View.VISIBLE);
                    layoutCheckBalance.setVisibility(View.VISIBLE);
                    view.setTag(0);
                } else {
                    layoutWrite.setVisibility(View.INVISIBLE);
                    layoutCheckBalance.setVisibility(View.INVISIBLE);
                    view.setTag(1);
                }
            }
        });

        fab_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoaderActivity.this, NFCWriterActivity.class);
                startActivity(intent);
            }
        });

        fab_check_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoaderActivity.this, BalanceCheckerActivity.class);
                startActivity(intent);
            }
        });

        medtCustomerID.addTextChangedListener(new TextWatcher() {
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
//                            if (snapshot.child("tagId").exists()) {
//                                String cusID = snapshot.child("tagId").getValue().toString().trim();
//                                customerName = snapshot.child("name").getValue().toString().trim();
//
//                                if (cusID.equals(id)) {
//                                    medtCustomerName.setText(customerName);
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//                        Toast.makeText(LoaderActivity.this, "failed", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mbtnLoad = (Button) findViewById(R.id.btnLoad);

        mbtnLoad.setVisibility(View.INVISIBLE);

        mbtnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //retrieve customer's name for verification

                //get customer's balance then add to amount loaded
//                firebase.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            if (snapshot.child("tagId").exists() && snapshot.child("balance").exists()) {
//                                String cusID = snapshot.child("tagId").getValue().toString().trim();
//                                customerName = snapshot.child("name").getValue().toString().trim();
//                                balance = Integer.valueOf(snapshot.child("balance").getValue().toString().trim());
//
//                                if (cusID.equals(id)) {
//                                    newLoad = balance + amountLoad;
//                                    Toast.makeText(LoaderActivity.this, "balance: " + newLoad, Toast.LENGTH_SHORT).show();
//                                    snapshot.getRef().child("balance").setValue(Integer.toString(newLoad));
//                                    Toast.makeText(LoaderActivity.this, "Loaded successfully.", Toast.LENGTH_SHORT).show();
//                                    medtCustomerID.setText("");
//                                    medtCustomerName.setText("");
//                                    mbtnLoad.setVisibility(View.INVISIBLE);
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//                        Toast.makeText(LoaderActivity.this, "failed", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });

        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        //todo: handle intent
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);
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
                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupForegroundDispatch(this, nfcAdapter);
    }

    @Override
    protected void onPause() {
        stopForegroundDispatch(this, nfcAdapter);
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.loader:
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
            case R.id.spinnerLoadAmount:
                amount = parent.getItemAtPosition(position).toString();
                if (amount.equals("100")) {
                    amountLoad = 100;
                } else if (amount.equals("200")) {
                    amountLoad = 200;
                } else if (amount.equals("300")) {
                    amountLoad = 300;
                } else if (amount.equals("400")) {
                    amountLoad = 400;
                } else if (amount.equals("500")) {
                    amountLoad = 500;
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Choose amount to load", Toast.LENGTH_SHORT).show();
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
                medtCustomerID.setText(tagId);
                mbtnLoad.setVisibility(View.VISIBLE);
            }
        }
    }
}