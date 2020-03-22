package com.mango.autumnleaves.activity.mahasiswa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.model.UserMahasiswa;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.adapter.adaptermahasiswa.JadwalAdapter;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.activity.base.BaseActivity;
import com.mango.autumnleaves.util.NotificationHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class JadwalActivity extends BaseActivity {

    private ArrayList<Jadwal> arrayList;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private String hari, waktusekarang;
    private TextView tvSekarang, tvDosen, tvMatakuliah, tvWaktuMulai, tvWaktuSelesai, tvRuangan, tvNodata;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        firebaseUser = firebaseAuth.getCurrentUser();

        tvDosen = findViewById(R.id.tv_detail_dosen);
        tvRuangan = findViewById(R.id.tv_detail_ruangan);
        tvWaktuMulai = findViewById(R.id.tv_waktu_mulai);
        tvWaktuSelesai = findViewById(R.id.tv_waktu_selesai);
        tvSekarang = findViewById(R.id.tvSekarang);
        tvMatakuliah = findViewById(R.id.tv_detail_matkul);
        tvNodata = findViewById(R.id.tv_no_data);
        tvNodata.setVisibility(View.GONE);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.rvJadwalView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();


        jadwalRealtime();
        showJadwal();
        getNamaHari();
        getWaktuSekarang();
        progressBar.setVisibility(View.VISIBLE);
    }

    // Show Jadwal
    private void showJadwal() {
        // doccumentsnapshoot untuk mendapatkan dokumen secara spesifik
        DocumentReference docRef = firebaseFirestore.collection("user").document(getFirebaseUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UserMahasiswa userMahasiswa = new UserMahasiswa();
                        userMahasiswa.setJurusan(document.getString("jurusan"));
                        userMahasiswa.setKode_kelas(document.getString("kode_kelas"));

                        // Doc Ref Dari user
                        String jurusanRef = userMahasiswa.getJurusan();
                        String kelasRef = userMahasiswa.getKode_kelas();

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
        JadwalAdapter jadwalAdapter = new JadwalAdapter(this, arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(jadwalAdapter);
    }

    // Get Jadwal selanjutnya Realtime
    private void jadwalRealtime() {
        final NotificationHelper notificationHelper = new NotificationHelper(this);
        String idUser;
        idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // doccumentsnapshoot untuk mendapatkan dokumen user secara spesifik
        DocumentReference docRef = firebaseFirestore.collection("user").document(idUser);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UserMahasiswa userMahasiswa = new UserMahasiswa();
                        userMahasiswa.setJurusan(document.getString("jurusan"));
                        userMahasiswa.setKode_kelas(document.getString("kode_kelas"));

                        // Doc Ref Dari user
                        String jurusanRef = userMahasiswa.getJurusan();
                        String kelasRef = userMahasiswa.getKode_kelas();

                        // Querysnapshot untuk mendapatkan data jadwal hari ini
                        // Query Firestore : Get semua jadwal yang ada di "Hari ini" & waktu_mulainya ">= (whereGratherThan)" "waktu_sekarang".
                        // Untuk Get matakuliah jam sekarang Querynya "<=" waktu_mulai
                        // Untuk Get matakuliah selanjutnya Querynya ">="
                        getNamaHari();
                        firebaseFirestore
                                .collection("prodi")
                                .document(jurusanRef)
                                .collection("kelas")
                                .document(kelasRef)
                                .collection("jadwal")
                                .whereEqualTo("hari", hari).whereLessThan("waktu_mulai", waktusekarang)
                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.w("TAGQUERYSNAPHSOT", "Listen failed.", e);
                                            return;
                                        }
                                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                            Jadwal jadwal = new Jadwal();
                                            jadwal.setHari(documentSnapshot.getString("hari"));
                                            jadwal.setMatakuliah(documentSnapshot.getString("matakuliah"));
                                            jadwal.setDosen(documentSnapshot.getString("dosen"));
                                            jadwal.setRuangan(documentSnapshot.getString("ruangan"));
                                            jadwal.setWaktu_mulai(documentSnapshot.getString("waktu_mulai"));
                                            jadwal.setWaktu_selesai(documentSnapshot.getString("waktu_selesai"));

                                            if (queryDocumentSnapshots != null && documentSnapshot.exists()) {
                                                tvDosen.setText(jadwal.getDosen());
                                                tvRuangan.setText(jadwal.getRuangan());
                                                tvWaktuMulai.setText(jadwal.getWaktu_mulai());
                                                tvWaktuSelesai.setText(jadwal.getWaktu_selesai());
                                                tvMatakuliah.setText(jadwal.getMatakuliah());

                                                notificationHelper.notify(
                                                        "Matakuliah : " + jadwal.getMatakuliah()
                                                                + " Ruangan : " + jadwal.getRuangan(),
                                                        "Jadwal Selanjutnya");
                                            } else {
                                                tvNodata.setVisibility(View.VISIBLE);
                                                tvDosen.setVisibility(View.GONE);
                                                tvRuangan.setVisibility(View.GONE);
                                                tvWaktuMulai.setVisibility(View.GONE);
                                                tvWaktuSelesai.setVisibility(View.GONE);
                                                tvMatakuliah.setVisibility(View.GONE);
                                            }
                                        }
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

    // Refferensi Waktu
    public void getWaktuSekarang() {
        Date date = Calendar.getInstance().getTime();
        String tanggal = (String) DateFormat.format("d", date); // 20
        String monthNumber = (String) DateFormat.format("M", date); // 06
        String year = (String) DateFormat.format("yyyy", date); // 2013

        int month = Integer.parseInt(monthNumber);
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
        String formatFix = hari + ", " + tanggal + " " + bulan + " " + year;
        tvSekarang.setText(String.valueOf(formatFix));
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

