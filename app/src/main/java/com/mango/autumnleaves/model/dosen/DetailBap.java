package com.mango.autumnleaves.model.dosen;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DetailBap {
    private String id_mahasiswa;
    private String name;
    private int status;

    public DetailBap(String id_mahasiswa, String name, int status) {
        this.id_mahasiswa = id_mahasiswa;
        this.name = name;
        this.status = status;
    }

    public DetailBap() {
    }

    public String getId_mahasiswa() {
        return id_mahasiswa;
    }

    public void setId_mahasiswa(String id_mahasiswa) {
        this.id_mahasiswa = id_mahasiswa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
