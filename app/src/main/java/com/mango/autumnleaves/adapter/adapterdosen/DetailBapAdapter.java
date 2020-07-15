package com.mango.autumnleaves.adapter.adapterdosen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.dosen.DetailBap;

public class DetailBapAdapter extends FirestoreRecyclerAdapter<DetailBap, DetailBapAdapter.DetailBapHolder> {

    private Context mContext;

    public DetailBapAdapter(@NonNull FirestoreRecyclerOptions<DetailBap> options, Context mContext) {
        super(options);
        this.mContext = mContext;
    }

    @Override
    protected void onBindViewHolder(@NonNull DetailBapHolder holder, int position, @NonNull DetailBap model) {
        Log.d("CHECK_ADAPTER", String.valueOf(position));
    }

    @NonNull
    @Override
    public DetailBapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_mahasiswa_bap, parent, false);
        return new DetailBapAdapter.DetailBapHolder(view);
    }

    class DetailBapHolder extends RecyclerView.ViewHolder {
        final TextView tvNo;
        final TextView tvNamaMahasiswa;
        final RadioGroup radioKehadiran;
        final RadioButton radioHadir , radioSakit , radioIzin , radioAlfa;

        public DetailBapHolder(@NonNull View itemView) {
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
