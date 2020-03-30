package com.mango.autumnleaves.adapter.adapterdosen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.activity.dosen.DetailOpenClassActivity;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.model.SesiKelas;

import java.util.List;

public class SesiDosenAdapter extends RecyclerView.Adapter <SesiDosenAdapter.ViewHolder> {

    private Context mContext;
    private List<SesiKelas> mData;

    public SesiDosenAdapter(Context mContext, List<SesiKelas> mData) {
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
        holder.tvRuangan.setText("Ruangan  " + mData.get(position).getRuangan());
        holder.tvAwalKuliah.setText("Jam  " + mData.get(position).getWaktu_mulai());
        holder.tvAakhirKuliah.setText(mData.get(position).getWaktu_selesai());
        holder.tvMatakuliah.setText(mData.get(position).getMatakuliah());


        holder.itemSesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(mContext,DetailOpenClassActivity.class));
            }
        });

        holder.SesiKelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean sesiAktif = true;
                boolean sesiMati = false;

                holder.SesiKelas.setText("Tutup Sesi");
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvRuangan;
        TextView tvAwalKuliah;
        TextView tvAakhirKuliah;
        TextView tvMatakuliah;
        Button SesiKelas;
        ConstraintLayout itemSesi;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             tvRuangan = itemView.findViewById(R.id.tvJadwalRuanganDosen);
             tvAwalKuliah = itemView.findViewById(R.id.tvJadwalWaktuMulaiDosen);
             tvAakhirKuliah = itemView.findViewById(R.id.tvJadwalWaktuSelesaiDosen);
             tvMatakuliah = itemView.findViewById(R.id.tvMatakuliahDosen);
             SesiKelas = itemView.findViewById(R.id.btnOpenSesi);
             itemSesi = itemView.findViewById(R.id.listItemSesi);
        }
    }
}
