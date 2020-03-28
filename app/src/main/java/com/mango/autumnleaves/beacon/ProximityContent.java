package com.mango.autumnleaves.beacon;

public class ProximityContent {
    private String kelas;
    private String idbeacon;

    public String getLokasi() {
        return lokasi;
    }

    private String lokasi;
    private int id;
    private String hari;
    private String matakuliah;
    private String dosen;

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public void setIdbeacon(String idbeacon) {
        this.idbeacon = idbeacon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getMatakuliah() {
        return matakuliah;
    }

    public void setMatakuliah(String matakuliah) {
        this.matakuliah = matakuliah;
    }

    public String getDosen() {
        return dosen;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }

    public String getRuangan() {
        return ruangan;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getWaktu_selesai() {
        return waktu_selesai;
    }

    public void setWaktu_selesai(String waktu_selesai) {
        this.waktu_selesai = waktu_selesai;
    }

    private String ruangan;

    public ProximityContent(String kelas, String idbeacon, int id, String hari, String matakuliah, String dosen, String ruangan, String waktu, String waktu_selesai) {
        this.kelas = kelas;
        this.idbeacon = idbeacon;
        this.id = id;
        this.hari = hari;
        this.matakuliah = matakuliah;
        this.dosen = dosen;
        this.ruangan = ruangan;
        this.waktu = waktu;
        this.waktu_selesai = waktu_selesai;
    }

    private String waktu;
    private String waktu_selesai;


    public ProximityContent(String kelas, String idbeacon , String lokasi) {
        this.kelas = kelas;
        this.idbeacon = idbeacon;
        this.lokasi = lokasi;
    }

    public String getKelas() {
        return kelas;
    }

    public String getIdbeacon() {
        return idbeacon;
    }
}
