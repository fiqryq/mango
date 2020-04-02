package com.mango.autumnleaves.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.activity.dosen.KelasDuaActivity;
import com.mango.autumnleaves.activity.dosen.KelasEmpatActivity;
import com.mango.autumnleaves.activity.dosen.KelasSatuActivity;
import com.mango.autumnleaves.activity.dosen.KelasTigaActivity;
import com.mango.autumnleaves.activity.base.BaseFragment;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.model.UserDosen;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeDosenFragment extends BaseFragment {

    private String hari, waktusekarang;
    private TextView HariIni , tvNodata , tvRuangan , tvWaktuMulai , tvWaktuSelesai , tvMatakuliah ,tvStrip;
    private ConstraintLayout KelasSatu,KelasDua,KelasTiga,KelasEmpat;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_dosen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HariIni = view.findViewById(R.id.tvHomeDosenWaktuSekarang);
        tvNodata = view.findViewById(R.id.tvDosenNodata);
        tvMatakuliah = view.findViewById(R.id.tvMatakuliahDosen);
        tvRuangan = view.findViewById(R.id.tvJadwalRuanganDosen);
        tvWaktuMulai = view.findViewById(R.id.tvJadwalWaktuMulaiDosen);
        tvWaktuSelesai = view.findViewById(R.id.tvJadwalWaktuSelesaiDosen);
        tvStrip = view.findViewById(R.id.strip);
        progressBar = view.findViewById(R.id.homeDosenProgressBar);

        KelasSatu = view.findViewById(R.id.constraintKelasSatu);
        KelasDua = view.findViewById(R.id.constraintKelasDua);
        KelasTiga = view.findViewById(R.id.constraintKelasTiga);
        KelasEmpat = view.findViewById(R.id.constraintKelasEmpat);
        KelasSatu.setVisibility(View.GONE);
        KelasDua.setVisibility(View.GONE);
        KelasTiga.setVisibility(View.GONE);
        KelasEmpat.setVisibility(View.GONE);

        progressBar.setVisibility(View.VISIBLE);
        tvNodata.setVisibility(View.VISIBLE);
        tvMatakuliah.setVisibility(View.GONE);
        tvRuangan.setVisibility(View.GONE);
        tvWaktuMulai.setVisibility(View.GONE);
        tvWaktuSelesai.setVisibility(View.GONE);

        KelasSatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentKelasSatu();
            }
        });
        KelasDua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentKelasDua();
            }
        });
        KelasTiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentKelasTiga();
            }
        });
        KelasEmpat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentKelasEmpat();
            }
        });

        showJadwal();
        getNamaHari();
        getWaktuSekarang();
    }

    // Show Jadwal
    private void showJadwal() {
        // doccumentsnapshoot untuk mendapatkan dokumen secara spesifik
        DocumentReference docRef = firebaseFirestore.collection("user").document(getFirebaseUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        UserDosen userDosen = new UserDosen();
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
                                .orderBy("waktu_mulai", Query.Direction.DESCENDING)
                                .limit(1)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Jadwal jadwal = new Jadwal();
                                        jadwal.setHari(document.getString("hari"));
                                        jadwal.setMatakuliah(document.getString("matakuliah"));
                                        jadwal.setDosen(document.getString("dosen"));
                                        jadwal.setKelas(document.getString("kelas"));
                                        jadwal.setRuangan(document.getString("ruangan"));
                                        jadwal.setWaktu_mulai(document.getString("waktu_mulai"));
                                        jadwal.setWaktu_selesai(document.getString("waktu_selesai"));
                                        Log.d("Berhasil",document.getData().toString());
                                        int selesai = Integer.parseInt(document.getString("waktu_selesai").replace(":", ""));
                                        int sekarang = Integer.parseInt(waktusekarang.replace(":", ""));

                                        if (sekarang <= selesai && jadwal.getKelas().equals("41-01")){
                                            progressBar.setVisibility(View.GONE);
                                            tvNodata.setVisibility(View.GONE);
                                            tvMatakuliah.setVisibility(View.VISIBLE);
                                            tvRuangan.setVisibility(View.VISIBLE);
                                            tvWaktuMulai.setVisibility(View.VISIBLE);
                                            tvWaktuSelesai.setVisibility(View.VISIBLE);

                                            KelasSatu.setVisibility(View.VISIBLE);
                                            KelasDua.setVisibility(View.GONE);
                                            KelasTiga.setVisibility(View.GONE);
                                            KelasEmpat.setVisibility(View.GONE);

                                            tvMatakuliah.setText(jadwal.getMatakuliah());
                                            tvRuangan.setText(jadwal.getRuangan());
                                            tvWaktuMulai.setText(jadwal.getWaktu_mulai());
                                            tvWaktuSelesai.setText(jadwal.getWaktu_selesai());

                                        } else if (sekarang <= selesai && jadwal.getKelas().equals("41-02")){

                                            progressBar.setVisibility(View.GONE);
                                            tvNodata.setVisibility(View.GONE);
                                            tvMatakuliah.setVisibility(View.VISIBLE);
                                            tvRuangan.setVisibility(View.VISIBLE);
                                            tvWaktuMulai.setVisibility(View.VISIBLE);
                                            tvWaktuSelesai.setVisibility(View.VISIBLE);

                                            KelasSatu.setVisibility(View.GONE);
                                            KelasDua.setVisibility(View.VISIBLE);
                                            KelasTiga.setVisibility(View.GONE);
                                            KelasEmpat.setVisibility(View.GONE);

                                            tvMatakuliah.setText(jadwal.getMatakuliah());
                                            tvRuangan.setText(jadwal.getRuangan());
                                            tvWaktuMulai.setText(jadwal.getWaktu_mulai());
                                            tvWaktuSelesai.setText(jadwal.getWaktu_selesai());

                                        } else if (sekarang <= selesai && jadwal.getKelas().equals("41-03")){

                                            progressBar.setVisibility(View.GONE);
                                            tvNodata.setVisibility(View.GONE);
                                            tvMatakuliah.setVisibility(View.VISIBLE);
                                            tvRuangan.setVisibility(View.VISIBLE);
                                            tvWaktuMulai.setVisibility(View.VISIBLE);
                                            tvWaktuSelesai.setVisibility(View.VISIBLE);

                                            KelasSatu.setVisibility(View.GONE);
                                            KelasDua.setVisibility(View.GONE);
                                            KelasTiga.setVisibility(View.VISIBLE);
                                            KelasEmpat.setVisibility(View.GONE);

                                            tvMatakuliah.setText(jadwal.getMatakuliah());
                                            tvRuangan.setText(jadwal.getRuangan());
                                            tvWaktuMulai.setText(jadwal.getWaktu_mulai());
                                            tvWaktuSelesai.setText(jadwal.getWaktu_selesai());

                                        } else if (sekarang <= selesai && jadwal.getKelas().equals("41-04")){

                                            progressBar.setVisibility(View.GONE);
                                            tvNodata.setVisibility(View.GONE);
                                            tvMatakuliah.setVisibility(View.VISIBLE);
                                            tvRuangan.setVisibility(View.VISIBLE);
                                            tvWaktuMulai.setVisibility(View.VISIBLE);
                                            tvWaktuSelesai.setVisibility(View.VISIBLE);

                                            KelasSatu.setVisibility(View.GONE);
                                            KelasDua.setVisibility(View.GONE);
                                            KelasTiga.setVisibility(View.GONE);
                                            KelasEmpat.setVisibility(View.VISIBLE);

                                            tvMatakuliah.setText(jadwal.getMatakuliah());
                                            tvRuangan.setText(jadwal.getRuangan());
                                            tvWaktuMulai.setText(jadwal.getWaktu_mulai());
                                            tvWaktuSelesai.setText(jadwal.getWaktu_selesai());

                                        } else {
                                            progressBar.setVisibility(View.GONE);
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
        HariIni.setText(formatFix);
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
    private void IntentKelasSatu(){
        Intent intent = new Intent(getActivity(), KelasSatuActivity.class);
        startActivity(intent);
    }
    private void IntentKelasDua(){
        Intent intent = new Intent(getActivity(), KelasDuaActivity.class);
        startActivity(intent);
    }
    private void IntentKelasTiga(){
        Intent intent = new Intent(getActivity(), KelasTigaActivity.class);
        startActivity(intent);
    }
    private void IntentKelasEmpat(){
        Intent intent = new Intent(getActivity(), KelasEmpatActivity.class);
        startActivity(intent);
    }
}


