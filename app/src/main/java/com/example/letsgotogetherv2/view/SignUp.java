package com.example.letsgotogetherv2.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letsgotogetherv2.R;
import com.example.letsgotogetherv2.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {

    private ActionBar toolbar;

    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference mRef;

    ProgressDialog progressDialog;
    boolean showPassFlag = false;
    TextView tvSignIn, tvShowPass;
    EditText edtName, edtPhone, edtMail, edtAddress, edtPass, edtConfirmPass;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mapping();

        toolbar.setTitle("Đăng kí");

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        tvShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showPassFlag == false){
                    edtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edtConfirmPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPassFlag = true;
                } else {
                    edtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edtConfirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPassFlag = false;
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }

        });
    }

    private void mapping(){
        tvSignIn        = (TextView) findViewById(R.id.signup_tvSignIn);
        tvShowPass      = (TextView) findViewById(R.id.signup_tvShowPassword);
        btnSignUp       = (Button)   findViewById(R.id.signup_btnSignUp);
        edtName         = (EditText) findViewById(R.id.signup_edtName);
        edtPhone        = (EditText) findViewById(R.id.signup_edtPhone);
        edtMail         = (EditText) findViewById(R.id.signup_edtEmail);
        edtAddress      = (EditText) findViewById(R.id.signup_edtAddress);
        edtPass         = (EditText) findViewById(R.id.signup_edtPassword);
        edtConfirmPass  = (EditText) findViewById(R.id.signup_edtConfirmPassword);
        toolbar         = getSupportActionBar();
        progressDialog  = new ProgressDialog(this);
        mAuth           = FirebaseAuth.getInstance();
    }

    private void startRegister() {
        progressDialog.setMessage("Đang đăng kí...");
        progressDialog.show();

        final String name         = edtName.getText().toString();
        final String phone        = edtPhone.getText().toString();
        final String email        = edtMail.getText().toString();
        final String address      = edtAddress.getText().toString();
        String pass               = edtPass.getText().toString();
        String confirmPass        = edtConfirmPass.getText().toString();

        if(!name.equals("") && !phone.equals("") && !email.equals("") && !address.equals("")
                    && !pass.equals("") && !confirmPass.equals("")){
            if (pass.equals(confirmPass)){
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    String uid = mAuth.getCurrentUser().getUid();

                                    db = FirebaseFirestore.getInstance();
                                    mRef = db.collection("users").document(uid);
                                    mRef.set(new User(name, email, phone, address));

                                    Toast.makeText(SignUp.this, "Đăng kí thành công!", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(SignUp.this, SignIn.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                } else{
                                    Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                return;
            }else{
                progressDialog.dismiss();
                Toast.makeText(this, "Mật khẩu chưa trùng khớp kìa bạn! ^^", Toast.LENGTH_SHORT).show();
                return;
            }
        }else{
            progressDialog.dismiss();
            Toast.makeText(this, "Điền đầy đủ thông tin dùm bạn ơi! ^^", Toast.LENGTH_SHORT).show();
            return;
        }
    }

}
