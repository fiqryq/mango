package com.mango.autumnleaves.beacon;

public class ProximityContent {

    private String kelas;
    private String matakuliah;

    ProximityContent(String kelas, String matakuliah) {
        this.kelas = kelas;
        this.matakuliah = matakuliah;
    }

    String getTitle() {
        return kelas;
    }

    String getSubtitle() {
        return matakuliah;
    }
}
