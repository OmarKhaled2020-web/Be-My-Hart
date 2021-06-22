package com.omar.bemyhart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class Log_In_Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView btn_create_account,btn_reset_pass;
    private TextInputLayout inputEmail,inputPass;
    private Button btn_LogIn;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        auth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.TextInputEmail_login);
        inputPass = findViewById(R.id.textInputPass_login);

        btn_create_account = findViewById(R.id.btn_Sign_up_move);
        btn_create_account.setOnClickListener(this);

        btn_reset_pass = findViewById(R.id.btn_forget_pass_move);
        btn_reset_pass.setOnClickListener(this);

        btn_LogIn = findViewById(R.id.btn_login);
        btn_LogIn.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Sign_up_move:
                startActivity(new Intent(this, Sign_Up_Profile_Activity.class));
                break;

            case R.id.btn_login:
                LogInMethod();
                break;
        }
    }

    private void LogInMethod() {

        String email = inputEmail.getEditText().getText().toString().trim();
        String pass = inputPass.getEditText().getText().toString().trim();

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
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    startActivity(new Intent(Log_In_Activity.this,MainActivity.class));
                    finish();
                }else
                    progressDialog.dismiss();
                    Toast.makeText(Log_In_Activity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ShowError(TextInputLayout layout, String error){
        layout.setError(error);
        layout.requestFocus();
    }
}