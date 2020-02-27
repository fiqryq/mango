package com.mango.autumnleaves.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.adapter.JadwalAdapter;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.remote.Koneksi;
import com.mango.autumnleaves.remote.Volley;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class JadwalActivity extends AppCompatActivity {

    private ArrayList<Jadwal> arrayList;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private String hari, timeNow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.rvJadwalView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();

        showJadwal();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showJadwal() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.jadwal, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = response.getJSONArray("jadwal_kuliah");
                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Jadwal data = new Jadwal();
                                data.setHari(jsonObject.getString("hari"));
                                data.setMatakuliah(jsonObject.getString("matakuliah"));
                                data.setDosen(jsonObject.getString("dosen"));
                                data.setRuangan(jsonObject.getString("ruangan"));
                                data.setWaktu(jsonObject.getString("waktu"));
                                data.setWaktu_selesai(jsonObject.getString("waktu_selesai"));
                                arrayList.add(data);
                            }
                            Log.d("data2w",response.getJSONArray("jadwal").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setuprecyclerView(arrayList);
                        Log.d("json",response.toString());
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DynamicToast.makeError(getApplicationContext(), "Error" + error);
            }
        });
        Volley.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    private void setuprecyclerView(ArrayList<Jadwal> arrayList) {
        JadwalAdapter jadwalAdapter= new JadwalAdapter(this,arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(jadwalAdapter);
    }

    private void getHari() {
        Date dateNow = Calendar.getInstance().getTime();
        timeNow = (String) android.text.format.DateFormat.format("HH:mm", dateNow);
        hari = (String) android.text.format.DateFormat.format("EEEE", dateNow);

        if (hari.equalsIgnoreCase("sunday")) {
            hari = "minggu";
        } else if (hari.equalsIgnoreCase("monday")) {
            hari = "senin";
        } else if (hari.equalsIgnoreCase("tuesday")) {
            hari = "selasa";
        } else if (hari.equalsIgnoreCase("wednesday")) {
            hari = "rabu";
        } else if (hari.equalsIgnoreCase("thursday")) {
            hari = "kamis";
        } else if (hari.equalsIgnoreCase("friday")) {
            hari = "jumat";
        } else if (hari.equalsIgnoreCase("saturday")) {
            hari = "sabtu";
        }
    }
}

