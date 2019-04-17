package com.example.letsgotogetherv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class SignIn extends AppCompatActivity {

    TextView tvSignUp;
    Button btnLogin;
    AutoCompleteTextView edtpassword;
    AutoCompleteTextView edtuserName;
    TextView showPass;
    CheckBox cbSave;
    private ActionBar toolbar;
    SharedPreferences sharedPreferences;
    boolean showPassFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Anhxa();

        /* truy cập username và password đã lưu */
        sharedPreferences = getSharedPreferences("dataSignin",MODE_PRIVATE);
        edtuserName.setText(sharedPreferences.getString("userName",""));
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
        /* Chuyển activity SignIn -> Main*/
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = edtuserName.getText().toString().trim();
                String password = edtpassword.getText().toString().trim();
                /* save username, password */
                if (cbSave.isChecked()){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userName",userName);
                    editor.putString("password",password);
                    editor.putBoolean("cbCheck",true);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("userName");
                    editor.remove("password");
                    editor.remove("cbCheck");
                    editor.commit();
                }

                Intent intent = new Intent(SignIn.this, MainActivity.class);
                startActivity(intent);
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

    private void Anhxa() {
        cbSave      = (CheckBox)                findViewById(R.id.signin_cbSave);
        tvSignUp    = (TextView)                findViewById(R.id.signin_tvSignUp);
        btnLogin    = (Button)                  findViewById(R.id.signin_btnSignin);
        edtpassword    = (AutoCompleteTextView)    findViewById(R.id.signin_edtPassword);
        edtuserName    = (AutoCompleteTextView)    findViewById(R.id.signin_edtUserName);
        showPass    = (TextView)                findViewById(R.id.signin_tvShowPassword);
    }
}
