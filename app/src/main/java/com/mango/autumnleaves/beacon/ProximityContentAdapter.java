package com.mango.autumnleaves.beacon;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.developer.kalert.KAlertDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.model.Presensi;
import com.mango.autumnleaves.model.mahasiswa.UserMahasiswa;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.Jadwal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mango.autumnleaves.util.FunctionHelper.Func.getHour;
import static com.mango.autumnleaves.util.FunctionHelper.Func.getNameDay;
import static com.mango.autumnleaves.util.FunctionHelper.Func.getTimeNow;

public class ProximityContentAdapter extends BaseAdapter {

    private Context context;
    private String dataDosen, dataMatakuliah, dataWaktuMulai, dataWaktuSelesai, dataRuangan, dataNodata;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private FirebaseFirestore firebaseFirestore;

    public ProximityContentAdapter(Context context) {
        this.context = context;
    }

    private List<ProximityContent> nearbyContent = new ArrayList<>();

    public void setNearbyContent(List<ProximityContent> nearbyContent) {
        this.nearbyContent = nearbyContent;
    }

    @Override
    public int getCount() {
        return nearbyContent.size();
    }

    @Override
    public Object getItem(int position) {
        return nearbyContent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate Layout presensi untuk beacon
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.presensi_content_beacon, parent, false);

        }

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        // Inisialisasi Di sini
        TextView kelas = convertView.findViewById(R.id.beacon_kelas);
        TextView lokasi = convertView.findViewById(R.id.beacon_lokasi);
        LinearLayout tapLayout = convertView.findViewById(R.id.linearLayout);

        // Set Content Beacon
        ProximityContent content = nearbyContent.get(position);
        kelas.setText("Ruangan " + content.getKelas());
        lokasi.setText(content.getLokasi() + " Telkom University");

        // BottomSheetDialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.TransparentDialog);
        View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.layout_bottom_sheet,
                        (LinearLayout) convertView.findViewById(R.id.bottomSheetContainer));

        tapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView btsMatakuliah = bottomSheetView.findViewById(R.id.btsMatakuliah);
                TextView btsJam = bottomSheetView.findViewById(R.id.btsJam);
                TextView btsRuangan = bottomSheetView.findViewById(R.id.btsRuangan);
                TextView btsWaktu = bottomSheetView.findViewById(R.id.btsWaktu);
                LinearLayout linearLayout = bottomSheetView.findViewById(R.id.linearLayoutBotttomSheetValid);
                ProgressBar mProgressBarBts = bottomSheetView.findViewById(R.id.progressBts);

                firestorescheduleRef(btsMatakuliah,btsJam,content,btsWaktu,btsRuangan,bottomSheetDialog, linearLayout,mProgressBarBts);
                bottomSheetView.findViewById(R.id.btsPresensi).setVisibility(View.GONE);
                bottomSheetView.findViewById(R.id.btsPresensi).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebasePushData(content,bottomSheetDialog);
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        return convertView;
    }

    private void FirebasePushData(ProximityContent content ,BottomSheetDialog bottomSheetViewPresensi){
        String idUser;
        idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // doccumentsnapshoot untuk mendapatkan dokumen user secara spesifik
        DocumentReference docRef = firebaseFirestore.collection("user").document(idUser);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UserMahasiswa userMahasiswa = new UserMahasiswa();
                        userMahasiswa.setNama(document.getString("nama"));
                        userMahasiswa.setJurusan(document.getString("jurusan"));
                        userMahasiswa.setKode_kelas(document.getString("kode_kelas"));
                        // Doc Ref Dari user
                        String jurusanRef = userMahasiswa.getJurusan();
                        String kelasRef = userMahasiswa.getKode_kelas();
                        String nama_mhs = userMahasiswa.getNama();
                        dataRef(jurusanRef,kelasRef,nama_mhs,content,bottomSheetViewPresensi);

                    } else {
                        Log.d("TAG", "Documment tidak ada");
                    }
                } else {
                    Log.d("TAG", "gagal", task.getException());
                }
            }
        });
    }

    private void dataRef(String jurusanRef,String kelasRef, String nama_mhs, ProximityContent content , BottomSheetDialog bottomSheetViewPresensi){
        firebaseFirestore
                .collection("prodi")
                .document(jurusanRef)
                .collection("kelas")
                .document(kelasRef)
                .collection("jadwal")
                .whereEqualTo("hari", getNameDay()).whereLessThan("waktu_mulai", getHour())
                .orderBy("waktu_mulai", Query.Direction.DESCENDING)
                .limit(1)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            Log.w("TAGQUERYSNAPHSOT", "Listen failed.", e);
                            return;
                        }

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Jadwal jadwal = new Jadwal();
                            jadwal.setHari(documentSnapshot.getString("hari"));
                            jadwal.setMatakuliah(documentSnapshot.getString("matakuliah"));
                            jadwal.setDosen(documentSnapshot.getString("dosen"));
                            jadwal.setSesi(documentSnapshot.getString("sesi"));
                            jadwal.setRuangan(documentSnapshot.getString("ruangan"));
                            jadwal.setWaktu_mulai(documentSnapshot.getString("waktu_mulai"));
                            jadwal.setWaktu_selesai(documentSnapshot.getString("waktu_selesai"));

                            dataMatakuliah = jadwal.getMatakuliah();
                            dataRuangan = jadwal.getRuangan();
                        }

                        Map<String, Object> data = new HashMap<>();
                        data.put("nama", nama_mhs);
                        data.put("matakuliah", dataMatakuliah);
                        data.put("ruangan", content.getKelas());
                        data.put("jam", getHour());
                        data.put("waktu", getTimeNow());
                        data.put("created", new Timestamp(new Date()));

                        firebaseFirestore
                                .collection("presensiMahasiswa")
                                .document("kelas")
                                .collection(kelasRef)
                                .add(data)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        new KAlertDialog(context, KAlertDialog.SUCCESS_TYPE)
                                                .setTitleText("Berhasil Presensi")
                                                .setContentText("klik tombl ok untuk keluar")
                                                .show();
                                        bottomSheetViewPresensi.findViewById(R.id.btsPresensi).setVisibility(View.GONE);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                new KAlertDialog(context, KAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Gagal Presensi")
                                        .show();
                            }
                        });

                        databaseReference = FirebaseDatabase.getInstance().getReference().child("data").child(kelasRef);
                        String key = databaseReference.push().getKey();
                        Presensi presensi = new Presensi();
                        presensi.setNama(nama_mhs);
                        presensi.setJam(getHour());
                        databaseReference.child(key).setValue(presensi);

                    }
                });
    }
    private void firestorescheduleRef(TextView btsMatakuliah , TextView btsJam , ProximityContent content, TextView btsWaktu , TextView btsRuangan,BottomSheetDialog bottomSheetViewPresensi, LinearLayout mBottomSheetValid,ProgressBar mProgressBarBts){
        String idUser;
        idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // doccumentsnapshoot untuk mendapatkan dokumen user secara spesifik
        DocumentReference docRef = firebaseFirestore.collection("user").document(idUser);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UserMahasiswa userMahasiswa = new UserMahasiswa();
                        userMahasiswa.setJurusan(document.getString("jurusan"));
                        userMahasiswa.setKode_kelas(document.getString("kode_kelas"));

                        // Doc Ref Dari user
                        String jurusanRef = userMahasiswa.getJurusan();
                        String kelasRef = userMahasiswa.getKode_kelas();
                        setScheduleBeacon(jurusanRef,kelasRef,btsMatakuliah,btsRuangan,btsWaktu,btsJam,content,bottomSheetViewPresensi, mBottomSheetValid,mProgressBarBts);
                    } else {
                        Log.d("TAG", "Documment tidak ada");
                    }
                } else {
                    Log.d("TAG", "gagal", task.getException());
                }
            }
        });
    }
    private void setScheduleBeacon(String jurusanRef, String kelasRef , TextView btsMatakuliah , TextView btsRuangan , TextView btsWaktu, TextView btsJam, ProximityContent content, BottomSheetDialog bottomSheetViewPresensi, LinearLayout mBottomSheetValid, ProgressBar mProgressBarBts) {
        firebaseFirestore
                .collection("prodi")
                .document(jurusanRef)
                .collection("kelas")
                .document(kelasRef)
                .collection("jadwal")
                .whereEqualTo("hari", getNameDay())
                .whereLessThan("waktu_mulai", getHour())
                .orderBy("waktu_mulai", Query.Direction.DESCENDING)
                .limit(1)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Jadwal jadwal = new Jadwal();
                            jadwal.setMatakuliah(documentSnapshot.getString("matakuliah"));
                            jadwal.setRuangan(documentSnapshot.getString("ruangan"));
                            jadwal.setSesi(documentSnapshot.getString("sesi"));

                            if (jadwal.getSesi().equals("1")){
                                bottomSheetViewPresensi.findViewById(R.id.btsPresensi).setVisibility(View.VISIBLE);
                            } else {
                                bottomSheetViewPresensi.findViewById(R.id.btsPresensi).setVisibility(View.GONE);
                            }

                            Log.d("TESQUERY", "jadwal ruangan " + jadwal.getMatakuliah());
                            Log.d("TESQUERY", "jadwal contex " + content.getKelas());

                            int selesai = Integer.parseInt(documentSnapshot.getString("waktu_selesai").replace(":", ""));
                            int sekarang = Integer.parseInt(getHour().replace(":", ""));

                            if (sekarang <= selesai && jadwal.getRuangan().equals(content.getKelas())){
                                mBottomSheetValid.setVisibility(View.VISIBLE);
                                mProgressBarBts.setVisibility(View.GONE);
                                btsMatakuliah.setText(jadwal.getMatakuliah());
                                btsRuangan.setText(content.getKelas());
                                btsWaktu.setText(getTimeNow());
                                btsJam.setText(getHour());
                            }else {
                                Toast.makeText(context, "Tidak ada Kelas", Toast.LENGTH_SHORT).show();
                                bottomSheetViewPresensi.cancel();
                            }
                        }
                    }
                });
    }
}
