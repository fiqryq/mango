package com.mango.autumnleaves.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.mango.autumnleaves.model.dosen.UserDosen;
import com.mango.autumnleaves.model.mahasiswa.UserMahasiswa;

public class Session {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public Session(Context context) {
        preferences = context.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setupSessionMahasiswa(UserMahasiswa userMahasiswa) {
        editor.putString(Constant.KEY_SESSION_MAHASISWA_ALAMAT, userMahasiswa.getAlamat());
        editor.putString(Constant.KEY_SESSION_MAHASISWA_NAMA, userMahasiswa.getNama());
        editor.putString(Constant.KEY_SESSION_MAHASISWA_NIM, userMahasiswa.getNim_mhs());
        editor.putString(Constant.KEY_SESSION_MAHASISWA_PASSWORD, userMahasiswa.getPassword());
        editor.putString(Constant.KEY_SESSION_MAHASISWA_TELP, userMahasiswa.getTelp());
        editor.putString(Constant.KEY_SESSION_MAHASISWA_TTL, userMahasiswa.getTtl());
        editor.putString(Constant.KEY_SESSION_MAHASISWA_USERNAME, userMahasiswa.getUsername());
        editor.putString(Constant.KEY_SESSION_MAHASISWA_TAG, userMahasiswa.getTag());
        editor.putString(Constant.KEY_SESSION_MAHASISWA_STATUS, userMahasiswa.getStatus());
        editor.putString(Constant.KEY_SESSION_MAHASISWA_KELAMIN, userMahasiswa.getKelamin());
        editor.putString(Constant.KEY_MAHASISWA_KELAS, userMahasiswa.getKode_kelas());
        editor.putString(Constant.KEY_MAHASISWA_JURUSAN, userMahasiswa.getJurusan());
        editor.putString(Constant.KEY_SESSION_MAHASISWA_GAMBAR, userMahasiswa.getGambar());
        editor.putString(Constant.KEY_SESSION_MAHASISWA_DEVICE, userMahasiswa.getDeviceId());
        editor.putString(Constant.KEY_IS_LOGIN, userMahasiswa.getTag());
        editor.putString(Constant.KEY_DEVICE_ID, userMahasiswa.getDeviceId());
        editor.putString(Constant.KEY_UID, userMahasiswa.getuId());
        editor.commit();
    }

    public void setupSessionDosen(UserDosen userDosen) {
        editor.putString(Constant.KEY_SESSION_DOSENALAMAT, userDosen.getAlamat());
        editor.putString(Constant.KEY_SESSION_DOSENNAMA, userDosen.getNama());
        editor.putString(Constant.KEY_SESSION_DOSENNIP, userDosen.getNip());
        editor.putString(Constant.KEY_SESSION_DOSENPASSWORD, userDosen.getPassword());
        editor.putString(Constant.KEY_SESSION_DOSENTELP, userDosen.getTelp());
        editor.putString(Constant.KEY_SESSION_DOSENTTL, userDosen.getTtl());
        editor.putString(Constant.KEY_SESSION_DOSENUSERNAME, userDosen.getUsername());
        editor.putString(Constant.KEY_SESSION_DOSENTAG, userDosen.getTag());
        editor.putString(Constant.KEY_SESSION_GAMBAR, userDosen.getGambar());
        editor.putString(Constant.KEY_SESSION_DOSENKELAMIN, userDosen.getKelamin());
        editor.putString(Constant.KEY_SESSION_DEVICE_ID, userDosen.getDeviceId());
        editor.putString(Constant.KEY_DEVICE_ID_DOSEN, userDosen.getDeviceId());
        editor.putString(Constant.KEY_SESSION_ID_DOSEN, userDosen.getIdDosen());
        editor.putString(Constant.KEY_IS_LOGIN, userDosen.getTag());
        editor.putString(Constant.KEY_UID, userDosen.getuId());

        editor.commit();
    }

    public void removeSession(){
        editor.clear();
        editor.commit();
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }
}
