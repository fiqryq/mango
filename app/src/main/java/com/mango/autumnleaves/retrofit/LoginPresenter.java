package com.mango.autumnleaves.retrofit;

import android.content.Context;

import com.mango.autumnleaves.model.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginPresenter {
    private LoginCallback callback;

    public LoginPresenter(LoginCallback callback) {
        this.callback = callback;
    }

    public void login(Context context, String username , String password){
        Api api = ApiClient.getApiClient(context).create(Api.class);
        Call<Response> responseCall = api.getResponse(username,password);
        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                callback.onSuccessLogin(response.body().getUser());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                callback.onFailedLogin();
            }
        });
    }
}
