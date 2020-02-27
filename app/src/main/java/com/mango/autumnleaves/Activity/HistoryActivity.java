package com.mango.autumnleaves.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.adapter.HistoryAdapter;
import com.mango.autumnleaves.adapter.JadwalAdapter;
import com.mango.autumnleaves.model.History;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.remote.Koneksi;
import com.mango.autumnleaves.remote.Volley;
import com.mango.autumnleaves.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ArrayList<History> arrayList;
    RecyclerView recyclerView;
    private String getid;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        progressBar = findViewById(R.id.progressBarHistory);
        recyclerView = findViewById(R.id.rvHistory);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();


        getid = Util.getData("account", "id", getApplicationContext());
        progressBar.setVisibility(View.VISIBLE);
        getHistory();

    }

    private void getHistory() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.presensi, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = response.getJSONArray("presensi");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                History history = new History();
                                history.setId_presensi(jsonObject.getInt("id_presensi"));
                                history.setId_mahasiswa(jsonObject.getInt("id_mahasiswa"));
                                history.setRuangan(jsonObject.getString("ruangan"));
                                history.setTanggal(jsonObject.getString("tanggal"));
                                history.setWaktu(jsonObject.getString("waktu"));
                                history.setMatakuliah(jsonObject.getString("matakuliah"));

                                String id = String.valueOf(history.getId_mahasiswa());

                                if (getid.equals(id)){
                                    arrayList.add(history);
                                }

                                setuprecyclerview(arrayList);
                            }
                        } catch (JSONException e) {
                                e.printStackTrace();
                        }

//                        Log.d("jsonhistory", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    private void setuprecyclerview(ArrayList<History> arrayList) {
        HistoryAdapter historyAdapter = new HistoryAdapter(this,arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(historyAdapter);
    }
}
