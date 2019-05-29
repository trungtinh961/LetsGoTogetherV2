package com.example.letsgotogetherv2.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letsgotogetherv2.R;
import com.example.letsgotogetherv2.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
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
    public FirebaseUser currentUser;

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
        //tvManageTrip = (TextView) view.findViewById(R.id.user_tvManage);
        tvChangePass = (TextView) view.findViewById(R.id.user_tvChangePassword);
       // tvResponse   = (TextView) view.findViewById(R.id.user_tvResponse);

        //--------------------------- Lấy thông tin user --------------------------------------------
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        userID = currentUser.getUid();

        mRef  = db.collection("users").document(userID);
        mRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        user = documentSnapshot.toObject(User.class);

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

        //----------------------- Đổi mật khẩu --------------------------------------

        tvChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogResetPass();
            }
        });


        //-----------------------------------------------------------------------
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (currentUser != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + currentUser.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                }
            }
        };

        return view;
    }

    public void dialogResetPass(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.change_password);
        dialog.setCanceledOnTouchOutside(false);

        final EditText edtOld       = dialog.findViewById(R.id.change_edtOldPass);
        final EditText edtNew       = dialog.findViewById(R.id.change_edtNewPass);
        final EditText edtConfirm   = dialog.findViewById(R.id.change_edtConfirm);
        Button btnCancel            = dialog.findViewById(R.id.change_btnCancel);
        Button btnOK                = dialog.findViewById(R.id.change_btnOK);

        dialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edtOld.getText().toString().equals("") && !edtNew.getText().toString().equals("") && !edtConfirm.getText().toString().equals("") && edtNew.getText().toString().equals(edtConfirm.getText().toString())){
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),edtConfirm.getText().toString());
                    currentUser.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    currentUser.updatePassword(edtConfirm.getText().toString())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(getContext(), "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                                                        dialog.dismiss();
                                                    } else {
                                                        Toast.makeText(getContext(), "Đổi mật khẩu thất bại!", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });
                                }
                            });
                } else{
                    Toast.makeText(getActivity(), "Mật khẩu mới chưa trùng khớp!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
