package com.mango.autumnleaves.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.remote.Koneksi;
import com.mango.autumnleaves.remote.Volley;
import com.mango.autumnleaves.util.Session;
import com.mango.autumnleaves.util.Util;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText username,password;
    private Button btLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private Session mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //session
        //Jika Session login (True) maka akan langsung intent ke dashoard
        mSession = new Session(getApplicationContext());
        if (mSession.loggedIn()) {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            finish();
        }

        firebaseAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.tvUser);
        password = findViewById(R.id.etpassword);
        btLogin = findViewById(R.id.button_login);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog();
                login();
            }
        });
    }

    private void login(){
        String mUsername = username.getText().toString().trim();
        String mPassword = password.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(mUsername,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    mSession.setLoggedin(true);
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(LoginActivity.this,"Berhasil Login",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this,"gagal",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void progressDialog(){
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    public void onBackPressed() {
        progressDialog.dismiss();
    }

}