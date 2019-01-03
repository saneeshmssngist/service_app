package com.saneesh.serviceapp.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saneesh.serviceapp.Adapter.PhoneServiceAdapter;
import com.saneesh.serviceapp.Model.PhoneServiceModel;
import com.saneesh.serviceapp.R;
import com.stone.vega.library.VegaLayoutManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PhoneInfoViewFragment extends Fragment {

    private String operator = "";
    private RecyclerView recyclerView;
    private View view;
    private ArrayList<PhoneServiceModel> serviceModelsArray;
    private String phoneNum = "";

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 22222;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_phone_info_view, container, false);

        operator = getArguments().getString("operator", "");

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Phone Service -"+operator);

        intiViews();
        initControls();

        return view;
    }

    private void intiViews() {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new VegaLayoutManager());

        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(final View view,
                                       final int left, final int top, final int right, final int bottom,
                                       final int oldLeft, final int oldTop, final int oldRight, final int oldBottom) {
                recyclerView.removeOnLayoutChangeListener(this);
                for (int i = 0, count = recyclerView.getChildCount(); i < count; ++i) {
                    final View child = recyclerView.getChildAt(i);
                    child.setTranslationX(0 - child.getLeft());
                    child.setTranslationY(0 - child.getTop());
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

    }

    private void initControls() {

        String fileName = "";

        switch (operator) {
            case "Idea":
                fileName = "Idea_Service.json";
                break;
            case "Jio":
                fileName = "Jio_Service.json";
                break;
            case "Vodafone":
                fileName = "Vodafone_Service.json";
                break;
            case "Bsnl":
                fileName = "Bsnl_Service.json";
                break;
            case "Airtel":
                fileName = "Airtel_Service.json";
                break;

        }

        serviceModelsArray = new
                Gson().fromJson(loadJSONFromAsset(fileName), new TypeToken<ArrayList<PhoneServiceModel>>() {
        }.getType());

        PhoneServiceAdapter serviceAdapter = new PhoneServiceAdapter(getActivity(), serviceModelsArray, new PhoneServiceAdapter.PhoneServiceListeners() {
            @Override
            public void OnPhoneServiceTapped(int pos) {

                phoneNum = serviceModelsArray.get(pos).getUssd();
                callPhone();
            }
        });
        recyclerView.setAdapter(serviceAdapter);

        //animations for recycler view

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getActivity(),R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();

    }


    public String loadJSONFromAsset(String operator) {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("Mobile/" + operator);
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

    public void callPhone() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        } else {

            if (!TextUtils.isEmpty(phoneNum)) {

                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(new Intent(Intent.ACTION_CALL,ussdToCallableUri(phoneNum)));
            }

        }
    }

    private Uri ussdToCallableUri(String ussd) {

        String uriString = "";

        if(!ussd.startsWith("tel:"))
            uriString += "tel:";

        for(char c : ussd.toCharArray()) {

            if(c == '#')
                uriString += Uri.encode("#");
            else
                uriString += c;
        }

        return Uri.parse(uriString);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phoneNum));

                    if (ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(callIntent);

                } else {
                    Toast.makeText(getActivity(), "Call faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            break;
        }

    }
}
