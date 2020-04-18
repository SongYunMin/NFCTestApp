package com.example.nfctestapp;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.nfc.cardemulation.HostApduService;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Arrays;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class CardService extends HostApduService {
    private static final String TAG = "CardService";
    private static final String SAMPLE_LOYALTY_CARD_AID = "F222222222";
    private static final String SELECT_APDU_HEADER = "00A40400";
    private static final byte[] SELECT_OK_SW = HexStringToByteArray("9000");
    private static final byte[] UNKNOWN_CMD_SW = HexStringToByteArray("0000");
    private static final byte[] SELECT_APDU = BuildSelectApdu(SAMPLE_LOYALTY_CARD_AID);

    private Messenger _handler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // UI Thread 핸들 얻음
        Bundle extras = intent.getExtras();

        return START_STICKY;
    }

    // 외부로부터의 APDU 명령을 받았을때 호출
    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        if (Arrays.equals(SELECT_APDU, commandApdu)) {
            Log.i(TAG, "Application selected");
            Counter.AddOne();
            return SELECT_OK_SW;
        } else {
            return UNKNOWN_CMD_SW;
        }
    }


    // 연결을 잃었을때 호출됨
    @Override
    public void onDeactivated(int reason) {
        Log.i(TAG, "Deactivated : " + reason);
    }

    public static byte[] BuildSelectApdu(String aid) {
        return HexStringToByteArray(SELECT_APDU_HEADER +
                String.format("%02X", aid.length() / 2) + aid);
    }

    public static byte[] HexStringToByteArray(String s) throws IllegalArgumentException {
        int len = s.length();
        if (len % 2 == 1) {
            throw new IllegalArgumentException("Hex string must have even number of characters");
        }
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit((s.charAt(i)), 16) << 4) +
                    Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }


}
