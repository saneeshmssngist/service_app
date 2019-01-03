package com.saneesh.serviceapp;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.saneesh.serviceapp.Fragments.BankserviceFragment;
import com.saneesh.serviceapp.Fragments.DTHServiceFragment;
import com.saneesh.serviceapp.Fragments.MobileServiceFragment;
import com.saneesh.serviceapp.Fragments.OnlineBookingFragment;

public class MainActivity extends AppCompatActivity {

    private BottomBar bottomBar;
    private AdView adMobView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setUpAdmob();

    }

    private void setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this, getResources().getString(R.string.APPID));

        adMobView = (AdView) findViewById(R.id.adMobView);
        adMobView.loadAd(new AdRequest.Builder().build());

    }

    private void initViews() {

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                //replacing each fragments
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                switch (tabId) {
                    case R.id.notstarted:
                        transaction.replace(R.id.frameContainer, new BankserviceFragment()).commit();
                        break;

                    case R.id.progressing:
                        transaction.replace(R.id.frameContainer, new MobileServiceFragment()).commit();
                        break;

                    case R.id.completed:
                        transaction.replace(R.id.frameContainer, new DTHServiceFragment()).commit();
                        break;

                    case R.id.delivered:
                        transaction.replace(R.id.frameContainer, new OnlineBookingFragment()).commit();
                        break;

                    default:
                        return;

                }
            }
        });
    }
}
