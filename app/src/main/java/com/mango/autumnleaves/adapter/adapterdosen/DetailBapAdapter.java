package com.mango.autumnleaves.adapter.adapterdosen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.dosen.DetailBap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailBapAdapter extends RecyclerView.Adapter<DetailBapAdapter.ViewHolder> {

    private Context mContext;
    private List<DetailBap> mData;
    private String mIdDosen;
    private String mIdBap;

    public DetailBapAdapter(Context mContext, List<DetailBap> mData, String mIdDosen, String mIdBap) {
        this.mContext = mContext;
        this.mData = mData;
        this.mIdDosen = mIdDosen;
        this.mIdBap = mIdBap;
    }

    public DetailBapAdapter() {

    }

    DocumentReference reference;
    Map<String, Object> dataBap;
    Map<String, Object> idMahasiswa;

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
        Log.d("CHECK_PARAMETER", mData.get(position).getIdMahasiswa());
        int ai = position + 1;
        holder.tvNo.setText(String.valueOf(ai));
        holder.tvNamaMahasiswa.setText(mData.get(position).getName());


        reference = FirebaseFirestore.getInstance().collection("dosen").document(mIdDosen).collection("bap").document(mIdBap);
        dataBap = new HashMap<>();
        idMahasiswa = new HashMap<>();

        int status = mData.get(position).getStatus();

        if (status == 1) {
            //hadir
            holder.radioHadir.setChecked(true);
        } else if (status == 2) {
            //izin
            holder.radioIzin.setChecked(true);
        } else if (status == 3) {
            //sakit
            holder.radioSakit.setChecked(true);
        } else {
            //alfa
            holder.radioAlfa.setChecked(true);
        }

        String nama = mData.get(position).getName();

        holder.radioSakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idMahasiswa.put(mData.get(position).getIdMahasiswa(), Arrays.asList(nama, 3));
            }
        });

        holder.radioAlfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idMahasiswa.put(mData.get(position).getIdMahasiswa(), Arrays.asList(nama, 0));
            }
        });

        holder.radioIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idMahasiswa.put(mData.get(position).getIdMahasiswa(), Arrays.asList(nama, 2));
            }
        });

        holder.radioHadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idMahasiswa.put(mData.get(position).getIdMahasiswa(), Arrays.asList(nama, 1));

            }
        });

        dataBap.put("mahasiswa", idMahasiswa);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void updateData() {
        reference.update(dataBap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(mContext, "Update Berhasil", Toast.LENGTH_SHORT).show();
            }
        });
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
