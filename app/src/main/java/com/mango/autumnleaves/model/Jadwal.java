package com.mango.autumnleaves.model;

public class Jadwal {

    private String id;
    private String hari;
    private String matakuliah;
    private String dosen;
    private String ruangan;
    private String jurusan;
    private String waktu_mulai;
    private String waktu_selesai;
    private long posisi;
    private long pertemuan;
    private long jumlah_pertemuan;
    private String docId;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public long getPertemuan() {
        return pertemuan;
    }

    public void setPertemuan(long pertemuan) {
        this.pertemuan = pertemuan;
    }

    public long getJumlah_pertemuan() {
        return jumlah_pertemuan;
    }

    public void setJumlah_pertemuan(long jumlah_pertemuan) {
        this.jumlah_pertemuan = jumlah_pertemuan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPosisi() {
        return posisi;
    }

    public void setPosisi(long posisi) {
        this.posisi = posisi;
    }

    public String getSesi() {
        return sesi;
    }

    public void setSesi(String sesi) {
        this.sesi = sesi;
    }
    private String sesi;


    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }
    private String kelas;

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getMatakuliah() {
        return matakuliah;
    }

    public void setMatakuliah(String matakuliah) {
        this.matakuliah = matakuliah;
    }

    public String getDosen() {
        return dosen;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }

    public String getRuangan() {
        return ruangan;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
    }

    public String getWaktu_mulai() {
        return waktu_mulai;
    }

    public void setWaktu_mulai(String waktu_mulai) {
        this.waktu_mulai = waktu_mulai;
    }

    public String getWaktu_selesai() {
        return waktu_selesai;
    }

    public void setWaktu_selesai(String waktu_selesai) {
        this.waktu_selesai = waktu_selesai;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }
}
