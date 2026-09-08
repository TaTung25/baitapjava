/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package dao;

import context.DBContext;
import model.ChiTietPhieuXuat;
import model.PhieuXuat;
import java.sql.*;
import java.util.List;

public class PhieuXuatDAO {

    // Xuất kho với logic: Kiểm tra số lượng tồn kho trước khi xuất
    public boolean savePhieuXuat(PhieuXuat px, List<ChiTietPhieuXuat> listCT) throws Exception {
        Connection conn = null;
        try {
            conn = DBContext.getConnection();
            conn.setAutoCommit(false);

            // Kiểm tra số lượng kho cho từng sản phẩm
            String checkStockSql = "SELECT SoLuong, TenSP FROM SanPham WHERE MaSP = ?";
            PreparedStatement psCheck = conn.prepareStatement(checkStockSql);
            for (ChiTietPhieuXuat ct : listCT) {
                psCheck.setInt(1, ct.getMaSP());
                ResultSet rs = psCheck.executeQuery();
                if (rs.next()) {
                    int stock = rs.getInt("SoLuong");
                    if (stock < ct.getSoLuong()) {
                        conn.rollback();
                        throw new Exception("Sản phẩm '" + rs.getString("TenSP") + "' không đủ số lượng tồn (Hiện có: " + stock + ")");
                    }
                }
            }

            // Tạo phiếu xuất
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

            // Giảm tồn kho
            String sqlCT = "INSERT INTO ChiTietPhieuXuat (MaPX, MaSP, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
            String sqlUpdateStock = "UPDATE SanPham SET SoLuong = SoLuong - ? WHERE MaSP = ?";

            PreparedStatement psCT = conn.prepareStatement(sqlCT);
            PreparedStatement psStock = conn.prepareStatement(sqlUpdateStock);

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
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}
