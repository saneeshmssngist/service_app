package com.saneesh.serviceapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saneesh.serviceapp.Model.BankServiceModel;
import com.saneesh.serviceapp.R;

import java.util.ArrayList;

/**
 * Created by saNeesH on 2018-10-06.
 */

public class BankServiceAdapter extends RecyclerView.Adapter<BankServiceAdapter.PhoneHolder> {

    private Context context;
    private ArrayList<BankServiceModel> bankServiceModelsArray;
    private BankListeners bankListeners;

    public interface BankListeners
    {
        void onBankTapped(BankServiceModel serviceModel);
    }

    public BankServiceAdapter(Context context, ArrayList<BankServiceModel> bankServiceModels,BankListeners bankListeners) {
        this.context = context;
        this.bankServiceModelsArray = bankServiceModels;
        this.bankListeners = bankListeners;
    }

    @Override
    public int getItemCount() {
        return bankServiceModelsArray.size();
    }

    @NonNull
    @Override
    public PhoneHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_bank_service, parent, false);
        return new PhoneHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneHolder holder, final int position) {

        final BankServiceModel bankServiceModel = bankServiceModelsArray.get(position);

        holder.txtBankName.setText(bankServiceModel.getBankName());

        holder.imgBank.setImageResource(context.getResources().getIdentifier(bankServiceModel.getImgPath(),"drawable",context.getPackageName()));

        holder.txtBankName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bankListeners.onBankTapped(bankServiceModel);
            }
        });

    }

    public void setFilter(ArrayList<BankServiceModel> bankServiceModelArry) {

        bankServiceModelsArray = bankServiceModelArry;
        notifyDataSetChanged();
    }

    public class PhoneHolder extends RecyclerView.ViewHolder {

        TextView txtBankName;
        ImageView imgBank;

        public PhoneHolder(View itemView) {
            super(itemView);

            txtBankName = itemView.findViewById(R.id.txtBankName);
            imgBank = itemView.findViewById(R.id.imgBank);

        }
    }

}
