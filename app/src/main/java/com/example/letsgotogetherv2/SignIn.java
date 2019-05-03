package com.example.letsgotogetherv2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {

    TextView tvSignUp;
    Button btnLogin;
    EditText edtpassword, edtuserName;
    TextView showPass;
    CheckBox cbSave;
    private ActionBar toolbar;
    FirebaseAuth mAuth;
    DatabaseReference mDatebase;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    boolean showPassFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mapping();

        /* truy cập username và password đã lưu */
        sharedPreferences = getSharedPreferences("dataSignin",MODE_PRIVATE);
        edtuserName.setText(sharedPreferences.getString("email",""));
        edtpassword.setText(sharedPreferences.getString("password",""));
        cbSave.setChecked(sharedPreferences.getBoolean("cbCheck",false));

        toolbar = getSupportActionBar();
        toolbar.setTitle("Đăng nhập");

        /* Chuyển activity SignIn -> SignUp*/
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
            }
        });
        /* Chuyển activity SignIn -> Main nếu đăng nhập thành công*/
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Đang đăng nhập...");
                progressDialog.show();

                String email = edtuserName.getText().toString().trim();
                String password = edtpassword.getText().toString().trim();

                /* save username, password */
                if (cbSave.isChecked()){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email",email);
                    editor.putString("password",password);
                    editor.putBoolean("cbCheck",true);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("email");
                    editor.remove("password");
                    editor.remove("cbCheck");
                    editor.commit();
                }

                startLogin(email, password);
            }
        });

        /* Show Password */
        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showPassFlag == false){
                    edtpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPassFlag = true;
                } else {
                    edtpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPassFlag = false;
                }
            }
        });
    }

    private void startLogin(String email, String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // LogIn thành công, chuyển qua MainActivity
                            progressDialog.dismiss();
                            Toast.makeText(SignIn.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignIn.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void mapping() {
        cbSave          = (CheckBox)    findViewById(R.id.signin_cbSave);
        tvSignUp        = (TextView)    findViewById(R.id.signin_tvSignUp);
        btnLogin        = (Button)      findViewById(R.id.signin_btnSignin);
        edtpassword     = (EditText)    findViewById(R.id.signup_edtPassword);
        edtuserName     = (EditText)    findViewById(R.id.signin_edtUserName);
        showPass        = (TextView)    findViewById(R.id.signin_tvShowPassword);
        toolbar         = getSupportActionBar();
        progressDialog  = new ProgressDialog(this);
        mAuth           = FirebaseAuth.getInstance();
        mDatebase       = FirebaseDatabase.getInstance().getReference().child("Users");
    }
}
