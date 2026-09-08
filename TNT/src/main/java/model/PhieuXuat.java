/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package model;

import java.sql.Timestamp;
import java.util.List;

public class PhieuXuat {

    private int maPX;
    private int maKH;
    private int maNguoiDung;
    private Timestamp ngayXuat;
    private double tongTien;
    private String ghiChu;

    // Bổ sung các trường phục vụ hiển thị trên JSP
    private String tenKH;
    private String tenNguoiDung;
    private List<ChiTietPhieuXuat> chiTietList;

    public PhieuXuat() {
    }

    public PhieuXuat(int maPX, int maKH, int maNguoiDung, Timestamp ngayXuat, double tongTien, String ghiChu) {
        this.maPX = maPX;
        this.maKH = maKH;
        this.maNguoiDung = maNguoiDung;
        this.ngayXuat = ngayXuat;
        this.tongTien = tongTien;
        this.ghiChu = ghiChu;
    }

    public int getMaPX() {
        return maPX;
    }

    public void setMaPX(int maPX) {
        this.maPX = maPX;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public Timestamp getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(Timestamp ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public List<ChiTietPhieuXuat> getChiTietList() {
        return chiTietList;
    }

    public void setChiTietList(List<ChiTietPhieuXuat> chiTietList) {
        this.chiTietList = chiTietList;
    }
}
