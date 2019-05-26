package com.example.letsgotogetherv2.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.letsgotogetherv2.R;
import com.example.letsgotogetherv2.adapter.tripAdapter;
import com.example.letsgotogetherv2.model.Trip;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.CollationElementIterator;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_home extends Fragment {

    public fragment_home() {
        // Required empty public constructor
    }
    public View view;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference mRef = db.collection("trips");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);

        initView();
        return view;
    }

    public void initView() {
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.home_rcHome);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        //----------------------
        final ArrayList<Trip> arrayList = new ArrayList<>();
        mRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshots: queryDocumentSnapshots){
                    Trip trip = documentSnapshots.toObject(Trip.class);

                    if (trip.getChoose() == false)
                    {
                        arrayList.add(trip);
                    }
                }
                tripAdapter tripAdapter = new tripAdapter(arrayList,getContext());
                recyclerView.setAdapter(tripAdapter);


            }
        });

        //----------------------

    }

}
