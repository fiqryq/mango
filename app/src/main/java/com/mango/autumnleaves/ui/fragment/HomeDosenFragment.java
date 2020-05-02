package com.mango.autumnleaves.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

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
import com.mango.autumnleaves.ui.activity.base.BaseFragment;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.model.dosen.UserDosen;
import com.mango.autumnleaves.ui.activity.dosen.kelas.KelasActivity;

import static com.mango.autumnleaves.util.FunctionHelper.Func.getHour;
import static com.mango.autumnleaves.util.FunctionHelper.Func.getNameDay;
import static com.mango.autumnleaves.util.FunctionHelper.Func.getTimeNow;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeDosenFragment extends BaseFragment {

    private TextView HariIni , tvNodata , tvRuangan , tvWaktuMulai , tvWaktuSelesai , tvMatakuliah , tvViewKelas ;
    private ConstraintLayout ConstraintKelas;
    private ProgressBar progressBar;
    private String datMatakuliah , datDosen , datRuangan , datKelas;

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
        tvViewKelas = view.findViewById(R.id.tvViewKelas);
        progressBar = view.findViewById(R.id.homeDosenProgressBar);

        datMatakuliah ="";
        datDosen ="";
        datKelas ="";
        datRuangan ="";

        ConstraintKelas = view.findViewById(R.id.constraintKelas);
        ConstraintKelas.setVisibility(View.GONE);

        progressBar.setVisibility(View.GONE);
        tvNodata.setVisibility(View.VISIBLE);
        tvMatakuliah.setVisibility(View.GONE);
        tvRuangan.setVisibility(View.GONE);
        tvWaktuMulai.setVisibility(View.GONE);
        tvWaktuSelesai.setVisibility(View.GONE);

        HariIni.setText(getTimeNow());

        ConstraintKelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentConstraintKelas();
            }
        });

        showJadwal();
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
                                .whereEqualTo("hari",getNameDay())
                                .whereLessThan("waktu_mulai",getHour())
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
                                        jadwal.setJurusan(document.getString("jurusan"));
                                        jadwal.setRuangan(document.getString("ruangan"));
                                        jadwal.setWaktu_mulai(document.getString("waktu_mulai"));
                                        jadwal.setWaktu_selesai(document.getString("waktu_selesai"));

                                        datDosen = jadwal.getDosen();
                                        datMatakuliah = jadwal.getMatakuliah();
                                        datRuangan = jadwal.getRuangan();
                                        datKelas = jadwal.getKelas();

                                        Log.d("Berhasil",document.getData().toString());
                                        int selesai = Integer.parseInt(document.getString("waktu_selesai").replace(":", ""));
                                        int sekarang = Integer.parseInt(getHour().replace(":", ""));

                                        if (sekarang <= selesai && jadwal.getKelas().equals(jadwal.getKelas())){
                                            progressBar.setVisibility(View.GONE);
                                            tvNodata.setVisibility(View.GONE);
                                            tvMatakuliah.setVisibility(View.VISIBLE);
                                            tvRuangan.setVisibility(View.VISIBLE);
                                            tvWaktuMulai.setVisibility(View.VISIBLE);
                                            tvWaktuSelesai.setVisibility(View.VISIBLE);

                                            ConstraintKelas.setVisibility(View.VISIBLE);

                                            tvMatakuliah.setText(jadwal.getMatakuliah());
                                            tvRuangan.setText(jadwal.getRuangan());
                                            tvWaktuMulai.setText(jadwal.getWaktu_mulai());
                                            tvWaktuSelesai.setText(jadwal.getWaktu_selesai());
                                            tvViewKelas.setText(jadwal.getJurusan() + " " + jadwal.getKelas());

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

    private void IntentConstraintKelas(){
        Intent intent = new Intent(getActivity(), KelasActivity.class);
        intent.putExtra("MATAKULIAH",datMatakuliah);
        intent.putExtra("DOSEN",datDosen);
        intent.putExtra("RUANGAN",datRuangan);
        intent.putExtra("KELAS",datKelas);
        startActivity(intent);
    }
}


