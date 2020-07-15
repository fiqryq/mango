package com.mango.autumnleaves.ui.activity.mahasiswa;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.adapter.adaptermahasiswa.StatistikAdapter;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.model.Statistik;
import com.mango.autumnleaves.ui.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.Map;

public class StatistikActivity extends BaseActivity {

    ArrayList<Statistik> arrayList;
    RecyclerView recyclerView;
    private StatistikAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistik_activity);

        recyclerView = findViewById(R.id.rvStatistik);
        arrayList = new ArrayList<>();

        Intent intent = getIntent();
        String kelas = intent.getStringExtra("KELAS");
        String idMatkul = intent.getStringExtra("DATAID");


        getDataMatakuliah();
        getStatistik();
    }

    private void getDataMatakuliah() {
        firebaseFirestore
                .collection("statistik")
                .document("kelas")
                .collection(getKelasMhs())
                .document(getFirebaseUserId())
                .collection("jadwal")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        Statistik statistik = new Statistik();
                        statistik.setId(documentSnapshot.getString("id"));
                        statistik.setDosen(documentSnapshot.getString("dosen"));
                        statistik.setMatakuliah(documentSnapshot.getString("matakuliah"));
                        statistik.setPertemuan(documentSnapshot.getLong("pertemuan").intValue());
                        statistik.setJumlah_pertemuan(documentSnapshot.getLong("jumlah_pertemuan").intValue());
                        arrayList.add(statistik);
                    }
                }
                setuprecyclerView(arrayList);
            }
        });
    }
    private void getStatistik(){
        firebaseFirestore.collection("matakuliah").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){

                    }
                }

            }
        });
    }

    private void setuprecyclerView(ArrayList<Statistik> arrayList) {
        StatistikAdapter statistikAdapter = new StatistikAdapter(getApplicationContext(),arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(statistikAdapter);
    }
}
