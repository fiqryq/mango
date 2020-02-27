package com.mango.autumnleaves.beacon;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.model.Presensi;
import com.mango.autumnleaves.remote.Koneksi;
import com.mango.autumnleaves.remote.Volley;
import com.mango.autumnleaves.util.EstimoteUtils;
import com.mango.autumnleaves.util.Util;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProximityContentAdapter extends BaseAdapter {

    private AppCompatActivity activityAlert;
    private Context context;
    private String getwaktu , gettanggal ,getjam;
    private String mKelas , mMatkul;
    String getid;
    private String hari, timeNow;

    public ProximityContentAdapter(Context context) {
        this.context = context;
    }

    private List<ProximityContent> nearbyContent = new ArrayList<>();


    public void setNearbyContent(List<ProximityContent> nearbyContent) {
        this.nearbyContent = nearbyContent;
    }

    // Jangan lupa manggil ini
    public void setActivityAlert(AppCompatActivity activityAlert) {
        this.activityAlert = activityAlert;
    }

    @Override
    public int getCount() {
        return nearbyContent.size();
    }

    @Override
    public Object getItem(int position) {
        return nearbyContent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate Layout presensi untuk beacon
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.presensi_content_beacon, parent, false);

        }

        TextView kelas = convertView.findViewById(R.id.beacon_kelas);
        TextView matakuliah = convertView.findViewById(R.id.beacon_matakuliah);
        TextView waktu = convertView.findViewById(R.id.tVwaktu);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.jadwal_jumat, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("jumat");
                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Jadwal data = new Jadwal();
                                data.setId(jsonObject.getInt("id"));
                                data.setHari(jsonObject.getString("hari"));
                                data.setMatakuliah(jsonObject.getString("matakuliah"));
                                data.setDosen(jsonObject.getString("dosen"));
                                data.setRuangan(jsonObject.getString("ruangan"));
                                data.setWaktu(jsonObject.getString("waktu"));
                                data.setWaktu_selesai(jsonObject.getString("waktu_selesai"));

                                int id = jsonObject.getInt("id");
                                String dtHari = jsonObject.getString("hari");
                                String dtMatkul = jsonObject.getString("matakuliah");
                                String dosen = jsonObject.getString("dosen");
                                String ruangan = jsonObject.getString("ruangan");
                                String waktu = jsonObject.getString("waktu");
                                String waktu_selesai = jsonObject.getString("waktu_selesai");

                                // Waktu
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

                                mMatkul = data.getMatakuliah();
                                mKelas = data.getRuangan();

                                kelas.setText(ruangan);
                                matakuliah.setText(dtMatkul);

//                                Log.d("hari",mData);
                                Log.d("hari2",hari);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("json",response.toString());

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.getInstance().addToRequestQueue(jsonObjectRequest);

        ProximityContent content = nearbyContent.get(position);

//        kelas.setText(content.getRuangan());
//        matakuliah.setText(content.getMatakuliah());

        // Get Waktu Dari Method Untuk Di tampilkan DI card
        // Waktu Terpisah dari model
        waktu();
        waktu.setText(getwaktu);

        // Test Button
        Button presensiButton = convertView.findViewById(R.id.button_presensi);
        presensiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProximityContent data = nearbyContent.get(position);
                getid = Util.getData("account", "id", context);
                Log.d("getid",getid);
                Log.d("matakuliah",content.getMatakuliah());


                StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.presensi_post, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Respone", response);
                        DynamicToast.makeSuccess(context,"Presensi Berhasil").show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DynamicToast.makeError(context,"Gagal presensi").show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id_mahasiswa", getid);
                        params.put("waktu", getwaktu);
                        params.put("tanggal", gettanggal);
                        params.put("ruangan", mKelas);
                        params.put("matakuliah", mMatkul);
                        return params;
                    }
                };
                Volley.getInstance().addToRequestQueue(stringRequest);
            }
        });
        return convertView;
    }

    // Method Get Waktu
    public void waktu(){

        Calendar calendar = Calendar.getInstance();
        DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat time = new SimpleDateFormat("kk:mm");
        DateFormat jam = new SimpleDateFormat("kk");
        DateFormat bulan = new SimpleDateFormat("M");

        getwaktu = time.format(calendar.getTime());
        gettanggal = date.format(calendar.getTime());
        getjam = jam.format(calendar.getTime());

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
