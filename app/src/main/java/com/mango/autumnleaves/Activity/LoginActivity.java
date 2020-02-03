package com.mango.autumnleaves.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.mango.autumnleaves.MainActivity;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.remote.Koneksi;
import com.mango.autumnleaves.util.SessionManager;
import com.mango.autumnleaves.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText username,password;
    private Button btLogin,btRegister;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(this);

        username = findViewById(R.id.tvUsername);
        password = findViewById(R.id.password);
        btLogin = findViewById(R.id.button_login);
        btRegister = findViewById(R.id.btRegister);
        btRegister.setVisibility(View.GONE);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUsername = username.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                if (!mUsername.isEmpty() || !mPassword.isEmpty()){
                    Login(mUsername,mPassword);
                } else {
                    username.setError("Harap isi Username");
                    password.setError("Harap isi Password");
                }
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(register);
            }
        });

    }

    private void Login(String username, String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("success");
//                            JSONArray jsonArray = jsonObject.getJSONArray("login");
//
//                            if (success.equals("1")) {
//
//                                for (int i = 0; i < jsonArray.length(); i++) {
//
//                                    JSONObject object = jsonArray.getJSONObject(i);
//
//                                    String nama = object.getString("nama").trim();
//                                    String email = object.getString("email").trim();
//                                    String id = object.getString("id").trim();

                                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
//                                    intent.putExtra("name", nama);
//                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                    finish();


                                }

//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(LoginActivity.this, "Error " +e.toString(), Toast.LENGTH_SHORT).show();
//                        }

//                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,"Error" + error.toString(),Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> user = new HashMap<>();
                user.put("username",username);
                user.put("password",password);
                return super.getParams();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

