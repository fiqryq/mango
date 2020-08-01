package com.mango.autumnleaves.ui.activity.login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mango.autumnleaves.ui.activity.base.BaseActivity;
import com.mango.autumnleaves.ui.activity.dosen.MainDosenActivity;
import com.mango.autumnleaves.ui.activity.mahasiswa.DashboardMahasiswaActivity;
import com.mango.autumnleaves.model.dosen.UserDosen;
import com.mango.autumnleaves.model.mahasiswa.UserMahasiswa;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.callback.LoginViewCallback;
import com.mango.autumnleaves.util.Constant;
import com.mango.autumnleaves.util.CustomLoadingDialog;

public class LoginActivity extends BaseActivity implements LoginViewCallback {
    private EditText username, password;
    private ProgressDialog progressDialog;
    private View progres;
    final CustomLoadingDialog loadingDialog = new CustomLoadingDialog(LoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = findViewById(R.id.etusername);
        password = findViewById(R.id.etpassword);

        Button btLogin = findViewById(R.id.button_login);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowProgress();
                String mUsername = username.getText().toString();
                String mPassword = password.getText().toString();

                if (TextUtils.isEmpty(username.getText()) && TextUtils.isEmpty(password.getText())) {
                    showErrorToast("Username / Password Kosong");
                    onHideProgress();
                } else if (TextUtils.isEmpty(username.getText())) {
                    showErrorToast("Username Kosong");
                    onHideProgress();
                } else if (TextUtils.isEmpty(password.getText())) {
                    showErrorToast("Password Kosong");
                    onHideProgress();
                } else {
                    getAuthFirebase(mUsername, mPassword);
                }
            }
        });

        checkUserLogin(mSession.getPreferences().getString(Constant.KEY_IS_LOGIN, ""));
    }
    
    private void getAuthFirebase(String mUsername, String mPassword) {
        firebaseAuth.signInWithEmailAndPassword(mUsername, mPassword).addOnCompleteListener(task -> {
            task.addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    onFailedAuthFirebase();
                }
            });
            if (task.isSuccessful()) {
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
            userDosen.setGambar(documentSnapshot.getString("gambar"));
            userDosen.setuId(uid);
            mSession.setupSessionDosen(userDosen);

        } else if (userTag.equals(Constant.TAG_USER_MAHASISWA)) {
            UserMahasiswa userMahasiswa = new UserMahasiswa();
            userMahasiswa.setNama(documentSnapshot.getString("nama"));
            userMahasiswa.setNim_mhs(documentSnapshot.getString("nim_mhs"));
            userMahasiswa.setAlamat(documentSnapshot.getString("alamat"));
            userMahasiswa.setTelp(documentSnapshot.getString("telp"));
            userMahasiswa.setTempat_lahir(documentSnapshot.getString("tempat_lahir"));
            userMahasiswa.setKode_kelas(documentSnapshot.getString("kode_kelas"));
            userMahasiswa.setJurusan(documentSnapshot.getString("jurusan"));
            userMahasiswa.setTgl_lahir(documentSnapshot.getString("tgl_lahir"));
            userMahasiswa.setTag(documentSnapshot.getString("tag"));
            userMahasiswa.setGambar(documentSnapshot.getString("gambar"));
            userMahasiswa.setDeviceId(documentSnapshot.getString("deviceId"));
            userMahasiswa.setuId(uid);
            mSession.setupSessionMahasiswa(userMahasiswa);
        }
        checkUserLogin(mSession.getPreferences().getString(Constant.KEY_IS_LOGIN, ""));
    }

    private void checkUserLogin(String cekPengguna) {
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        onHideProgress();
        if (cekPengguna != null) {
            if (cekPengguna.equalsIgnoreCase(Constant.TAG_USER_MAHASISWA) && android_id.equalsIgnoreCase(DeviceIdMahasiswa())
            ) {
                Intent j = new Intent(LoginActivity.this, DashboardMahasiswaActivity.class);
                startActivity(j);
                finish();
            } else if (cekPengguna.equalsIgnoreCase(Constant.TAG_USER_DOSEN)) {
                Intent i = new Intent(LoginActivity.this, MainDosenActivity.class);
                startActivity(i);
                finish();
            }
        }

        if (cekPengguna.equalsIgnoreCase(Constant.TAG_USER_MAHASISWA) && !android_id.equalsIgnoreCase(DeviceIdMahasiswa())){
            loadingDialog.dismissDialog();
            showErrorToast("Device Id Tidak Cocok");
            logoutApps();
            firebaseAuth.signOut();
        }

    }

    @Override
    public void onBackPressed() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onSuccessAuthFirebase(String uid) {
        getFireStoreData(uid);
    }

    @Override
    public void onFailedAuthFirebase() {
        showErrorToast("Login Gagal");
        loadingDialog.dismissDialog();
    }

    @Override
    public void onShowProgress() {
        loadingDialog.startLoadingDialog();
    }

    @Override
    public void onHideProgress() {

    }
}