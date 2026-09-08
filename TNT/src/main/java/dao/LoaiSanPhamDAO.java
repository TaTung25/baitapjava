/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package dao;

import context.DBContext;
import model.LoaiSanPham;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoaiSanPhamDAO {

    public List<LoaiSanPham> getAllLoai() {
        List<LoaiSanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM LoaiSanPham WHERE TrangThai = 1";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
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
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, l.getTenLoai());
            ps.setString(2, l.getMoTa());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
