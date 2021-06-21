package com.omar.bemyhart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Log_In_Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView btn_create_account,btn_reset_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        btn_create_account = findViewById(R.id.btn_Sign_up_move);
        btn_create_account.setOnClickListener(this);

        btn_reset_pass = findViewById(R.id.btn_forget_pass_move);
        btn_reset_pass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Sign_up_move:
                startActivity(new Intent(this,Sign_Up_Activity.class));
                break;

            case R.id.btn_forget_pass_move:
                break;
        }
    }
}