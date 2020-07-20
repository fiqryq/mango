package com.mango.autumnleaves.model.mahasiswa;

public class UserMahasiswa {
    private Integer id_mahasiswa;
    private String nim_mhs;
    private String nama;
    private String username;
    private String password;
    private String telp;
    private String kelamin;
    private String tempat_lahir;
    private String alamat;
    private String kode_kelas;
    private String uId;
    private String deviceId;
    private String tgl_lahir;

    public UserMahasiswa(Integer id_mahasiswa, String nim_mhs, String nama, String username, String password, String telp, String kelamin, String tempat_lahir, String alamat, String kode_kelas, String uId, String deviceId, String tgl_lahir, String status, String tag, String gambar, String jurusan) {
        this.id_mahasiswa = id_mahasiswa;
        this.nim_mhs = nim_mhs;
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.telp = telp;
        this.kelamin = kelamin;
        this.tempat_lahir = tempat_lahir;
        this.alamat = alamat;
        this.kode_kelas = kode_kelas;
        this.uId = uId;
        this.deviceId = deviceId;
        this.tgl_lahir = tgl_lahir;
        this.status = status;
        this.tag = tag;
        this.gambar = gambar;
        this.jurusan = jurusan;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public void setTempat_lahir(String tempat_lahir) {
        this.tempat_lahir = tempat_lahir;
    }

    public UserMahasiswa() {
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    private String tag;

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    private String gambar;

    public String getNim_mhs() {
        return nim_mhs;
    }

    public void setNim_mhs(String nim_mhs) {
        this.nim_mhs = nim_mhs;
    }

    public String getKode_kelas() {
        return kode_kelas;
    }

    public void setKode_kelas(String kode_kelas) {
        this.kode_kelas = kode_kelas;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    private String jurusan;

    public Integer getId_mahasiswa() {
        return id_mahasiswa;
    }

    public void setId_mahasiswa(Integer id_mahasiswa) {
        this.id_mahasiswa = id_mahasiswa;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getKelamin() {
        return kelamin;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
