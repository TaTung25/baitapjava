/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package dao;

import context.DBContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.ChiTietPhieuNhap;
import model.PhieuNhap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PhieuNhapDAO {

    @Inject
    private DBContext dbContext;

//Lưu phiếu nhập kho + Thêm chi tiết + Tăng số lượng tồn kho
    public boolean savePhieuNhap(PhieuNhap pn, List<ChiTietPhieuNhap> listCT) {
        Connection conn = null;
        try {
            conn = dbContext.getConnection();
            conn.setAutoCommit(false);

            String sqlPN = "INSERT INTO PhieuNhap (MaNCC, MaNguoiDung, TongTien, GhiChu) VALUES (?, ?, ?, ?)";
            PreparedStatement psPN = conn.prepareStatement(sqlPN, Statement.RETURN_GENERATED_KEYS);
            psPN.setInt(1, pn.getMaNCC());
            psPN.setInt(2, pn.getMaNguoiDung());
            psPN.setDouble(3, pn.getTongTien());
            psPN.setString(4, pn.getGhiChu());
            psPN.executeUpdate();

            ResultSet rs = psPN.getGeneratedKeys();
            int maPN = 0;
            if (rs.next()) {
                maPN = rs.getInt(1);
            }

            String sqlCT = "INSERT INTO ChiTietPhieuNhap (MaPN, MaSP, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
            String sqlStock = "UPDATE SanPham SET SoLuong = SoLuong + ? WHERE MaSP = ?";

            PreparedStatement psCT = conn.prepareStatement(sqlCT);
            PreparedStatement psStock = conn.prepareStatement(sqlStock);

            for (ChiTietPhieuNhap ct : listCT) {
                psCT.setInt(1, maPN);
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
            e.printStackTrace();
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
        return false;
    }

//Lấy tất cả phiếu nhập kho (allPhieuNhap)
    public List<PhieuNhap> getAllPhieuNhap() {
        List<PhieuNhap> list = new ArrayList<>();
        String sql = "SELECT pn.*, ncc.TenNCC, nd.HoTen FROM PhieuNhap pn "
                + "JOIN NhaCungCap ncc ON pn.MaNCC = ncc.MaNCC "
                + "JOIN NguoiDung nd ON pn.MaNguoiDung = nd.MaNguoiDung "
                + "ORDER BY pn.MaPN DESC";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PhieuNhap pn = new PhieuNhap();
                pn.setMaPN(rs.getInt("MaPN"));
                pn.setMaNCC(rs.getInt("MaNCC"));
                pn.setMaNguoiDung(rs.getInt("MaNguoiDung"));
                pn.setNgayNhap(rs.getTimestamp("NgayNhap"));
                pn.setTongTien(rs.getDouble("TongTien"));
                pn.setGhiChu(rs.getString("GhiChu"));
                pn.setTenNCC(rs.getString("TenNCC"));
                pn.setTenNguoiDung(rs.getString("HoTen"));
                list.add(pn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

// Lấy thông tin 1 Phiếu Nhập theo ID
    public PhieuNhap getPhieuNhapById(int maPN) {
        String sql = "SELECT pn.*, ncc.TenNCC, nd.HoTen FROM PhieuNhap pn "
                + "JOIN NhaCungCap ncc ON pn.MaNCC = ncc.MaNCC "
                + "JOIN NguoiDung nd ON pn.MaNguoiDung = nd.MaNguoiDung "
                + "WHERE pn.MaPN = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maPN);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PhieuNhap pn = new PhieuNhap();
                    pn.setMaPN(rs.getInt("MaPN"));
                    pn.setMaNCC(rs.getInt("MaNCC"));
                    pn.setMaNguoiDung(rs.getInt("MaNguoiDung"));
                    pn.setNgayNhap(rs.getTimestamp("NgayNhap"));
                    pn.setTongTien(rs.getDouble("TongTien"));
                    pn.setGhiChu(rs.getString("GhiChu"));
                    pn.setTenNCC(rs.getString("TenNCC"));
                    pn.setTenNguoiDung(rs.getString("HoTen"));
                    return pn;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//Lấy chi tiết các sản phẩm thuộc về một Phiếu Nhập
    public List<ChiTietPhieuNhap> getChiTietPhieuNhapByMaPN(int maPN) {
        List<ChiTietPhieuNhap> list = new ArrayList<>();
        String sql = "SELECT ct.*, sp.TenSP FROM ChiTietPhieuNhap ct "
                + "JOIN SanPham sp ON ct.MaSP = sp.MaSP "
                + "WHERE ct.MaPN = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maPN);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuNhap ct = new ChiTietPhieuNhap();
                    ct.setMaPN(rs.getInt("MaPN"));
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
