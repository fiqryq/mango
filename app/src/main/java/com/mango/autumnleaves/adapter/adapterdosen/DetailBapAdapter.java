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

import java.util.List;

public class DetailBapAdapter extends RecyclerView.Adapter<DetailBapAdapter.ViewHolder> {

    private Context mContext;
    private List<DetailBap> mData;

    public DetailBapAdapter(Context mContext, List<DetailBap> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.list_mahasiswa_bap, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNamaMahasiswa.setText(mData.get(position).getName());
        int status = mData.get(position).getStatus();

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvNo;
        final TextView tvNamaMahasiswa;
        final RadioGroup radioKehadiran;
        final RadioButton radioHadir , radioSakit , radioIzin , radioAlfa;

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
