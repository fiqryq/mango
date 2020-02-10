package com.mango.autumnleaves.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.Presensi;

import java.util.List;

public class PresensiAdapter extends ArrayAdapter<Presensi> {

    Context context;
    List<Presensi> list;

    public PresensiAdapter(Context context, List<Presensi> objects) {
        super(context, R.layout.presensi_content_beacon, objects);
        this.context = context;
        list = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater layoutInflater;

            layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.presensi_content_beacon, null);
        }

        View v = convertView;

        final Presensi data = list.get(position);

//        TextView nama = (TextView) v.findViewById(R.id.tv);
//        TextView ruangan = (TextView) v.findViewById(R.id.lokasi);
//        TextView tanggal = (TextView) v.findViewById(R.id.tanggal);
//        TextView waktu = (TextView) v.findViewById(R.id.waktu);
//
//        nama.setText(data.getNama());
//        lokasi.setText(data.getLokasi());
//        tanggal.setText(data.getTanggal());
//        waktu.setText(data.getWaktu());

        return v;

    }


}
