package com.saneesh.serviceapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.saneesh.serviceapp.Model.BankServiceModel;

public class BankInfoViewActivity extends BaseActivity {


    private TextView txtVwBankBalance,txtBankBalance,txtBankBalanceInfo,txtVwBankMini,txtBankMiniInfo,
            txtBankMini,txtBankCustomCare;
    private ImageView imgCall1,imgCall2,imgCall3,imgMessage1,imgMessage2;
    private AdView adMobView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_info_view);

        initViews();
        setDatas();

    }

    private void setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this, getResources().getString(R.string.APPID));

        adMobView = (AdView) findViewById(R.id.adMobView);
        adMobView.loadAd(new AdRequest.Builder().build());
    }

    private void initViews() {

        txtVwBankBalance = (TextView) findViewById(R.id.txtVwBankBalance);
        txtBankBalanceInfo = (TextView) findViewById(R.id.txtBankBalanceInfo);
        txtBankBalance = (TextView) findViewById(R.id.txtBankBalance);

        txtVwBankMini = (TextView) findViewById(R.id.txtVwBankMini);
        txtBankMiniInfo = (TextView) findViewById(R.id.txtBankMiniInfo);
        txtBankMini = (TextView) findViewById(R.id.txtBankMini);

        txtBankCustomCare = (TextView) findViewById(R.id.txtBankCustomCare);

        imgCall1 = (ImageView) findViewById(R.id.imgCall1);
        imgCall2 = (ImageView) findViewById(R.id.imgCall2);
        imgCall3 = (ImageView) findViewById(R.id.imgCall3);
        imgMessage1 = (ImageView) findViewById(R.id.imgMessage1);
        imgMessage2 = (ImageView) findViewById(R.id.imgMessage2);

    }

    private void setDatas() {

        final BankServiceModel bankServiceModel = (BankServiceModel) getIntent().getSerializableExtra("bankInfo");

        getSupportActionBar().setTitle(bankServiceModel.getBankName());


        if(bankServiceModel.getBankbalance().getFlag().equals("0"))
        {
            txtVwBankBalance.setText("Bank Balance");
            txtBankBalanceInfo.setVisibility(View.GONE);
            txtBankBalance.setText(TextUtils.isEmpty(bankServiceModel.getBankbalance().getNumber()) ? "NA" : bankServiceModel.getBankbalance().getNumber());

            imgCall1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    phoneCall(bankServiceModel.getBankbalance().getNumber());
                }
            });
        }
        else
        {
            txtVwBankBalance.setText("Bank Balance by SMS");
            txtBankBalanceInfo.setVisibility(View.VISIBLE);
            txtBankBalanceInfo.setText("Send SMS "+ bankServiceModel.getBankbalance().getInfo());
            txtBankBalance.setText(bankServiceModel.getBankbalance().getNumber());

            imgCall1.setVisibility(View.GONE);
            imgMessage1.setVisibility(View.VISIBLE);

            imgMessage1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    sendMessage(bankServiceModel.getBankbalance().getNumber(),bankServiceModel.getBankbalance().getInfo());
                }
            });
        }

        if(bankServiceModel.getMiniStatement().getFlag().equals("0"))
        {
            txtVwBankMini.setText("Mini Statement");
            txtBankMiniInfo.setVisibility(View.GONE);
            txtBankMini.setText(TextUtils.isEmpty(bankServiceModel.getMiniStatement().getNumber()) ? "NA" :bankServiceModel.getMiniStatement().getNumber());

            imgCall2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    phoneCall(bankServiceModel.getMiniStatement().getNumber());
                }
            });
        }
        else
        {
            txtVwBankMini.setText("Mini Statement by SMS");
            txtBankMiniInfo.setVisibility(View.VISIBLE);
            txtBankMiniInfo.setText("Send SMS "+ bankServiceModel.getMiniStatement().getInfo());
            txtBankMini.setText(bankServiceModel.getMiniStatement().getNumber());

            imgCall2.setVisibility(View.GONE);
            imgMessage2.setVisibility(View.VISIBLE);

            imgMessage2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    sendMessage(bankServiceModel.getMiniStatement().getNumber(),bankServiceModel.getBankbalance().getInfo());
                }
            });

        }

        txtBankCustomCare.setText(TextUtils.isEmpty(bankServiceModel.getCustomerNo()) ? "NA" :bankServiceModel.getCustomerNo());
        imgCall3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phoneCall(bankServiceModel.getCustomerNo());
            }
        });
    }




}
