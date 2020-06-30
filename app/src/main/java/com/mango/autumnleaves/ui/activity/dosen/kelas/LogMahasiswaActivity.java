package com.mango.autumnleaves.ui.activity.dosen.kelas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.adapter.adapterdosen.KelasAdapter;
import com.mango.autumnleaves.model.Presensi;

import java.util.ArrayList;

public class LogMahasiswaActivity extends AppCompatActivity {

    private RecyclerView mPresensiRecycleview;
    private View emptyview;
    private KelasAdapter mAdapter;
    private ArrayList<Presensi> mData;
    private ArrayList<String> mDataId;
    private DatabaseReference mDatabase;
    public String kelas;
    private ProgressBar progressBarLoadMhs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_mahasiswa);
        Intent intent = getIntent();
        kelas = intent.getStringExtra("DATAKELAS");
        progressBarLoadMhs = findViewById(R.id.progressMahasiswa);
        LogPresensi();
    }

    private void LogPresensi() {
        emptyview = findViewById(R.id.textView_empty_viiew);
        mPresensiRecycleview = findViewById(R.id.rvMahasiswa);

        mData = new ArrayList<>();
        mDataId = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("data").child(kelas);
        mDatabase.addChildEventListener(childEventListener);

        mPresensiRecycleview.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(true);
        mPresensiRecycleview.setLayoutManager(linearLayoutManager);

        mAdapter = new KelasAdapter(this, mData, mDataId, emptyview, new KelasAdapter.ClickHandler() {
            @Override
            public void onItemClick(int position) {
                //ketika rec di click
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        mPresensiRecycleview.setAdapter(mAdapter);
    }

    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            mData.add(dataSnapshot.getValue(Presensi.class));
            mDataId.add(dataSnapshot.getKey());
            mAdapter.updateEmptyView();
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            int pos = mDataId.indexOf(dataSnapshot.getKey());
            mData.set(pos, dataSnapshot.getValue(Presensi.class));
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            int pos = mDataId.indexOf(dataSnapshot.getKey());
            mDataId.remove(pos);
            mData.remove(pos);
            mAdapter.updateEmptyView();
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
