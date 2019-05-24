package com.example.letsgotogetherv2.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.letsgotogetherv2.R;
import com.example.letsgotogetherv2.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private  String userID;
    public User user;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference mRef;

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
       // tvResponse   = (TextView) view.findViewById(R.id.user_tvResponse);

        //-----------------------------------------------------------------------
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mRef  = db.collection("users").document(userID);
        mRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        User user = documentSnapshot.toObject(User.class);

                        if (user != null) {
                            Log.d("VALUE", "Name: " + user.getName());
                            Log.d("VALUE", "Email: " + user.getEmail());
                            Log.d("VALUE", "Phone: " + user.getPhone());
                            Log.d("VALUE", "Address: " + user.getAddress());
                            Log.d("VALUE", "Listsize" + user.getTripArrayList().size());


                            tvName.setText(user.getName());
                            tvEmail.setText(user.getEmail());
                            tvPhone.setText(user.getPhone());
                            tvAddress.setText(user.getAddress());
                        } else {
                            Log.d("VALUE", "NULL");
                        }

                    }
                });

        //-----------------------------------------------------------------------

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
