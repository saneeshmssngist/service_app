package com.saneesh.serviceapp;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saneesh.serviceapp.Adapter.DTHServiceAdapter;
import com.saneesh.serviceapp.Model.DTHServiceModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DTHInfoViewActivity extends BaseActivity {

    private String operator = "";
    private RecyclerView recyclerView;
    private ArrayList<DTHServiceModel> serviceModelsArray;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 11111;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 22222;
    private String messageN = "";
    private String phoneN = "";
    private String phoneCall = "";
    private AdView adMobView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dthinfo_view);

        if (getIntent().hasExtra("provider"))
            operator = getIntent().getStringExtra("provider");


        getSupportActionBar().setTitle(operator);

        intiViews();
        initControls();
        setUpAdmob();

    }

    private void setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this, getResources().getString(R.string.APPID));

        adMobView = (AdView) findViewById(R.id.adMobView);
        adMobView.loadAd(new AdRequest.Builder().build());

    }

    private void intiViews() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initControls() {

        String fileName = "";

        switch (operator) {
            case "Airtel":
                fileName = "airtel_dth.json";
                break;
            case "Dishtv":
                fileName = "dishtv_dth.json";
                break;
            case "Videocon":
                fileName = "videocon_dth.json";
                break;
            case "Reliance":
                fileName = "reliancetv_dth.json";
                break;
            case "Tatasky":
                fileName = "tatasky_dth.json";
                break;
            case "Sundirect":
                fileName = "sundirect_dth.json";
                break;

        }

        serviceModelsArray = new
                Gson().fromJson(loadJSONFromAsset(fileName), new TypeToken<ArrayList<DTHServiceModel>>() {
        }.getType());

        DTHServiceAdapter serviceAdapter = new DTHServiceAdapter(this, serviceModelsArray, new DTHServiceAdapter.OnDTHEventListeners() {
            @Override
            public void onCallTapped(String phone) {

                phoneCall(phone);
            }

            @Override
            public void onMessageTapped(String message, String phone) {

                sendMessage(phone, message);
            }
        });
        recyclerView.setAdapter(serviceAdapter);

    }

    public String loadJSONFromAsset(String operator) {
        String json = null;
        try {
            InputStream is = this.getAssets().open("DTH/" + operator);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}


