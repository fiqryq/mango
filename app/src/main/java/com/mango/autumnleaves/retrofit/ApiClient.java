package com.mango.autumnleaves.retrofit;

import android.content.Context;

import com.mango.autumnleaves.remote.Koneksi;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.net.URL;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit getApiClient(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Koneksi.URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }

    public static Retrofit getApiClient(Context context){
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new ChuckInterceptor(context)).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Koneksi.URL)
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();

        return retrofit;
    }

}
