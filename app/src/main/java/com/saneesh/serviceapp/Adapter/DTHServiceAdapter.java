package com.saneesh.serviceapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saneesh.serviceapp.Model.DTHServiceModel;
import com.saneesh.serviceapp.R;

import java.util.ArrayList;

/**
 * Created by saNeesH on 2018-10-14.
 */

public class DTHServiceAdapter extends RecyclerView.Adapter<DTHServiceAdapter.DTHHolder> {

    private Context context;
    private ArrayList<DTHServiceModel> serviceModelsArray;

    public OnDTHEventListeners onDTHEventListeners;

    public interface OnDTHEventListeners {

        void onCallTapped(String phone);

        void onMessageTapped(String s, String phone);
    }

    public DTHServiceAdapter(Context context, ArrayList<DTHServiceModel> serviceModelsArray, OnDTHEventListeners onDTHEventListeners) {

        this.context = context;
        this.serviceModelsArray = serviceModelsArray;
        this.onDTHEventListeners = onDTHEventListeners;
    }

    @Override
    public int getItemCount() {
        return serviceModelsArray.size();
    }

    @NonNull
    @Override
    public DTHHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.dth_service_raw, parent, false);
        return new DTHHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DTHHolder holder, final int position) {

        final DTHServiceModel dthServiceModel = serviceModelsArray.get(position);

        holder.txtViewHead.setText(dthServiceModel.getTitle());

        if (dthServiceModel.getStatus().equals("0")) {
            holder.txtViewDescription.setText(dthServiceModel.getDescription());
            holder.imgViewCall.setVisibility(View.VISIBLE);
            holder.imgViewMessage.setVisibility(View.GONE);

            holder.imgViewCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onDTHEventListeners.onCallTapped(dthServiceModel.getPhone());
                }
            });
        } else {
            holder.imgViewCall.setVisibility(View.GONE);
            holder.txtViewDescription.setVisibility(View.GONE);
            holder.imgViewMessage.setVisibility(View.VISIBLE);

            holder.imgViewMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onDTHEventListeners.onMessageTapped(dthServiceModel.getDescription(), dthServiceModel.getPhone());
                }
            });

        }
        holder.txtViewPhone.setText(dthServiceModel.getPhone());

    }

    public class DTHHolder extends RecyclerView.ViewHolder {

        TextView txtViewHead, txtViewDescription, txtViewPhone;
        ImageView imgViewMessage, imgViewCall;

        public DTHHolder(View itemView) {
            super(itemView);

            txtViewHead = (TextView) itemView.findViewById(R.id.txtViewHead);
            txtViewDescription = (TextView) itemView.findViewById(R.id.txtViewDescription);
            txtViewPhone = (TextView) itemView.findViewById(R.id.txtViewPhone);

            imgViewMessage = (ImageView) itemView.findViewById(R.id.imgViewMessage);
            imgViewCall = (ImageView) itemView.findViewById(R.id.imgViewCall);

        }
    }


}
