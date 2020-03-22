package com.mango.autumnleaves.callback;

public interface LoginViewCallback {

    void onSuccessAuthFirebase(String uid);

    void onFailedAuthFirebase();

    void onShowProgress();

    void onHideProgress();


}
