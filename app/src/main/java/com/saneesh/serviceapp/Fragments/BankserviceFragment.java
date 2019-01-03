package com.saneesh.serviceapp.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saneesh.serviceapp.BankBalanceActivity;
import com.saneesh.serviceapp.BankIFSCActivity;
import com.saneesh.serviceapp.R;

import java.net.URLEncoder;
import java.util.Locale;

public class BankserviceFragment extends Fragment {


    private View view;
    private CardView btnbankBalance, btnbankIFSC, btnfindATM, btnfindBank;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bank, container, false);


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Bank Service");
        intViews();
        initControls();

        return view;
    }

    private void intViews() {

        btnbankBalance = view.findViewById(R.id.btnbankBalance);
        btnbankIFSC = view.findViewById(R.id.btnbankIFSC);
        btnfindATM = view.findViewById(R.id.btnfindATM);
        btnfindBank = view.findViewById(R.id.btnfindBank);

    }

    private void initControls() {

        btnbankBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), BankBalanceActivity.class));
            }
        });
        btnbankIFSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), BankIFSCActivity.class));
            }
        });

        btnfindATM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uri = "https://www.google.com/maps/search/atm/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

        btnfindBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uri = "https://www.google.com/maps/search/bank/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });


    }


}
