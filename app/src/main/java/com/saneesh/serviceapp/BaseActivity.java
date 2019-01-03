package com.saneesh.serviceapp;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 11111;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 22222;

    private String messageN = "";
    private String phoneN = "";
    private String phoneCall = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

    }


    public void sendMessage(String number, String info) {

        phoneN = number;
        messageN = info;

        final Dialog dialog = new Dialog(this);
        View view1 = LayoutInflater.from(this).inflate(R.layout.dialog_message_dth, null);
        dialog.setContentView(view1);
        dialog.setCancelable(false);
        dialog.show();

        final EditText edtTextMessage = (EditText) view1.findViewById(R.id.edtTextMessage);
        Button btnCancel = (Button) view1.findViewById(R.id.btnCancel);
        Button btnSend = (Button) view1.findViewById(R.id.btnSend);

        edtTextMessage.setText(info);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(BaseActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(BaseActivity.this,
                            Manifest.permission.SEND_SMS)) {
                    } else {
                        ActivityCompat.requestPermissions(BaseActivity.this, new String[]{Manifest.permission.SEND_SMS},
                                MY_PERMISSIONS_REQUEST_SEND_SMS);
                    }
                } else {

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneN, null, messageN, null, null);
                    Toast.makeText(BaseActivity.this, "SMS sent.", Toast.LENGTH_LONG).show();
                }
                dialog.cancel();
            }
        });

    }

    public void phoneCall(String number) {

        phoneCall = number;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        } else {

            if (!TextUtils.isEmpty(phoneCall)) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneCall));

                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (!TextUtils.isEmpty(phoneN) && !TextUtils.isEmpty(messageN)) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneN, null, messageN, null, null);
                        Toast.makeText(this, "SMS sent.", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(this, "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            break;
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phoneCall));

                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(callIntent);

                } else {
                    Toast.makeText(this, "Call faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            break;
        }

    }




}
