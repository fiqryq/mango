package com.mango.autumnleaves.adapter.adapterdosen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mango.autumnleaves.R;

import java.util.ArrayList;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mData;

    public MahasiswaAdapter(Context mContext, ArrayList<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.list_mahasiswa,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Presensi presensi = mData.get(position);
        holder.tvNamaMahasiswa.setText(mData.get(position));
        holder.tvJamPresensi.setText(mData.get(position));
        int ai = position + 1;
        holder.tvNo.setText(String.valueOf(ai));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvNo;
        TextView tvNamaMahasiswa;
        TextView tvJamPresensi;
        TextView tvStatusPresensi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNo = itemView.findViewById(R.id.tvNomor);
            tvNamaMahasiswa = itemView.findViewById(R.id.tvNamaMahasiswa);
            tvJamPresensi = itemView.findViewById(R.id.tvJamPresensi);
        }
    }
}
