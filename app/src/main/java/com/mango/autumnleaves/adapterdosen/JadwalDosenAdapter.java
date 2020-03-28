package com.mango.autumnleaves.adapterdosen;

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

public class JadwalDosenAdapter extends RecyclerView.Adapter <JadwalDosenAdapter.ViewHolder> {

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
        view = inflater.inflate(R.layout.list_home_dosen,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvRuangan.setText(mData.get(position).getRuangan());
        holder.tvAwalKuliah.setText(mData.get(position).getWaktu_mulai());
        holder.tvAakhirKuliah.setText(mData.get(position).getWaktu_selesai());
        holder.tvMatakuliah.setText(mData.get(position).getMatakuliah());
        holder.tvNamaDosen.setText(mData.get(position).getDosen());
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvRuangan;
        TextView tvAwalKuliah;
        TextView tvAakhirKuliah;
        TextView tvMatakuliah;
        TextView tvNamaDosen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             tvRuangan = itemView.findViewById(R.id.textView20);
             tvAwalKuliah = itemView.findViewById(R.id.textView21);
             tvAakhirKuliah = itemView.findViewById(R.id.textView24);
             tvMatakuliah = itemView.findViewById(R.id.textView22);
             tvNamaDosen = itemView.findViewById(R.id.textView25);
        }
    }
}
