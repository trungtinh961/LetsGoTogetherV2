package com.example.letsgotogetherv2.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letsgotogetherv2.R;
import com.example.letsgotogetherv2.model.Trip;
import com.example.letsgotogetherv2.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
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
        Button btnChoose;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvFrom  = (TextView) itemView.findViewById(R.id.customtrip_tvFrom);
            tvTo    = (TextView) itemView.findViewById(R.id.customtrip_tvTo);
            tvDate  = (TextView) itemView.findViewById(R.id.customtrip_tvDate);
            tvTime  = (TextView) itemView.findViewById(R.id.customtrip_tvTime);
            tvDriver = (TextView) itemView.findViewById(R.id.customtrip_tvDriver);
            btnChoose = (Button) itemView.findViewById(R.id.recycle_btnChoose);


            //------ User chọn chuyến -----------------------
            btnChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (tripArrayList.get(getAdapterPosition()).getUserID().equals(partnerID)){
                        Toast.makeText(itemView.getContext(), "Chuyến này do bạn tạo nên không thể chọn!", Toast.LENGTH_SHORT).show();
                    } else{
                        confirmChoose(itemView,getAdapterPosition());
                    }
                }
            });

            //-------------------------------------------------------------------------------
        }
    }

    public void confirmChoose(final View itemView, final int position){

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(itemView.getContext());
        alertDialog.setTitle("Thông báo!");
        alertDialog.setMessage("Bạn có muốn chọn chuyến này không?");

        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final Trip trip = tripArrayList.get(position);
                trip.setChoose(true);
                trip.setPartnerID(partnerID);

                // 1. Cập nhật lại trip hiện tại.
                db.collection("trips").document(trip.getTripID()).set(trip);

                // 2. Cập nhật thông tin trip của user tạo nó.
                db.collection("users").document(trip.getUserID())
                        .update("tripArrayList", FieldValue.arrayRemove(trip.getTripID()));

                // 3. Show thông tin người tạo chuyến đi.
                dialog.cancel();

                db.collection("users").document(trip.getUserID()).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        User user = documentSnapshot.toObject(User.class);

                        if (user != null) {

                            dialogTrip(itemView, trip, user);

                        } else {
                            Log.d("VALUE", "NULL");
                        }
                    }
                });

            }
        });

        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    public void dialogTrip(View itemView, Trip trip, User user){
        final Dialog dialog = new Dialog(itemView.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_choose_trip_success);
        dialog.setCanceledOnTouchOutside(false);

        TextView tvTrip = dialog.findViewById(R.id.success_trip);
        TextView tvUser = dialog.findViewById(R.id.success_user);
        TextView btnOK = dialog.findViewById(R.id.success_btnOK);

        tvTrip.setText("Từ: " + trip.getFrom() +
                "\nĐến: " + trip.getTo() +
                "\nNgày: " + trip.getDate() +
                "\nGiờ: " + trip.getTime());

        tvUser.setText("Tên: " + user.getName() +
                "\nSĐT: " + user.getPhone());

        dialog.show();

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }
}

