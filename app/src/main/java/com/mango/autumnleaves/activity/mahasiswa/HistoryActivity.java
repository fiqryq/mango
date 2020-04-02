package com.mango.autumnleaves.activity.mahasiswa;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.model.UserMahasiswa;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.adapter.adaptermahasiswa.HistoryAdapter;
import com.mango.autumnleaves.model.History;
import com.mango.autumnleaves.activity.base.BaseActivity;
import com.mango.autumnleaves.util.Util;

import java.util.ArrayList;

public class HistoryActivity extends BaseActivity {

    private ArrayList<History> arrayList;
    RecyclerView recyclerView;
    private String getid;
    private ProgressBar progressBar;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        progressBar = findViewById(R.id.progressBarHistory);
        recyclerView = findViewById(R.id.rvHistory);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        showHistory();

        progressBar.setVisibility(View.VISIBLE);

    }

    private void showHistory(){
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
                    UserMahasiswa userMahasiswa = new UserMahasiswa();
                    userMahasiswa.setJurusan(document.getString("jurusan"));
                    userMahasiswa.setKode_kelas(document.getString("kode_kelas"));

                    // Doc Ref Dari user
                    String jurusanRef = userMahasiswa.getJurusan();
                    String kelasRef = userMahasiswa.getKode_kelas();

                    // Querysnapshot untuk mendapatkan semua data dari doccument
                    firebaseFirestore
                            .collection("presensiMahasiswa")
                            .document("kelas")
                            .collection(kelasRef).orderBy("created", Query.Direction.ASCENDING)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    History history = new History();
                                    history.setMatakuliah(document.getString("matakuliah"));
                                    history.setRuangan(document.getString("ruangan"));
                                    history.setTanggal(document.getString("waktu"));
                                    history.setWaktu(document.getString("jam"));
                                    arrayList.add(history);
                                }
                            } else {
                                Log.d("tes", "Error getting documents: ", task.getException());
                            }
                            setuprecyclerview(arrayList);
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
    private void setuprecyclerview(ArrayList<History> arrayList) {
        HistoryAdapter historyAdapter = new HistoryAdapter(this,arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(historyAdapter);
    }
}
