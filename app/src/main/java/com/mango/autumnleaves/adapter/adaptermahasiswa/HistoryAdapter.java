package com.mango.autumnleaves.adapter.adaptermahasiswa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.History;
import com.mango.autumnleaves.ui.activity.mahasiswa.DetailHistoryActivity;

import java.util.List;

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

        holder.tvHistoryTanggal.setText(mData.get(position).getTanggal());

        String matakuliah = mData.get(position).getMatakuliah();
        String ruangan = mData.get(position).getRuangan();
        String tanggal = mData.get(position).getTanggal();
        String waktu = mData.get(position).getWaktu();

        holder.tableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailHistoryActivity.class);
                intent.putExtra("MATAKULIAH",matakuliah);
                intent.putExtra("RUANGAN",ruangan);
                intent.putExtra("TANGGAL",tanggal);
                intent.putExtra("WAKTU",waktu);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvHistoryTanggal;
        TableLayout tableLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHistoryTanggal = itemView.findViewById(R.id.tvHistoryTanggal);
            tableLayout = itemView.findViewById(R.id.tablePresensi);
        }
    }
}
