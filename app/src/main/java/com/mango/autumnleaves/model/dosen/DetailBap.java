package com.mango.autumnleaves.model.dosen;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DetailBap {
    private String idMahasiswa;
    private String name;
    private int status;

    public DetailBap(String idMahasiswa, String name, int status) {
        this.idMahasiswa = idMahasiswa;
        this.name = name;
        this.status = status;
    }

    public DetailBap() {
    }

    public String getIdMahasiswa() {
        return idMahasiswa;
    }

    public void setIdMahasiswa(String idMahasiswa) {
        this.idMahasiswa = idMahasiswa;
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
