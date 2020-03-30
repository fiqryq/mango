package com.mango.autumnleaves.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
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
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.model.SesiKelas;
import com.mango.autumnleaves.model.UserDosen;
import com.mango.autumnleaves.model.UserMahasiswa;
import com.mango.autumnleaves.util.NotificationHelper;

import java.util.Calendar;
import java.util.Date;

public class KelasTigaActivity extends BaseActivity {

    private TextView tvMatakuliah , tvDosen , tvKelas;
    private Button btnSesi,btnSubmit;
    private String hari, waktusekarang;
    private  TextView HariIni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas_tiga);

        tvMatakuliah = findViewById(R.id.tvSesiMatakuliah);
        tvDosen = findViewById(R.id.tvSesiDosen);
        tvKelas = findViewById(R.id.tvSesiRuangan);
        showJadwal();
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
                                .whereEqualTo("hari",hari)
                                .whereLessThan("waktu_mulai",waktusekarang)
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
                                           tvDosen.setText(sesiKelas.getDosen());
                                           tvMatakuliah.setText(sesiKelas.getMatakuliah());
                                           tvKelas.setText(sesiKelas.getRuangan());
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
