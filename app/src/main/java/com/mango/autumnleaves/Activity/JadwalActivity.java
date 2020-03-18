package com.mango.autumnleaves.Activity;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.adapter.JadwalAdapter;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.model.User;
import com.mango.autumnleaves.remote.Koneksi;
import com.mango.autumnleaves.remote.Volley;
import com.mango.autumnleaves.util.NotificationHelper;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.squareup.picasso.Picasso;

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
    private String hari, waktusekarang;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.rvJadwalView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        getnotification();
        showJadwal();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showJadwal(){
        String idUser;
        idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // doccumentsnapshoot untuk mendapatkan dokumen secara spesifik
        DocumentReference docRef = firebaseFirestore.collection("user").document(idUser);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        User user = new User();
                        user.setJurusan(document.getString("jurusan"));
                        user.setKode_kelas(document.getString("kode_kelas"));

                        // Doc Ref Dari user
                        String jurusanRef = user.getJurusan();
                        String kelasRef = user.getKode_kelas();

                        // Querysnapshot untuk mendapatkan semua data dari doccument
                        firebaseFirestore
                                .collection("prodi")
                                .document(jurusanRef)
                                .collection("kelas")
                                .document(kelasRef)
                                .collection("jadwal")
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Jadwal jadwal = new Jadwal();
                                                jadwal.setHari(document.getString("hari"));
                                                jadwal.setMatakuliah(document.getString("matakuliah"));
                                                jadwal.setDosen(document.getString("dosen"));
                                                jadwal.setRuangan(document.getString("ruangan"));
                                                jadwal.setWaktu_mulai(document.getString("waktu_mulai"));
                                                jadwal.setWaktu_selesai(document.getString("waktu_selesai"));
                                                arrayList.add(jadwal);
                                                Log.d("logdokumen", document.getId() + " => " + document.getData());
                                            }
                                        } else {
                                            Log.d("tes", "Error getting documents: ", task.getException());
                                        }
                                        setuprecyclerView(arrayList);
                                    }
                                });
                    } else {
                        Log.d("gagal", "Documment tidak ada");
                    }
                } else {
                    Log.d("gagal", "gagal", task.getException());
                }
            }
        });
    }
    private void setuprecyclerView(ArrayList<Jadwal> arrayList) {
        JadwalAdapter jadwalAdapter= new JadwalAdapter(this,arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(jadwalAdapter);
    }

    private void getnotification(){
        final NotificationHelper notificationHelper = new NotificationHelper(this);
        String idUser;
        idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // doccumentsnapshoot untuk mendapatkan dokumen secara spesifik
        DocumentReference docRef = firebaseFirestore.collection("user").document(idUser);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        User user = new User();
                        user.setJurusan(document.getString("jurusan"));
                        user.setKode_kelas(document.getString("kode_kelas"));

                        // Doc Ref Dari user
                        String jurusanRef = user.getJurusan();
                        String kelasRef = user.getKode_kelas();

                        // Querysnapshot untuk mendapatkan data jadwal hari ini
                        getHari();

                        CollectionReference collectionReference = firebaseFirestore
                                .collection("prodi")
                                .document(jurusanRef)
                                .collection("kelas")
                                .document(kelasRef)
                                .collection("jadwal");

                        firebaseFirestore
                                .collection("prodi")
                                .document(jurusanRef)
                                .collection("kelas")
                                .document(kelasRef)
                                .collection("jadwal")
                                .whereEqualTo("hari",hari).whereGreaterThanOrEqualTo("waktu_mulai",waktusekarang)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        notificationHelper.notify("Manggo","Kamu Berada Di dalam Kelas " + "wadaw");
                                        Log.d("jadwal", document.getData().toString());
                                    }
                                } else {
                                    Log.d("tes", "Error getting documents: ", task.getException());
                                }
                            }
                        });
                    } else {
                        Log.d("gagal", "Documment tidak ada");
                    }
                } else {
                    Log.d("gagal", "gagal", task.getException());
                }
            }
        });
    }
    private void getHari() {
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

