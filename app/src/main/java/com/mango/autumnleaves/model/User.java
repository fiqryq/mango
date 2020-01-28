package com.mango.autumnleaves.model;

public class User {
    private Integer id_mahasiswa;
    private String nama;
    private String username;
    private String password;
    private String telp;
    private String kelamin;
    private String ttl;
    private String alamat;

    public User(Integer id_mahasiswa, String nama, String username, String password, String telp, String kelamin, String ttl, String alamat) {
        this.id_mahasiswa = id_mahasiswa;
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.telp = telp;
        this.kelamin = kelamin;
        this.ttl = ttl;
        this.alamat = alamat;
    }

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

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
