package edu.quangtk.TongHopGK.Models;

public class SinhVien {
    private int mssv;
    private String name;
    private float diemTB;

    // Constructor
    public SinhVien(int mssv, String name, float diemTB) {
        this.mssv = mssv;
        this.name = name;
        this.diemTB = diemTB;
    }

    // Getters và Setters
    public int getmssv() {
        return mssv;
    }

    public void setmssv(int mssv) {
        this.mssv = mssv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getdiemTB() {
        return diemTB;
    }

    public void setdiemTB(float diemTB) {
        this.diemTB = diemTB;
    }
}
