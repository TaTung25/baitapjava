package model;

/**
 * Một dòng trong bảng xếp hạng bán chạy.
 * Dùng chung cho cả "Loại sản phẩm bán chạy" (biểu đồ tròn)
 * và "Sản phẩm bán chạy" (biểu đồ cột).
 */
public class TopBanChay {

    private int ma;
    private String ten;

    /** Tên loại sản phẩm (chỉ có ý nghĩa khi xếp hạng theo sản phẩm). */
    private String tenLoai;

    /** Tổng số lượng đã bán ra trong kỳ. */
    private int soLuongBan;

    /** Tổng doanh thu mang lại trong kỳ. */
    private double doanhThu;

    /** Số phiếu xuất có chứa mặt hàng này. */
    private int soLanBan;

    /** Tỷ lệ % số lượng bán trên tổng của bảng xếp hạng. */
    private double tyLe;

    public TopBanChay() {
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

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public int getSoLuongBan() {
        return soLuongBan;
    }

    public void setSoLuongBan(int soLuongBan) {
        this.soLuongBan = soLuongBan;
    }

    public double getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(double doanhThu) {
        this.doanhThu = doanhThu;
    }

    public int getSoLanBan() {
        return soLanBan;
    }

    public void setSoLanBan(int soLanBan) {
        this.soLanBan = soLanBan;
    }

    public double getTyLe() {
        return tyLe;
    }

    public void setTyLe(double tyLe) {
        this.tyLe = tyLe;
    }
}
