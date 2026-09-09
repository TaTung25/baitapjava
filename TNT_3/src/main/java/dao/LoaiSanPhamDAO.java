/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package dao;

import context.DBContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.LoaiSanPham;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LoaiSanPhamDAO {

    @Inject
    private DBContext dbContext;

    public List<LoaiSanPham> getAllLoai() {
        List<LoaiSanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM LoaiSanPham WHERE TrangThai = 1";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new LoaiSanPham(
                        rs.getInt("MaLoai"),
                        rs.getString("TenLoai"),
                        rs.getString("MoTa"),
                        rs.getBoolean("TrangThai")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertLoai(LoaiSanPham l) {
        String sql = "INSERT INTO LoaiSanPham(TenLoai, MoTa, TrangThai) VALUES(?, ?, 1)";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, l.getTenLoai());
            ps.setString(2, l.getMoTa());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin loại sản phẩm
    public boolean updateLoai(LoaiSanPham l) {
        String sql = "UPDATE LoaiSanPham SET TenLoai=?, MoTa=? WHERE MaLoai=?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, l.getTenLoai());
            ps.setString(2, l.getMoTa());
            ps.setInt(3, l.getMaLoai());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa mềm: chuyển TrangThai = 0 (giữ nguyên dòng dữ liệu vì SanPham đang
    // tham chiếu MaLoai qua khóa ngoại, xóa cứng sẽ vỡ ràng buộc)
    public boolean deleteLoai(int maLoai) {
        String sql = "UPDATE LoaiSanPham SET TrangThai = 0 WHERE MaLoai = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maLoai);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
