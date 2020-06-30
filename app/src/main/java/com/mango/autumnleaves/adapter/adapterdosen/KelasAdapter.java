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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.Presensi;

import java.util.ArrayList;

public class KelasAdapter extends RecyclerView.Adapter<KelasAdapter.ViewHolder> {

    private ClickHandler mClickHandler;
    private Context mContext;
    private ArrayList<Presensi> mData;
    private ArrayList<String> mDataId;
    private ArrayList<String> mSelectedId;
    private View mEmptyView;

    public KelasAdapter(Context context, ArrayList<Presensi> data, ArrayList<String> dataId, View emptyView, ClickHandler handler) {
        mContext = context;
        mData = data;
        mDataId = dataId;
        mEmptyView = emptyView;
        mClickHandler = handler;
        mSelectedId = new ArrayList<>();
    }

    public void updateEmptyView() {
        if (mData.size() == 0)
            mEmptyView.setVisibility(View.VISIBLE);
        else
            mEmptyView.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_mahasiswa, parent, false);
        return new KelasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Presensi presensi = mData.get(position);

        holder.tvNamaMahasiswa.setText(presensi.getNama());
        int ai = position + 1;
        holder.tvNo.setText(String.valueOf(ai));

        holder.itemView.setSelected(mSelectedId.contains(mDataId.get(position)));

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("data").child(presensi.getKelas());


        int statusMahasiswa = presensi.getStatus();
        String key = mDataId.get(position);
        String kelas = presensi.getKelas();

        if (statusMahasiswa == 1) {
            //hadir
            holder.radioHadir.setChecked(true);
        } else if (statusMahasiswa == 2) {
            //izin
            holder.radioIzin.setChecked(true);
        } else if (statusMahasiswa == 3) {
            //sakit
            holder.radioSakit.setChecked(true);
        } else {
            //alfa
            holder.radioAlfa.setChecked(true);
        }

        holder.radioSakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // kalau radio sakit di tekan
                changeStatus(key, kelas, 3);
            }
        });

        holder.radioAlfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(key, kelas, 0);
            }
        });

        holder.radioIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(key, kelas, 2);
            }
        });

        holder.radioHadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(key, kelas, 1);
            }
        });
    }

    private void changeStatus(String key, String kelas, int status) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("data").child(kelas).child(key).child("status");
        database.setValue(status);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final TextView tvNo;
        final TextView tvNamaMahasiswa;
        final RadioGroup radioKehadiran;
        final RadioButton radioHadir , radioSakit , radioIzin , radioAlfa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNo = itemView.findViewById(R.id.tvNomor);
            tvNamaMahasiswa = itemView.findViewById(R.id.tvNamaMahasiswa);
            radioKehadiran = itemView.findViewById(R.id.radio_kehadiran);
            radioHadir = itemView.findViewById(R.id.radio_hadir);
            radioAlfa = itemView.findViewById(R.id.radio_alfa);
            radioIzin = itemView.findViewById(R.id.radio_izin);
            radioSakit = itemView.findViewById(R.id.radio_sakit);

            // focusable sengaja di false biar ngga bisa di klik
            itemView.setFocusable(false);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public interface ClickHandler {
        void onItemClick(int position);
        boolean onItemLongClick(int position);
    }

}
