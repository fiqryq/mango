package com.mango.autumnleaves.remote;

public class Koneksi {
    public static final String URL = "http://mangodb.online/";

    public static final String login = URL + "api/login.php";

    // presensi
    public static final String presensi = URL + "api/presensi.php";
    public static final String presensi_post = URL + "api/presensi_tambah.php";
    public static final String presensi_pulang = URL + "api/presensi_update.php";
    public static final String presensi_cek = URL + "api/presensi_cek.php";

    // izin
    public static final String izin = URL + "api/izin.php";
    public static final String izin_post = URL + "api/izin_tambah.php";

    // Mahasiswa
    public static final String mahasiswa_post = URL + "api/mahasiswa_update.php";
    public static final String mahasiswa_profil = URL + "api/mahasiswa.php";
    public static final String mahasiswa_update = URL + "api/mahasiswa_update.php";

    public static final String gambar = URL + "gambar/izin/";
}
