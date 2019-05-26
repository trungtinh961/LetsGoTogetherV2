package com.example.letsgotogetherv2.view;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.letsgotogetherv2.R;
import com.example.letsgotogetherv2.model.Trip;
import com.example.letsgotogetherv2.model.User;

import java.util.Objects;

public class choose_trip_success extends Activity{

    TextView tvTrip, tvUser;
    Button btnOK;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_trip_success);

        tvTrip = findViewById(R.id.success_trip);
        tvUser = findViewById(R.id.success_user);
        btnOK = findViewById(R.id.success_btnOK);

        Intent intent = getIntent();

        Bundle bundle = intent.getBundleExtra("data");

        if (bundle != null){
            Trip trip = (Trip) bundle.getSerializable("trip");
            User user = (User) bundle.getSerializable("user");

            tvTrip.setText("Từ: " + trip.getFrom() +
                           "\nĐến: " + trip.getTo() +
                           "\nNgày: " + trip.getDate() +
                           "\nGiờ: " + trip.getTime());

            tvUser.setText("Tên: " + user.getName() +
                            "\nSĐT: " + user.getPhone());

            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(choose_trip_success.this, MainActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    finish();
                }
            });
        }
    }
}
