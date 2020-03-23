package com.mango.autumnleaves.activity.mahasiswa;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mango.autumnleaves.activity.base.BaseActivity;
import com.mango.autumnleaves.model.UserMahasiswa;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.util.Session;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import com.shreyaspatil.MaterialDialog.interfaces.OnCancelListener;
import com.shreyaspatil.MaterialDialog.interfaces.OnDismissListener;
import com.shreyaspatil.MaterialDialog.interfaces.OnShowListener;
import com.squareup.picasso.Picasso;

public class InfoActivity extends BaseActivity implements View.OnClickListener, OnShowListener, OnCancelListener, OnDismissListener {

    private TextView tvInfoUsername, tvInfoLogout, tvInfoMango;
    private ImageView imvInfoUsername;
    private MaterialDialog logoutDialog;
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        imvInfoUsername = findViewById(R.id.imvInfoUser);
        tvInfoUsername = findViewById(R.id.tvInfoUsername);
        tvInfoLogout = findViewById(R.id.tvInfoLogout);
        tvInfoMango = findViewById(R.id.tvInfoMango);

        getprofile();
        logoutDialog = new MaterialDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Apakah Kamu Yakin Akan Logout Dari Mango?")
                .setCancelable(false)
                .setPositiveButton("Logout", R.drawable.ic_power_settings_new_black_24dp, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logout();
                        Toast.makeText(getApplicationContext(), "Berhasil Logout", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Toast.makeText(getApplicationContext(), "Dibatalkan", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                })
                .build();
        tvInfoUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(InfoActivity.this, ProfileActivity.class);
                startActivity(profile);
            }
        });
        tvInfoLogout.setOnClickListener(this::onClick);
        tvInfoMango.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent about = new Intent(InfoActivity.this,AboutActivity.class);
                startActivity(about);
            }
        });

    }

    private void getprofile() {
        DocumentReference docRef = firebaseFirestore.collection("user").document(getFirebaseUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UserMahasiswa userMahasiswa = new UserMahasiswa();
                        userMahasiswa.setNama(document.getString("nama"));
                        userMahasiswa.setGambar(document.getString("gambar"));
                        tvInfoUsername.setText(userMahasiswa.getNama());
                        Picasso.get().load(userMahasiswa.getGambar()).into(imvInfoUsername);
                    } else {
                        Log.d("gagal", "Documment tidak ada");
                    }
                } else {
                    Log.d("TAG", "gagal", task.getException());
                }
            }
        });
    }

    private void logout() {
        firebaseAuth.signOut();
        logoutApps();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvInfoLogout:
                logoutDialog.show();
        }
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {

    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

    }

    @Override
    public void onShow(DialogInterface dialogInterface) {

    }
}