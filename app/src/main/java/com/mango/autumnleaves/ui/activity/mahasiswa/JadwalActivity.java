package com.mango.autumnleaves.ui.activity.mahasiswa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DirectAction;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.model.mahasiswa.UserMahasiswa;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.adapter.adaptermahasiswa.JadwalAdapter;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.ui.activity.base.BaseActivity;
import com.mango.autumnleaves.util.Constant;
import com.mango.autumnleaves.util.NotificationHelper;
import com.mango.autumnleaves.util.ReminderBroadcast;

import static com.mango.autumnleaves.util.FunctionHelper.Func.getHour;
import static com.mango.autumnleaves.util.FunctionHelper.Func.getNameDay;
import static com.mango.autumnleaves.util.FunctionHelper.Func.getTimeNow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class JadwalActivity extends BaseActivity {

    private ArrayList<Jadwal> arrayList;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
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

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.rvJadwalView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();

        tvNodata.setVisibility(View.VISIBLE);
        tvDosen.setVisibility(View.GONE);
        tvRuangan.setVisibility(View.GONE);
        tvWaktuMulai.setVisibility(View.GONE);
        tvWaktuSelesai.setVisibility(View.GONE);
        tvMatakuliah.setVisibility(View.GONE);

//        jadwalrealtimedb();
        jadwalNow();
        jadwalAll();
        progressBar.setVisibility(View.VISIBLE);
        tvSekarang.setText(getTimeNow());
    }

    // Show Jadwal
//    private void jadwalrealtimedb(){
//        DatabaseReference jadwalref = FirebaseDatabase.getInstance().getReference("jadwal")
//                .child(getKelasMhs());
//        jadwalref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot Snapshot :  snapshot.getChildren()){
//                    Jadwal jadwal = Snapshot.getValue(Jadwal.class);
//                    arrayList.add(jadwal);
//                }
//                progressBar.setVisibility(View.GONE);
//                setuprecyclerView(arrayList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
    private void jadwalNow() {
        // Querysnapshot untuk mendapatkan data jadwal hari ini
        // Query Firestore : Get semua jadwal yang ada di "Hari ini" & waktu_mulainya ">= (whereGratherThan)" "waktu_sekarang".
        // Untuk Get matakuliah jam sekarang Querynya "<=" waktu_mulai
        // Untuk Get matakuliah selanjutnya Querynya ">="
        firebaseFirestore
                .collection("prodi")
                .document(getJurusanMhs())
                .collection("kelas")
                .document(getKelasMhs())
                .collection("jadwal")
                .whereEqualTo("hari", getNameDay())
                .whereLessThan("waktu_mulai", getHour())
                .orderBy("waktu_mulai", Query.Direction.ASCENDING)
                .limit(1)
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

                            int selesai = Integer.parseInt(documentSnapshot.getString("waktu_selesai").replace(":", ""));
                            int sekarang = Integer.parseInt(getHour().replace(":", ""));

                            if (sekarang <= selesai) {
                                tvNodata.setVisibility(View.GONE);
                                tvDosen.setVisibility(View.VISIBLE);
                                tvRuangan.setVisibility(View.VISIBLE);
                                tvWaktuMulai.setVisibility(View.VISIBLE);
                                tvWaktuSelesai.setVisibility(View.VISIBLE);
                                tvMatakuliah.setVisibility(View.VISIBLE);

                                tvDosen.setText(jadwal.getDosen());
                                tvRuangan.setText(jadwal.getRuangan());
                                tvWaktuMulai.setText(jadwal.getWaktu_mulai());
                                tvWaktuSelesai.setText(jadwal.getWaktu_selesai());
                                tvMatakuliah.setText(jadwal.getMatakuliah());

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
    }

    private void jadwalAll() {
        // doccumentsnapshoot untuk mendapatkan dokumen secara spesifik
        DocumentReference docRef = firebaseFirestore.collection("user").document(getFirebaseUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                // Querysnapshot untuk mendapatkan semua data dari doccument
                firebaseFirestore
                        .collection("prodi")
                        .document(getJurusanMhs())
                        .collection("kelas")
                        .document(getKelasMhs())
                        .collection("jadwal")
                        .orderBy("posisi")
                        .orderBy("waktu_mulai", Query.Direction.ASCENDING)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressBar.setVisibility(View.GONE);
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
            }

            private void setuprecyclerView(ArrayList<Jadwal> arrayList) {
                JadwalAdapter jadwalAdapter = new JadwalAdapter(getBaseContext(), arrayList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(jadwalAdapter);
            }
        });
    }
}


