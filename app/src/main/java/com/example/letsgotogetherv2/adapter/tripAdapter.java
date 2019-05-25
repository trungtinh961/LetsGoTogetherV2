package com.example.letsgotogetherv2.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letsgotogetherv2.R;
import com.example.letsgotogetherv2.model.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * Created by Trung Tinh on 5/17/2019.
 */
public class tripAdapter extends RecyclerView.Adapter<tripAdapter.ViewHolder>{

    ArrayList<Trip> tripArrayList;
    Context context;
    String partnerID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public tripAdapter(ArrayList<Trip> tripArrayList, Context context) {
        this.tripArrayList = tripArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.custom_trip_recycleview,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvFrom.setText("Từ: " + tripArrayList.get(i).getFrom());
        viewHolder.tvTo.setText("Đến: " + tripArrayList.get(i).getTo());
        viewHolder.tvDate.setText("Ngày: " + tripArrayList.get(i).getDate());
        viewHolder.tvTime.setText("Giờ: " + tripArrayList.get(i).getTime());
        viewHolder.tvDriver.setText("Tài xế/khách: " + (tripArrayList.get(i).getDriver() ? "Tài xế!" : "Khách!"));
    }

    @Override
    public int getItemCount() {
        return tripArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvFrom, tvTo, tvDate, tvTime, tvDriver;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvFrom  = (TextView) itemView.findViewById(R.id.customtrip_tvFrom);
            tvTo    = (TextView) itemView.findViewById(R.id.customtrip_tvTo);
            tvDate  = (TextView) itemView.findViewById(R.id.customtrip_tvDate);
            tvTime  = (TextView) itemView.findViewById(R.id.customtrip_tvTime);
            tvDriver = (TextView) itemView.findViewById(R.id.customtrip_tvDriver);

            //------------- User chọn chuyến -------------------------------------------------
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), ""+getAdapterPosition(), Toast.LENGTH_SHORT).show();

                    Trip trip = tripArrayList.get(getAdapterPosition());

                    trip.setChoose(true);
                    trip.setPartnerID(partnerID);
                    confirmChoose(itemView);

                }
            });

            //-------------------------------------------------------------------------------
        }
    }

    public void confirmChoose(View itemView){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(itemView.getContext());
        alertDialog.setTitle("Thông báo!");
        alertDialog.setMessage("Bạn có muốn chọn chuyến này không?");

        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create().show();
    }

}

