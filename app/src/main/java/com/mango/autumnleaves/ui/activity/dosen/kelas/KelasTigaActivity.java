package com.mango.autumnleaves.ui.activity.dosen.kelas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.ui.activity.base.BaseActivity;
import com.mango.autumnleaves.adapter.adapterdosen.KelasAdapter;
import com.mango.autumnleaves.model.Presensi;
import com.mango.autumnleaves.model.SesiKelas;
import com.mango.autumnleaves.model.dosen.UserDosen;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import com.shreyaspatil.MaterialDialog.interfaces.OnCancelListener;
import com.shreyaspatil.MaterialDialog.interfaces.OnDismissListener;
import com.shreyaspatil.MaterialDialog.interfaces.OnShowListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class KelasTigaActivity extends BaseActivity implements View.OnClickListener, OnShowListener, OnCancelListener, OnDismissListener {

    private TextView tvMatakuliah, tvDosen, tvKelas;
    private Button btnSubmit;
    private MaterialDialog bapdialog;
    private Switch switchSesi;
    private String hari, waktusekarang;
    public String idDoccument;
    public String mataKuliahNow;
    public String RuanganNow;

    private RecyclerView mPresensiRecycleview;
    private View emptyview;
    private KelasAdapter mAdapter;
    private ArrayList<Presensi> mData;
    private ArrayList<String> mDataId;
    private DatabaseReference mDatabase;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("41-03");

    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            mData.add(dataSnapshot.getValue(Presensi.class));
            mDataId.add(dataSnapshot.getKey());
            mAdapter.updateEmptyView();
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            int pos = mDataId.indexOf(dataSnapshot.getKey());
            mData.set(pos, dataSnapshot.getValue(Presensi.class));
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            int pos = mDataId.indexOf(dataSnapshot.getKey());
            mDataId.remove(pos);
            mData.remove(pos);
            mAdapter.updateEmptyView();
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas_tiga);
        idDoccument = "";
        mataKuliahNow = "";

        tvMatakuliah = findViewById(R.id.tvSesiMatakuliah);
        tvDosen = findViewById(R.id.tvSesiDosen);
        tvKelas = findViewById(R.id.tvSesiRuangan);
        btnSubmit = findViewById(R.id.btnSubmit);
        switchSesi = findViewById(R.id.ButtonSwitch);


        dataRef();
        jadwalRef();
        showJadwal();
        getDataPresensiMahasiswa();

        switchSesi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchSesi.setText("Sesi Aktif");
                    updateTrue();
                } else {
                    switchSesi.setText("Sesi Tidak Aktif");
                    updateFalse();
                }
            }
        });

        if (switchSesi.isChecked()) {
            switchSesi.setText("Sesi Aktif");
        } else {
            switchSesi.setText("Sesi Tidak Aktif");
        }

        bapdialog = new MaterialDialog.Builder(this)
                .setTitle("Submit Bap")
                .setMessage("Apakah Kamu Yakin Akan Submit Bap?")
                .setCancelable(false)
                .setPositiveButton("Submit", R.drawable.ic_power_settings_new_black_24dp, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showSuccessToast("Berhasil Submit");
                        resetAndPushData();
                        switchSesi.setChecked(false);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Toast.makeText(getApplicationContext(), "Dibatalkan", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                })
                .build();
        btnSubmit.setOnClickListener(this::onClick);
    }

    private void showJadwal() {
        getWaktuSekarang();
        getNamaHari();
        // doccumentsnapshoot untuk mendapatkan dokumen secara spesifik
        DocumentReference docRef = firebaseFirestore.collection("user").document(getFirebaseUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        UserDosen userDosen = new UserDosen();
                        userDosen.setNama(document.getString("nama"));
                        userDosen.setJurusan(document.getString("jurusan"));
                        userDosen.setNip(document.getString("nip"));

                        // Doc Ref Dari user
                        String nipRef = userDosen.getNip();

                        // Querysnapshot untuk mendapatkan semua data dari doccument
                        firebaseFirestore
                                .collection("jadwalDosen")
                                .document(nipRef)
                                .collection("jadwal")
                                .whereEqualTo("hari", hari)
                                .whereLessThan("waktu_mulai", waktusekarang)
                                .orderBy("waktu_mulai", Query.Direction.DESCENDING)
                                .limit(1)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        SesiKelas sesiKelas = new SesiKelas();
                                        sesiKelas.setHari(document.getString("hari"));
                                        sesiKelas.setMatakuliah(document.getString("matakuliah"));
                                        sesiKelas.setDosen(document.getString("dosen"));
                                        sesiKelas.setRuangan(document.getString("ruangan"));
                                        sesiKelas.setWaktu_mulai(document.getString("waktu_mulai"));
                                        sesiKelas.setWaktu_selesai(document.getString("waktu_selesai"));

                                        int selesai = Integer.parseInt(document.getString("waktu_selesai").replace(":", ""));
                                        int sekarang = Integer.parseInt(waktusekarang.replace(":", ""));

                                        if (sekarang <= selesai) {
                                            tvDosen.setText("Dosen : " + sesiKelas.getDosen());
                                            tvMatakuliah.setText("Matakuliah : " + sesiKelas.getMatakuliah());
                                            tvKelas.setText("Kelas : " + sesiKelas.getRuangan());
                                        }
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
    private void dataRef() {
        getNamaHari();
        getWaktuSekarang();
        firebaseFirestore
                .collection("prodi")
                .document("rpla")
                .collection("kelas")
                .document("41-03")
                .collection("jadwal")
                .whereEqualTo("hari", hari)
                .whereLessThan("waktu_mulai", waktusekarang)
                .orderBy("waktu_mulai", Query.Direction.DESCENDING)
                .limit(1)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                           idDoccument = documentSnapshot.getId();
                           if(documentSnapshot.getString("sesi").equals("1")){
                               switchSesi.setChecked(true);
                           }else {
                               switchSesi.setChecked(false);
                           }
                        }
                    }
                });
    }
    private void updateTrue(){
        DocumentReference documentReference = firebaseFirestore
                .collection("prodi")
                .document("rpla")
                .collection("kelas")
                .document("41-03")
                .collection("jadwal")
                .document(idDoccument);

        Map<String, Object> map = new HashMap<>();
        map.put("sesi", "1");
        documentReference.update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showToast("Berhasil Buka Sesi");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showErrorToast("Gagal Buka Sesi");
            }
        });
    }
    private void updateFalse(){
        DocumentReference documentReference = firebaseFirestore
                .collection("prodi")
                .document("rpla")
                .collection("kelas")
                .document("41-03")
                .collection("jadwal")
                .document(idDoccument);

        Map<String, Object> map = new HashMap<>();
        map.put("sesi", "0");
        documentReference.update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showToast("Berhasil Tutup Sesi");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showErrorToast("Gagal Tutup Sesi");
            }
        });
    }

    private void getDataPresensiMahasiswa() {
        emptyview = findViewById(R.id.textView_empty_viiew);
        mPresensiRecycleview = findViewById(R.id.rvMahasiswaTiga);

        mData = new ArrayList<>();
        mDataId = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("41-03");
        mDatabase.addChildEventListener(childEventListener);

        mPresensiRecycleview.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(true);
        mPresensiRecycleview.setLayoutManager(linearLayoutManager);

        mAdapter = new KelasAdapter(this, mData, mDataId, emptyview, new KelasAdapter.ClickHandler() {
            @Override
            public void onItemClick(int position) {
                //ketika rec di click
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        mPresensiRecycleview.setAdapter(mAdapter);
    }

    private void jadwalRef() {
        getWaktuSekarang();
        getNamaHari();
        // doccumentsnapshoot untuk mendapatkan dokumen secara spesifik
        DocumentReference docRef = firebaseFirestore.collection("user").document(getFirebaseUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        UserDosen userDosen = new UserDosen();
                        userDosen.setNama(document.getString("nama"));
                        userDosen.setJurusan(document.getString("jurusan"));
                        userDosen.setNip(document.getString("nip"));

                        // Doc Ref Dari user
                        String nipRef = userDosen.getNip();

                        // Querysnapshot untuk mendapatkan semua data dari doccument
                        firebaseFirestore
                                .collection("jadwalDosen")
                                .document(nipRef)
                                .collection("jadwal")
                                .whereEqualTo("hari", hari)
                                .whereLessThan("waktu_mulai", waktusekarang)
                                .orderBy("waktu_mulai", Query.Direction.DESCENDING)
                                .limit(1)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        SesiKelas sesiKelas = new SesiKelas();
                                        sesiKelas.setHari(document.getString("hari"));
                                        sesiKelas.setMatakuliah(document.getString("matakuliah"));
                                        sesiKelas.setDosen(document.getString("dosen"));
                                        sesiKelas.setRuangan(document.getString("ruangan"));
                                        sesiKelas.setWaktu_mulai(document.getString("waktu_mulai"));
                                        sesiKelas.setWaktu_selesai(document.getString("waktu_selesai"));

                                        int selesai = Integer.parseInt(document.getString("waktu_selesai").replace(":", ""));
                                        int sekarang = Integer.parseInt(waktusekarang.replace(":", ""));

                                        if (sekarang <= selesai) {
                                            mataKuliahNow = sesiKelas.getMatakuliah();
                                            RuanganNow = sesiKelas.getRuangan();
                                        }
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
    private void resetAndPushData(){

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                    dataCount = ds.child("41-03").getChildrenCount();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        Map<String, Object> dataBap = new HashMap<>();
        dataBap.put("matakuliah", mataKuliahNow);
        dataBap.put("jam",waktusekarang);
        dataBap.put("waktu",hari);
        dataBap.put("ruangan",RuanganNow);
        dataBap.put("created", new Timestamp(new Date()));

        firebaseFirestore
                .collection("bap")
                .document(getFirebaseUserId())
                .collection("data")
                .add(dataBap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                databaseReference.removeValue();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmit :
                bapdialog.show();
        }
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {

    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

    }

    @Override

    public void onShow(DialogInterface dialogInterface) {

    }
    private void getWaktuSekarang() {
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
