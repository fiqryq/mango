package com.mango.autumnleaves.activity.dosen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.activity.base.BaseActivity;
import com.mango.autumnleaves.adapter.adapterdosen.MahasiswaAdapter;
import com.mango.autumnleaves.adapter.adaptermahasiswa.JadwalAdapter;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.model.SesiKelas;
import com.mango.autumnleaves.model.UserDosen;
import com.mango.autumnleaves.model.UserMahasiswa;
import com.mango.autumnleaves.util.NotificationHelper;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import com.shreyaspatil.MaterialDialog.interfaces.OnCancelListener;
import com.shreyaspatil.MaterialDialog.interfaces.OnDismissListener;
import com.shreyaspatil.MaterialDialog.interfaces.OnShowListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class KelasTigaActivity extends BaseActivity implements View.OnClickListener, OnShowListener, OnCancelListener, OnDismissListener {

    private TextView tvMatakuliah, tvDosen, tvKelas;
    private Button btnSubmit;
    private MaterialDialog logoutDialog;
    private Switch switchSesi;
    private String hari, waktusekarang;
    public String idDoccument;
    private ArrayList<UserMahasiswa> arrayList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas_tiga);

        idDoccument = "";
        recyclerView = findViewById(R.id.rvListMahasiswaTiga);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();

        tvMatakuliah = findViewById(R.id.tvSesiMatakuliah);
        tvDosen = findViewById(R.id.tvSesiDosen);
        tvKelas = findViewById(R.id.tvSesiRuangan);
        btnSubmit = findViewById(R.id.btnSubmit);
        switchSesi = findViewById(R.id.ButtonSwitch);

        dataRef();
        showJadwal();
        getDataMahasiswa();
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

        logoutDialog = new MaterialDialog.Builder(this)
                .setTitle("Submit Bap")
                .setMessage("Apakah Kamu Yakin Akan Submit Bap?")
                .setCancelable(false)
                .setPositiveButton("Submit", R.drawable.ic_power_settings_new_black_24dp, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showSuccessToast("Berhasil Ditekan");
                        updateFalse();
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
        map.put("sesi", 1);
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
        map.put("sesi", 0);
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
    private void getDataMahasiswa() {
        firebaseFirestore.collection("user")
                .whereEqualTo("kode_kelas", "41-03")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        UserMahasiswa userMahasiswa = new UserMahasiswa();
                        userMahasiswa.setNama(documentSnapshot.getString("nama"));
                        userMahasiswa.setNim_mhs(documentSnapshot.getString("nim_mhs"));
                        userMahasiswa.setAlamat(documentSnapshot.getString("alamat"));
                        userMahasiswa.setTelp(documentSnapshot.getString("telp"));
                        userMahasiswa.setTtl(documentSnapshot.getString("ttl"));
                        userMahasiswa.setKelamin(documentSnapshot.getString("jenis_kelamin"));
                        userMahasiswa.setKode_kelas(documentSnapshot.getString("kode_kelas"));
                        userMahasiswa.setGambar(documentSnapshot.getString("gambar"));
                        userMahasiswa.setJurusan(documentSnapshot.getString("jurusan"));
                        arrayList.add(userMahasiswa);
                    }
                } else {
                    showToast("gagal");
                }
                setuprecyclerView(arrayList);
            }
        });
    }
    private void setuprecyclerView(ArrayList<UserMahasiswa> arrayList) {
        MahasiswaAdapter mahasiswaAdapter = new MahasiswaAdapter(this, arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mahasiswaAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmit :
            logoutDialog.show();
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
}
