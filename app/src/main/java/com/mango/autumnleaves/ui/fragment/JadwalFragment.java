package com.mango.autumnleaves.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.mango.autumnleaves.adapter.adaptermahasiswa.JadwalAdapter;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.model.dosen.UserDosen;
import com.mango.autumnleaves.ui.activity.base.BaseFragment;

import java.util.ArrayList;

import static com.mango.autumnleaves.util.FunctionHelper.Func.getHour;
import static com.mango.autumnleaves.util.FunctionHelper.Func.getNameDay;

/**
 * A simple {@link Fragment} subclass.
 */
public class JadwalFragment extends BaseFragment {

    private ArrayList<Jadwal> arrayList;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jadwal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rvJadwalDosen);
        arrayList = new ArrayList<>();
        showJadwal();
    }

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
                                .orderBy("posisi")
                                .whereGreaterThan("posisi", 0)
                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                            Jadwal jadwal = new Jadwal();
                                            jadwal.setHari(document.getString("hari"));
                                            jadwal.setMatakuliah(document.getString("matakuliah"));
                                            jadwal.setDosen(document.getString("dosen"));
                                            jadwal.setJurusan(document.getString("jurusan"));
                                            jadwal.setKelas(document.getString("kelas"));
                                            jadwal.setRuangan(document.getString("ruangan"));
                                            jadwal.setWaktu_mulai(document.getString("waktu_mulai"));
                                            jadwal.setWaktu_selesai(document.getString("waktu_selesai"));
                                            arrayList.add(jadwal);
                                        }
                                        setuprecyclerView(arrayList);
                                    }
                                });
                    }
                }

            }

            private void setuprecyclerView(ArrayList<Jadwal> arrayList) {
                JadwalAdapter jadwalAdapter = new JadwalAdapter(getContext(), arrayList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(jadwalAdapter);
            }
        });
    }
}