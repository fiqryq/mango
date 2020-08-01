package com.mango.autumnleaves.ui.activity.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mango.autumnleaves.ui.activity.login.LoginActivity;
import com.mango.autumnleaves.util.Constant;
import com.mango.autumnleaves.util.Session;

public class BaseFragment extends Fragment {

    protected BaseActivity mActivity;
    protected Session mSession;
    protected FirebaseAuth firebaseAuth;
    protected FirebaseUser firebaseUser;
    protected FirebaseFirestore firebaseFirestore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        mSession = new Session(mActivity);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    protected void logoutApps() {
        mSession.removeSession();
        mActivity.finish();
        startActivity(new Intent(mActivity, LoginActivity.class));
    }

    protected String getFirebaseUserId() {
        return mSession.getPreferences().getString(Constant.KEY_UID, "");
    }

    protected String getUserKelas(){
        return mSession.getPreferences().getString(Constant.KEY_MAHASISWA_KELAS,"");
    }

    protected String getUserJurusan(){
        return mSession.getPreferences().getString(Constant.KEY_MAHASISWA_JURUSAN,"");
    }

    protected void showToast(String text) {
        mActivity.showToast(text);
    }

    protected String getNamaDosen(){
        return mSession.getPreferences().getString(Constant.KEY_SESSION_DOSENNAMA,"");
    }
    protected String getGambarDosen(){
        return mSession.getPreferences().getString(Constant.KEY_SESSION_GAMBAR,"");
    }



}
