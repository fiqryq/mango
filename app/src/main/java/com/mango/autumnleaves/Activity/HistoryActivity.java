package com.mango.autumnleaves.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.adapter.HistoryAdapter;
import com.mango.autumnleaves.adapter.JadwalAdapter;
import com.mango.autumnleaves.model.History;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.model.User;
import com.mango.autumnleaves.remote.Koneksi;
import com.mango.autumnleaves.remote.Volley;
import com.mango.autumnleaves.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ArrayList<History> arrayList;
    RecyclerView recyclerView;

    private TextView emptyView;
    private String getid;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

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

        getid = Util.getData("account", "id", getApplicationContext());
        showHistory();

        progressBar.setVisibility(View.VISIBLE);

    }
//    private void getHistory() {
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.presensi, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        progressBar.setVisibility(View.GONE);
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("presensi");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                History history = new History();
//                                history.setId_presensi(jsonObject.getInt("id_presensi"));
//                                history.setId_mahasiswa(jsonObject.getInt("id_mahasiswa"));
//                                history.setRuangan(jsonObject.getString("ruangan"));
//                                history.setTanggal(jsonObject.getString("tanggal"));
//                                history.setWaktu(jsonObject.getString("waktu"));
//                                history.setMatakuliah(jsonObject.getString("matakuliah"));
//
//                                String id = String.valueOf(history.getId_mahasiswa());
//
//                                if (getid.equals(id)){
//                                    arrayList.add(history);
//                                }
//
//                                setuprecyclerview(arrayList);
//                            }
//                        } catch (JSONException e) {
//                                e.printStackTrace();
//                        }
//
////                        Log.d("jsonhistory", response.toString());
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        Volley.getInstance().addToRequestQueue(jsonObjectRequest);
//    }
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
                    User user = new User();
                    user.setJurusan(document.getString("jurusan"));
                    user.setKode_kelas(document.getString("kode_kelas"));

                    // Doc Ref Dari user
                    String jurusanRef = user.getJurusan();
                    String kelasRef = user.getKode_kelas();

                    // Querysnapshot untuk mendapatkan semua data dari doccument
                    firebaseFirestore
                            .collection("presensi")
                            .document("kelas")
                            .collection(kelasRef)
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
