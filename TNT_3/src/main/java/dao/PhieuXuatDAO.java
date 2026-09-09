/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package dao;

import context.DBContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.ChiTietPhieuXuat;
import model.PhieuXuat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PhieuXuatDAO {

    @Inject
    private DBContext dbContext;

    //Lưu phiếu xuất kho + Kiểm tra tồn kho + Trừ tồn kho
    public boolean savePhieuXuat(PhieuXuat px, List<ChiTietPhieuXuat> listCT) throws Exception {
        Connection conn = null;
        try {
            conn = dbContext.getConnection();
            conn.setAutoCommit(false);

            // Kiểm tra số lượng tồn kho trước khi cho phép xuất
            String checkStockSql = "SELECT SoLuong, TenSP FROM SanPham WHERE MaSP = ?";
            PreparedStatement psCheck = conn.prepareStatement(checkStockSql);
            for (ChiTietPhieuXuat ct : listCT) {
                psCheck.setInt(1, ct.getMaSP());
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        int stock = rs.getInt("SoLuong");
                        if (stock < ct.getSoLuong()) {
                            conn.rollback();
                            throw new Exception("Sản phẩm '" + rs.getString("TenSP") + "' không đủ số lượng tồn kho (Hiện còn: " + stock + ")");
                        }
                    }
                }
            }

            // Tạo bản ghi Phiếu Xuất
            String sqlPX = "INSERT INTO PhieuXuat (MaKH, MaNguoiDung, TongTien, GhiChu) VALUES (?, ?, ?, ?)";
            PreparedStatement psPX = conn.prepareStatement(sqlPX, Statement.RETURN_GENERATED_KEYS);
            psPX.setInt(1, px.getMaKH());
            psPX.setInt(2, px.getMaNguoiDung());
            psPX.setDouble(3, px.getTongTien());
            psPX.setString(4, px.getGhiChu());
            psPX.executeUpdate();

            ResultSet rs = psPX.getGeneratedKeys();
            int maPX = 0;
            if (rs.next()) {
                maPX = rs.getInt(1);
            }

            // Thêm Chi Tiết Phiếu Xuất & Trừ số lượng tồn kho
            String sqlCT = "INSERT INTO ChiTietPhieuXuat (MaPX, MaSP, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
            String sqlStock = "UPDATE SanPham SET SoLuong = SoLuong - ? WHERE MaSP = ?";

            PreparedStatement psCT = conn.prepareStatement(sqlCT);
            PreparedStatement psStock = conn.prepareStatement(sqlStock);

            for (ChiTietPhieuXuat ct : listCT) {
                psCT.setInt(1, maPX);
                psCT.setInt(2, ct.getMaSP());
                psCT.setInt(3, ct.getSoLuong());
                psCT.setDouble(4, ct.getDonGia());
                psCT.setDouble(5, ct.getThanhTien());
                psCT.addBatch();

                psStock.setInt(1, ct.getSoLuong());
                psStock.setInt(2, ct.getMaSP());
                psStock.addBatch();
            }

            psCT.executeBatch();
            psStock.executeBatch();

            conn.commit();
            return true;
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    //Lấy tất cả phiếu xuất kho
    public List<PhieuXuat> getAllPhieuXuat() {
        List<PhieuXuat> list = new ArrayList<>();
        String sql = "SELECT px.*, kh.TenKH, nd.HoTen FROM PhieuXuat px "
                + "JOIN KhachHang kh ON px.MaKH = kh.MaKH "
                + "JOIN NguoiDung nd ON px.MaNguoiDung = nd.MaNguoiDung "
                + "ORDER BY px.MaPX DESC";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PhieuXuat px = new PhieuXuat();
                px.setMaPX(rs.getInt("MaPX"));
                px.setMaKH(rs.getInt("MaKH"));
                px.setMaNguoiDung(rs.getInt("MaNguoiDung"));
                px.setNgayXuat(rs.getTimestamp("NgayXuat"));
                px.setTongTien(rs.getDouble("TongTien"));
                px.setGhiChu(rs.getString("GhiChu"));
                px.setTenKH(rs.getString("TenKH"));
                px.setTenNguoiDung(rs.getString("HoTen"));
                list.add(px);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy thông tin 1 Phiếu Xuất theo ID
    public PhieuXuat getPhieuXuatById(int maPX) {
        String sql = "SELECT px.*, kh.TenKH, nd.HoTen FROM PhieuXuat px "
                + "JOIN KhachHang kh ON px.MaKH = kh.MaKH "
                + "JOIN NguoiDung nd ON px.MaNguoiDung = nd.MaNguoiDung "
                + "WHERE px.MaPX = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maPX);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PhieuXuat px = new PhieuXuat();
                    px.setMaPX(rs.getInt("MaPX"));
                    px.setMaKH(rs.getInt("MaKH"));
                    px.setMaNguoiDung(rs.getInt("MaNguoiDung"));
                    px.setNgayXuat(rs.getTimestamp("NgayXuat"));
                    px.setTongTien(rs.getDouble("TongTien"));
                    px.setGhiChu(rs.getString("GhiChu"));
                    px.setTenKH(rs.getString("TenKH"));
                    px.setTenNguoiDung(rs.getString("HoTen"));
                    return px;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy chi tiết danh sách sản phẩm thuộc về một Phiếu Xuất
    public List<ChiTietPhieuXuat> getChiTietPhieuXuatByMaPX(int maPX) {
        List<ChiTietPhieuXuat> list = new ArrayList<>();
        String sql = "SELECT ct.*, sp.TenSP FROM ChiTietPhieuXuat ct "
                + "JOIN SanPham sp ON ct.MaSP = sp.MaSP "
                + "WHERE ct.MaPX = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maPX);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuXuat ct = new ChiTietPhieuXuat();
                    ct.setMaPX(rs.getInt("MaPX"));
                    ct.setMaSP(rs.getInt("MaSP"));
                    ct.setSoLuong(rs.getInt("SoLuong"));
                    ct.setDonGia(rs.getDouble("DonGia"));
                    ct.setThanhTien(rs.getDouble("ThanhTien"));
                    ct.setTenSP(rs.getString("TenSP"));
                    list.add(ct);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
