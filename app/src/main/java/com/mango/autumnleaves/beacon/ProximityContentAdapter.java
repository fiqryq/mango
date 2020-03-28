package com.mango.autumnleaves.beacon;

import android.content.Context;
import android.media.MediaSync;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.developer.kalert.KAlertDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.model.UserMahasiswa;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.util.NotificationHelper;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProximityContentAdapter extends BaseAdapter {

    private Context context;
    private String getjam;
    private String hari, waktusekarang;
    private String dataDosen, dataMatakuliah, dataWaktuMulai, dataWaktuSelesai, dataRuangan, dataNodata;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
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

        final NotificationHelper notificationHelper = new NotificationHelper(context);
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

        View finalConvertView = convertView;
        tapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.TransparentDialog);
                View bottomSheetView = LayoutInflater.from(context)
                        .inflate(R.layout.layout_bottom_sheet,
                                (LinearLayout) finalConvertView.findViewById(R.id.bottomSheetContainer));

                TextView btsMatakuliah = bottomSheetView.findViewById(R.id.btsMatakuliah);
                TextView btsJam = bottomSheetView.findViewById(R.id.btsJam);
                TextView btsRuangan = bottomSheetView.findViewById(R.id.btsRuangan);
                TextView btsWaktu = bottomSheetView.findViewById(R.id.btsWaktu);

                firestorescheduleRef(btsMatakuliah,btsJam,content,btsWaktu,btsRuangan,bottomSheetDialog);
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
        Date date = Calendar.getInstance().getTime();
        String tanggal = (String) android.text.format.DateFormat.format("d", date); // 20
        String monthNumber = (String) android.text.format.DateFormat.format("M", date); // 06
        String year = (String) DateFormat.format("yyyy", date); // 2013
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat jam = new SimpleDateFormat("kk:mm");

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

        String formatWaktuFix = hari + ", " + tanggal + " " + bulan + " " + year;
        getjam = jam.format(calendar.getTime());

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
                        getNamaHari();
                        dataRef(jurusanRef,kelasRef,nama_mhs,content,formatWaktuFix,bottomSheetViewPresensi);

                    } else {
                        Log.d("TAG", "Documment tidak ada");
                    }
                } else {
                    Log.d("TAG", "gagal", task.getException());
                }
            }
        });
    }
    private void dataRef(String jurusanRef,String kelasRef, String nama_mhs, ProximityContent content , String formatWaktuFix, BottomSheetDialog bottomSheetViewPresensi){
        firebaseFirestore
                .collection("prodi")
                .document(jurusanRef)
                .collection("kelas")
                .document(kelasRef)
                .collection("jadwal")
                .whereEqualTo("hari", hari).whereLessThan("waktu_mulai", waktusekarang)
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
                        data.put("jam", getjam);
                        data.put("waktu", formatWaktuFix);
                        data.put("created", new Timestamp(new Date()));

                        firebaseFirestore
                                .collection("presensi")
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
                    }
                });
    }
    private void firestorescheduleRef(TextView btsMatakuliah , TextView btsJam , ProximityContent content, TextView btsWaktu , TextView btsRuangan,BottomSheetDialog bottomSheetViewPresensi){
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
                        getNamaHari();
                        setScheduleBeacon(jurusanRef,kelasRef,btsMatakuliah,btsRuangan,btsWaktu,btsJam,content,bottomSheetViewPresensi);

                    } else {
                        Log.d("TAG", "Documment tidak ada");
                    }
                } else {
                    Log.d("TAG", "gagal", task.getException());
                }
            }
        });
    }

    private void setScheduleBeacon(String jurusanRef, String kelasRef , TextView btsMatakuliah , TextView btsRuangan ,TextView btsWaktu,TextView btsJam, ProximityContent content,BottomSheetDialog bottomSheetViewPresensi){
        Date date = Calendar.getInstance().getTime();
        String tanggal = (String) android.text.format.DateFormat.format("d", date); // 20
        String monthNumber = (String) android.text.format.DateFormat.format("M", date); // 06
        String year = (String) DateFormat.format("yyyy", date); // 2013
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat jam = new SimpleDateFormat("kk:mm");

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

        String formatWaktuFixBts = hari + ", " + tanggal + " " + bulan + " " + year;
        String getjamBts = jam.format(calendar.getTime());

        firebaseFirestore
                .collection("prodi")
                .document(jurusanRef)
                .collection("kelas")
                .document(kelasRef)
                .collection("jadwal")
                .whereEqualTo("hari", hari)
                .whereLessThan("waktu_mulai", waktusekarang)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("TAGQUERYSNAPHSOT", "Listen failed.", e);
                            return;
                        }

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Jadwal jadwal = new Jadwal();
                            jadwal.setMatakuliah(documentSnapshot.getString("matakuliah"));

                            int selesai = Integer.parseInt(documentSnapshot.getString("waktu_selesai").replace(":", ""));
                            int sekarang = Integer.parseInt(waktusekarang.replace(":", ""));

                            if (sekarang <= selesai){
                                btsMatakuliah.setText(jadwal.getMatakuliah());
                                btsRuangan.setText(content.getKelas());
                                btsWaktu.setText(formatWaktuFixBts);
                                btsJam.setText(getjamBts);
//                                bottomSheetViewPresensi.findViewById(R.id.btsPresensi).setVisibility(View.VISIBLE);
                            } else if (sekarang >= selesai){
                                btsMatakuliah.setText(jadwal.getMatakuliah());
                                btsRuangan.setText(content.getKelas());
                                btsWaktu.setText(formatWaktuFixBts);
                                btsJam.setText(getjamBts);
//                                bottomSheetViewPresensi.findViewById(R.id.btsPresensi).setVisibility(View.VISIBLE);
                            }else {
                                // Handle If No Jadwal
                            }
                        }
                    }
                });
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
