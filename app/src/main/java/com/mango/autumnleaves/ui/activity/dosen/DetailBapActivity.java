package com.mango.autumnleaves.ui.activity.dosen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.mango.autumnleaves.R;

public class DetailBapActivity extends AppCompatActivity {
    private TextView mBapMatakuliah , mBapRuangan , mBapWaktu , mBapJam , mBapMateri , mbaPJumlahMhs , mBapPertemuan , mBapKelas , mBapCatatan;
    private TextView mHadir,mIzin,mAlfa,mSakit;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_bap,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_bap:
                Intent intent = new Intent(DetailBapActivity.this,EditBapActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
        mbaPJumlahMhs = findViewById(R.id.bapHadir);
        mBapKelas = findViewById(R.id.bapKelas);
        mHadir = findViewById(R.id.bapMhsHadir);
        mIzin = findViewById(R.id.bapMhsIzin);
        mAlfa = findViewById(R.id.bapMhsAlfa);
        mSakit = findViewById(R.id.bapMhsSakit);
        mBapCatatan = findViewById(R.id.bapCatatan);

        Intent intent = getIntent();
        String matakuliah = intent.getStringExtra("MATAKULIAH");
        String ruangan = intent.getStringExtra("RUANGAN");
        String tanggal = intent.getStringExtra("TANGGAL");
        String waktuPresensi = intent.getStringExtra("WAKTU");
        String materi = intent.getStringExtra("MATERI");
        int pertemuan = intent.getIntExtra("PERTEMUAN",0);
        int jumlahmhs = intent.getIntExtra("JUMLAHMHS",0);
        int hadir = intent.getIntExtra("HADIR",0);
        int sakit = intent.getIntExtra("SAKIT",0);
        int izin = intent.getIntExtra("IZIN",0);
        int alfa = intent.getIntExtra("ALFA",0);
        String kelas = intent.getStringExtra("KELAS");
        String catatan = intent.getStringExtra("CATATAN");

        mBapMatakuliah.setText(": " + matakuliah);
        mBapRuangan.setText(": " + ruangan);
        mBapWaktu.setText(": " + tanggal);
        mBapJam.setText(": " + waktuPresensi);
        mBapMateri.setText(": " + materi);
        mBapPertemuan.setText(": " + pertemuan);
        mbaPJumlahMhs.setText(": " + jumlahmhs);
        mBapKelas.setText(": " + kelas);
        mHadir.setText(": " + hadir);
        mSakit.setText(": " + sakit);
        mAlfa.setText(": " + alfa);
        mIzin.setText(": " + izin);
        mBapCatatan.setText(": " + catatan);

    }
}
