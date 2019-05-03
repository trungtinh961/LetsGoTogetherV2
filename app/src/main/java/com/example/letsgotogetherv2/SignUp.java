package com.example.letsgotogetherv2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private ActionBar toolbar;
    FirebaseAuth mAuth;
    DatabaseReference mDatebase;
    ProgressDialog progressDialog;
    TextView edtSignIn;
    EditText edtName, edtPhone, edtMail, edtAddress, edtPass, edtConfirmPass;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mapping();

        toolbar.setTitle("Đăng kí");

        edtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
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
        edtSignIn       = (TextView) findViewById(R.id.signup_tvSignIn);
        edtName         = (EditText) findViewById(R.id.signup_edtName);
        edtPhone        = (EditText) findViewById(R.id.signup_edtPhone);
        edtMail         = (EditText) findViewById(R.id.signup_edtEmail);
        edtAddress      = (EditText) findViewById(R.id.signup_edtAddress);
        edtPass         = (EditText) findViewById(R.id.signup_edtPassword);
        edtConfirmPass  = (EditText) findViewById(R.id.signup_edtConfirmPassword);
        toolbar         = getSupportActionBar();
        progressDialog  = new ProgressDialog(this);
        mAuth           = FirebaseAuth.getInstance();
        mDatebase       = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    private void startRegister() {
        progressDialog.setMessage("Đang đăng kí...");
        progressDialog.show();

        String name         = edtName.getText().toString();
        String phone        = edtPhone.getText().toString();
        String mail         = edtMail.getText().toString();
        String address      = edtAddress.getText().toString();
        String pass         = edtPass.getText().toString();
        String confirmPass  = edtConfirmPass.getText().toString();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(mail) && !TextUtils.isEmpty(address)
                    && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirmPass)){
            if (pass.equals(confirmPass)){

                progressDialog.dismiss();
                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
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
