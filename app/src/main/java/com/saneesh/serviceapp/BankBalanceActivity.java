package com.saneesh.serviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saneesh.serviceapp.Adapter.BankServiceAdapter;
import com.saneesh.serviceapp.Model.BankServiceModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BankBalanceActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private RecyclerView recyclerView;
    private BankServiceAdapter phoneServiceAdapter;

    ArrayList<BankServiceModel> bankServiceModelsArray;
    private AdView adMobView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_ussd);

        getSupportActionBar().setTitle("Bank Balance");

        initViews();
        setAdapter();
        setUpAdmob();

    }
    private void setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this, getResources().getString(R.string.APPID));

        adMobView = (AdView) findViewById(R.id.adMobView);
        adMobView.loadAd(new AdRequest.Builder().build());

    }

    private void initViews() {

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setAdapter() {

        Gson gson = new Gson();
        bankServiceModelsArray = gson.fromJson(loadJSONFromAsset(),
                new TypeToken<ArrayList<BankServiceModel>>() {
                }.getType());


        phoneServiceAdapter = new BankServiceAdapter(this, bankServiceModelsArray, new BankServiceAdapter.BankListeners() {
            @Override
            public void onBankTapped(BankServiceModel serviceModel) {

                startActivity(new Intent(BankBalanceActivity.this, BankInfoViewActivity.class).putExtra("bankInfo", serviceModel));
            }
        });
        recyclerView.setAdapter(phoneServiceAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

//        MenuItem menuItem = menu.findItem(R.id.search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
//
//        if (searchView != null) {
//
//            searchView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
//        }
//        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id)
        {
            case R.id.search :
                SearchView searchView = (SearchView)MenuItemCompat.getActionView(item);
                searchView.setOnQueryTextListener(this);
                break;
        }


        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onQueryTextSubmit(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        ArrayList<BankServiceModel> bankServiceModelArry = new ArrayList<BankServiceModel>();

        if((newText.length()!= 0) && (bankServiceModelsArray != null) && (bankServiceModelsArray.size() != 0))
        {
            for (BankServiceModel bankServiceModel : bankServiceModelsArray)
            {
                if (bankServiceModel.getBankName().toLowerCase().contains(newText.toLowerCase()))
                {
                    bankServiceModelArry.add(bankServiceModel);
                }
            }
            phoneServiceAdapter.setFilter(bankServiceModelArry);

        }
        else if(newText.equals("") && (bankServiceModelsArray != null))
        {
            phoneServiceAdapter.setFilter(bankServiceModelsArray);

        }

        return false;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("Bank_Balance_Service.json");
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
