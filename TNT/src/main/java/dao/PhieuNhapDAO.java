/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package dao;

import context.DBContext;
import model.ChiTietPhieuNhap;
import model.PhieuNhap;
import java.sql.*;
import java.util.List;

public class PhieuNhapDAO {

    // Transaction an toàn dữ liệu: Tạo phiếu nhập + Thêm chi tiết + Tăng kho
    public boolean savePhieuNhap(PhieuNhap pn, List<ChiTietPhieuNhap> listCT) {
        Connection conn = null;
        try {
            conn = DBContext.getConnection();
            conn.setAutoCommit(false); // Bắt đầu Transaction

            // 1. Thêm Phiếu Nhập
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

            // 2. Thêm Chi Tiết Phiếu Nhập & Cập nhật tăng tồn kho
            String sqlCT = "INSERT INTO ChiTietPhieuNhap (MaPN, MaSP, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
            String sqlUpdateStock = "UPDATE SanPham SET SoLuong = SoLuong + ? WHERE MaSP = ?";

            PreparedStatement psCT = conn.prepareStatement(sqlCT);
            PreparedStatement psStock = conn.prepareStatement(sqlUpdateStock);

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

            conn.commit(); // Commit Transaction thành công
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
}
