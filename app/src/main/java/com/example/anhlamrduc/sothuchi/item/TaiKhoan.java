package com.example.anhlamrduc.sothuchi.item;

/**
 * Created by AnhlaMrDuc on 20-Mar-16.
 */
public class TaiKhoan {

    private int maTaiKhoan;
    private String tenTaiKhoan;
    private double soTienBanDau;
    private double soTienHienCo;
    private String ghiChu;
    private LoaiTaiKhoan loaiTaiKhoan;

    public int getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(int maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public double getSoTienBanDau() {
        return soTienBanDau;
    }

    public void setSoTienBanDau(double soTienBanDau) {
        this.soTienBanDau = soTienBanDau;
    }

    public double getSoTienHienCo() {
        return soTienHienCo;
    }

    public void setSoTienHienCo(double soTienHienCo) {
        this.soTienHienCo = soTienHienCo;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public LoaiTaiKhoan getLoaiTaiKhoan() {
        return loaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(LoaiTaiKhoan loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    public TaiKhoan() {
    }

    public TaiKhoan(int maTaiKhoan, String tenTaiKhoan, double soTienBanDau, double soTienHienCo, String ghiChu, LoaiTaiKhoan loaiTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
        this.tenTaiKhoan = tenTaiKhoan;
        this.soTienBanDau = soTienBanDau;
        this.soTienHienCo = soTienHienCo;
        this.ghiChu = ghiChu;
        this.loaiTaiKhoan = loaiTaiKhoan;
    }
}
