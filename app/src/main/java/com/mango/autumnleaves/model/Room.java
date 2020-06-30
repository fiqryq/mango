package com.mango.autumnleaves.model;

public class Room {
    private String nama;
    private int status;
    private Room kelas;
    private String userId;

    public Room getKelas() {
        return kelas;
    }

    public void setKelas(Room kelas) {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
