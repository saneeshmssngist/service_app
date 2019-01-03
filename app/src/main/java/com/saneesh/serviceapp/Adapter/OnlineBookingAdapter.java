package com.saneesh.serviceapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saneesh.serviceapp.Model.OnlineModel;
import com.saneesh.serviceapp.R;

import java.util.ArrayList;

/**
 * Created by saNeesH on 2018-10-25.
 */

public class OnlineBookingAdapter extends RecyclerView.Adapter<OnlineBookingAdapter.OnlineHolder> {

    private ArrayList<OnlineModel> onlineModelsArray;
    private Context context;
    private OnlineBookingListeners bookingListeners;

    public interface OnlineBookingListeners
    {
        public void OnButtonTapped(int position);
    }

    public OnlineBookingAdapter(Context context, ArrayList<OnlineModel> onlineModelsArray,OnlineBookingListeners bookingListeners) {
        this.context = context;
        this.onlineModelsArray = onlineModelsArray;
        this.bookingListeners = bookingListeners;
    }

    @Override
    public int getItemCount() {
        return onlineModelsArray.size();
    }

    @NonNull
    @Override
    public OnlineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_online_booking, parent, false);
        return new OnlineHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnlineHolder holder, final int position) {


        OnlineModel onlineModel = onlineModelsArray.get(position);

        holder.imgOnline.setImageResource(context.getResources().getIdentifier(onlineModel.getImagePath(),"drawable",context.getPackageName()));
        holder.txtOnlineName.setText(onlineModel.getName());
        holder.txtOnlineDescri.setText(onlineModel.getDescr());
        holder.txtOnlineRating.setText("Ratings : "+onlineModel.getRating());

        holder.cardViewOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookingListeners.OnButtonTapped(position);
            }
        });
    }


    public class OnlineHolder extends RecyclerView.ViewHolder {

        CardView cardViewOnline;
        ImageView imgOnline;
        TextView txtOnlineName,txtOnlineDescri,txtOnlineRating;

        public OnlineHolder(View itemView) {
            super(itemView);

            cardViewOnline = (CardView) itemView.findViewById(R.id.cardViewOnline);
            imgOnline = (ImageView) itemView.findViewById(R.id.imgOnline);
            txtOnlineName = (TextView) itemView.findViewById(R.id.txtOnlineName);
            txtOnlineDescri = (TextView) itemView.findViewById(R.id.txtOnlineDescri);
            txtOnlineRating = (TextView) itemView.findViewById(R.id.txtOnlineRating);

        }
    }
}
