package com.mango.autumnleaves.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.remote.Koneksi;
import com.mango.autumnleaves.remote.Volley;
import com.mango.autumnleaves.util.SessionManager;
import com.mango.autumnleaves.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText username,password;
    private Button btLogin;
    String getusername, getpassword ;
    private ProgressDialog progressDialog;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        username = findViewById(R.id.tvUser);
        password = findViewById(R.id.etpassword);
        btLogin = findViewById(R.id.button_login);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getusername = username.getText().toString();
                getpassword = password.getText().toString();

                Log.e("user", "" +getusername);
                Log.e("pass", "" +getpassword);

                if (getusername.equals("")||getpassword.equals("")){
                    Util.toastShow(getApplicationContext(),"Harap Isi Form");
                } else {
                    login();
                }
            }
        });
    }

    public void login() {
        StringRequest request = new StringRequest(Request.Method.POST, Koneksi.login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", " " + response);
                        try {
                            JSONObject data = new JSONObject(response);
                            if (!data.getBoolean("error")) {
                                JSONObject account = data.getJSONObject("user");
                                String username = account.getString("username");
                                String pass = account.getString("password");
                                String id = account.getString("id");

//                                LOG
                                Log.e("account", "" + username);
                                Log.e("account", "" + pass);

                                Util.saveData("account", "username", getusername, getApplicationContext());
                                Util.saveData("account", "password", getpassword, getApplicationContext());
                                Util.saveData("account", "id", id, getApplicationContext());

                                progressDialog();
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();

                                Util.toastShow(getApplicationContext(), "Login Sukses");
                            } else {
                                Util.toastShow(getApplicationContext(), "Login gagal");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("error", "" + e);
                            Util.toastShow(getApplicationContext(), "Login Gagal");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "" + error);
                Util.toastShow(getApplicationContext(), "Login Gagal");
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", getusername);
                params.put("password", getpassword);
                return params;
            }
        };
        Volley.getInstance().addToRequestQueue(request);
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

