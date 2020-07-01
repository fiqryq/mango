package com.mango.autumnleaves.adapter.adapterdosen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.Jadwal;

import java.util.ArrayList;
import java.util.List;

public class JadwalDosenAdapter extends RecyclerView.Adapter<JadwalDosenAdapter.ViewHolder> {

    private Context mContext;
    private List<Jadwal> mData;

    public JadwalDosenAdapter(Context mContext, List<Jadwal> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.list_jadwal_dosen,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.hari.setText(mData.get(position).getHari());
        holder.matakuliah.setText(mData.get(position).getMatakuliah());
        holder.dosen.setText(mData.get(position).getDosen());
        holder.ruangan.setText(mData.get(position).getRuangan());
        holder.waktu.setText(mData.get(position).getWaktu_mulai() + " " + mData.get(position).getWaktu_selesai() + " " + "Wib");
        holder.kelas.setText("Kelas" + " " + " : " + mData.get(position).getKelas());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView hari;
        TextView matakuliah;
        TextView dosen;
        TextView ruangan;
        TextView waktu;
        TextView kelas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            hari = itemView.findViewById(R.id.tvHari);
            matakuliah = itemView.findViewById(R.id.tvMatakuliah);
            dosen = itemView.findViewById(R.id.tvDosen);
            ruangan = itemView.findViewById(R.id.tvRuangan);
            waktu = itemView.findViewById(R.id.waktu);
            kelas = itemView.findViewById(R.id.tvKelas);
        }
    }
}
