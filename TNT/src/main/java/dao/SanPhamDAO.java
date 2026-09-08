/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package dao;

import context.DBContext;
import model.SanPham;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {

    public List<SanPham> getAllSanPham() {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT sp.*, l.TenLoai, ncc.TenNCC FROM SanPham sp "
                + "JOIN LoaiSanPham l ON sp.MaLoai = l.MaLoai "
                + "JOIN NhaCungCap ncc ON sp.MaNCC = ncc.MaNCC "
                + "WHERE sp.TrangThai = 1 ORDER BY sp.MaSP DESC";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getInt("MaSP"));
                sp.setTenSP(rs.getString("TenSP"));
                sp.setMaLoai(rs.getInt("MaLoai"));
                sp.setMaNCC(rs.getInt("MaNCC"));
                sp.setHangSX(rs.getString("HangSX"));
                sp.setDonViTinh(rs.getString("DonViTinh"));
                sp.setDonGiaNhap(rs.getDouble("DonGiaNhap"));
                sp.setDonGiaBan(rs.getDouble("DonGiaBan"));
                sp.setSoLuong(rs.getInt("SoLuong"));
                sp.setBaoHanh(rs.getInt("BaoHanh"));
                sp.setMoTa(rs.getString("MoTa"));
                sp.setTenLoai(rs.getString("TenLoai"));
                sp.setTenNCC(rs.getString("TenNCC"));
                list.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertSanPham(SanPham sp) {
        String sql = "INSERT INTO SanPham(TenSP, MaLoai, MaNCC, HangSX, DonViTinh, DonGiaNhap, DonGiaBan, SoLuong, BaoHanh, MoTa) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sp.getTenSP());
            ps.setInt(2, sp.getMaLoai());
            ps.setInt(3, sp.getMaNCC());
            ps.setString(4, sp.getHangSX());
            ps.setString(5, sp.getDonViTinh());
            ps.setDouble(6, sp.getDonGiaNhap());
            ps.setDouble(7, sp.getDonGiaBan());
            ps.setInt(8, sp.getSoLuong());
            ps.setInt(9, sp.getBaoHanh());
            ps.setString(10, sp.getMoTa());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
