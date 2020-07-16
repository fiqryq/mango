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
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.ui.activity.base.BaseFragment;
import com.mango.autumnleaves.adapter.adapterdosen.BapAdapter;
import com.mango.autumnleaves.model.Bap;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HostoryBapDosenFragment extends BaseFragment {

    private ArrayList<Bap> arrayList;
    RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bap_dosen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rvBapDosen);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        arrayList = new ArrayList<>();
        progressBar = view.findViewById(R.id.progressBarBap);
        showdata();
    }

//    private void showHistory() {
//        firebaseFirestore
//                .collection("bap")
//                .document(getFirebaseUserId())
//                .collection("data")
//                .orderBy("created", Query.Direction.DESCENDING)
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Bap Bap = new Bap();
//                        Bap.setMatakuliah(document.getString("matakuliah"));
//                        Bap.setRuangan(document.getString("ruangan"));
//                        Bap.setWaktu(document.getString("waktu"));
//                        Bap.setJam(document.getString("jam"));
//                        Bap.setMateri(document.getString("materi"));
//                        Bap.setPertemuan(document.getLong("pertemuan").intValue());
//                        Bap.setJumlahMhs(document.getLong("jumlahMhs").intValue());
//                        Bap.setKelas(document.getString("kelas"));
//                        Bap.setHadir(document.getLong("hadir").intValue());
//                        Bap.setAlfa(document.getLong("alfa").intValue());
//                        Bap.setSakit(document.getLong("sakit").intValue());
//                        Bap.setIzin(document.getLong("izin").intValue());
//                        Bap.setCatatan(document.getString("catatan"));
//                        arrayList.add(Bap);
//                    }
//                } else {
//                    Log.d("tes", "Error getting documents: ", task.getException());
//                }
////                setuprecyclerview(arrayList);
//            }
//        });
//    }

    private void showdata() {
        firebaseFirestore
                .collection("dosen")
                .document(getFirebaseUserId())
                .collection("bap")
                .orderBy("created", Query.Direction.DESCENDING)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressBar.setVisibility(View.GONE);
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                    Bap Bap = new Bap();
                    Bap.setMatakuliah(queryDocumentSnapshot.getString("matakuliah"));
                    Bap.setRuangan(queryDocumentSnapshot.getString("ruangan"));
                    Bap.setWaktu(queryDocumentSnapshot.getString("waktu"));
                    Bap.setJam(queryDocumentSnapshot.getString("jam"));
                    Bap.setMateri(queryDocumentSnapshot.getString("materi"));
                    Bap.setPertemuan(queryDocumentSnapshot.getLong("pertemuan").intValue());
                    Bap.setJumlahMhs(queryDocumentSnapshot.getLong("jumlahMhs").intValue());
                    Bap.setKelas(queryDocumentSnapshot.getString("kelas"));
                    Bap.setCatatan(queryDocumentSnapshot.getString("catatan"));
                    Bap.setId(queryDocumentSnapshot.getId());
                    arrayList.add(Bap);
                }
                setuprecyclerview(arrayList);
            }
        });
    }

    private void setuprecyclerview(ArrayList<Bap> arrayList) {
        BapAdapter bapAdapter = new BapAdapter(mActivity, arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(bapAdapter);
    }
}
