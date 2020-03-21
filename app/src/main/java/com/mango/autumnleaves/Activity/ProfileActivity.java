package com.mango.autumnleaves.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.User;
import com.mango.autumnleaves.remote.Koneksi;
import com.mango.autumnleaves.remote.Volley;
import com.mango.autumnleaves.util.Session;
import com.mango.autumnleaves.util.Util;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import com.shreyaspatil.MaterialDialog.interfaces.OnCancelListener;
import com.shreyaspatil.MaterialDialog.interfaces.OnDismissListener;
import com.shreyaspatil.MaterialDialog.interfaces.OnShowListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, OnShowListener, OnCancelListener, OnDismissListener {

    private TextView tvUsername , tvNamaLengkap , tvNim , tvAlamat , tvKelas ,tvJurusan,tvTTL,tvKontak , tvEmailUser;
    private ImageView mBack,mLogout,mProfile;
    private View progressDialog;
    private MaterialDialog logoutDialog;
    private Session session;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        session = new Session(getApplicationContext());
        if (!session.loggedIn()) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

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
        mLogout = findViewById(R.id.imv_logout);
        mProfile = findViewById(R.id.profileImg);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(ProfileActivity.this,DashboardActivity.class);
                startActivity(back);
            }
        });
        tvEmailUser.setText(firebaseAuth.getCurrentUser().getEmail());

        progressDialog.setVisibility(View.VISIBLE);
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

        mLogout.setOnClickListener(this::onClick);
        getprofile();
    }

    private void getprofile(){
        String idUser;
        idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference docRef = firebaseFirestore.collection("user").document(idUser);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    progressDialog.setVisibility(View.GONE);
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        User user = new User();
                        user.setNama(document.getString("nama"));
                        user.setNim_mhs(document.getString("nim_mhs"));
                        user.setAlamat(document.getString("alamat"));
                        user.setTelp(document.getString("telp"));
                        user.setTtl(document.getString("ttl"));
                        user.setKelamin(document.getString("jenis_kelamin"));
                        user.setKode_kelas(document.getString("kode_kelas"));
                        user.setGambar(document.getString("gambar"));
                        user.setJurusan(document.getString("jurusan"));

                        tvUsername.setText(user.getNama());
                        tvNamaLengkap.setText(user.getNama());
                        tvKontak.setText(user.getTelp());
                        tvTTL.setText(user.getTtl());
                        tvAlamat.setText(user.getAlamat());
                        tvKelas.setText(user.getKode_kelas());
                        tvJurusan.setText(user.getJurusan());
                        tvNim.setText(user.getNim_mhs());
                        Picasso.get().load(user.getGambar()).into(mProfile);
                    } else {
                        Log.d("gagal", "Documment tidak ada");
                    }
                } else {
                    Log.d("TAG", "gagal", task.getException());
                }
            }
        });
    }

    private void logout(){
        session.setLoggedin(false);
        finish();
        Intent logout = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(logout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imv_logout:
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
