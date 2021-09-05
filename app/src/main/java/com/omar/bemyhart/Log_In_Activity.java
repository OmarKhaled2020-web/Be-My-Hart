package com.omar.bemyhart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Log_In_Activity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout inputEmail,inputPass;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        setDefaultTheme();

        Initializing();
    }

    private void Initializing(){
        auth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.TextInputEmail_login);
        Objects.requireNonNull(inputEmail.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                inputEmail.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputPass = findViewById(R.id.textInputPass_login);
        Objects.requireNonNull(inputPass.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                inputPass.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        TextView btn_create_account = findViewById(R.id.btn_Sign_up_move);
        btn_create_account.setOnClickListener(this);

        TextView btn_reset_pass = findViewById(R.id.btn_forget_pass_move);
        btn_reset_pass.setOnClickListener(this);

        Button btn_LogIn = findViewById(R.id.btn_login);
        btn_LogIn.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

    private void setDefaultTheme(){
        Set_Default_Theme theme = new Set_Default_Theme(this);
        theme.setDefaultTheme();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Sign_up_move:
                startActivity(new Intent(this, Sign_Up_Activity.class));
                break;

            case R.id.btn_login:
                LogInMethod();
                break;
        }
    }

    private void LogInMethod() {

        String email = Objects.requireNonNull(inputEmail.getEditText()).getText().toString().trim();
        String pass = Objects.requireNonNull(inputPass.getEditText()).getText().toString().trim();

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

        progressDialog.setTitle("Registration");
        progressDialog.setMessage("Please wait, While complete your Log In");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()){
                    startActivity(new Intent(Log_In_Activity.this,MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(Log_In_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void ShowError(TextInputLayout layout, String error){
        layout.setError(error);
        layout.requestFocus();
    }
}