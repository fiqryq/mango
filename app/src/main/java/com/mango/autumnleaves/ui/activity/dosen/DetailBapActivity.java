package com.mango.autumnleaves.ui.activity.dosen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mango.autumnleaves.R;

public class DetailBapActivity extends AppCompatActivity {
    private TextView mBapMatakuliah , mBapRuangan , mBapWaktu , mBapJam , mBapMateri , mBaphadir , mBapPertemuan , mBapKelas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bap_class);

        mBapMatakuliah = findViewById(R.id.bapMatakuliah);
        mBapRuangan = findViewById(R.id.bapRuangan);
        mBapWaktu = findViewById(R.id.bapWaktu);
        mBapJam = findViewById(R.id.bapJam);
        mBapMateri = findViewById(R.id.bapMateri);
        mBapPertemuan = findViewById(R.id.bapPertemuan);
        mBaphadir = findViewById(R.id.bapHadir);
        mBapKelas = findViewById(R.id.bapKelas);

        Intent intent = getIntent();
        String matakuliah = intent.getStringExtra("MATAKULIAH");
        String ruangan = intent.getStringExtra("RUANGAN");
        String tanggal = intent.getStringExtra("TANGGAL");
        String waktuPresensi = intent.getStringExtra("WAKTU");
        String materi = intent.getStringExtra("MATERI");
        String pertemuan = intent.getStringExtra("PERTEMUAN");
        String hadir = intent.getStringExtra("HADIR");
        String kelas = intent.getStringExtra("KELAS");

        mBapMatakuliah.setText(": " + matakuliah);
        mBapRuangan.setText(": " + ruangan);
        mBapWaktu.setText(": " + tanggal);
        mBapJam.setText(": " + waktuPresensi);
        mBapMateri.setText(": " + materi);
        mBapPertemuan.setText(": " + pertemuan);
        mBaphadir.setText(": " + hadir);
        mBapKelas.setText(": " + kelas);
    }
}
