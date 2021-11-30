package com.example.recibesms;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;

public class MainActivity extends Activity {

    private void checkSMSStatePermission(){

        int permissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if (permissionCheck!= PackageManager.PERMISSION_GRANTED){
            Log.i("Mensaje", "No se tiene permiso para recibir SMS");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS},225);
        } else {
            Log.i("Mensaje","Se tiene permiso para recibir SMS");
        }

        permissionCheck=ContextCompat.checkSelfPermission(this,Manifest.permission.READ_SMS);
        if (permissionCheck!=PackageManager.PERMISSION_GRANTED){
            Log.i("Mensaje","No se tiene permiso para leer SMS");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS},225);
        }else {
            Log.i("Mensaje","Se tiene permiso para leer SMS");
        }

        permissionCheck= ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS);
        if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
            Log.i("mensaje","No se tiene permiso para recibir SMS");
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},225);
        }else{
            Log.i("mensaje","Se tiene permiso para recibir SMS");
        }
    }

    public void sendSM(String tel, String msg) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(tel, null, msg, null, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkSMSStatePermission();
        setContentView(R.layout.activity_main);
    }


}