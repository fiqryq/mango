package com.mango.autumnleaves.ui.activity.dosen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mango.autumnleaves.R;

public class DetailBapActivity extends AppCompatActivity {
    private TextView mBapMatakuliah , mBapRuangan , mBapWaktu , mBapJam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bap_class);

        mBapMatakuliah = findViewById(R.id.bapMatakuliah);
        mBapRuangan = findViewById(R.id.bapRuangan);
        mBapWaktu = findViewById(R.id.bapWaktu);
        mBapJam = findViewById(R.id.bapJam);

        Intent intent = getIntent();
        String matakuliah = intent.getStringExtra("MATAKULIAH");
        String ruangan = intent.getStringExtra("RUANGAN");
        String tanggal = intent.getStringExtra("TANGGAL");
        String waktuPresensi = intent.getStringExtra("WAKTU");

        mBapMatakuliah.setText(": " + matakuliah);
        mBapRuangan.setText(": " + ruangan);
        mBapWaktu.setText(": " + tanggal);
        mBapJam.setText(": " + waktuPresensi);
    }
}
