package com.mango.autumnleaves.adapter.adapterdosen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.adapter.adaptermahasiswa.HistoryAdapter;
import com.mango.autumnleaves.model.Bap;
import com.mango.autumnleaves.model.History;

import java.util.List;

public class BapAdapter extends RecyclerView.Adapter<BapAdapter.ViewHolder> {

    private Context mContext;
    private List<Bap> mData;

    public BapAdapter(Context mContext, List<Bap> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.list_bap_dosen,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvBapMatakuliah.setText(mData.get(position).getMatakuliah());
        holder.tvBapRuangan.setText(mData.get(position).getRuangan());
        holder.tvBapTanggal.setText(mData.get(position).getWaktu());
        holder.tvBapWaktu.setText(mData.get(position).getJam());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBapMatakuliah;
        TextView tvBapRuangan;
        TextView tvBapTanggal;
        TextView tvBapWaktu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBapMatakuliah = itemView.findViewById(R.id.tvBapMatakuliah);
            tvBapRuangan = itemView.findViewById(R.id.tvBapRuangan);
            tvBapTanggal = itemView.findViewById(R.id.tvBapTanggal);
            tvBapWaktu = itemView.findViewById(R.id.tvBapWaktu);
        }
    }
}
