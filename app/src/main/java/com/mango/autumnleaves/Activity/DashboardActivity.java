package com.mango.autumnleaves.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonArray;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.User;
import com.mango.autumnleaves.remote.Koneksi;
import com.mango.autumnleaves.remote.Volley;
import com.mango.autumnleaves.util.SessionManager;
import com.mango.autumnleaves.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {

    private ImageView imvPresensi ,imvJadwal,imvHistory,imvProfile;
    TextView dshUsername;
    String getid,getUsername;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        //Menu
        imvPresensi = findViewById(R.id.presensi);
        imvJadwal = findViewById(R.id.jadwal);
        imvHistory = findViewById(R.id.history);
        imvProfile = findViewById(R.id.profile);

        //Content
        dshUsername = findViewById(R.id.dashUsername);


        //Intent Menu Presensi
        imvPresensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent presensi = new Intent(DashboardActivity.this,PresensiActivity.class);
                startActivity(presensi);
            }
        });

        //intent menu jadwal
        imvJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jadwal = new Intent(DashboardActivity.this,JadwalActivity.class);
                startActivity(jadwal);
            }
        });

        //intent Menu History
        imvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent history = new Intent(DashboardActivity.this,HistoryActivity.class);
                startActivity(history);
            }
        });

        //intent Menu profile
        imvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(DashboardActivity.this,ProfileActivity.class);
                startActivity(profile);
            }
        });

    }


}

