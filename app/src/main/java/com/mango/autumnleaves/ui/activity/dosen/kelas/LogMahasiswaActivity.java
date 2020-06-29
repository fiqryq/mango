package com.mango.autumnleaves.ui.activity.dosen.kelas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.adapter.adapterdosen.RoomAdapter;
import com.mango.autumnleaves.adapter.adaptermahasiswa.JadwalAdapter;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.model.Room;

import java.util.ArrayList;

public class LogMahasiswaActivity extends AppCompatActivity {

    private ArrayList<Room> arrayList;
    private RecyclerView recyclerView;
    public String kelas;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_mahasiswa);

        Intent intent = getIntent();
        kelas = intent.getStringExtra("DATAKELAS");

        recyclerView = findViewById(R.id.rvMahasiswa);
        firebaseFirestore = FirebaseFirestore.getInstance();
        arrayList = new ArrayList<>();

        ListMahasiswa();
    }

    private void ListMahasiswa(){
        firebaseFirestore
                .collection("room")
                .document("kelas")
                .collection(kelas)
                .orderBy("nama")
                .startAt("A")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshots : task.getResult()){
                        Room room = new Room();
                        room.setNama(documentSnapshots.getString("nama"));
                        arrayList.add(room);
                    }
                    setuprecyclerView(arrayList);
                }
            }
        });
    }

    private void setuprecyclerView(ArrayList<Room> arrayList) {
        RoomAdapter roomAdapter = new RoomAdapter(getBaseContext(), arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(roomAdapter);
    }
}
