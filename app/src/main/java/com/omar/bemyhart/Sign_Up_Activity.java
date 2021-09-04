package com.omar.bemyhart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_Up_Activity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout userName, inputEmail, inputPass, inputConfirmPass;
    private ProgressDialog progressDialog;

    private FirebaseAuth auth;
    private boolean state_registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

        userName = findViewById(R.id.TextInputUserName_signup);
        inputEmail = findViewById(R.id.TextInputEmail_signup);
        inputPass = findViewById(R.id.textInputPass_signup);
        inputConfirmPass = findViewById(R.id.textInputPass2_signup);

        TextView btn_already_have_account = findViewById(R.id.btn_Login_return);
        btn_already_have_account.setOnClickListener(this);

        Button btn_Register = findViewById(R.id.btn_Sign_up);
        btn_Register.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_Login_return:
                super.onBackPressed();
                break;

            case R.id.btn_Sign_up:
                RegisterMethod();
                break;
        }
    }

    private void RegisterMethod() {

        String name = userName.getEditText().getText().toString().trim();
        String email = inputEmail.getEditText().getText().toString().trim();
        String pass = inputPass.getEditText().getText().toString().trim();
        String confirmPass = inputConfirmPass.getEditText().getText().toString().trim();

        if (name.isEmpty()){
            ShowError(userName,getString(R.string.UserNameRequire));
            return;
        }

        if (email.isEmpty()){
            ShowError(inputEmail,"Email is Require!");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ShowError(inputEmail,"Email is Valid!");
            return;
        }

        if (pass.isEmpty()){
            ShowError(inputPass,"Password is Require!");
            return;
        }

        if (pass.length() < 6){
            ShowError(inputPass,"Min Password length is 6 character!");
            return;
        }

        if (confirmPass.isEmpty()){
            ShowError(inputConfirmPass,"You should Confirm your Password!");
            return;
        }

        if (pass == confirmPass){
            ShowError(inputConfirmPass,"Password did not Matched!");
            return;
        }

        progressDialog.setTitle("Registration");
        progressDialog.setMessage("Please wait, While complete your registration");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    startActivity(new Intent(Sign_Up_Activity.this, Setup_Profile_Activity.class));
                    finish();
                } else {
                    state_registration = false;
                }
            }
        });

    }

    private void ShowError(TextInputLayout layout, String error){
        layout.setError(error);
        layout.requestFocus();
    }

}