package model;

import java.sql.Date;

/**
 * Một dòng thống kê theo kỳ (ngày / tuần / tháng / quý / năm).
 * Dùng chung cho bảng thống kê và biểu đồ miền tăng giảm tiền.
 */
public class ThongKeKy {

    /** Ngày đầu tiên của kỳ, dùng để sắp xếp. */
    private Date mocKy;

    /** Nhãn hiển thị: "20/09/2026", "Tuần 38/2026", "Tháng 9/2026", "Quý 3/2026", "Năm 2026". */
    private String nhan;

    /** Tổng tiền chi ra để nhập kho trong kỳ. */
    private double tongNhap;

    /** Tổng tiền thu về từ xuất kho trong kỳ. */
    private double tongXuat;

    private int soPhieuNhap;
    private int soPhieuXuat;

    /** % tăng/giảm doanh thu so với kỳ liền trước. */
    private double tangTruong;

    /** Có kỳ liền trước để so sánh hay không. */
    private boolean coKyTruoc;

    public ThongKeKy() {
    }

    /** Lợi nhuận gộp của kỳ = tiền thu về - tiền chi ra. */
    public double getLoiNhuan() {
        return tongXuat - tongNhap;
    }

    /** Dòng tiền trong kỳ tăng hay giảm. */
    public boolean isTang() {
        return getLoiNhuan() >= 0;
    }

    public Date getMocKy() {
        return mocKy;
    }

    public void setMocKy(Date mocKy) {
        this.mocKy = mocKy;
    }

    public String getNhan() {
        return nhan;
    }

    public void setNhan(String nhan) {
        this.nhan = nhan;
    }

    public double getTongNhap() {
        return tongNhap;
    }

    public void setTongNhap(double tongNhap) {
        this.tongNhap = tongNhap;
    }

    public double getTongXuat() {
        return tongXuat;
    }

    public void setTongXuat(double tongXuat) {
        this.tongXuat = tongXuat;
    }

    public int getSoPhieuNhap() {
        return soPhieuNhap;
    }

    public void setSoPhieuNhap(int soPhieuNhap) {
        this.soPhieuNhap = soPhieuNhap;
    }

    public int getSoPhieuXuat() {
        return soPhieuXuat;
    }

    public void setSoPhieuXuat(int soPhieuXuat) {
        this.soPhieuXuat = soPhieuXuat;
    }

    public double getTangTruong() {
        return tangTruong;
    }

    public void setTangTruong(double tangTruong) {
        this.tangTruong = tangTruong;
    }

    public boolean isCoKyTruoc() {
        return coKyTruoc;
    }

    public void setCoKyTruoc(boolean coKyTruoc) {
        this.coKyTruoc = coKyTruoc;
    }
}
