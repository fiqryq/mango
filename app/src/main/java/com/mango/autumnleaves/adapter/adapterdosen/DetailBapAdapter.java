package com.mango.autumnleaves.adapter.adapterdosen;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.model.dosen.DetailBap;
import com.mango.autumnleaves.model.dosen.UserDosen;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mango.autumnleaves.util.FunctionHelper.Func.getHour;
import static com.mango.autumnleaves.util.FunctionHelper.Func.getNameDay;

public class DetailBapAdapter extends FirestoreRecyclerAdapter<DetailBap, DetailBapAdapter.ViewHolder> {

    private String idDosen;
    private String idBap;
    private String nipDosen;
    public String idMatkul = "";
    public String KelasMatkul = "";
    public long Pertemuan;
    public FirebaseFirestore statRef = FirebaseFirestore.getInstance();
    public DocumentReference docReff;

    public DetailBapAdapter(@NonNull FirestoreRecyclerOptions<DetailBap> options, String idDosen, String idBap, String nipDosen) {
        super(options);
        this.idDosen = idDosen;
        this.idBap = idBap;
        this.nipDosen = nipDosen;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull DetailBap model) {
        int status = model.getStatus();
        String nama = model.getName();
        String idDokumen = getSnapshots().getSnapshot(position).getId();

        holder.tvNamaMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statRef.collection("statistik").document("kelas")
                        .collection(KelasMatkul).document(model.getId_mahasiswa())
                        .collection("jadwal").whereEqualTo("id",idMatkul).limit(1).addSnapshotListener((queryDocumentSnapshots, e) -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots){
                        Jadwal jadwal = new Jadwal();
                        jadwal.setPertemuan(doc.getLong("pertemuan").intValue());
                        Pertemuan = (int) jadwal.getPertemuan();
                    }
                });
            }
        });

        DocumentReference reference = FirebaseFirestore.getInstance()
                .collection("dosen").document(idDosen)
                .collection("bap").document(idBap)
                .collection("mahasiswa").document(idDokumen);

        FirebaseFirestore.getInstance()
                .collection("jadwalDosen")
                .document(nipDosen)
                .collection("jadwal")
                .whereEqualTo("hari", getNameDay())
                .whereLessThan("waktu_mulai", getHour())
                .orderBy("waktu_mulai", Query.Direction.DESCENDING)
                .limit(1)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Jadwal jadwal = new Jadwal();
                        jadwal.setId(document.getString("id"));
                        jadwal.setKelas(document.getString("kelas"));
                        idMatkul = jadwal.getId();
                        KelasMatkul = jadwal.getKelas();
                    }
                }
            }
        });

        HashMap<String, Object> update = new HashMap<>();
        HashMap<String, Object> updateStat = new HashMap<>();


        if (status == 1) {
            //hadir
            holder.radioHadir.setChecked(true);
        } else if (status == 2) {
            //izin
            holder.radioIzin.setChecked(true);

        } else if (status == 3) {
            //sakit
            holder.radioSakit.setChecked(true);
        } else {
            //alfa
            holder.radioAlfa.setChecked(true);
        }


        holder.radioIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statRef.collection("statistik").document("kelas")
                        .collection(KelasMatkul).document(model.getId_mahasiswa())
                        .collection("jadwal").whereEqualTo("id",idMatkul).limit(1).addSnapshotListener((queryDocumentSnapshots, e) -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots){
                        Jadwal jadwal = new Jadwal();
                        jadwal.setPertemuan(doc.getLong("pertemuan").intValue());
                        Pertemuan = (int) jadwal.getPertemuan();
                    }
                });

                docReff = FirebaseFirestore.getInstance().collection("statistik").document("kelas")
                        .collection(KelasMatkul).document(model.getId_mahasiswa())
                        .collection("jadwal").document(idMatkul);

                update.put("status", 2);
                reference.update(update);
//                updateStat.put("pertemuan", Pertemuan + 1);
//                docReff.update(updateStat);
            }
        });

        holder.radioHadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statRef.collection("statistik").document("kelas")
                        .collection(KelasMatkul).document(model.getId_mahasiswa())
                        .collection("jadwal").whereEqualTo("id",idMatkul).limit(1).addSnapshotListener((queryDocumentSnapshots, e) -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots){
                        Jadwal jadwal = new Jadwal();
                        jadwal.setPertemuan(doc.getLong("pertemuan").intValue());
                        Pertemuan = (int) jadwal.getPertemuan();

                    }
                });

                update.put("status", 1);
                reference.update(update);

                docReff = FirebaseFirestore.getInstance().collection("statistik").document("kelas")
                        .collection(KelasMatkul).document(model.getId_mahasiswa())
                        .collection("jadwal").document(idMatkul);

                updateStat.put("pertemuan", Pertemuan + 1);
                docReff.update(updateStat);

            }
        });

        holder.radioAlfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statRef.collection("statistik").document("kelas")
                        .collection(KelasMatkul).document(model.getId_mahasiswa())
                        .collection("jadwal").whereEqualTo("id",idMatkul).limit(1).addSnapshotListener((queryDocumentSnapshots, e) -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots){
                        Jadwal jadwal = new Jadwal();
                        jadwal.setPertemuan(doc.getLong("pertemuan").intValue());
                        Pertemuan = (int) jadwal.getPertemuan();

                    }
                });

                update.put("status", 0);
                reference.update(update);

                docReff = FirebaseFirestore.getInstance().collection("statistik").document("kelas")
                        .collection(KelasMatkul).document(model.getId_mahasiswa())
                        .collection("jadwal").document(idMatkul);

                updateStat.put("pertemuan", Pertemuan - 1);
                docReff.update(updateStat);

            }
        });

        holder.radioSakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statRef.collection("statistik").document("kelas")
                        .collection(KelasMatkul).document(model.getId_mahasiswa())
                        .collection("jadwal").whereEqualTo("id",idMatkul).limit(1).addSnapshotListener((queryDocumentSnapshots, e) -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots){
                        Jadwal jadwal = new Jadwal();
                        jadwal.setPertemuan(doc.getLong("pertemuan").intValue());
                        Pertemuan = (int) jadwal.getPertemuan();
                    }
                });

                docReff = FirebaseFirestore.getInstance().collection("statistik").document("kelas")
                        .collection(KelasMatkul).document(model.getId_mahasiswa())
                        .collection("jadwal").document(idMatkul);

                update.put("status", 3);
                reference.update(update);
//                updateStat.put("pertemuan", Pertemuan + 1);
//                docReff.update(updateStat);
            }
        });

        int ai = position + 1;
        holder.tvNo.setText(String.valueOf(ai));
        holder.tvNamaMahasiswa.setText(nama);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mahasiswa_bap,
                parent, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvNo;
        final TextView tvNamaMahasiswa;
        final RadioGroup radioKehadiran;
        final RadioButton radioHadir, radioSakit, radioIzin, radioAlfa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNo = itemView.findViewById(R.id.tvNomor_bap);
            tvNamaMahasiswa = itemView.findViewById(R.id.tvNamaMahasiswa_bap);
            radioKehadiran = itemView.findViewById(R.id.radio_kehadiran_bap);
            radioHadir = itemView.findViewById(R.id.radio_hadir_bap);
            radioSakit = itemView.findViewById(R.id.radio_sakit_bap);
            radioIzin = itemView.findViewById(R.id.radio_izin_bap);
            radioAlfa = itemView.findViewById(R.id.radio_alfa_bap);
        }
    }
}
