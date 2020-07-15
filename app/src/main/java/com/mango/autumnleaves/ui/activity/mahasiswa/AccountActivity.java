package com.mango.autumnleaves.ui.activity.mahasiswa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.ui.activity.AboutActivity;
import com.mango.autumnleaves.ui.activity.base.BaseActivity;
import com.mango.autumnleaves.model.mahasiswa.UserMahasiswa;
import com.mango.autumnleaves.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import com.shreyaspatil.MaterialDialog.interfaces.OnCancelListener;
import com.shreyaspatil.MaterialDialog.interfaces.OnDismissListener;
import com.shreyaspatil.MaterialDialog.interfaces.OnShowListener;
import com.squareup.picasso.Picasso;

import static com.mango.autumnleaves.util.FunctionHelper.Func.getHour;
import static com.mango.autumnleaves.util.FunctionHelper.Func.getNameDay;

public class AccountActivity extends BaseActivity implements View.OnClickListener, OnShowListener, OnCancelListener, OnDismissListener {

    private TextView tvInfoUsername ;
    private LinearLayout linearProfile , tvInfoLogout , tvInfoMango , linearStatistik;
    private ImageView imvInfoUsername;
    private MaterialDialog logoutDialog;
    public String dataKelas = "";
    public String dataId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        imvInfoUsername = findViewById(R.id.imvInfoUser);
        tvInfoUsername = findViewById(R.id.tvInfoUsername);
        tvInfoLogout = findViewById(R.id.tvLogoutMhs);
        tvInfoMango = findViewById(R.id.tvInfoMango);
        linearProfile = findViewById(R.id.linearProfile);
        linearStatistik = findViewById(R.id.linearStatistik);

        tvInfoUsername.setText(getNamaMhs());
        Picasso.get().load(getProfileMhs()).into(imvInfoUsername);

        firebaseFirestore.collection("jadwal").document(getJurusanMhs()).collection("kelas").document(getKelasMhs()).collection("jadwal")
                .whereEqualTo("hari", getNameDay())
                .whereLessThan("waktu_mulai", getHour())
                .orderBy("waktu_mulai", Query.Direction.DESCENDING)
                .limit(1).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("TAGQUERYSNAPHSOT", "Listen failed.", e);
                    return;
                }
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Jadwal jadwal = new Jadwal();
                    jadwal.setId(documentSnapshot.getString("id"));
                    jadwal.setKelas(documentSnapshot.getString("kelas"));

                    dataKelas = jadwal.getKelas();
                    dataId = jadwal.getId();

                }
            }
        });

        logoutDialog = new MaterialDialog.Builder(this)
                .setTitle("Logout Dialog")
                .setMessage("Apakah Kamu Yakin Akan Keluar Dari Mango?")
                .setCancelable(false)
                .setPositiveButton("Keluar", R.drawable.ic_power_settings_new_black_24dp, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logout();
                        showSuccessToast("Berhasil Keluar");
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Batalkan", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Toast.makeText(getApplicationContext(), "Dibatalkan", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                })
                .build();

        linearProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(AccountActivity.this, ProfileActivity.class);
                startActivity(profile);
            }
        });

        linearStatistik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StatistikActivity.class);
                intent.putExtra("KELAS",dataKelas);
                intent.putExtra("DATAID",dataId);
                startActivity(intent);
            }
        });

        tvInfoLogout.setOnClickListener(this::onClick);

        tvInfoMango.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent about = new Intent(AccountActivity.this, AboutActivity.class);
                startActivity(about);
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
            case R.id.tvLogoutMhs:
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