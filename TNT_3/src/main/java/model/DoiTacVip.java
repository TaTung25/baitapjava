package model;

import java.sql.Timestamp;

/**
 * Một dòng trong bảng xếp hạng đối tác giao dịch nhiều nhất.
 * Dùng chung cho Nhà Cung Cấp VIP (dựa trên phiếu nhập)
 * và Khách Hàng VIP (dựa trên phiếu xuất).
 */
public class DoiTacVip {

    private int ma;
    private String ten;
    private String soDienThoai;
    private String email;
    private String diaChi;

    /** Số phiếu đã giao dịch trong kỳ. */
    private int soGiaoDich;

    /** Tổng giá trị đã giao dịch trong kỳ. */
    private double tongTien;

    /** Thời điểm giao dịch gần nhất. */
    private Timestamp lanGiaoDichCuoi;

    /** Thứ hạng 1, 2, 3... theo tổng giá trị giao dịch. */
    private int thuHang;

    /** Điểm uy tín 0 - 100: 70% theo doanh số, 30% theo số lần giao dịch. */
    private int diemUyTin;

    /** Hạng thành viên: KIM CƯƠNG / VÀNG / BẠC / ĐỒNG / THƯỜNG. */
    private String hangVip;

    public DoiTacVip() {
    }

    /** Giá trị trung bình mỗi lần giao dịch. */
    public double getGiaTriTrungBinh() {
        return soGiaoDich <= 0 ? 0d : tongTien / soGiaoDich;
    }

    /** Lớp màu CSS tương ứng với hạng, dùng cho badge trong JSP. */
    public String getMauHang() {
        if (hangVip == null) {
            return "bg-secondary";
        }
        switch (hangVip) {
            case "KIM CƯƠNG":
                return "vip-kimcuong";
            case "VÀNG":
                return "vip-vang";
            case "BẠC":
                return "vip-bac";
            case "ĐỒNG":
                return "vip-dong";
            default:
                return "vip-thuong";
        }
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getSoGiaoDich() {
        return soGiaoDich;
    }

    public void setSoGiaoDich(int soGiaoDich) {
        this.soGiaoDich = soGiaoDich;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public Timestamp getLanGiaoDichCuoi() {
        return lanGiaoDichCuoi;
    }

    public void setLanGiaoDichCuoi(Timestamp lanGiaoDichCuoi) {
        this.lanGiaoDichCuoi = lanGiaoDichCuoi;
    }

    public int getThuHang() {
        return thuHang;
    }

    public void setThuHang(int thuHang) {
        this.thuHang = thuHang;
    }

    public int getDiemUyTin() {
        return diemUyTin;
    }

    public void setDiemUyTin(int diemUyTin) {
        this.diemUyTin = diemUyTin;
    }

    public String getHangVip() {
        return hangVip;
    }

    public void setHangVip(String hangVip) {
        this.hangVip = hangVip;
    }
}
