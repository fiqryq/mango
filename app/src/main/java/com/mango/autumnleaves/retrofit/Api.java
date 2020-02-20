package com.mango.autumnleaves.retrofit;

import com.mango.autumnleaves.model.Response;
import com.mango.autumnleaves.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @GET("api/mahasiswa.php")
    Call<User> getUser();

    @FormUrlEncoded
    @POST("api/login.php")
    Call<Response> getResponse(
            @Field("username") String username,
            @Field("password") String password
    );
}
