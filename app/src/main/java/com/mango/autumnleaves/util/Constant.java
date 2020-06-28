package com.mango.autumnleaves.util;

import android.content.ContentResolver;
import android.provider.Settings;

public class Constant {

    // Constant Dosen
    public static final String KEY_SESSION_DOSENNIP = "KEY_SESSION_DOSENNIP";
    public static final String KEY_SESSION_DOSENNAMA = "KEY_SESSION_DOSENNAMA";
    public static final String KEY_SESSION_DOSENUSERNAME = "KEY_SESSION_DOSENUSERNAME";
    public static final String KEY_SESSION_DOSENPASSWORD = "KEY_SESSION_DOSENPASSWORD";
    public static final String KEY_SESSION_DOSENTELP = "KEY_SESSION_DOSENTELP";
    public static final String KEY_SESSION_DOSENKELAMIN = "KEY_SESSION_DOSENKELAMIN";
    public static final String KEY_SESSION_DOSENTTL = "KEY_SESSION_DOSENTTL";
    public static final String KEY_SESSION_DOSENALAMAT = "KEY_SESSION_DOSENALAMAT";
    public static final String KEY_SESSION_DOSENTAG = "KEY_SESSION_DOSENTAG";

    // Constant Mahasiswa
    public static final String KEY_SESSION_MAHASISWA_NIM = "KEY_SESSION_MAHASISWA_NIM";
    public static final String KEY_SESSION_MAHASISWA_NAMA = "KEY_SESSION_MAHASISWA_NAMA";
    public static final String KEY_SESSION_MAHASISWA_USERNAME = "KEY_SESSION_MAHASISWA_USERNAME";
    public static final String KEY_SESSION_MAHASISWA_PASSWORD = "KEY_SESSION_MAHASISWA_PASSWORD";
    public static final String KEY_SESSION_MAHASISWA_TELP = "KEY_SESSION_MAHASISWA_TELP";
    public static final String KEY_SESSION_MAHASISWA_KELAMIN = "KEY_SESSION_MAHASISWA_KELAMIN";
    public static final String KEY_SESSION_MAHASISWA_TTL = "KEY_SESSION_MAHASISWA_TTL";
    public static final String KEY_SESSION_MAHASISWA_ALAMAT = "KEY_SESSION_MAHASISWA_ALAMAT";
    public static final String KEY_SESSION_MAHASISWA_TAG = "KEY_SESSION_MAHASISWA_TAG";
    public static final String KEY_SESSION_MAHASISWA_STATUS = "KEY_SESSION_MAHASISWA_STATUS";
    public static final String KEY_SESSION_MAHASISWA_GAMBAR = "KEY_SESSION_MAHASISWA_GAMBAR";
    public static final String KEY_SESSION_MAHASISWA_DEVICE = "KEY_SESSION_MAHASISWA_DEVICE";

    public static final String KEY_IS_LOGIN = "KEY_IS_LOGIN";
    public static final String KEY_UID = "KEY_UID";
    public static final String KEY_MAHASISWA_KELAS = "KEY_MAHASISWA_KELAS";
    public static final String KEY_MAHASISWA_JURUSAN = "KEY_MAHASISWA_JURUSAN";
    public static final String TAG_USER_MAHASISWA = "2";
    public static final String TAG_USER_DOSEN = "1";

    public static final String SESI_AKTIF = "1";
    public static final String SESI_TIDAK_AKTIF = "0";

    public static String deviceId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

    private static ContentResolver getContentResolver() {
        return getContentResolver();
    }

    public static final String DEVICE_ID = deviceId;

}
