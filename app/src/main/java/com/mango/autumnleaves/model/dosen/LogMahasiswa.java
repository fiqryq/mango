package com.mango.autumnleaves.model.dosen;

public class LogMahasiswa {
    private String kelas;
    private String nama;
    private int status;

    public LogMahasiswa(String kelas, String nama, int status) {
        this.kelas = kelas;
        this.nama = nama;
        this.status = status;
    }

    public LogMahasiswa() {
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
