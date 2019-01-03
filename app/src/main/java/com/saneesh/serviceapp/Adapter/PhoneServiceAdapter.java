package com.saneesh.serviceapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saneesh.serviceapp.Model.PhoneServiceModel;
import com.saneesh.serviceapp.R;

import java.util.ArrayList;

/**
 * Created by saNeesH on 2018-10-13.
 */

public class PhoneServiceAdapter extends RecyclerView.Adapter<PhoneServiceAdapter.PhoneHolder> {

    private ArrayList<PhoneServiceModel> serviceModelsArray;
    private Context context;
    private PhoneServiceListeners serviceListeners;

    public interface PhoneServiceListeners
    {
        void OnPhoneServiceTapped(int position);
    }

    public PhoneServiceAdapter(Context context, ArrayList<PhoneServiceModel> serviceModelsArray,PhoneServiceListeners serviceListeners) {

        this.context = context;
        this.serviceModelsArray = serviceModelsArray;
        this.serviceListeners = serviceListeners;
    }

    @Override
    public int getItemCount() {
        return serviceModelsArray.size();
    }

    @NonNull
    @Override
    public PhoneHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.phone_service_raw,parent,false);
        return new PhoneHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneHolder holder, final int position) {

        PhoneServiceModel serviceModel = serviceModelsArray.get(position);

        holder.txtViewHead.setText(serviceModel.getTitle());
        holder.txtViewUssd.setText(serviceModel.getUssd());

        holder.callLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                serviceListeners.OnPhoneServiceTapped(position);
            }
        });
    }


    public class PhoneHolder extends RecyclerView.ViewHolder {

        TextView txtViewHead, txtViewUssd;
        LinearLayout callLayout;

        public PhoneHolder(View itemView) {
            super(itemView);

            callLayout = (LinearLayout) itemView.findViewById(R.id.callLayout);

            txtViewHead = (TextView) itemView.findViewById(R.id.txtViewHead);
            txtViewUssd = (TextView) itemView.findViewById(R.id.txtViewUssd);
        }
    }
}
