package com.mango.autumnleaves.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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
import com.mango.autumnleaves.util.Waktu;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class DashboardActivity extends AppCompatActivity {

    private ImageView imvPresensi ,imvJadwal,imvHistory, imvProfile;
//    private Button btPresensi;
    TextView dshUsername,tvWaktu;
    String getid,getruangan,gettanggal,getmatakuliah,getwaktu,getjam;

    private ProximityContentManager proximityContentManager;
    private ProximityContentAdapter proximityContentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        imvPresensi = findViewById(R.id.presensi);
        imvJadwal = findViewById(R.id.jadwal);
        imvHistory = findViewById(R.id.history);
        imvProfile = findViewById(R.id.profile);
        dshUsername = findViewById(R.id.dashUsername);
//        btPresensi = findViewById(R.id.button_presensi);
        GridView gridView = findViewById(R.id.gridView);

        intentPresensi();
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


        // Add Presensi
//        btPresensi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                presensi();
//            }
//        });

    }

    public void intentPresensi(){
        //Intent Menu Presensi
        imvPresensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent presensi = new Intent(DashboardActivity.this,PresensiActivity.class);
                startActivity(presensi);
            }
        });
    }
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
                try {
                    JSONArray jsonArray = response.getJSONArray("mahasiswa");

                    for (int i = 0; i <jsonArray.length() ; i++) {
                        // Object Mahasiswa
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        User data = new User();
                        data.setId_mahasiswa(jsonObject.getInt("id_mahasiswa"));
                        data.setNama(jsonObject.getString("nama"));
                        data.setUsername(jsonObject.getString("username"));
                        data.setPassword(jsonObject.getString("password"));
                        data.setTelp(jsonObject.getString("no_tlpn"));
                        data.setKelamin(jsonObject.getString("jenis_kelamin"));
                        data.setTtl(jsonObject.getString("tempat_tgl_lahir"));
                        data.setAlamat(jsonObject.getString("alamat"));

                        String id = String.valueOf(data.getId_mahasiswa());
                        if (getid.equals(id)){
                            dshUsername.setText(data.getNama());
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

    private void presensi(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.presensi_post, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                DynamicToast.makeSuccess(getApplicationContext(),"Presensi Berhasil");
//                try {
//                    JSONObject obj = new JSONObject(response);
//                    if (!obj.getBoolean("error")){
//
//                        DynamicToast.makeError(getApplicationContext(), obj.getString("message"));
//                        Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
//                        startActivity(intent);
//                    } else {
//
//                        DynamicToast.makeError(getApplicationContext(), obj.getString("message"));
//                        Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
//                        startActivity(intent);
//
//                    }
//
//                } catch (Exception e) {
//                    DynamicToast.makeError(getApplicationContext(),e+toString());
//                    e.printStackTrace();
//                    finish();
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DynamicToast.makeError(getApplicationContext(),"Error");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id_mahasiswa", getid);
//                params.put("waktu", getwaktu);
//                params.put("tanggal", gettanggal);
                params.put("ruangan", getruangan);
                params.put("mata_kuliah", getmatakuliah);

                return params;
            }
        };

        Volley.getInstance().addToRequestQueue(stringRequest);
    }

}

