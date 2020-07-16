package com.mango.autumnleaves.ui.activity.dosen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.L;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.adapter.adapterdosen.DetailBapAdapter;
import com.mango.autumnleaves.model.dosen.DetailBap;
import com.mango.autumnleaves.ui.activity.base.BaseActivity;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailBapActivity extends BaseActivity {
    private TextView mBapMatakuliah , mBapRuangan , mBapWaktu , mBapJam , mBapMateri , mbaPJumlahMhs , mBapPertemuan , mBapKelas , mBapCatatan;
    private TextView mHadir,mIzin,mAlfa,mSakit;
    private RecyclerView mRecycleView;
    private DetailBapAdapter mAdapter;

    private static int INTENT_TEMP = 2000;

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
                saveData();
//                Intent intent = new Intent(DetailBapActivity.this,EditBapActivity.class);
//                startActivity(intent);
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
//        mHadir = findViewById(R.id.bapMhsHadir);
//        mIzin = findViewById(R.id.bapMhsIzin);
//        mAlfa = findViewById(R.id.bapMhsAlfa);
//        mSakit = findViewById(R.id.bapMhsSakit);
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
//        mHadir.setText(": " + hadir);
//        mSakit.setText(": " + sakit);
//        mAlfa.setText(": " + alfa);
//        mIzin.setText(": " + izin);
        mBapCatatan.setText(": " + catatan);

        showRecycle(id_bap);
    }

    private void showRecycle(String id) {
        ArrayList<DetailBap> arrayList = new ArrayList<>();
        firebaseFirestore.collection("dosen").document(getFirebaseUserId()).collection("bap").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("HHHH", String.valueOf(documentSnapshot.get("mahasiswa")));

                String objek = documentSnapshot.get("mahasiswa").toString();
                String rep = objek.replace("{", "");
                String rep2 = rep.replace("}", "");
                String rep3 = rep2.replace(",", "',");

                String[] userId = rep3.split("\\]',");

                for (int i = 0; i < userId.length; i++) {
                    String[] pisahKeyValue = userId[i].split("=\\[");

                    String realKey = null;
                    String contentTmp = null;

                    for (int j = 0; j < pisahKeyValue.length ; j++) {
                        realKey = pisahKeyValue[0].replace(" ", "");
                        contentTmp = pisahKeyValue[1];
                    }
//                    Log.d("HAHAH", realKey);
//                    Log.d("HAHAH", contentTmp);

                    String nama = null;
                    String status = null;

                    String[] splitContent = contentTmp.split("', ");

                    for (int j = 0; j < splitContent.length; j++) {
                        nama = splitContent[0];
                        status = splitContent[1].replace("]", "");
                    }

                    Log.d("HAHAH", realKey);
                    Log.d("HAHAH", nama);
                    Log.d("HAHAH", status);

                    DetailBap model = new DetailBap();

                    model.setIdMahasiswa(realKey);
                    model.setName(nama);
                    model.setStatus(Integer.parseInt(status));
                    arrayList.add(model);

//                    Log.d("HAHAH", pisahKeyValue[0]);
                }
//                Log.d("HAHAH", split[0]);
                setupRecycleView(arrayList, id);
            }
        });
//        firebaseFirestore.collection("dosen").document(getFirebaseUserId()).collection("bap").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                DocumentSnapshot snapshot = task.getResult();
//                Map<String, Object> map = snapshot.getData();
//
//
//
//                JsonParser parser = new JsonParser();
//                JsonObject jsonObject = (JsonObject) parser.parse(String.valueOf(snapshot.get("mahasiswa")));
//
//                Log.d("HHHH", jsonObject.toString());
//            }
//        });
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

    private void setupRecycleView(ArrayList<DetailBap> arraylist, String idBap) {
        mAdapter = new DetailBapAdapter(this, arraylist, getFirebaseUserId(), idBap);
        mRecycleView = findViewById(R.id.recycleViewDetailBap);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mAdapter);
    }

    private void saveData() {
        mAdapter.updateData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBackPressed();
            }
        }, INTENT_TEMP);
    }
}
