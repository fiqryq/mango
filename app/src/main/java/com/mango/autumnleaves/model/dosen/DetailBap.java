package com.mango.autumnleaves.model.dosen;

public class DetailBap {
    private String nama;
    private int status;

    public DetailBap(String nama, int status) {
        this.nama = nama;
        this.status = status;
    }

    public DetailBap() {
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
