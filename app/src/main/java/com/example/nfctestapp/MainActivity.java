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

public class MainActivity extends AppCompatActivity {

    Button readBT = (Button)findViewById(R.id.readbutton);
    Button writeBT = (Button)findViewById(R.id.writebutton);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readBT.setText(R.string.READ);
        writeBT.setText(R.string.WRITE);

        readBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 구현 중
                //Intent intent = new Intent(getBaseContext(),)
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);

        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())){
            Parcelable[] rawMessages =
                    intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if(rawMessages != null){
                NdefMessage[] messages = new NdefMessage[rawMessages.length];
                for(int i = 0; i < rawMessages.length;i++){
                    messages[i] = (NdefMessage) rawMessages[i];
                }
                NdefRecord rtdUriRecord1 =
                        NdefRecord.createUri("210.124.110.96/Ticket_Value.php");
            }
        }
    }
}
