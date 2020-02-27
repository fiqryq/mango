package com.mango.autumnleaves.beacon;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.remote.Koneksi;
import com.mango.autumnleaves.remote.Volley;
import com.mango.autumnleaves.util.EstimoteUtils;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ProximityContentManager {
    private Context context;
    private ProximityContentAdapter proximityContentAdapter;
    private EstimoteCloudCredentials cloudCredentials;
    private ProximityObserver.Handler proximityObserverHandler;
    private String dataMatakuliah;
    private String hari, timeNow;

    public ProximityContentManager(Context context, ProximityContentAdapter proximityContentAdapter, EstimoteCloudCredentials cloudCredentials) {
        this.context = context;
        this.proximityContentAdapter = proximityContentAdapter;
        this.cloudCredentials = cloudCredentials;
    }

    public void start() {
        ProximityObserver proximityObserver = new ProximityObserverBuilder(context, cloudCredentials)
                .onError(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Log.e("app", "proximity observer error: " + throwable);
                        return null;
                    }
                })
                .withBalancedPowerMode()
                .build();

        ProximityZone zone = new ProximityZoneBuilder()
                .forTag("kelas")
                .inCustomRange(1.0)
                .onContextChange(new Function1<Set<? extends ProximityZoneContext>, Unit>() {
                    @Override
                    public Unit invoke(Set<? extends ProximityZoneContext> contexts) {
                        List<ProximityContent> nearbyContent = new ArrayList<>(contexts.size());

                        for (ProximityZoneContext proximityContext : contexts) {
                            // Ngodingnya di sini
                            String kelas = proximityContext.getAttachments().get("kelas");
                            if (kelas == null) {
                                kelas = "unknown";
                            }
                            String matakuliah = EstimoteUtils.getShortIdentifier(proximityContext.getDeviceId());

                            nearbyContent.add(new ProximityContent(kelas, matakuliah));
                        }
                        proximityContentAdapter.setNearbyContent(nearbyContent);
                        proximityContentAdapter.notifyDataSetChanged();
                        return null;
                    }
                })
                .build();

        proximityObserverHandler = proximityObserver.startObserving(zone);
    }

    public void stop() {
        proximityObserverHandler.stop();
    }

    private void jadwal(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.jadwal_jumat, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("jumat");
                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Jadwal data = new Jadwal();
                                data.setHari(jsonObject.getString("hari"));
                                data.setMatakuliah(jsonObject.getString("matakuliah"));
                                data.setDosen(jsonObject.getString("dosen"));
                                data.setRuangan(jsonObject.getString("ruangan"));
                                data.setWaktu(jsonObject.getString("waktu"));
                                data.setWaktu_selesai(jsonObject.getString("waktu_selesai"));

                                getHari();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("json",response.toString());
                        Log.d("dataMatkul",dataMatakuliah);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.getInstance().addToRequestQueue(jsonObjectRequest);

        // Akhir Request

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

