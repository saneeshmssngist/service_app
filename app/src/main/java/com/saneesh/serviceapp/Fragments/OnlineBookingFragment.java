package com.saneesh.serviceapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.saneesh.serviceapp.Adapter.OnlineBookingAdapter;
import com.saneesh.serviceapp.Model.OnlineModel;
import com.saneesh.serviceapp.OnilneBookingActivity;
import com.saneesh.serviceapp.R;

import java.util.ArrayList;

/**
 * Created by saNeesH on 2018-10-21.
 */

public class OnlineBookingFragment extends Fragment implements View.OnClickListener {

    private View view;
    private CardView cardViewBus, cardViewTrain, cardViewKsrtc;
    private RecyclerView recyclerView;
    private ArrayList<OnlineModel> onlineModelsArray = new ArrayList<>();
    private LinearLayout layoutAnim;
    private View view1,view2,view3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_online_booking, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Travel Service - Bus");

        intViews();
        return view;
    }

    private void intViews() {

        cardViewBus = (CardView) view.findViewById(R.id.cardViewBus);
        cardViewTrain = (CardView) view.findViewById(R.id.cardViewTrain);
        cardViewKsrtc = (CardView) view.findViewById(R.id.cardViewKsrtc);
        layoutAnim = (LinearLayout) view.findViewById(R.id.layoutAnim);

        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        view3 = view.findViewById(R.id.view3);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        cardViewBus.setOnClickListener(this);
        cardViewTrain.setOnClickListener(this);
        cardViewKsrtc.setOnClickListener(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        final int width = displayMetrics.widthPixels;

        view1.setVisibility(View.VISIBLE);

        layoutAnim.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(final View view,
                                       final int left, final int top, final int right, final int bottom,
                                       final int oldLeft, final int oldTop, final int oldRight, final int oldBottom) {

                layoutAnim.removeOnLayoutChangeListener(this);
                for (int i = 0, count = layoutAnim.getChildCount(); i < count; ++i) {
                    final View child = layoutAnim.getChildAt(i);
                    child.setTranslationX(width / 2 - child.getLeft());
                    child.setTranslationY((height - 50) - child.getTop());
                    child.animate()
                            .translationX(0f)
                            .translationY(0f)
                            .setStartDelay(5 * i * i)
                            .setDuration(1000)
                            .withLayer()
                            .start();
                }
            }
        });

        onlineModelsArray = getBusDatas();
        setViewAdapter();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.refresh_icon:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        String title = "";

        resetView();

        switch (id) {
            case R.id.cardViewBus:
                onlineModelsArray = getBusDatas();
                title = "Bus";
                view1.setVisibility(View.VISIBLE);
                break;
            case R.id.cardViewTrain:
                onlineModelsArray = getTrainDatas();
                title = "Train";
                view2.setVisibility(View.VISIBLE);
                break;
            case R.id.cardViewKsrtc:
                onlineModelsArray = getKSRTCDatas();
                title = "KSRTC";
                view3.setVisibility(View.VISIBLE);
                break;
        }

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Travel Service - "+title);
        setViewAdapter();

    }

    private void resetView() {

        view1.setVisibility(View.INVISIBLE);
        view2.setVisibility(View.INVISIBLE);
        view3.setVisibility(View.INVISIBLE);
    }

    private void setViewAdapter() {

        OnlineBookingAdapter onlineBookingAdapter = new OnlineBookingAdapter(getActivity(), onlineModelsArray, new OnlineBookingAdapter.OnlineBookingListeners() {
            @Override
            public void OnButtonTapped(int position) {

                OnlineModel onlineModel = onlineModelsArray.get(position);
                startActivity(new Intent(getActivity(), OnilneBookingActivity.class).putExtra("onlineUrl", onlineModel.getUrl()));
            }
        });
        recyclerView.setAdapter(onlineBookingAdapter);

    }

    private ArrayList<OnlineModel> getBusDatas() {

        ArrayList<OnlineModel> onlineModels = new ArrayList<>();

        OnlineModel onlineModel1 = new OnlineModel();
        onlineModel1.setUrl("https://www.redbus.in");
        onlineModel1.setName("Redbus");
        onlineModel1.setImagePath("on_redbus");
        onlineModel1.setDescr("Trusted by Over 8 Million Indians. Get Ticket on SMS; No More Printout. Book Now. Track Your Bus. Know Your Rest Stops. Extra Offers-Select Buses.");
        onlineModel1.setRating("4.5");

        OnlineModel onlineModel2 = new OnlineModel();
        onlineModel2.setUrl("https://www.travelyaari.com");
        onlineModel2.setName("TravelYathri");
        onlineModel2.setImagePath("on_travelyathri");
        onlineModel2.setDescr("Travelyaari.com is the largest online bus booking portal headquartered at Bangalore, India. With a network of over 3,500 bus operators on its website and 150,000 bus options per day on 45,000 routes");
        onlineModel2.setRating("4.0");

        OnlineModel onlineModel3 = new OnlineModel();
        onlineModel3.setUrl("https://www.goibibo.com/bus/");
        onlineModel3.setName("Goibibo");
        onlineModel3.setImagePath("on_goibibo");
        onlineModel3.setDescr("Find best deals on flight booking, bus booking, and hotel booking at Goibibo. Get best deals on hotels, trains, cheap flights, and bus ticket booking.");
        onlineModel3.setRating("3.0");

        onlineModels.add(onlineModel1);
        onlineModels.add(onlineModel2);
        onlineModels.add(onlineModel3);

        return onlineModels;
    }

    private ArrayList<OnlineModel> getTrainDatas() {

        ArrayList<OnlineModel> onlineModels = new ArrayList<>();

        OnlineModel onlineModel1 = new OnlineModel();
        onlineModel1.setUrl("https://www.irctc.co.in/");
        onlineModel1.setName("IRCTC");
        onlineModel1.setImagePath("on_irctc");
        onlineModel1.setDescr("Indian Railway Catering and Tourism Corporation is a subsidiary of the Indian Railways that handles the catering, tourism and online ticketing operations of the Indian railways, with around 5,50,000 to 6,00,000 bookings everyday is the world's second busiest");
        onlineModel1.setRating("4.6");

        OnlineModel onlineModel2 = new OnlineModel();
        onlineModel2.setUrl("https://www.goibibo.com/trains/");
        onlineModel2.setName("Goibibo");
        onlineModel2.setImagePath("on_goibibo");
        onlineModel2.setDescr("Find best deals on flight booking, bus booking, and hotel booking at Goibibo. Get best deals on hotels, trains, cheap flights, and bus ticket booking.");
        onlineModel2.setRating("3.0");

        OnlineModel onlineModel3 = new OnlineModel();
        onlineModel3.setUrl("https://www.cleartrip.com/trains");
        onlineModel3.setName("Cleartrip");
        onlineModel3.setImagePath("on_cleartrip");
        onlineModel3.setDescr("Cleartrip is a global online travel company. The company operates an online travel aggregator website for booking flights and train tickets, hotel reservations, and activities in India and the Middle East countries. ");
        onlineModel3.setRating("4.5");

        OnlineModel onlineModel4 = new OnlineModel();
        onlineModel4.setUrl("https://www.yatra.com/trains");
        onlineModel4.setName("Yatra");
        onlineModel4.setImagePath("on_yathra");
        onlineModel4.setDescr("Yatra.com is an Indian online travel agency and a travel search engine based in Gurugram, Haryana, founded by Dhruv Shringi, Manish Amin and Sabina Chopra in August 2006.");
        onlineModel4.setRating("4.0");

        onlineModels.add(onlineModel1);
        onlineModels.add(onlineModel2);
        onlineModels.add(onlineModel3);
        onlineModels.add(onlineModel4);

        return onlineModels;
    }

    private ArrayList<OnlineModel> getKSRTCDatas() {

        ArrayList<OnlineModel> onlineModels = new ArrayList<>();

        OnlineModel onlineModel1 = new OnlineModel();
        onlineModel1.setUrl("https://www.aanavandi.com/");
        onlineModel1.setName("Aanavandi");
        onlineModel1.setImagePath("on_aanavandi");
        onlineModel1.setDescr("KSRTC Kerala bus timings powered by Team KSRTC Blog. Search KSRTC bus timings across kerala. Plan your trip with KSRTC.");
        onlineModel1.setRating("4.0");

        OnlineModel onlineModel2 = new OnlineModel();
        onlineModel2.setUrl("https://www.yatra.com/bus-operators-india/ksrtc-kerala");
        onlineModel2.setName("Yatra");
        onlineModel2.setImagePath("on_yathra");
        onlineModel2.setDescr("Yatra.com is an Indian online travel agency and a travel search engine based in Gurugram, Haryana, founded by Dhruv Shringi, Manish Amin and Sabina Chopra in August 2006.");
        onlineModel2.setRating("4.0");

        OnlineModel onlineModel3 = new OnlineModel();
        onlineModel3.setUrl("https://www.redbus.in/online-booking/ksrtc-kerala.aspx");
        onlineModel3.setName("Redbus");
        onlineModel3.setImagePath("on_redbus");
        onlineModel3.setDescr("Trusted by Over 8 Million Indians. Get Ticket on SMS; No More Printout. Book Now. Track Your Bus. Know Your Rest Stops. Extra Offers-Select Buses.");
        onlineModel3.setRating("4.5");

        onlineModels.add(onlineModel1);
        onlineModels.add(onlineModel2);
        onlineModels.add(onlineModel3);

        return onlineModels;
    }
}
