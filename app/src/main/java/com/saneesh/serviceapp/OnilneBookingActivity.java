package com.saneesh.serviceapp;

import android.annotation.TargetApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.wang.avi.AVLoadingIndicatorView;

public class OnilneBookingActivity extends AppCompatActivity {


    private WebView webView;
    private LinearLayout layoutProgress;
    private AVLoadingIndicatorView avilayoutProgress;
    private AdView adMobView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onilne_booking);

        webView = (WebView) findViewById(R.id.webView);
        layoutProgress = findViewById(R.id.layoutProgress);
        avilayoutProgress = findViewById(R.id.avilayoutProgress);

        getSupportActionBar().setTitle("Travel Service");

        setData();
        setUpAdmob();
    }

    private void setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this, getResources().getString(R.string.APPID));

        adMobView = (AdView) findViewById(R.id.adMobView);
        adMobView.loadAd(new AdRequest.Builder().build());

    }

    private void setData() {

        layoutProgress.setVisibility(View.VISIBLE);
        avilayoutProgress.show();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(OnilneBookingActivity.this, "Check your network... ", Toast.LENGTH_SHORT).show();
                avilayoutProgress.smoothToHide();
                layoutProgress.setVisibility(View.GONE);

            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                avilayoutProgress.smoothToHide();
                layoutProgress.setVisibility(View.GONE);

            }


        });

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

                if(progress == 100)
                {
                    avilayoutProgress.smoothToHide();
                    layoutProgress.setVisibility(View.GONE);

                }
            }
        });
        webView.loadUrl(getIntent().getStringExtra("onlineUrl"));

    }


}
