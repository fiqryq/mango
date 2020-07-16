package com.mango.autumnleaves.model.dosen;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DetailBap {
    private String name;
    private int status;

    public DetailBap(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public DetailBap() {
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
