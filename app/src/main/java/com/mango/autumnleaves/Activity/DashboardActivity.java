package com.mango.autumnleaves.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.beacon.ProximityContentAdapter;
import com.mango.autumnleaves.beacon.ProximityContentManager;
import com.mango.autumnleaves.model.User;
import com.mango.autumnleaves.remote.Koneksi;
import com.mango.autumnleaves.remote.Volley;
import com.mango.autumnleaves.util.Util;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class DashboardActivity extends AppCompatActivity {

    private ImageView imvPresensi ,imvJadwal,imvHistory, imvProfile;
    private TextView dshUsername,dshNim;
    private String getid,getmatakuliah;
    private ImageView dashImg;
    private ProgressBar progressBar;

    // nitip
    private TextView tvHariIni, tvMatkul, tvDosen, tvJam, tvRuangan;
    private String hari, timeNow;

    private ProximityContentManager proximityContentManager;
    private ProximityContentAdapter proximityContentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        progressBar = findViewById(R.id.progressBarImg);
//        imvPresensi = findViewById(R.id.presensi);
        imvJadwal = findViewById(R.id.jadwal);
        imvHistory = findViewById(R.id.history);
        imvProfile = findViewById(R.id.profile);
        dshUsername = findViewById(R.id.dashUsername);
        dshNim = findViewById(R.id.dashNim);
        dashImg = findViewById(R.id.dashIgm);
        GridView gridView = findViewById(R.id.gridView);

//        intentPresensi();
        intentJadwal();
        intentHistory();
        intentProfile();


        Intent data = getIntent();
        getmatakuliah = data.getStringExtra("data");

        getid = Util.getData("account", "id", getApplicationContext());
        getprofile();

        proximityContentAdapter = new ProximityContentAdapter(this);

        gridView.setAdapter(proximityContentAdapter);
        getEstimote();
        progressBar.setVisibility(View.VISIBLE);

    }

//    public void intentPresensi(){
//        //Intent Menu Presensi
//        imvPresensi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent presensi = new Intent(DashboardActivity.this, CalendarActivity.class);
//                startActivity(presensi);
//            }
//        });
//    }
    public void intentJadwal(){
        //intent menu jadwal
        imvJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jadwal = new Intent(DashboardActivity.this,JadwalActivity.class);
                startActivity(jadwal);
            }
        });
    }
    public void intentHistory(){
        //intent Menu History
        imvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent history = new Intent(DashboardActivity.this,HistoryActivity.class);
                startActivity(history);
            }
        });

    }
    public void intentProfile(){
        //intent Menu profile
        imvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(DashboardActivity.this,ProfileActivity.class);
                startActivity(profile);
            }
        });
    }

    private void getprofile(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.mahasiswa_profil, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray = response.getJSONArray("mahasiswa");

                    for (int i = 0; i <jsonArray.length() ; i++) {
                        // Object Mahasiswa
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        User data = new User();
                        data.setId_mahasiswa(jsonObject.getInt("id_mahasiswa"));
                        data.setNim_mhs(jsonObject.getString("nim_mhs"));
                        data.setNama(jsonObject.getString("nama"));
                        data.setUsername(jsonObject.getString("username"));
                        data.setPassword(jsonObject.getString("password"));
                        data.setTelp(jsonObject.getString("no_tlpn"));
                        data.setKelamin(jsonObject.getString("jenis_kelamin"));
                        data.setTtl(jsonObject.getString("tempat_tgl_lahir"));
                        data.setAlamat(jsonObject.getString("alamat"));
                        data.setKode_kelas(jsonObject.getString("kode_kelas"));
                        data.setJurusan(jsonObject.getString("jurusan"));
                        data.setGambar(jsonObject.getString("gambar"));

                        String id = String.valueOf(data.getId_mahasiswa());
                        if (getid.equals(id)){
                            dshUsername.setText(data.getNama());
                            dshNim.setText(data.getNim_mhs());
                            Picasso.get().load(data.getGambar()).into(dashImg);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error" + e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public void getEstimote(){

        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(this,
                        new Function0<Unit>() {
                            @Override
                            public Unit invoke() {
                                Log.d("app", "requirements fulfilled");
                                startProximityContentManager();
                                return null;
                            }
                        },

                        new Function1<List<? extends Requirement>, Unit>() {
                            @Override
                            public Unit invoke(List<? extends Requirement> requirements) {
                                Log.e("app", "requirements missing: " + requirements);
                                return null;
                            }
                        },

                        new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Log.e("app", "requirements error: " + throwable);
                                return null;
                            }
                        });
    }

    private void startProximityContentManager() {
        EstimoteCloudCredentials cloudCredentials = new EstimoteCloudCredentials("mango-master-2zw", "2501de53cda0da86930e7f9650032f0d");
        proximityContentManager = new ProximityContentManager(this, proximityContentAdapter, cloudCredentials);
        proximityContentManager.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (proximityContentManager != null)
            proximityContentManager.stop();
    }

    // Untuk Get Tanggal Hari ini
    private void getBulan() {
        Date date = Calendar.getInstance().getTime();
        String tanggal = (String) android.text.format.DateFormat.format("d", date);
        String mBulan = (String) android.text.format.DateFormat.format("M", date);
        String tahun = (String) android.text.format.DateFormat.format("yyyy", date);

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
        timeNow = (String) android.text.format.DateFormat.format("HH:mm", dateNow);
        hari = (String) DateFormat.format("EEEE", dateNow);
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

