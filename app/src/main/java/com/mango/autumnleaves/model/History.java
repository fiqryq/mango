package com.mango.autumnleaves.model;

public class History {
   private int id_presensi;
   private int id_mahasiswa;
   private String tanggal;
   private String waktu;
   private String ruangan;

    private String matakuliah;

    public int getId_presensi() {
        return id_presensi;
    }

    public void setId_presensi(int id_presensi) {
        this.id_presensi = id_presensi;
    }

    public int getId_mahasiswa() {
        return id_mahasiswa;
    }

    public void setId_mahasiswa(int id_mahasiswa) {
        this.id_mahasiswa = id_mahasiswa;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getRuangan() {
        return ruangan;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
    }

    public String getMatakuliah() {
        return matakuliah;
    }

    public void setMatakuliah(String matakuliah) {
        this.matakuliah = matakuliah;
    }

}
