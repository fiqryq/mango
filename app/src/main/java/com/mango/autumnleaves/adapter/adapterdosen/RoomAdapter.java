package com.mango.autumnleaves.adapter.adapterdosen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.Room;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Room> mData;

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
