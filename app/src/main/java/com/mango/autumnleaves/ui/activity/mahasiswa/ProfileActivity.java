package com.mango.autumnleaves.ui.activity.mahasiswa;

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
import com.mango.autumnleaves.model.mahasiswa.UserMahasiswa;
import com.mango.autumnleaves.ui.activity.base.BaseActivity;
import com.mango.autumnleaves.util.Session;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends BaseActivity {

    private TextView tvUsername, tvNamaLengkap, tvNim, tvAlamat, tvKelas, tvJurusan, tvTTL, tvKontak, tvEmailUser;
    private ImageView mBack, mProfile;
    private View progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUsername = findViewById(R.id.etusername);
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
        progressDialog.setVisibility(View.GONE);

        mBack = findViewById(R.id.imv_back);
        mProfile = findViewById(R.id.profileImg);

        tvUsername.setText(getNamaMhs());
        tvNamaLengkap.setText(getNamaMhs());
        tvEmailUser.setText(firebaseAuth.getCurrentUser().getEmail());
        tvKontak.setText(getTlpMhs());
        tvTTL.setText(getTtlMhs());
        tvAlamat.setText(getAlamatMhs());
        tvKelas.setText(getKelasMhs());
        tvJurusan.setText(getJurusanMhs());
        tvNim.setText(getNimMhs());
        Picasso.get().load(getProfileMhs()).into(mProfile);

        mBack.setOnClickListener(v -> {
            Intent back = new Intent(ProfileActivity.this, AccountActivity.class);
            startActivity(back);
        });
    }
}
