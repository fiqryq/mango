package com.mango.autumnleaves.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.History;
import com.mango.autumnleaves.model.Jadwal;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context mContext;
    private List<History> mData;

    public HistoryAdapter(Context mContext, List<History> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.listitem_history,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvHistoryRuangan.setText(mData.get(position).getRuangan());
        holder.tvHistoryMatakuliah.setText(mData.get(position).getMatakuliah());
        holder.tvHistoryTanggal.setText(mData.get(position).getTanggal());
        holder.tvHistoryWaktu.setText(mData.get(position).getWaktu());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvHistoryMatakuliah;
        TextView tvHistoryRuangan;
        TextView tvHistoryTanggal;
        TextView tvHistoryWaktu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHistoryMatakuliah = itemView.findViewById(R.id.tvHistoryMatakuliah);
            tvHistoryRuangan = itemView.findViewById(R.id.tvHistoryRuangan);
            tvHistoryTanggal = itemView.findViewById(R.id.tvHistoryTanggal);
            tvHistoryWaktu = itemView.findViewById(R.id.tvHistoryWaktu);
        }
    }
}
