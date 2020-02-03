package com.mango.autumnleaves.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mango.autumnleaves.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText regUsername,regEmail,regPassword;
    private Button btRegis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regUsername = findViewById(R.id.regUsername);
        regEmail = findViewById(R.id.regEmail);
        regPassword = findViewById(R.id.regPassword);
        btRegis = findViewById(R.id.regButton);


        btRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void regis(){
        final String nama = this.regUsername.getText().toString().trim();
        final String email = this.regEmail.getText().toString().trim();
        final String Password = this.regPassword.getText().toString().trim();
    }
}
