package com.saneesh.serviceapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.saneesh.serviceapp.DTHInfoViewActivity;
import com.saneesh.serviceapp.R;

/**
 * Created by saNeesH on 2018-10-13.
 */

public class DTHServiceFragment extends Fragment {

    private View view;
    private CardView cardVwAirtel,cardVwDish,cardVwVideocon,cardVwReliance,cardVwTataSky,cardVwSunDirect;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dth, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("DTH Service");

        context = getContext();
        intViews();
        initControls();

        return view;
    }

    private void intViews() {

        cardVwAirtel = view.findViewById(R.id.cardVwAirtel);
        cardVwDish = view.findViewById(R.id.cardVwDish);
        cardVwVideocon = view.findViewById(R.id.cardVwVideocon);
        cardVwReliance = view.findViewById(R.id.cardVwReliance);
        cardVwTataSky = view.findViewById(R.id.cardVwTataSky);
        cardVwSunDirect = view.findViewById(R.id.cardVwSunDirect);

    }

    private void initControls() {

        cardVwAirtel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), DTHInfoViewActivity.class).putExtra("provider", "Airtel"));
            }
        });

        cardVwDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), DTHInfoViewActivity.class).putExtra("provider", "Dishtv"));
            }
        });

        cardVwVideocon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), DTHInfoViewActivity.class).putExtra("provider", "Videocon"));
            }
        });

        cardVwReliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), DTHInfoViewActivity.class).putExtra("provider", "Reliance"));
            }
        });
        cardVwTataSky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), DTHInfoViewActivity.class).putExtra("provider", "Tatasky"));
            }
        });
        cardVwSunDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), DTHInfoViewActivity.class).putExtra("provider", "Sundirect"));
            }
        });

    }

}
