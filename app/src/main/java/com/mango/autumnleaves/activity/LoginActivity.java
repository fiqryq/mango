package com.mango.autumnleaves.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mango.autumnleaves.activity.base.BaseActivity;
import com.mango.autumnleaves.activity.dosen.MainDosenActivity;
import com.mango.autumnleaves.activity.mahasiswa.DashboardMahasiswaActivity;
import com.mango.autumnleaves.model.UserDosen;
import com.mango.autumnleaves.model.UserMahasiswa;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.callback.LoginViewCallback;
import com.mango.autumnleaves.util.Constant;

public class LoginActivity extends BaseActivity implements LoginViewCallback {
    private EditText username, password;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.tvUser);
        password = findViewById(R.id.etpassword);
        Button btLogin = findViewById(R.id.button_login);

        btLogin.setOnClickListener(v -> {

            String mUsername = username.getText().toString();
            String mPassword = password.getText().toString();

            if (TextUtils.isEmpty(username.getText()) && TextUtils.isEmpty(password.getText())) {
                showToast("Username / Password Kosong");
            } else if (TextUtils.isEmpty(username.getText())) {
                showToast("Username Kosong");
            } else if (TextUtils.isEmpty(password.getText())) {
                showToast("Password Kosong");
            } else {
                getAuthFirebase(mUsername, mPassword);
            }

        });

        checkUserLogin(mSession.getPreferences().getString(Constant.KEY_IS_LOGIN, ""));
    }

    private void getAuthFirebase(String mUsername, String mPassword) {
        onShowProgress();
        firebaseAuth.signInWithEmailAndPassword(mUsername, mPassword).addOnCompleteListener(task -> {
            task.addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    onFailedAuthFirebase();
                }
            });

            if (task.isSuccessful()) {
                onHideProgress();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                onSuccessAuthFirebase(firebaseUser.getUid());
            } else {
                onFailedAuthFirebase();
            }
        });
    }

    private void getFireStoreData(String idUser) {
        DocumentReference docRef = firebaseFirestore.collection("user").document(idUser);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String userTag = document.getString("tag");
                    saveSession(idUser, userTag, document);
                } else {
                    Log.d("gagal", "Documment tidak ada");
                }
            } else {
                Log.d("TAG", "gagal", task.getException());
            }
        });
    }

    // Save Session
    private void saveSession(String uid, String userTag, DocumentSnapshot documentSnapshot) {
        if (userTag.equals(Constant.TAG_USER_DOSEN)) {
            UserDosen userDosen = new UserDosen();
            userDosen.setNama(documentSnapshot.getString("nama"));
            userDosen.setNip(documentSnapshot.getString("nip"));
            userDosen.setEmail(documentSnapshot.getString("email"));
            userDosen.setTag(documentSnapshot.getString("tag"));
            userDosen.setuId(uid);
            mSession.setupSessionDosen(userDosen);
        } else if (userTag.equals(Constant.TAG_USER_MAHASISWA)) {
            UserMahasiswa userMahasiswa = new UserMahasiswa();
            userMahasiswa.setNama(documentSnapshot.getString("nama"));
            userMahasiswa.setNim_mhs(documentSnapshot.getString("nim_mhs"));
            userMahasiswa.setAlamat(documentSnapshot.getString("alamat"));
            userMahasiswa.setTelp(documentSnapshot.getString("telp"));
            userMahasiswa.setTtl(documentSnapshot.getString("ttl"));
            userMahasiswa.setKode_kelas(documentSnapshot.getString("kode_kelas"));
            userMahasiswa.setJurusan(documentSnapshot.getString("jurusan"));
            userMahasiswa.setTag(documentSnapshot.getString("tag"));
            userMahasiswa.setuId(uid);
            mSession.setupSessionMahasiswa(userMahasiswa);
        }
        checkUserLogin(mSession.getPreferences().getString(Constant.KEY_IS_LOGIN, ""));
    }

    private void checkUserLogin(String cekPengguna) {
        if (cekPengguna != null) {
            if (cekPengguna.equalsIgnoreCase(Constant.TAG_USER_MAHASISWA)) {
                Intent j = new Intent(LoginActivity.this, DashboardMahasiswaActivity.class);
                startActivity(j);
                finish();
            } else if (cekPengguna.equalsIgnoreCase(Constant.TAG_USER_DOSEN)) {
                Intent i = new Intent(LoginActivity.this, MainDosenActivity.class);
                startActivity(i);
                finish();
            }
        }

    }

    public ProgressDialog myProgressDialog() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return progressDialog;
    }

    @Override
    public void onBackPressed() {
        progressDialog.dismiss();
    }

    @Override
    public void onSuccessAuthFirebase(String uid) {
        getFireStoreData(uid);
    }

    @Override
    public void onFailedAuthFirebase() {
        showToast("Gagal Auth Firebase");
    }

    @Override
    public void onShowProgress() {
        myProgressDialog().show();
    }

    @Override
    public void onHideProgress() {
        myProgressDialog().dismiss();
    }
}