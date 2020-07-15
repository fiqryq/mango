package com.mango.autumnleaves.adapter.adaptermahasiswa;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.Statistik;

public class StatistikAdapter extends FirestoreRecyclerAdapter<Statistik, StatistikAdapter.ViewHolder> {

    private String mKelas;

    public StatistikAdapter(@NonNull FirestoreRecyclerOptions<Statistik> options, String mKelas) {
        super(options);
        this.mKelas = mKelas;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Statistik model) {
        Log.d("CHECK_ADAPTER", mKelas);

        // 0 untuk 41-03
        // 1 untuk 41-04

        DocumentReference firestore = FirebaseFirestore.getInstance().collection("matakuliah").document(mKelas);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_statistik, parent, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView Matakuliah;
        final TextView Dosen;
        final TextView Pertemuan;
        final TextView Persentase;
        final ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Matakuliah = itemView.findViewById(R.id.statMatakuliah);
            Dosen = itemView.findViewById(R.id.statDosen);
            Pertemuan = itemView.findViewById(R.id.statPertemuan);
            Persentase = itemView.findViewById(R.id.statPersentase);
            progressBar = itemView.findViewById(R.id.ststProgressBar);
        }
    }
}
