package com.mango.autumnleaves.ui.activity.dosen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.adapter.adapterdosen.DetailBapAdapter;
import com.mango.autumnleaves.model.dosen.DetailBap;
import com.mango.autumnleaves.ui.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailBapActivity extends BaseActivity {
    private TextView mBapMatakuliah , mBapRuangan , mBapWaktu , mBapJam , mBapMateri , mbaPJumlahMhs , mBapPertemuan , mBapKelas , mBapCatatan;
    private TextView mHadir,mIzin,mAlfa,mSakit;
    private RecyclerView mRecycleView;
    private DetailBapAdapter mAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_bap,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_bap:
                Intent intent = new Intent(DetailBapActivity.this,EditBapActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bap_class);

        mBapMatakuliah = findViewById(R.id.bapMatakuliah);
        mBapRuangan = findViewById(R.id.bapRuangan);
        mBapWaktu = findViewById(R.id.bapWaktu);
        mBapJam = findViewById(R.id.bapJam);
        mBapMateri = findViewById(R.id.bapMateri);
        mBapPertemuan = findViewById(R.id.bapPertemuan);
        mbaPJumlahMhs = findViewById(R.id.bapHadir);
        mBapKelas = findViewById(R.id.bapKelas);
        mHadir = findViewById(R.id.bapMhsHadir);
        mIzin = findViewById(R.id.bapMhsIzin);
        mAlfa = findViewById(R.id.bapMhsAlfa);
        mSakit = findViewById(R.id.bapMhsSakit);
        mBapCatatan = findViewById(R.id.bapCatatan);

        Intent intent = getIntent();
        String matakuliah = intent.getStringExtra("MATAKULIAH");
        String ruangan = intent.getStringExtra("RUANGAN");
        String tanggal = intent.getStringExtra("TANGGAL");
        String waktuPresensi = intent.getStringExtra("WAKTU");
        String materi = intent.getStringExtra("MATERI");
        int pertemuan = intent.getIntExtra("PERTEMUAN",0);
        int jumlahmhs = intent.getIntExtra("JUMLAHMHS",0);
        int hadir = intent.getIntExtra("HADIR",0);
        int sakit = intent.getIntExtra("SAKIT",0);
        int izin = intent.getIntExtra("IZIN",0);
        int alfa = intent.getIntExtra("ALFA",0);
        String kelas = intent.getStringExtra("KELAS");
        String catatan = intent.getStringExtra("CATATAN");
        String id_bap = intent.getStringExtra("ID_BAP");

        mBapMatakuliah.setText(": " + matakuliah);
        mBapRuangan.setText(": " + ruangan);
        mBapWaktu.setText(": " + tanggal);
        mBapJam.setText(": " + waktuPresensi);
        mBapMateri.setText(": " + materi);
        mBapPertemuan.setText(": " + pertemuan);
        mbaPJumlahMhs.setText(": " + jumlahmhs);
        mBapKelas.setText(": " + kelas);
        mHadir.setText(": " + hadir);
        mSakit.setText(": " + sakit);
        mAlfa.setText(": " + alfa);
        mIzin.setText(": " + izin);
        mBapCatatan.setText(": " + catatan);

        showRecycle(id_bap);
    }

    private void showRecycle(String id) {
        firebaseFirestore.collection("dosen").document(getFirebaseUserId()).collection("bap").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                String mahasiswa = documentSnapshot.get("mahasiswa").toString();
//                documentSnapshot
                Map<String, Object> detailBap = documentSnapshot.getData();
                Log.d("ARRAY_DETAIL", detailBap.toString());
            }
        });
//        DocumentSnapshot reference = FirebaseFirestore.getInstance().collection("dosen").document(getFirebaseUserId()).collection("bap").document(id);
//        Query query = reference;

//        FirestoreRecyclerOptions<DetailBap> options = new FirestoreRecyclerOptions.Builder<DetailBap>()
//                .setQuery(query, DetailBap.class)
//                .build();
//
//        mAdapter = new DetailBapAdapter(options, this);
//
//        mRecycleView = findViewById(R.id.recycleViewDetailBap);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
//        mRecycleView.setHasFixedSize(true);
//        mRecycleView.setLayoutManager(layoutManager);
//        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mAdapter.stopListening();
    }
}
