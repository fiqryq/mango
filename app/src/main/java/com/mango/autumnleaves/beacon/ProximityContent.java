package com.mango.autumnleaves.beacon;

public class ProximityContent {

    private String kelas;
    private String matakuliah;

    ProximityContent(String kelas, String matakuliah) {
        this.kelas = kelas;
        this.matakuliah = matakuliah;
    }

    String getKelas() {
        return kelas;
    }

    String getMatakuliah() {
        return matakuliah;
    }
}
