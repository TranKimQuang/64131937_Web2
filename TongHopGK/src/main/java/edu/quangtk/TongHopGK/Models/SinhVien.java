package edu.quangtk.TongHopGK.Models;

public class SinhVien {
    private Integer mssv; 
    private String name;
    private float diemTB;

    // Constructor
    public SinhVien(Integer mssv, String name, float diemTB) {
        this.mssv = mssv;
        this.name = name;
        this.diemTB = diemTB;
    }

    // Getters và Setters
    public Integer getMssv() {
        return mssv;
    }

    public void setMssv(Integer mssv) {
        this.mssv = mssv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDiemTB() {
        return diemTB;
    }

    public void setDiemTB(float diemTB) {
        this.diemTB = diemTB;
    }
}