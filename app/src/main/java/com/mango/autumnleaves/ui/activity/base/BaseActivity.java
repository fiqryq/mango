package com.mango.autumnleaves.ui.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mango.autumnleaves.model.SesiKelas;
import com.mango.autumnleaves.model.dosen.UserDosen;
import com.mango.autumnleaves.ui.activity.LoginActivity;
import com.mango.autumnleaves.util.Constant;
import com.mango.autumnleaves.util.Session;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import static com.mango.autumnleaves.util.FunctionHelper.Func.getHour;
import static com.mango.autumnleaves.util.FunctionHelper.Func.getNameDay;

public abstract class BaseActivity extends AppCompatActivity {

    protected BaseActivity mActivity;
    protected Session mSession;
    protected FirebaseAuth firebaseAuth;
    protected FirebaseUser firebaseUser;
    protected FirebaseFirestore firebaseFirestore;
    protected String kelasGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mSession = new Session(this);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    protected void showToast(String text) {
        DynamicToast.makeSuccess(this, text).show();
    }

    protected void showSuccessToast(String text) {
        DynamicToast.makeSuccess(this, text).show();
    }

    protected void showErrorToast(String text) {
        DynamicToast.makeError(this, text).show();
    }

    protected void logoutApps() {
        mSession.removeSession();
        finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    protected String getFirebaseUserId() {
        return mSession.getPreferences().getString(Constant.KEY_UID, "");
    }

    protected String getKelasMhs(){
        return mSession.getPreferences().getString(Constant.KEY_MAHASISWA_KELAS,"");
    }

    protected String getJurusanMhs(){
        return mSession.getPreferences().getString(Constant.KEY_MAHASISWA_JURUSAN,"");
    }

    protected String getNamaMhs(){
        return mSession.getPreferences().getString(Constant.KEY_SESSION_MAHASISWA_NAMA,"");
    }

    protected String getProfileMhs(){
        return mSession.getPreferences().getString(Constant.KEY_SESSION_MAHASISWA_GAMBAR,"");
    }

    protected String gettglLahirMhs(){
        return mSession.getPreferences().getString(Constant.KEY_SESSION_MAHASISWA_TGL_LAHIR,"");
    }

    protected String getNimMhs(){
        return mSession.getPreferences().getString(Constant.KEY_SESSION_MAHASISWA_NIM,"");
    }

    protected String getTlpMhs(){
        return mSession.getPreferences().getString(Constant.KEY_SESSION_MAHASISWA_TELP,"");
    }

    protected String getTempatLahir(){
        return mSession.getPreferences().getString(Constant.KEY_SESSION_MAHASISWA_TEMPAT_LAHIR,"");
    }

    protected String getAlamatMhs(){
        return mSession.getPreferences().getString(Constant.KEY_SESSION_MAHASISWA_ALAMAT,"");
    }

    protected String getNipDosen(){
        return mSession.getPreferences().getString(Constant.KEY_SESSION_DOSENNIP,"");
    }

    protected String DeviceIdMahasiswa(){
        return mSession.getPreferences().getString(Constant.KEY_SESSION_MAHASISWA_DEVICE,"");
    }

    protected String getIdDosen(){
        return mSession.getPreferences().getString(Constant.KEY_SESSION_ID_DOSEN,"");
    }
}
