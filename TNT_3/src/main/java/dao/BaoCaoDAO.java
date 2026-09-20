/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package dao;

import context.DBContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.DoiTacVip;
import model.ThongKeKy;
import model.TonKhoItem;
import model.TopBanChay;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Toàn bộ truy vấn phục vụ màn hình Thống Kê.
 * Mọi số liệu đều lấy trực tiếp từ database, không hard-code.
 */
@ApplicationScoped
public class BaoCaoDAO {

    /** Các kỳ thống kê được phép. */
    public static final String KY_NGAY = "ngay";
    public static final String KY_TUAN = "tuan";
    public static final String KY_THANG = "thang";
    public static final String KY_QUY = "quy";
    public static final String KY_NAM = "nam";

    @Inject
    private DBContext dbContext;

    // =====================================================================
    // 1. THỐNG KÊ THEO NGÀY / TUẦN / THÁNG / QUÝ / NĂM
    //    (Nguồn dữ liệu cho biểu đồ miền tăng giảm tiền)
    // =====================================================================
    /**
     * Gộp phiếu nhập và phiếu xuất về cùng một trục thời gian rồi nhóm theo kỳ
     * được chọn. Trả về danh sách đã sắp xếp tăng dần theo thời gian, kèm nhãn
     * hiển thị và % tăng trưởng doanh thu so với kỳ liền trước.
     *
     * @param ky      một trong: ngay, tuan, thang, quy, nam
     * @param tuNgay  lọc từ ngày (có thể null)
     * @param denNgay lọc đến ngày, đã bao gồm cả ngày này (có thể null)
     */
    public List<ThongKeKy> getThongKeTheoKy(String ky, Date tuNgay, Date denNgay) {
        List<ThongKeKy> danhSach = new ArrayList<>();
        String kyChuan = chuanHoaKy(ky);
        String bieuThuc = bieuThucNhomKy(kyChuan);

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(bieuThuc).append(" AS MocKy, ");
        sql.append("ISNULL(SUM(t.TienNhap), 0) AS TongNhap, ");
        sql.append("ISNULL(SUM(t.TienXuat), 0) AS TongXuat, ");
        sql.append("ISNULL(SUM(t.SoPN), 0) AS SoPN, ");
        sql.append("ISNULL(SUM(t.SoPX), 0) AS SoPX ");
        sql.append("FROM ( ");
        sql.append("  SELECT NgayNhap AS Ngay, TongTien AS TienNhap, ");
        sql.append("         CAST(0 AS DECIMAL(18,2)) AS TienXuat, 1 AS SoPN, 0 AS SoPX ");
        sql.append("  FROM PhieuNhap ");
        sql.append("  UNION ALL ");
        sql.append("  SELECT NgayXuat, CAST(0 AS DECIMAL(18,2)), TongTien, 0, 1 ");
        sql.append("  FROM PhieuXuat ");
        sql.append(") t WHERE 1 = 1 ");
        if (tuNgay != null) {
            sql.append("AND t.Ngay >= ? ");
        }
        if (denNgay != null) {
            sql.append("AND t.Ngay < DATEADD(DAY, 1, ?) ");
        }
        sql.append("GROUP BY ").append(bieuThuc).append(" ");
        sql.append("ORDER BY MocKy ASC");

        try (Connection conn = dbContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int viTri = 1;
            if (tuNgay != null) {
                ps.setDate(viTri++, tuNgay);
            }
            if (denNgay != null) {
                ps.setDate(viTri++, denNgay);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ThongKeKy dong = new ThongKeKy();
                    dong.setMocKy(rs.getDate("MocKy"));
                    dong.setTongNhap(rs.getDouble("TongNhap"));
                    dong.setTongXuat(rs.getDouble("TongXuat"));
                    dong.setSoPhieuNhap(rs.getInt("SoPN"));
                    dong.setSoPhieuXuat(rs.getInt("SoPX"));
                    dong.setNhan(taoNhanKy(kyChuan, dong.getMocKy()));
                    danhSach.add(dong);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tinhTangTruong(danhSach);
        return danhSach;
    }

    /** Biểu thức SQL trả về ngày đầu tiên của kỳ chứa t.Ngay. */
    private String bieuThucNhomKy(String kyChuan) {
        switch (kyChuan) {
            case KY_NGAY:
                return "CAST(t.Ngay AS date)";
            case KY_TUAN:
                // Ngày 0 của SQL Server (1900-01-01) là thứ Hai nên phép chia dư
                // cho 7 luôn cho ra thứ Hai đầu tuần, không phụ thuộc DATEFIRST.
                return "DATEADD(DAY, -(DATEDIFF(DAY, 0, t.Ngay) % 7), CAST(t.Ngay AS date))";
            case KY_QUY:
                return "DATEFROMPARTS(YEAR(t.Ngay), (DATEPART(QUARTER, t.Ngay) - 1) * 3 + 1, 1)";
            case KY_NAM:
                return "DATEFROMPARTS(YEAR(t.Ngay), 1, 1)";
            case KY_THANG:
            default:
                return "DATEFROMPARTS(YEAR(t.Ngay), MONTH(t.Ngay), 1)";
        }
    }

    /** Nhãn hiển thị của kỳ. Ghép ở Java để không phải nhúng tiếng Việt vào SQL. */
    private String taoNhanKy(String kyChuan, Date mocKy) {
        if (mocKy == null) {
            return "";
        }
        LocalDate ngay = mocKy.toLocalDate();
        switch (kyChuan) {
            case KY_NGAY:
                return String.format("%02d/%02d/%d",
                        ngay.getDayOfMonth(), ngay.getMonthValue(), ngay.getYear());
            case KY_TUAN:
                return "Tuần " + ngay.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
                        + "/" + ngay.get(IsoFields.WEEK_BASED_YEAR);
            case KY_QUY:
                return "Quý " + ((ngay.getMonthValue() - 1) / 3 + 1) + "/" + ngay.getYear();
            case KY_NAM:
                return "Năm " + ngay.getYear();
            case KY_THANG:
            default:
                return "Tháng " + ngay.getMonthValue() + "/" + ngay.getYear();
        }
    }

    /** Tính % tăng giảm doanh thu của từng kỳ so với kỳ liền trước. */
    private void tinhTangTruong(List<ThongKeKy> danhSach) {
        for (int i = 1; i < danhSach.size(); i++) {
            ThongKeKy hienTai = danhSach.get(i);
            double truoc = danhSach.get(i - 1).getTongXuat();
            hienTai.setCoKyTruoc(true);
            if (truoc == 0d) {
                hienTai.setTangTruong(hienTai.getTongXuat() > 0d ? 100d : 0d);
            } else {
                hienTai.setTangTruong((hienTai.getTongXuat() - truoc) / truoc * 100d);
            }
        }
    }

    /** Chuẩn hóa tham số kỳ, mặc định là tháng. */
    public String chuanHoaKy(String ky) {
        if (ky == null) {
            return KY_THANG;
        }
        switch (ky.trim().toLowerCase()) {
            case KY_NGAY:
                return KY_NGAY;
            case KY_TUAN:
                return KY_TUAN;
            case KY_QUY:
                return KY_QUY;
            case KY_NAM:
                return KY_NAM;
            default:
                return KY_THANG;
        }
    }

    // =====================================================================
    // 2. LOẠI SẢN PHẨM BÁN CHẠY NHẤT (biểu đồ tròn)
    // =====================================================================
    public List<TopBanChay> getTopLoaiBanChay(Date tuNgay, Date denNgay, int soDong) {
        int soDongToiDa = gioiHan(soDong, 8, 30);

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TOP ").append(soDongToiDa).append(" ");
        sql.append("l.MaLoai AS Ma, l.TenLoai AS Ten, ");
        sql.append("ISNULL(SUM(ct.SoLuong), 0) AS SoLuongBan, ");
        sql.append("ISNULL(SUM(ct.ThanhTien), 0) AS DoanhThu, ");
        sql.append("COUNT(DISTINCT px.MaPX) AS SoLanBan ");
        sql.append("FROM ChiTietPhieuXuat ct ");
        sql.append("INNER JOIN PhieuXuat px ON ct.MaPX = px.MaPX ");
        sql.append("INNER JOIN SanPham sp ON ct.MaSP = sp.MaSP ");
        sql.append("INNER JOIN LoaiSanPham l ON sp.MaLoai = l.MaLoai ");
        sql.append("WHERE 1 = 1 ");
        if (tuNgay != null) {
            sql.append("AND px.NgayXuat >= ? ");
        }
        if (denNgay != null) {
            sql.append("AND px.NgayXuat < DATEADD(DAY, 1, ?) ");
        }
        sql.append("GROUP BY l.MaLoai, l.TenLoai ");
        sql.append("ORDER BY SoLuongBan DESC, DoanhThu DESC");

        List<TopBanChay> danhSach = layDanhSachBanChay(sql.toString(), tuNgay, denNgay, false);
        tinhTyLe(danhSach);
        return danhSach;
    }

    // =====================================================================
    // 3. SẢN PHẨM BÁN CHẠY NHẤT (biểu đồ cột)
    // =====================================================================
    public List<TopBanChay> getTopSanPhamBanChay(Date tuNgay, Date denNgay, int soDong) {
        int soDongToiDa = gioiHan(soDong, 5, 50);

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TOP ").append(soDongToiDa).append(" ");
        sql.append("sp.MaSP AS Ma, sp.TenSP AS Ten, l.TenLoai AS TenLoai, ");
        sql.append("ISNULL(SUM(ct.SoLuong), 0) AS SoLuongBan, ");
        sql.append("ISNULL(SUM(ct.ThanhTien), 0) AS DoanhThu, ");
        sql.append("COUNT(DISTINCT px.MaPX) AS SoLanBan ");
        sql.append("FROM ChiTietPhieuXuat ct ");
        sql.append("INNER JOIN PhieuXuat px ON ct.MaPX = px.MaPX ");
        sql.append("INNER JOIN SanPham sp ON ct.MaSP = sp.MaSP ");
        sql.append("LEFT JOIN LoaiSanPham l ON sp.MaLoai = l.MaLoai ");
        sql.append("WHERE 1 = 1 ");
        if (tuNgay != null) {
            sql.append("AND px.NgayXuat >= ? ");
        }
        if (denNgay != null) {
            sql.append("AND px.NgayXuat < DATEADD(DAY, 1, ?) ");
        }
        sql.append("GROUP BY sp.MaSP, sp.TenSP, l.TenLoai ");
        sql.append("ORDER BY SoLuongBan DESC, DoanhThu DESC");

        List<TopBanChay> danhSach = layDanhSachBanChay(sql.toString(), tuNgay, denNgay, true);
        tinhTyLe(danhSach);
        return danhSach;
    }

    private List<TopBanChay> layDanhSachBanChay(String sql, Date tuNgay, Date denNgay, boolean coTenLoai) {
        List<TopBanChay> danhSach = new ArrayList<>();
        try (Connection conn = dbContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            int viTri = 1;
            if (tuNgay != null) {
                ps.setDate(viTri++, tuNgay);
            }
            if (denNgay != null) {
                ps.setDate(viTri++, denNgay);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TopBanChay dong = new TopBanChay();
                    dong.setMa(rs.getInt("Ma"));
                    dong.setTen(rs.getString("Ten"));
                    if (coTenLoai) {
                        dong.setTenLoai(rs.getString("TenLoai"));
                    }
                    dong.setSoLuongBan(rs.getInt("SoLuongBan"));
                    dong.setDoanhThu(rs.getDouble("DoanhThu"));
                    dong.setSoLanBan(rs.getInt("SoLanBan"));
                    danhSach.add(dong);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    private void tinhTyLe(List<TopBanChay> danhSach) {
        int tong = 0;
        for (TopBanChay dong : danhSach) {
            tong += dong.getSoLuongBan();
        }
        if (tong <= 0) {
            return;
        }
        for (TopBanChay dong : danhSach) {
            dong.setTyLe(dong.getSoLuongBan() * 100d / tong);
        }
    }

    // =====================================================================
    // 4. HÀNG TỒN KHO CÒN NHIỀU
    // =====================================================================
    /**
     * Lọc các mặt hàng đang tồn từ mức tối thiểu trở lên, kèm số lượng đã bán
     * để đánh giá mặt hàng ứ đọng.
     *
     * @param tonToiThieu số lượng tồn tối thiểu để lọt vào danh sách
     * @param tuKhoa      tìm theo tên sản phẩm hoặc hãng sản xuất (có thể null)
     * @param maLoai      lọc theo loại sản phẩm, 0 = tất cả
     * @param soDong      số dòng tối đa
     */
    public List<TonKhoItem> getTonKhoConNhieu(int tonToiThieu, String tuKhoa, int maLoai, int soDong) {
        List<TonKhoItem> danhSach = new ArrayList<>();
        int soDongToiDa = gioiHan(soDong, 20, 200);
        boolean coTuKhoa = tuKhoa != null && !tuKhoa.trim().isEmpty();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TOP ").append(soDongToiDa).append(" ");
        sql.append("sp.MaSP, sp.TenSP, sp.HangSX, sp.DonViTinh, sp.SoLuong, ");
        sql.append("sp.DonGiaNhap, sp.DonGiaBan, l.TenLoai, ncc.TenNCC, ");
        sql.append("ISNULL(ban.DaBan, 0) AS DaBan ");
        sql.append("FROM SanPham sp ");
        sql.append("LEFT JOIN LoaiSanPham l ON sp.MaLoai = l.MaLoai ");
        sql.append("LEFT JOIN NhaCungCap ncc ON sp.MaNCC = ncc.MaNCC ");
        sql.append("LEFT JOIN (SELECT MaSP, SUM(SoLuong) AS DaBan ");
        sql.append("           FROM ChiTietPhieuXuat GROUP BY MaSP) ban ON ban.MaSP = sp.MaSP ");
        sql.append("WHERE sp.TrangThai = 1 AND sp.SoLuong >= ? ");
        if (coTuKhoa) {
            sql.append("AND (sp.TenSP LIKE ? OR sp.HangSX LIKE ?) ");
        }
        if (maLoai > 0) {
            sql.append("AND sp.MaLoai = ? ");
        }
        sql.append("ORDER BY sp.SoLuong DESC, sp.MaSP DESC");

        try (Connection conn = dbContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int viTri = 1;
            ps.setInt(viTri++, Math.max(tonToiThieu, 0));
            if (coTuKhoa) {
                String mau = "%" + tuKhoa.trim() + "%";
                ps.setString(viTri++, mau);
                ps.setString(viTri++, mau);
            }
            if (maLoai > 0) {
                ps.setInt(viTri++, maLoai);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TonKhoItem dong = new TonKhoItem();
                    dong.setMaSP(rs.getInt("MaSP"));
                    dong.setTenSP(rs.getString("TenSP"));
                    dong.setHangSX(rs.getString("HangSX"));
                    dong.setDonViTinh(rs.getString("DonViTinh"));
                    dong.setSoLuong(rs.getInt("SoLuong"));
                    dong.setDonGiaNhap(rs.getDouble("DonGiaNhap"));
                    dong.setDonGiaBan(rs.getDouble("DonGiaBan"));
                    dong.setTenLoai(rs.getString("TenLoai"));
                    dong.setTenNCC(rs.getString("TenNCC"));
                    dong.setDaBan(rs.getInt("DaBan"));
                    danhSach.add(dong);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    // =====================================================================
    // 5. NHÀ CUNG CẤP / KHÁCH HÀNG GIAO DỊCH NHIỀU NHẤT (VIP - UY TÍN)
    // =====================================================================
    public List<DoiTacVip> getTopNhaCungCap(Date tuNgay, Date denNgay, int soDong) {
        int soDongToiDa = gioiHan(soDong, 5, 50);

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TOP ").append(soDongToiDa).append(" ");
        sql.append("ncc.MaNCC AS Ma, ncc.TenNCC AS Ten, ");
        sql.append("ncc.SoDienThoai AS SoDienThoai, ncc.Email AS Email, ncc.DiaChi AS DiaChi, ");
        sql.append("COUNT(pn.MaPN) AS SoGiaoDich, ");
        sql.append("ISNULL(SUM(pn.TongTien), 0) AS TongTien, ");
        sql.append("MAX(pn.NgayNhap) AS LanCuoi ");
        sql.append("FROM NhaCungCap ncc ");
        sql.append("INNER JOIN PhieuNhap pn ON ncc.MaNCC = pn.MaNCC ");
        sql.append("WHERE 1 = 1 ");
        if (tuNgay != null) {
            sql.append("AND pn.NgayNhap >= ? ");
        }
        if (denNgay != null) {
            sql.append("AND pn.NgayNhap < DATEADD(DAY, 1, ?) ");
        }
        sql.append("GROUP BY ncc.MaNCC, ncc.TenNCC, ncc.SoDienThoai, ncc.Email, ncc.DiaChi ");
        sql.append("ORDER BY TongTien DESC, SoGiaoDich DESC");

        return layDanhSachVip(sql.toString(), tuNgay, denNgay);
    }

    public List<DoiTacVip> getTopKhachHang(Date tuNgay, Date denNgay, int soDong) {
        int soDongToiDa = gioiHan(soDong, 5, 50);

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TOP ").append(soDongToiDa).append(" ");
        sql.append("kh.MaKH AS Ma, kh.TenKH AS Ten, ");
        sql.append("kh.SoDienThoai AS SoDienThoai, kh.Email AS Email, kh.DiaChi AS DiaChi, ");
        sql.append("COUNT(px.MaPX) AS SoGiaoDich, ");
        sql.append("ISNULL(SUM(px.TongTien), 0) AS TongTien, ");
        sql.append("MAX(px.NgayXuat) AS LanCuoi ");
        sql.append("FROM KhachHang kh ");
        sql.append("INNER JOIN PhieuXuat px ON kh.MaKH = px.MaKH ");
        sql.append("WHERE 1 = 1 ");
        if (tuNgay != null) {
            sql.append("AND px.NgayXuat >= ? ");
        }
        if (denNgay != null) {
            sql.append("AND px.NgayXuat < DATEADD(DAY, 1, ?) ");
        }
        sql.append("GROUP BY kh.MaKH, kh.TenKH, kh.SoDienThoai, kh.Email, kh.DiaChi ");
        sql.append("ORDER BY TongTien DESC, SoGiaoDich DESC");

        return layDanhSachVip(sql.toString(), tuNgay, denNgay);
    }

    private List<DoiTacVip> layDanhSachVip(String sql, Date tuNgay, Date denNgay) {
        List<DoiTacVip> danhSach = new ArrayList<>();
        try (Connection conn = dbContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            int viTri = 1;
            if (tuNgay != null) {
                ps.setDate(viTri++, tuNgay);
            }
            if (denNgay != null) {
                ps.setDate(viTri++, denNgay);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DoiTacVip dong = new DoiTacVip();
                    dong.setMa(rs.getInt("Ma"));
                    dong.setTen(rs.getString("Ten"));
                    dong.setSoDienThoai(rs.getString("SoDienThoai"));
                    dong.setEmail(rs.getString("Email"));
                    dong.setDiaChi(rs.getString("DiaChi"));
                    dong.setSoGiaoDich(rs.getInt("SoGiaoDich"));
                    dong.setTongTien(rs.getDouble("TongTien"));
                    dong.setLanGiaoDichCuoi(rs.getTimestamp("LanCuoi"));
                    danhSach.add(dong);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        xepHangUyTin(danhSach);
        return danhSach;
    }

    /**
     * Chấm điểm uy tín và xếp hạng VIP.
     * Điểm = 70% theo tổng giá trị giao dịch + 30% theo số lần giao dịch,
     * quy về thang 100 dựa trên đối tác đứng đầu bảng.
     */
    private void xepHangUyTin(List<DoiTacVip> danhSach) {
        if (danhSach.isEmpty()) {
            return;
        }

        double tienCaoNhat = 0d;
        int lanCaoNhat = 0;
        for (DoiTacVip dong : danhSach) {
            tienCaoNhat = Math.max(tienCaoNhat, dong.getTongTien());
            lanCaoNhat = Math.max(lanCaoNhat, dong.getSoGiaoDich());
        }

        int thuHang = 1;
        for (DoiTacVip dong : danhSach) {
            dong.setThuHang(thuHang++);

            double tyLeTien = tienCaoNhat > 0d ? dong.getTongTien() / tienCaoNhat : 0d;
            double tyLeLan = lanCaoNhat > 0 ? (double) dong.getSoGiaoDich() / lanCaoNhat : 0d;
            int diem = (int) Math.round((tyLeTien * 0.7d + tyLeLan * 0.3d) * 100d);
            if (diem < 0) {
                diem = 0;
            }
            if (diem > 100) {
                diem = 100;
            }
            dong.setDiemUyTin(diem);

            if (diem >= 80) {
                dong.setHangVip("KIM CƯƠNG");
            } else if (diem >= 60) {
                dong.setHangVip("VÀNG");
            } else if (diem >= 40) {
                dong.setHangVip("BẠC");
            } else if (diem >= 20) {
                dong.setHangVip("ĐỒNG");
            } else {
                dong.setHangVip("THƯỜNG");
            }
        }
    }

    // =====================================================================
    // 6. SỐ LIỆU TỔNG QUAN KHO
    // =====================================================================
    /**
     * Trả về map gồm: soMatHang, tongTon, giaTriTon, giaTriBanDuKien, sapHet.
     */
    public Map<String, Double> getTongQuanTonKho() {
        Map<String, Double> ketQua = new LinkedHashMap<>();
        ketQua.put("soMatHang", 0d);
        ketQua.put("tongTon", 0d);
        ketQua.put("giaTriTon", 0d);
        ketQua.put("giaTriBanDuKien", 0d);
        ketQua.put("sapHet", 0d);

        String sql = "SELECT COUNT(*) AS SoMatHang, "
                + "ISNULL(SUM(SoLuong), 0) AS TongTon, "
                + "ISNULL(SUM(SoLuong * DonGiaNhap), 0) AS GiaTriTon, "
                + "ISNULL(SUM(SoLuong * DonGiaBan), 0) AS GiaTriBan, "
                + "ISNULL(SUM(CASE WHEN SoLuong <= 5 THEN 1 ELSE 0 END), 0) AS SapHet "
                + "FROM SanPham WHERE TrangThai = 1";

        try (Connection conn = dbContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                ketQua.put("soMatHang", rs.getDouble("SoMatHang"));
                ketQua.put("tongTon", rs.getDouble("TongTon"));
                ketQua.put("giaTriTon", rs.getDouble("GiaTriTon"));
                ketQua.put("giaTriBanDuKien", rs.getDouble("GiaTriBan"));
                ketQua.put("sapHet", rs.getDouble("SapHet"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    // =====================================================================
    // 7. TỒN KHO THEO LOẠI SẢN PHẨM (biểu đồ tròn - giữ từ bản cũ)
    // =====================================================================
    public Map<String, Integer> getTonKhoTheoLoai() {
        Map<String, Integer> map = new LinkedHashMap<>();
        String sql = "SELECT l.TenLoai, ISNULL(SUM(sp.SoLuong), 0) AS TongTon "
                + "FROM LoaiSanPham l "
                + "LEFT JOIN SanPham sp ON l.MaLoai = sp.MaLoai AND sp.TrangThai = 1 "
                + "WHERE l.TrangThai = 1 "
                + "GROUP BY l.MaLoai, l.TenLoai "
                + "ORDER BY TongTon DESC";
        try (Connection conn = dbContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("TenLoai"), rs.getInt("TongTon"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // =====================================================================
    // 8. CÁC HÀM CŨ - GIỮ LẠI ĐỂ TƯƠNG THÍCH NGƯỢC
    // =====================================================================
    public Map<String, Double> getTongChiNhapTheoThang() {
        return layTongTienTheoThang("PhieuNhap", "NgayNhap");
    }

    public Map<String, Double> getTongThuXuatTheoThang() {
        return layTongTienTheoThang("PhieuXuat", "NgayXuat");
    }

    private Map<String, Double> layTongTienTheoThang(String bang, String cotNgay) {
        Map<String, Double> map = new LinkedHashMap<>();
        String sql = "SELECT YEAR(" + cotNgay + ") AS Nam, MONTH(" + cotNgay + ") AS Thang, "
                + "ISNULL(SUM(TongTien), 0) AS TongTien "
                + "FROM " + bang + " "
                + "GROUP BY YEAR(" + cotNgay + "), MONTH(" + cotNgay + ") "
                + "ORDER BY Nam ASC, Thang ASC";
        try (Connection conn = dbContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String nhan = "Tháng " + rs.getInt("Thang") + "/" + rs.getInt("Nam");
                map.put(nhan, rs.getDouble("TongTien"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /** Ép số dòng về khoảng hợp lệ để không cho phép chèn SQL qua tham số TOP. */
    private int gioiHan(int soDong, int macDinh, int toiDa) {
        if (soDong <= 0) {
            return macDinh;
        }
        return Math.min(soDong, toiDa);
    }
}
