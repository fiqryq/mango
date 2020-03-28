package com.mango.autumnleaves.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mango.autumnleaves.activity.LoginActivity;
import com.mango.autumnleaves.util.Constant;
import com.mango.autumnleaves.util.Session;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.Calendar;
import java.util.Date;

public abstract class BaseActivity extends AppCompatActivity {

    protected BaseActivity mActivity;
    protected Session mSession;
    protected FirebaseAuth firebaseAuth;
    protected FirebaseUser firebaseUser;
    protected FirebaseFirestore firebaseFirestore;

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


}
