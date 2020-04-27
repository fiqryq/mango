package com.mango.autumnleaves.ui.activity.mahasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mango.autumnleaves.R;

public class DataHistoryActivity extends AppCompatActivity {

    private TextView mDataMatakuliah , mDataRuangan , mDataTanggal , mDataWaktu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_history);
        mDataMatakuliah = findViewById(R.id.dataHistoryMatakuliah);
        mDataRuangan = findViewById(R.id.dataHistoryRuangan);
        mDataTanggal = findViewById(R.id.dataHistoryTanggal);
        mDataWaktu = findViewById(R.id.dataHistoryWaktuPresensi);

        Intent intent = getIntent();
        String matakuliah = intent.getStringExtra("MATAKULIAH");
        String ruangan = intent.getStringExtra("RUANGAN");
        String tanggal = intent.getStringExtra("TANGGAL");
        String waktuPresensi = intent.getStringExtra("WAKTU");

        mDataMatakuliah.setText(matakuliah);
        mDataRuangan.setText(ruangan);
        mDataTanggal.setText(tanggal);
        mDataWaktu.setText(waktuPresensi);
    }
}
