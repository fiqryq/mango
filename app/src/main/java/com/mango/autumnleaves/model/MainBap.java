package com.mango.autumnleaves.model;

public class MainBap {
    private String id_dosen;
    private String mata_kuliah;
    private String ruangan;
    private String kelas;
    private String waktu;
    private String materi;
    private String catatan;
    private int pertemuan;
    private int status;

    public MainBap(String id_dosen, String mata_kuliah, String ruangan, String kelas, String waktu, String materi, String catatan, int pertemuan, int status) {
        this.id_dosen = id_dosen;
        this.mata_kuliah = mata_kuliah;
        this.ruangan = ruangan;
        this.kelas = kelas;
        this.waktu = waktu;
        this.materi = materi;
        this.catatan = catatan;
        this.pertemuan = pertemuan;
        this.status = status;
    }

    public MainBap() {
    }

    public String getId_dosen() {
        return id_dosen;
    }

    public void setId_dosen(String id_dosen) {
        this.id_dosen = id_dosen;
    }

    public String getMata_kuliah() {
        return mata_kuliah;
    }

    public void setMata_kuliah(String mata_kuliah) {
        this.mata_kuliah = mata_kuliah;
    }

    public String getRuangan() {
        return ruangan;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getMateri() {
        return materi;
    }

    public void setMateri(String materi) {
        this.materi = materi;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public int getPertemuan() {
        return pertemuan;
    }

    public void setPertemuan(int pertemuan) {
        this.pertemuan = pertemuan;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
