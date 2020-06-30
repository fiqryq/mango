package com.mango.autumnleaves.adapter.adapterdosen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.Room;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Room> mData;
    private FirebaseFirestore firebaseFirestore;
    public String mKelas;
    public String mdocId;

    public RoomAdapter(Context mContext, ArrayList<Room> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.list_mahasiswa,parent,false);
        return new RoomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNamaMahasiswa.setText(mData.get(position).getNama());
        int ai = position + 1;
        holder.tvNo.setText(String.valueOf(ai));


//        firebaseFirestore
//                .collection("room")
//                .document("kelas")
//                .collection("41-03")
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()){
//                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
//                        Room room = new Room();
//                        room.setStatus(documentSnapshot.getLong("status").intValue());
//
//                        int checkedId = holder.radioKehadiran.getCheckedRadioButtonId();
//                        if (checkedId == 1) {
//                            holder.radioKehadiran.isActivated();
//                        }
//
//                    }
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaMahasiswa;
        TextView tvNo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaMahasiswa = itemView.findViewById(R.id.tvNamaMahasiswa);
            tvNo = itemView.findViewById(R.id.tvNomor);

        }
    }
}
