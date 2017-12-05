package com.example.christinebpolesti.busarteryadmin.Adapter;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

/**
 * Created by christine B. Polesti on 2/10/2017.
 */

public class NFCManager {

    private Activity mActivity;
    private NfcAdapter mnfcAdapter;


    public NFCManager(Activity activity) {
        this.mActivity = activity;
    }

    public void verifyNFC() throws NFCNotSupported, NFCNotEnabled {
        mnfcAdapter = NfcAdapter.getDefaultAdapter(mActivity);

        if (mnfcAdapter == null) {
            throw new NFCNotSupported();
        }
        if (!mnfcAdapter.isEnabled()) {
            throw new NFCNotEnabled();
        }
    }

    public void enableDispatch() {
        Intent nfcIntent = new Intent(mActivity, getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mActivity, 0, nfcIntent, 0);
        IntentFilter[] intentFiltersArrray = new IntentFilter[]{};
        String[][] techlist = new String[][]{{android.nfc.tech.Ndef.class.getName()}, {android.nfc.tech.NdefFormatable.class.getName()}};

        mnfcAdapter.enableForegroundDispatch(mActivity, pendingIntent, intentFiltersArrray, techlist);
    }

    public void disableDispatch() {
        mnfcAdapter.disableForegroundDispatch(mActivity);
    }

    public static class NFCNotSupported extends Exception {
        public NFCNotSupported() {
            super();
        }
    }

    public static class NFCNotEnabled extends Exception {
        public NFCNotEnabled() {
            super();
        }
    }

    public void writeTag(Tag tag, NdefMessage message) {
        if (tag != null) {
            try {
                Ndef ndeftag = Ndef.get(tag);

                if (ndeftag == null) {
                    //let's try to format the Tag in NDEF
                    NdefFormatable ndefFormatable = NdefFormatable.get(tag);
                    if (ndefFormatable != null) {
                        ndefFormatable.connect();
                        ndefFormatable.format(message);
                        ndefFormatable.close();
                    }
                } else {
                    ndeftag.connect();
                    ndeftag.writeNdefMessage(message);
                    ndeftag.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public NdefMessage createUriMessage(String content, String type) {
        NdefRecord record = NdefRecord.createUri(type + content);
        NdefMessage msg = new NdefMessage(new NdefRecord[]{record});
        return msg;
    }

    public NdefMessage createTextMessage(String content) {
        try {
            //get utf-8 byte
            byte[] lang = Locale.getDefault().getLanguage().getBytes("UTF-8");
            byte[] text = content.getBytes("UTF8");

            int langSize = lang.length;
            int textlength = text.length;

            ByteArrayOutputStream payload = new ByteArrayOutputStream(1 + langSize + textlength);
            payload.write((byte)(langSize & 0x1F));
            payload.write(lang, 0, langSize);
            payload.write(text, 0, textlength);
            NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload.toByteArray());
            return  new NdefMessage(new NdefRecord[]{record});
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public NdefMessage createExternalMessage(String content) {
        NdefRecord externalRecord = NdefRecord.createExternal("customer ID", "data", content.getBytes());
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[] {externalRecord});
        return ndefMessage;
    }
}
