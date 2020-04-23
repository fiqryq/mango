package com.mango.autumnleaves.ui.activity.dosen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.ui.activity.base.BaseActivity;
import com.mango.autumnleaves.model.dosen.UserDosen;
import com.squareup.picasso.Picasso;

public class ProfileDosenActivity extends BaseActivity {

    private TextView tvUsername, tvNamaLengkap, tvNip, tvAlamat, tvKelas, tvJurusan, tvTTL, tvKontak, tvEmailUser;
    private ImageView mBack, mProfile;
    private View progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_dosen);

        mBack = findViewById(R.id.imv_backDosen);
        tvUsername = findViewById(R.id.tvUserDosen);
        tvNamaLengkap = findViewById(R.id.tvNamaLengkapDosen);
        tvNip = findViewById(R.id.tvNip);
        tvKontak = findViewById(R.id.tvKontakDosen);
        tvAlamat = findViewById(R.id.tvAlamatDosen);
        tvEmailUser = findViewById(R.id.tvProfileEmailDosen);
        mProfile = findViewById(R.id.profileImgDosen);
        progressDialog = findViewById(R.id.progressBarProfile);
        progressDialog.setVisibility(View.VISIBLE);

        tvEmailUser.setText(firebaseAuth.getCurrentUser().getEmail());
        getProfile();
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(ProfileDosenActivity.this,MainDosenActivity.class);
                startActivity(back);
            }
        });
    }

    private void getProfile(){
        DocumentReference docRef = firebaseFirestore.collection("user").document(getFirebaseUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    progressDialog.setVisibility(View.GONE);
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        UserDosen userDosen = new UserDosen();
                        userDosen.setUsername(document.getString("nama"));
                        userDosen.setNama(document.getString("namalengkap"));
                        userDosen.setAlamat(document.getString("alamat"));
                        userDosen.setTelp(document.getString("telp"));
                        userDosen.setNip(document.getString("nip"));
                        userDosen.setTtl(document.getString("ttl"));
                        userDosen.setGambar(document.getString("gambar"));

                        tvUsername.setText(userDosen.getUsername());
                        tvNamaLengkap.setText(userDosen.getNama());
                        tvAlamat.setText(userDosen.getAlamat());
                        tvKontak.setText(userDosen.getTelp());
                        tvNip.setText(userDosen.getNip());
                        Picasso.get().load(userDosen.getGambar()).into(mProfile);
                    }
                }
            }
        });
    }
}
