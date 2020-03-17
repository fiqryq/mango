package com.mango.autumnleaves.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.Jadwal;

import java.util.List;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.ViewHolder> {

    private Context mContext;
    private List<Jadwal> mData;

    public JadwalAdapter(Context mContext, List<Jadwal> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.listitem_jadwal,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.hari.setText(mData.get(position).getHari());
        holder.matakuliah.setText(mData.get(position).getMatakuliah());
        holder.dosen.setText(mData.get(position).getDosen());
        holder.ruangan.setText(mData.get(position).getRuangan());
        holder.waktu.setText(mData.get(position).getWaktu_mulai());
        holder.waktu_selesai.setText(mData.get(position).getWaktu_selesai());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView hari;
        TextView matakuliah;
        TextView dosen;
        TextView ruangan;
        TextView waktu;
        TextView waktu_selesai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            hari = itemView.findViewById(R.id.tvHari);
            matakuliah = itemView.findViewById(R.id.tvMatakuliah);
            dosen = itemView.findViewById(R.id.tvDosen);
            ruangan = itemView.findViewById(R.id.tvRuangan);
            waktu = itemView.findViewById(R.id.tvWaktu);
            waktu_selesai = itemView.findViewById(R.id.tvWaktuSelesai);
        }
    }
}
