package com.mango.autumnleaves.activity.mahasiswa;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.UserMahasiswa;
import com.mango.autumnleaves.activity.base.BaseActivity;
import com.mango.autumnleaves.util.Session;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends BaseActivity {

    private TextView tvUsername, tvNamaLengkap, tvNim, tvAlamat, tvKelas, tvJurusan, tvTTL, tvKontak, tvEmailUser;
    private ImageView mBack, mProfile;
    private View progressDialog;
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        session = new Session(getApplicationContext());

        tvUsername = findViewById(R.id.tvUser);
        tvNamaLengkap = findViewById(R.id.tvNamaLengkap);
        tvNim = findViewById(R.id.tvNim);
        tvKelas = findViewById(R.id.tvKelas);
        tvAlamat = findViewById(R.id.tvAlamat);
        tvJurusan = findViewById(R.id.tvJurusan);
        tvKelas = findViewById(R.id.tvKelas);
        tvKontak = findViewById(R.id.tvKontak);
        tvTTL = findViewById(R.id.tvTTL);
        tvEmailUser = findViewById(R.id.tvProfileEmail);
        progressDialog = findViewById(R.id.progressBarProfile);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        mBack = findViewById(R.id.imv_back);
        mProfile = findViewById(R.id.profileImg);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(ProfileActivity.this, InfoActivity.class);
                startActivity(back);
            }
        });
        tvEmailUser.setText(firebaseAuth.getCurrentUser().getEmail());
        progressDialog.setVisibility(View.VISIBLE);
        getprofile();
    }

    private void getprofile() {
        DocumentReference docRef = firebaseFirestore.collection("user").document(getFirebaseUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    progressDialog.setVisibility(View.GONE);
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UserMahasiswa userMahasiswa = new UserMahasiswa();
                        userMahasiswa.setNama(document.getString("nama"));
                        userMahasiswa.setNim_mhs(document.getString("nim_mhs"));
                        userMahasiswa.setAlamat(document.getString("alamat"));
                        userMahasiswa.setTelp(document.getString("telp"));
                        userMahasiswa.setTtl(document.getString("ttl"));
                        userMahasiswa.setKelamin(document.getString("jenis_kelamin"));
                        userMahasiswa.setKode_kelas(document.getString("kode_kelas"));
                        userMahasiswa.setGambar(document.getString("gambar"));
                        userMahasiswa.setJurusan(document.getString("jurusan"));

                        tvUsername.setText(userMahasiswa.getNama());
                        tvNamaLengkap.setText(userMahasiswa.getNama());
                        tvKontak.setText(userMahasiswa.getTelp());
                        tvTTL.setText(userMahasiswa.getTtl());
                        tvAlamat.setText(userMahasiswa.getAlamat());
                        tvKelas.setText(userMahasiswa.getKode_kelas());
                        tvJurusan.setText(userMahasiswa.getJurusan());
                        tvNim.setText(userMahasiswa.getNim_mhs());
                        Picasso.get().load(userMahasiswa.getGambar()).into(mProfile);
                    } else {
                        Log.d("gagal", "Documment tidak ada");
                    }
                } else {
                    Log.d("TAG", "gagal", task.getException());
                }
            }
        });
    }
}
