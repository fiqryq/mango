package com.mango.autumnleaves.model.mahasiswa;

public class StatistikOld {
    private String id;
    private String matakuliah;
    private String dosen;
    private long pertemuan;
    private long jumlah_pertemuan;

    public long getPertemuan() {
        return pertemuan;
    }

    public void setPertemuan(long pertemuan) {
        this.pertemuan = pertemuan;
    }

    public String getDosen() {
        return dosen;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatakuliah() {
        return matakuliah;
    }

    public void setMatakuliah(String matakuliah) {
        this.matakuliah = matakuliah;
    }


    public long getJumlah_pertemuan() {
        return jumlah_pertemuan;
    }

    public void setJumlah_pertemuan(long jumlah_pertemuan) {
        this.jumlah_pertemuan = jumlah_pertemuan;
    }
}
