package com.example.anhlamrduc.sothuchi.item;

/**
 * Created by AnhlaMrDuc on 20-Mar-16.
 */
public class LoaiTaiKhoan {

    private int maLoai;
    private String tenLoai;
    private String hinhAnh;

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public LoaiTaiKhoan() {
    }

    public LoaiTaiKhoan(int maLoai, String tenLoai, String hinhAnh) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.hinhAnh = hinhAnh;
    }

}
