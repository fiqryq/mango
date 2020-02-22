package com.mango.autumnleaves.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonArray;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.adapter.PresensiAdapter;
import com.mango.autumnleaves.beacon.ProximityContentAdapter;
import com.mango.autumnleaves.beacon.ProximityContentManager;
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

public class PresensiActivity extends AppCompatActivity {

    private ArrayList<Jadwal> arrayList;
    private RecyclerView recyclerView;

    private Button back;
    private TextView tvHariIni, tvMatkul, tvDosen, tvJam, tvRuangan;
    String hari, timeNow;

    private ProximityContentManager proximityContentManager;
    private ProximityContentAdapter proximityContentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presensi);

        // panggil ini
        proximityContentAdapter = new ProximityContentAdapter(this);
        proximityContentAdapter.setActivityAlert(PresensiActivity.this);

        tvHariIni = findViewById(R.id.tvHariIni);
        tvDosen = findViewById(R.id.tvDosen);
        tvJam = findViewById(R.id.tvJam);
        tvMatkul = findViewById(R.id.tvMatkul);
        tvRuangan = findViewById(R.id.tvHari);
        recyclerView = findViewById(R.id.rvJadwalView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();

        back = findViewById(R.id.button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(PresensiActivity.this, DashboardActivity.class);
                startActivity(back);
            }
        });

        getHari();
        getBulan();
        showJadwal();

        Log.d("TEST", "Berhasil");
    }

    private void getBulan() {
        Date date = Calendar.getInstance().getTime();
        String tanggal = (String) android.text.format.DateFormat.format("d", date);
        String mBulan = (String) android.text.format.DateFormat.format("M", date);
        String tahun = (String) DateFormat.format("yyyy", date);

        int month = Integer.parseInt(mBulan);
        String bulan = null;

        if (month == 1) {
            bulan = "Januari";
        } else if (month == 2) {
            bulan = "Februari";
        } else if (month == 3) {
            bulan = "Maret";
        } else if (month == 4) {
            bulan = "April";
        } else if (month == 5) {
            bulan = "Mei";
        } else if (month == 6) {
            bulan = "Juni";
        } else if (month == 7) {
            bulan = "Juli";
        } else if (month == 8) {
            bulan = "Agustus";
        } else if (month == 9) {
            bulan = "September";
        } else if (month == 10) {
            bulan = "Oktober";
        } else if (month == 11) {
            bulan = "November";
        } else if (month == 12) {
            bulan = "Desember";
        }

        String sekarang = hari + ", " + tanggal + " " + bulan + " " + tahun;
        tvHariIni.setText(String.valueOf(sekarang));
    }

    private void getHari() {
        Date dateNow = Calendar.getInstance().getTime();
        timeNow = (String) DateFormat.format("HH:mm", dateNow);
        hari = (String) DateFormat.format("EEEE", dateNow);
        if (hari.equalsIgnoreCase("sunday")) {
            hari = "Minggu";
        } else if (hari.equalsIgnoreCase("monday")) {
            hari = "Senin";
        } else if (hari.equalsIgnoreCase("tuesday")) {
            hari = "Selasa";
        } else if (hari.equalsIgnoreCase("wednesday")) {
            hari = "Rabu";
        } else if (hari.equalsIgnoreCase("thursday")) {
            hari = "Kamis";
        } else if (hari.equalsIgnoreCase("friday")) {
            hari = "Jumat";
        } else if (hari.equalsIgnoreCase("saturday")) {
            hari = "Sabtu";
        }
    }

    private void showJadwal() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.jadwal, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Response Json to array
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
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DynamicToast.makeError(getApplicationContext(), "Error" + error);
            }
        });
        Volley.getInstance().addToRequestQueue(jsonObjectRequest);
    }
    // Set Adapter
    private void setuprecyclerView(ArrayList<Jadwal> arrayList) {
        PresensiAdapter presensiAdapter = new PresensiAdapter(this,arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(presensiAdapter);
    }
}
