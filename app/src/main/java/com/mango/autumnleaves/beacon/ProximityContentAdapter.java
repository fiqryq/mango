package com.mango.autumnleaves.beacon;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProximityContentAdapter extends BaseAdapter {

    private Context context;
    private String getjam;
    private String hari, waktusekarang;
    private String dataDosen,dataMatakuliah,dataWaktuMulai,dataWaktuSelesai,dataRuangan,dataNodata;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    public ProximityContentAdapter(Context context) {
        this.context = context;
    }

    private List<ProximityContent> nearbyContent = new ArrayList<>();

    public void setNearbyContent(List<ProximityContent> nearbyContent) {
        this.nearbyContent = nearbyContent;
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
        // Inisialisasi Di sini

        TextView kelas = convertView.findViewById(R.id.beacon_kelas);
        TextView idbeacon = convertView.findViewById(R.id.beacon_matakuliah);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // Set Content Beacon
        ProximityContent content = nearbyContent.get(position);
        kelas.setText(content.getKelas());
        idbeacon.setText(content.getIdbeacon());

        // Button Presensi
        Button Presensi = convertView.findViewById(R.id.button_presensi);
        Presensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Waktu
                Date date = Calendar.getInstance().getTime();
                String tanggal = (String) android.text.format.DateFormat.format("d",   date); // 20
                String monthNumber  = (String) android.text.format.DateFormat.format("M",   date); // 06
                String year         = (String) DateFormat.format("yyyy", date); // 2013
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat jam = new SimpleDateFormat("kk:mm");

                int month = Integer.parseInt(monthNumber);
                String bulan = null;

                if (month == 1){
                    bulan = "Januari";
                }else if (month == 2){
                    bulan = "Februari";
                }else if (month == 3){
                    bulan = "Maret";
                }else if (month == 4){
                    bulan = "April";
                }else if (month == 5){
                    bulan = "Mei";
                }else if (month == 6){
                    bulan = "Juni";
                }else if (month == 7){
                    bulan = "Juli";
                }else if (month == 8){
                    bulan = "Agustus";
                }else if (month == 9){
                    bulan = "September";
                }else if (month == 10){
                    bulan = "Oktober";
                }else if (month == 11){
                    bulan = "November";
                }else if (month == 12){
                    bulan = "Desember";
                }
                String formatWaktuFix = hari + ", "+tanggal+" "+bulan+" "+year;
                getjam = jam.format(calendar.getTime());

                String idUser;
                idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                // doccumentsnapshoot untuk mendapatkan dokumen user secara spesifik
                DocumentReference docRef = firebaseFirestore.collection("user").document(idUser);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                User user = new User();
                                user.setNama(document.getString("nama"));
                                user.setJurusan(document.getString("jurusan"));
                                user.setKode_kelas(document.getString("kode_kelas"));

                                // Doc Ref Dari user
                                String jurusanRef = user.getJurusan();
                                String kelasRef = user.getKode_kelas();
                                String nama_mhs = user.getNama();

                                getNamaHari();
                                firebaseFirestore
                                        .collection("prodi")
                                        .document(jurusanRef)
                                        .collection("kelas")
                                        .document(kelasRef)
                                        .collection("jadwal")
                                        .whereEqualTo("hari",hari).whereLessThan("waktu_mulai",waktusekarang)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.w("TAGQUERYSNAPHSOT", "Listen failed.", e);
                                                    return;
                                                }
                                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                                                    Jadwal jadwal = new Jadwal();
                                                    jadwal.setHari(documentSnapshot.getString("hari"));
                                                    jadwal.setMatakuliah(documentSnapshot.getString("matakuliah"));
                                                    jadwal.setDosen(documentSnapshot.getString("dosen"));
                                                    jadwal.setRuangan(documentSnapshot.getString("ruangan"));
                                                    jadwal.setWaktu_mulai(documentSnapshot.getString("waktu_mulai"));
                                                    jadwal.setWaktu_selesai(documentSnapshot.getString("waktu_selesai"));

                                                    dataMatakuliah = jadwal.getMatakuliah();
                                                    dataRuangan = jadwal.getRuangan();
                                                }

                                                Map<String, Object> data = new HashMap<>();
                                                data.put("nama", nama_mhs);
                                                data.put("kelas", content.getKelas());
                                                data.put("matakuliah", dataMatakuliah);
                                                data.put("ruangan", dataMatakuliah);
                                                data.put("jam", getjam);
                                                data.put("waktu", formatWaktuFix);

                                                firebaseFirestore
                                                        .collection("presensi")
                                                        .document("kelas")
                                                        .collection(kelasRef)
                                                        .add(data)
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
                                                                Toast.makeText(context,"Data Masuk",Toast.LENGTH_LONG).show();
                                                            }
                                                        });
                                            }
                                        });
                            } else {
                                Log.d("TAG", "Documment tidak ada");
                            }
                        } else {
                            Log.d("TAG", "gagal", task.getException());
                        }
                    }
                });
            }
        });

//        presensiButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ProximityContent data = nearbyContent.get(position);
//                getid = Util.getData("account", "id", context);
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.presensi_post, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("Respone", response);
//                        DynamicToast.makeSuccess(context,"Presensi Berhasil").show();
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        DynamicToast.makeError(context,"Gagal presensi").show();
//                    }
//                }){
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<>();
//                        params.put("id_mahasiswa", getid);
//                        params.put("waktu", getwaktu);
//                        params.put("tanggal", gettanggal);
//                        params.put("ruangan", data.getKelas());
//                        params.put("matakuliah", data.getKelas());
//                        return params;
//                    }
//                };
//                Volley.getInstance().addToRequestQueue(stringRequest);
//            }
//        });
        return convertView;
    }

    private void getNamaHari() {
        Date dateNow = Calendar.getInstance().getTime();
        waktusekarang = (String) android.text.format.DateFormat.format("HH:mm", dateNow);
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
