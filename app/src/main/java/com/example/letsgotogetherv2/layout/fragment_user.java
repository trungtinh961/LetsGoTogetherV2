package com.example.letsgotogetherv2.layout;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letsgotogetherv2.R;
import com.example.letsgotogetherv2.model.Account;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_user extends Fragment {

    private static final String TAG = "ViewDatabase";

    public fragment_user() {
        // Required empty public constructor
    }

    private TextView tvName, tvPhone, tvEmail, tvAddress, tvManageTrip, tvChangePass, tvResponse;
    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;
    static public Account account;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_user, container, false);

        /* Mapping */
        tvName       = (TextView) view.findViewById(R.id.user_tvName);
        tvPhone      = (TextView) view.findViewById(R.id.user_tvPhone);
        tvEmail      = (TextView) view.findViewById(R.id.user_tvEmail);
        tvAddress    = (TextView) view.findViewById(R.id.user_tvAddress);
        tvManageTrip = (TextView) view.findViewById(R.id.user_tvManage);
        tvChangePass = (TextView) view.findViewById(R.id.user_tvChangePassword);
        tvResponse   = (TextView) view.findViewById(R.id.user_tvResponse);

        //declare the database reference object. This is what we use to access the database.
        //NOTE: Unless you are signed in, this will not be usable.


        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        myRef = mFirebaseDatabase.getReference().child("Users").child(userID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator );

                String name = map.get("Name");
                String address = map.get("Address");
                String phone = map.get("Phone");
                String email = map.get("Email");

                Log.d("VALUE","Name: "+ name);
                Log.d("VALUE","Email: "+ email);
                Log.d("VALUE","Phone: "+ phone);
                Log.d("VALUE","Address: "+ address);

                account = new Account(name,email,phone,address);

                tvName.setText(account.getName());
                tvEmail.setText(account.getEmail());
                tvPhone.setText(account.getPhone());
                tvAddress.setText(account.getAddress());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                }
            }
        };

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
