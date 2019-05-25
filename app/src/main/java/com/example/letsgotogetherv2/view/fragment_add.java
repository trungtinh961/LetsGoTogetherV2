package com.example.letsgotogetherv2.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letsgotogetherv2.R;
import com.example.letsgotogetherv2.model.Trip;
import com.example.letsgotogetherv2.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_add extends Fragment {

    EditText edtFrom, edtTo;
    TextView tvDate, tvTime;
    Button btnAdd;
    RadioButton rdCustomer, rdDriver;
    RadioGroup radioGroup;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String userID = user.getUid();
    User curUser;
    int size;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference mRef;
    Task<Void> nRef;

    public fragment_add() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        edtFrom     = (EditText)    view.findViewById(R.id.add_edtFrom);
        edtTo       = (EditText)    view.findViewById(R.id.add_edtTo);
        tvDate      = (TextView)    view.findViewById(R.id.add_edtDate);
        tvTime      = (TextView)    view.findViewById(R.id.add_edtTime);
        btnAdd      = (Button)      view.findViewById(R.id.add_btnAdd);
        rdCustomer  = (RadioButton) view.findViewById(R.id.add_rdCustomer);
        rdDriver    = (RadioButton) view.findViewById(R.id.add_rdDriver);
        radioGroup  = (RadioGroup)  view.findViewById(R.id.add_radioGroup);


        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimePicker datePickerDialog =  new dateTimePicker();
                datePickerDialog.chooseDate(getActivity(), tvDate);
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimePicker dateTimePickerDialog = new dateTimePicker();
                dateTimePickerDialog.chooseTime(getActivity(), tvTime);
            }
        });



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRef  = db.collection("users").document(userID);
                mRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        curUser = documentSnapshot.toObject(User.class);
                        if (curUser.getTripArrayList().isEmpty()){
                            String from = edtFrom.getText().toString();
                            String to   = edtTo.getText().toString();
                            String date = tvDate.getText().toString();
                            String time = tvTime.getText().toString();

                            if (!from.equals("") && !to.equals("") && !date.equals("Chọn ngày") && !time.equals("Chọn giờ")
                                    && (rdDriver.isChecked() || rdCustomer.isChecked())){
                                // Kiểm tra các trường đã được nhập đầy đủ chưa

                                mRef = db.collection("trips").document();
                                String tripID = mRef.getId();
                                Trip trip = (new Trip(userID,tripID,from,to,date,time,rdDriver.isChecked()));

                                mRef.set(trip);

                                Log.d("userID", trip.getUserID()+"");
                                Log.d("tripID", trip.getTripID()+"");

                                /* Thêm tripID vào current User */
                                nRef = db.collection("users").document(userID)
                                        .update("tripArrayList", FieldValue.arrayUnion(tripID));

                                Toast.makeText(getActivity(), "Thêm chuyến thành công!", Toast.LENGTH_SHORT).show();

                                /* Refresh */
                                edtFrom.setText("");
                                edtTo.setText("");
                                tvDate.setText("Chọn ngày");
                                tvTime.setText("Chọn giờ");
                            } else {
                                Toast.makeText(getActivity(), "Điền đầy đủ bạn ơi! ^^", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Bạn đã tạo một chuyến, thực hiện nó trước khi tạo chuyến mới bạn nhé!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }
}
