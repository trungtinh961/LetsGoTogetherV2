package com.example.letsgotogetherv2.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.letsgotogetherv2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_search extends Fragment {

    EditText edtFrom, edtTo;
    TextView edtDate;
    private FirebaseAuth mAuth;
    private  String userID;

    public fragment_search() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        edtFrom = (EditText) view.findViewById(R.id.search_edtFrom);
        edtTo   = (EditText) view.findViewById(R.id.search_edtTo);
        edtDate = (TextView) view.findViewById(R.id.search_edtDate);

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimePicker datePickerDialog =  new dateTimePicker();
                datePickerDialog.chooseDate(getActivity(),edtDate);
            }
        });

        return view;
    }

}
