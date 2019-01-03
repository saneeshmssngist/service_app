package com.saneesh.serviceapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class BankIFSCActivity extends AppCompatActivity {


    private TextView txtViewBank, txtViewState, txtViewDistrict, txtViewBranch,
            txtViewIFSC, txtViewMICR, txtViewAddress, txtViewPhone;
    private LinearLayout layoutBottom;
    private RelativeLayout layoutShare;
    private int stateCount;
    private String stateData;
    private int districtCount;
    private String districtData;
    private String branchData;
    private int branchCount;
    private String bankData;
    private int bankCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_ifsc);

        txtViewBank = (TextView) findViewById(R.id.txtViewBank);
        txtViewState = (TextView) findViewById(R.id.txtViewState);
        txtViewDistrict = (TextView) findViewById(R.id.txtViewDistrict);
        txtViewBranch = (TextView) findViewById(R.id.txtViewBranch);

        txtViewIFSC = (TextView) findViewById(R.id.txtViewIFSC);
        txtViewMICR = (TextView) findViewById(R.id.txtViewMICR);
        txtViewAddress = (TextView) findViewById(R.id.txtViewAddress);
        txtViewPhone = (TextView) findViewById(R.id.txtViewPhone);
        layoutBottom = (LinearLayout) findViewById(R.id.layoutBottom);
        layoutShare = (RelativeLayout) findViewById(R.id.layoutShare);

        getSupportActionBar().setTitle("Bank IFSC/ADDRESS/MICR ");


        txtViewBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AssetManager am = getResources().getAssets();
                String fileList[] = new String[0];
                try {

                    fileList = am.list("IFSC");

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (fileList != null) {
                    List<String> bankList = Arrays.asList(fileList);
                    setDialogSelection("bank", bankList);
                }
                txtViewState.setText("State");
                txtViewDistrict.setText("District");
                txtViewBranch.setText("Branch");
                layoutBottom.setVisibility(View.GONE);
                layoutShare.setVisibility(View.GONE);

            }
        });
        txtViewState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtViewBank.getText().toString().equals("Bank"))
                    return;
                setData("state");
                txtViewDistrict.setText("District");
                txtViewBranch.setText("Branch");
                layoutBottom.setVisibility(View.GONE);
                layoutShare.setVisibility(View.GONE);
            }
        });
        txtViewDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtViewState.getText().toString().equals("State"))
                    return;
                setData("district");
                txtViewBranch.setText("Branch");
                layoutBottom.setVisibility(View.GONE);
                layoutShare.setVisibility(View.GONE);
            }
        });
        txtViewBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtViewDistrict.getText().toString().equals("District"))
                    return;
                setData("branch");

            }
        });

        layoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareData();
            }
        });

    }


    public void setData(String state) {

        try {

            int count = 1;

            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("IFSC/"+bankData)));
            String mLine;
            while ((mLine = reader.readLine()) != null) {

                if (count == 1 && state.equals("state")) {
                    List<String> statesArray = Arrays.asList(mLine.split("\\*"));
                    setDialogSelection("state", statesArray);

                } else if (count == 2 && state.equals("district")) {
                    List<String> array = Arrays.asList(mLine.split("==="));
                    String[] strings = array.get(stateCount + 1).split(stateData + "->");
                    List<String> placeArray = Arrays.asList(strings[1].split("\\*"));
                    setDialogSelection("district", placeArray);

                } else if (count == 3 && state.equals("branch")) {
                    String[] strings = mLine.split(stateData + "->" + districtData + "->");
                    String[] strings1 = strings[1].split("===");

                    List<String> branchArray = Arrays.asList(strings1[0].split("\\*"));
                    setDialogSelection("branch", branchArray);

                } else if (count == 4 && state.equals("address")) {
                    String[] strings = mLine.split(stateData + "->" + districtData + "->" + branchData + "->");
                    String[] strings1 = strings[1].split("===");

                    layoutBottom.setVisibility(View.VISIBLE);
                    layoutShare.setVisibility(View.VISIBLE);

                    String[] strings2 = strings1[0].split("\\*");
                    txtViewAddress.setText(strings2[0]);
                    txtViewPhone.setText(strings2[1]);
                    txtViewIFSC.setText(strings2[2]);
                    txtViewMICR.setText(strings2[3]);

                }


                count++;


                Log.d("dsada", "onCreate: ");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void shareData() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Quizrr");

        String message = "<b>Bank Name </b>: "+txtViewBank.getText().toString().split(".txt")[0]+
                "\nState : "+txtViewState.getText().toString()+
                "\nDistrict : "+txtViewDistrict.getText().toString()+
                "\nBranch : "+txtViewBranch.getText().toString()+
                "\nIFSC Code : "+txtViewIFSC.getText().toString()+
                "\nMICR Code : "+txtViewMICR.getText().toString()+
                "\nAddress : "+txtViewAddress.getText().toString()+
                "\nPhone No : "+txtViewPhone.getText().toString();

        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(intent, "Choose one"));
    }

    private void setDialogSelection(final String data, final List<String> statesArray) {

        final Dialog dialog = new Dialog(this);

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_menu_layout, null);

        ListView listView = (ListView) view.findViewById(R.id.listViewDatas);
        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        dialog.setContentView(view);
        dialog.show();


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, statesArray);
        listView.setAdapter(arrayAdapter);

        txtTitle.setText("Select "+data);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {


                if (data.equals("bank")) {
                    bankData = statesArray.get(pos);
                    bankCount = pos;
                    txtViewBank.setText(bankData);

                    txtViewBank.setBackground(getApplicationContext().getDrawable(R.drawable.ifsc_text_bg_selected));
                    txtViewState.setBackground(getApplicationContext().getDrawable(R.drawable.ifsc_text_bg));
                    txtViewDistrict.setBackground(getApplicationContext().getDrawable(R.drawable.ifsc_text_bg));
                    txtViewBranch.setBackground(getApplicationContext().getDrawable(R.drawable.ifsc_text_bg));
                }

                if (data.equals("state")) {
                    stateData = statesArray.get(pos);
                    stateCount = pos;
                    txtViewState.setText(stateData);

                    txtViewState.setBackground(getApplicationContext().getDrawable(R.drawable.ifsc_text_bg_selected));
                    txtViewDistrict.setBackground(getApplicationContext().getDrawable(R.drawable.ifsc_text_bg));
                    txtViewBranch.setBackground(getApplicationContext().getDrawable(R.drawable.ifsc_text_bg));
                }

                if (data.equals("district")) {
                    districtData = statesArray.get(pos);
                    districtCount = pos;
                    txtViewDistrict.setText(districtData);

                    txtViewDistrict.setBackground(getApplicationContext().getDrawable(R.drawable.ifsc_text_bg_selected));
                    txtViewBranch.setBackground(getApplicationContext().getDrawable(R.drawable.ifsc_text_bg));

                }

                if (data.equals("branch")) {
                    branchData = statesArray.get(pos);
                    branchCount = pos;
                    txtViewBranch.setText(branchData);

                    txtViewBranch.setBackground(getApplicationContext().getDrawable(R.drawable.ifsc_text_bg_selected));

                    setData("address");
                }

                dialog.cancel();

            }
        });


    }

}
