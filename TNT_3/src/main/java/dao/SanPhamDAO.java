/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package dao;

import context.DBContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.SanPham;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SanPhamDAO {

    @Inject
    private DBContext dbContext;

    // 1. Lấy tất cả sản phẩm đang hoạt động
    public List<SanPham> getAllSanPham() {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT sp.*, l.TenLoai, ncc.TenNCC FROM SanPham sp "
                + "LEFT JOIN LoaiSanPham l ON sp.MaLoai = l.MaLoai "
                + "LEFT JOIN NhaCungCap ncc ON sp.MaNCC = ncc.MaNCC "
                + "WHERE sp.TrangThai = 1 ORDER BY sp.MaSP DESC";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToSanPham(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Lấy sản phẩm theo Mã ID
    public SanPham getSanPhamById(int maSP) {
        String sql = "SELECT sp.*, l.TenLoai, ncc.TenNCC FROM SanPham sp "
                + "LEFT JOIN LoaiSanPham l ON sp.MaLoai = l.MaLoai "
                + "LEFT JOIN NhaCungCap ncc ON sp.MaNCC = ncc.MaNCC "
                + "WHERE sp.MaSP = ? AND sp.TrangThai = 1";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSP);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSanPham(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. Tìm kiếm sản phẩm theo từ khóa và lọc theo Loại sản phẩm
    public List<SanPham> searchSanPham(String keyword, int maLoai) {
        List<SanPham> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT sp.*, l.TenLoai, ncc.TenNCC FROM SanPham sp "
                + "LEFT JOIN LoaiSanPham l ON sp.MaLoai = l.MaLoai "
                + "LEFT JOIN NhaCungCap ncc ON sp.MaNCC = ncc.MaNCC "
                + "WHERE sp.TrangThai = 1 "
        );

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND sp.TenSP LIKE ? ");
        }
        if (maLoai > 0) {
            sql.append("AND sp.MaLoai = ? ");
        }
        sql.append("ORDER BY sp.MaSP DESC");

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + keyword.trim() + "%");
            }
            if (maLoai > 0) {
                ps.setInt(paramIndex++, maLoai);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToSanPham(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 4. Thêm mới sản phẩm
    public boolean insertSanPham(SanPham sp) {
        String sql = "INSERT INTO SanPham(TenSP, MaLoai, MaNCC, HangSX, DonViTinh, DonGiaNhap, DonGiaBan, SoLuong, BaoHanh, MoTa, TrangThai) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,1)";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
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

    // 5. Cập nhật thông tin sản phẩm
    public boolean updateSanPham(SanPham sp) {
        String sql = "UPDATE SanPham SET TenSP=?, MaLoai=?, MaNCC=?, HangSX=?, DonViTinh=?, DonGiaNhap=?, DonGiaBan=?, SoLuong=?, BaoHanh=?, MoTa=? "
                + "WHERE MaSP=?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
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
            ps.setInt(11, sp.getMaSP());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6. Xóa sản phẩm (Chuyển TrangThai = 0)
    public boolean deleteSanPham(int maSP) {
        String sql = "UPDATE SanPham SET TrangThai = 0 WHERE MaSP = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSP);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hàm Phụ trợ Ánh xạ ResultSet thành Đối Tượng SanPham
    private SanPham mapResultSetToSanPham(ResultSet rs) throws Exception {
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
        sp.setTrangThai(rs.getBoolean("TrangThai"));
        sp.setTenLoai(rs.getString("TenLoai"));
        sp.setTenNCC(rs.getString("TenNCC"));
        return sp;
    }
}
