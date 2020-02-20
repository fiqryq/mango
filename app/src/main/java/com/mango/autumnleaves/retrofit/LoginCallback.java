package com.mango.autumnleaves.retrofit;

import com.mango.autumnleaves.model.User;

public interface LoginCallback {

    public void onSuccessLogin(User user);
    public void onFailedLogin();

}
