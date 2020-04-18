package com.example.nfctestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private TextView _tv;
    private Intent _cardService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _tv = (TextView) findViewById(R.id._textView);

        _cardService = new Intent(this, CardService.class);
        startService(_cardService);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(_cardService);
    }

    public void onClickUpdate(View v) {
        _tv.setText("Count : " + Counter.GetCurrentCout());
    }

}
