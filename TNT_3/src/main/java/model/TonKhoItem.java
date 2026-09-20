package model;

/**
 * Một dòng trong bảng "Hàng tồn kho còn nhiều".
 * Ngoài thông tin sản phẩm còn kèm số lượng đã bán ra để đánh giá
 * mặt hàng đang tồn nhiều mà bán chậm.
 */
public class TonKhoItem {

    private int maSP;
    private String tenSP;
    private String hangSX;
    private String tenLoai;
    private String tenNCC;
    private String donViTinh;

    private int soLuong;
    private double donGiaNhap;
    private double donGiaBan;

    /** Tổng số lượng đã bán ra từ trước tới nay. */
    private int daBan;

    public TonKhoItem() {
    }

    /** Vốn đang nằm trong kho của mặt hàng này. */
    public double getGiaTriTon() {
        return soLuong * donGiaNhap;
    }

    /** Doanh thu dự kiến nếu bán hết lượng tồn. */
    public double getGiaTriBanDuKien() {
        return soLuong * donGiaBan;
    }

    /**
     * Tỷ lệ luân chuyển = đã bán / (đã bán + đang tồn) * 100.
     * Càng thấp nghĩa là hàng càng ứ đọng.
     */
    public double getTyLeLuanChuyen() {
        int tong = daBan + soLuong;
        return tong <= 0 ? 0d : (daBan * 100d) / tong;
    }

    /** Đánh giá nhanh mức độ ứ đọng để tô màu trong bảng. */
    public String getCanhBao() {
        double tyLe = getTyLeLuanChuyen();
        if (daBan == 0) {
            return "Chưa bán được";
        }
        if (tyLe < 20) {
            return "Bán rất chậm";
        }
        if (tyLe < 50) {
            return "Bán chậm";
        }
        return "Luân chuyển tốt";
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getHangSX() {
        return hangSX;
    }

    public void setHangSX(String hangSX) {
        this.hangSX = hangSX;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGiaNhap() {
        return donGiaNhap;
    }

    public void setDonGiaNhap(double donGiaNhap) {
        this.donGiaNhap = donGiaNhap;
    }

    public double getDonGiaBan() {
        return donGiaBan;
    }

    public void setDonGiaBan(double donGiaBan) {
        this.donGiaBan = donGiaBan;
    }

    public int getDaBan() {
        return daBan;
    }

    public void setDaBan(int daBan) {
        this.daBan = daBan;
    }
}
