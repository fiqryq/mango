package com.mango.autumnleaves.adapter.adapterdosen;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.activity.dosen.KelasTigaActivity;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.model.SesiKelas;
import com.mango.autumnleaves.model.UserMahasiswa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MahasiswaAdapter extends RecyclerView.Adapter <MahasiswaAdapter.ViewHolder> {

    private Context mContext;
    private List<UserMahasiswa> mData;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    private String hari, waktusekarang;

    public MahasiswaAdapter(Context mContext, List<UserMahasiswa> mData) {
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
        holder.tvNamaMahasiswa.setText(mData.get(position).getNama());
        holder.tvJumlahPertemuan.setText(mData.get(position).getJurusan());
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvNo;
        TextView tvNamaMahasiswa;
        TextView tvJumlahPertemuan;
        TextView tvStatus;
        Button btnSesi;
        Button btnSubmit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNo = itemView.findViewById(R.id.tvNomor);
            tvNamaMahasiswa = itemView.findViewById(R.id.tvNamaMahasiswa);
            tvJumlahPertemuan = itemView.findViewById(R.id.tvPertemuanMahasiswa);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }

}
