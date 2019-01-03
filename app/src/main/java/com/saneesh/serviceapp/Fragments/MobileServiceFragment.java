package com.saneesh.serviceapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.saneesh.serviceapp.R;

/**
 * Created by saNeesH on 2018-10-13.
 */

public class MobileServiceFragment extends Fragment implements View.OnClickListener {

    private View view;
    private CardView btnIdea, btnJio, btnVodafone, btnBsnl, btnAirtel;
    private Context context;
    private FrameLayout frameLayout;
    private HorizontalScrollView srcollView;
    private LinearLayout layoutLinear;
    private View view1,view2,view3,view4,view5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mobile, container, false);

        context = getContext();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Phone Service");

        intViews();
        setData();

        return view;
    }

    private void intViews() {

        btnIdea = view.findViewById(R.id.btnIdea);
        btnJio = view.findViewById(R.id.btnJio);
        btnVodafone = view.findViewById(R.id.btnVodafone);
        btnBsnl = view.findViewById(R.id.btnBsnl);
        btnAirtel = view.findViewById(R.id.btnAirtel);

        frameLayout = view.findViewById(R.id.frameLayout);
        srcollView = view.findViewById(R.id.srcollView);
        layoutLinear = view.findViewById(R.id.layoutLinear);

        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        view3 = view.findViewById(R.id.view3);
        view4 = view.findViewById(R.id.view4);
        view5 = view.findViewById(R.id.view5);

        btnIdea.setOnClickListener(this);
        btnJio.setOnClickListener(this);
        btnVodafone.setOnClickListener(this);
        btnBsnl.setOnClickListener(this);
        btnAirtel.setOnClickListener(this);

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_fall_down);
        srcollView.setLayoutAnimation(controller);
        srcollView.scheduleLayoutAnimation();
    }

    private void setData() {

        PhoneInfoViewFragment infoViewFragment = new PhoneInfoViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("operator", "Idea");

        view1.setVisibility(View.VISIBLE);

        infoViewFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.frameLayout, infoViewFragment).commit();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        final int width = displayMetrics.widthPixels;

        layoutLinear.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(final View view,
                                       final int left, final int top, final int right, final int bottom,
                                       final int oldLeft, final int oldTop, final int oldRight, final int oldBottom) {

                layoutLinear.removeOnLayoutChangeListener(this);
                for (int i = 0, count = layoutLinear.getChildCount(); i < count; ++i) {
                    final View child = layoutLinear.getChildAt(i);
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

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        final PhoneInfoViewFragment infoViewFragment = new PhoneInfoViewFragment();
        final Bundle bundle = new Bundle();

        resetView();

        switch (id) {
            case R.id.btnIdea:
                bundle.putString("operator", "Idea");
                view1.setVisibility(View.VISIBLE);
                break;
            case R.id.btnJio:
                bundle.putString("operator", "Jio");
                view2.setVisibility(View.VISIBLE);
                break;
            case R.id.btnVodafone:
                bundle.putString("operator", "Vodafone");
                view3.setVisibility(View.VISIBLE);
                break;
            case R.id.btnBsnl:
                bundle.putString("operator", "Bsnl");
                view4.setVisibility(View.VISIBLE);
                break;
            case R.id.btnAirtel:
                bundle.putString("operator", "Airtel");
                view5.setVisibility(View.VISIBLE);
                break;
        }

        infoViewFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.frameLayout, infoViewFragment).commit();

    }

    private void resetView() {

        view1.setVisibility(View.INVISIBLE);
        view2.setVisibility(View.INVISIBLE);
        view3.setVisibility(View.INVISIBLE);
        view4.setVisibility(View.INVISIBLE);
        view5.setVisibility(View.INVISIBLE);
    }

}
