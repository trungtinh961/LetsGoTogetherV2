package com.example.letsgotogetherv2.layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.letsgotogetherv2.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_add extends Fragment {

    EditText edtFrom, edtTo;
    TextView edtDate, edtTime;

    public fragment_add() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        edtFrom = (EditText) view.findViewById(R.id.add_edtFrom);
        edtTo   = (EditText) view.findViewById(R.id.add_edtTo);
        edtDate = (TextView) view.findViewById(R.id.add_edtDate);
        edtTime = (TextView) view.findViewById(R.id.add_edtTime);

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimePicker datePickerDialog =  new dateTimePicker();
                datePickerDialog.chooseDate(getActivity(),edtDate);
            }
        });

        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimePicker dateTimePickerDialog = new dateTimePicker();
                dateTimePickerDialog.chooseTime(getActivity(),edtTime);
            }
        });
        return view;
    }
}
