/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package dao;

import context.DBContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.NhaCungCap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class NhaCungCapDAO {

    @Inject
    private DBContext dbContext;

    public List<NhaCungCap> getAllNCC() {
        List<NhaCungCap> list = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap WHERE TrangThai = 1";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new NhaCungCap(
                        rs.getInt("MaNCC"),
                        rs.getString("TenNCC"),
                        rs.getString("DiaChi"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getBoolean("TrangThai")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Tìm kiếm nhà cung cấp theo tên, số điện thoại, email hoặc địa chỉ.
     *
     * @param tuKhoa từ khóa tìm kiếm, rỗng/null thì trả về toàn bộ danh sách
     * @param sapXep tenaz | tenza | moinhat | cunhat
     */
    public List<NhaCungCap> searchNCC(String tuKhoa, String sapXep) {
        List<NhaCungCap> list = new ArrayList<>();
        boolean coTuKhoa = tuKhoa != null && !tuKhoa.trim().isEmpty();

        StringBuilder sql = new StringBuilder(
                "SELECT * FROM NhaCungCap WHERE TrangThai = 1 ");
        if (coTuKhoa) {
            sql.append("AND (TenNCC LIKE ? OR SoDienThoai LIKE ? OR Email LIKE ? OR DiaChi LIKE ?) ");
        }
        sql.append(menhDeSapXep(sapXep));

        try (Connection conn = dbContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            if (coTuKhoa) {
                String mau = "%" + tuKhoa.trim() + "%";
                for (int i = 1; i <= 4; i++) {
                    ps.setString(i, mau);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new NhaCungCap(
                            rs.getInt("MaNCC"),
                            rs.getString("TenNCC"),
                            rs.getString("DiaChi"),
                            rs.getString("SoDienThoai"),
                            rs.getString("Email"),
                            rs.getBoolean("TrangThai")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /** Mệnh đề sắp xếp, chỉ nhận giá trị trong danh sách cho phép. */
    private String menhDeSapXep(String sapXep) {
        if (sapXep == null) {
            return "ORDER BY MaNCC DESC";
        }
        switch (sapXep.trim().toLowerCase()) {
            case "tenaz":
                return "ORDER BY TenNCC ASC";
            case "tenza":
                return "ORDER BY TenNCC DESC";
            case "cunhat":
                return "ORDER BY MaNCC ASC";
            case "moinhat":
            default:
                return "ORDER BY MaNCC DESC";
        }
    }

    public boolean insertNCC(NhaCungCap ncc) {
        String sql = "INSERT INTO NhaCungCap(TenNCC, DiaChi, SoDienThoai, Email, TrangThai) VALUES(?, ?, ?, ?, 1)";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ncc.getTenNCC());
            ps.setString(2, ncc.getDiaChi());
            ps.setString(3, ncc.getSoDienThoai());
            ps.setString(4, ncc.getEmail());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin nhà cung cấp
    public boolean updateNCC(NhaCungCap ncc) {
        String sql = "UPDATE NhaCungCap SET TenNCC=?, DiaChi=?, SoDienThoai=?, Email=? WHERE MaNCC=?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ncc.getTenNCC());
            ps.setString(2, ncc.getDiaChi());
            ps.setString(3, ncc.getSoDienThoai());
            ps.setString(4, ncc.getEmail());
            ps.setInt(5, ncc.getMaNCC());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa mềm: NhaCungCap đang được SanPham và PhieuNhap tham chiếu qua khóa
    // ngoại nên chỉ chuyển TrangThai = 0, không xóa cứng dòng dữ liệu.
    public boolean deleteNCC(int maNCC) {
        String sql = "UPDATE NhaCungCap SET TrangThai = 0 WHERE MaNCC = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNCC);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
