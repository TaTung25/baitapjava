/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package dao;

import context.DBContext;
import model.NguoiDung;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NguoiDungDAO {

    public NguoiDung checkLogin(String user, String pass) {
        String sql = "SELECT * FROM NguoiDung WHERE TenDangNhap = ? AND MatKhau = ? AND TrangThai = 1";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new NguoiDung(
                        rs.getInt("MaNguoiDung"),
                        rs.getString("TenDangNhap"),
                        rs.getString("MatKhau"),
                        rs.getString("HoTen"),
                        rs.getString("Email"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Quyen"),
                        rs.getBoolean("TrangThai")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
