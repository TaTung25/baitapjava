/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package dao;

import context.DBContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.KhachHang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class KhachHangDAO {

    @Inject
    private DBContext dbContext;

    public List<KhachHang> getAllKhachHang() {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new KhachHang(
                        rs.getInt("MaKH"),
                        rs.getString("TenKH"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("DiaChi")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Tìm kiếm khách hàng theo tên, số điện thoại, email hoặc địa chỉ.
     *
     * @param tuKhoa từ khóa tìm kiếm, rỗng/null thì trả về toàn bộ danh sách
     * @param sapXep tenaz | tenza | moinhat | cunhat
     */
    public List<KhachHang> searchKhachHang(String tuKhoa, String sapXep) {
        List<KhachHang> list = new ArrayList<>();
        boolean coTuKhoa = tuKhoa != null && !tuKhoa.trim().isEmpty();

        StringBuilder sql = new StringBuilder("SELECT * FROM KhachHang WHERE 1 = 1 ");
        if (coTuKhoa) {
            sql.append("AND (TenKH LIKE ? OR SoDienThoai LIKE ? OR Email LIKE ? OR DiaChi LIKE ?) ");
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
                    list.add(new KhachHang(
                            rs.getInt("MaKH"),
                            rs.getString("TenKH"),
                            rs.getString("SoDienThoai"),
                            rs.getString("Email"),
                            rs.getString("DiaChi")
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
            return "ORDER BY MaKH DESC";
        }
        switch (sapXep.trim().toLowerCase()) {
            case "tenaz":
                return "ORDER BY TenKH ASC";
            case "tenza":
                return "ORDER BY TenKH DESC";
            case "cunhat":
                return "ORDER BY MaKH ASC";
            case "moinhat":
            default:
                return "ORDER BY MaKH DESC";
        }
    }

    public boolean insertKhachHang(KhachHang kh) {
        String sql = "INSERT INTO KhachHang(TenKH, SoDienThoai, Email, DiaChi) VALUES(?, ?, ?, ?)";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getSoDienThoai());
            ps.setString(3, kh.getEmail());
            ps.setString(4, kh.getDiaChi());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin khách hàng
    public boolean updateKhachHang(KhachHang kh) {
        String sql = "UPDATE KhachHang SET TenKH=?, SoDienThoai=?, Email=?, DiaChi=? WHERE MaKH=?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getSoDienThoai());
            ps.setString(3, kh.getEmail());
            ps.setString(4, kh.getDiaChi());
            ps.setInt(5, kh.getMaKH());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa khách hàng. Bảng KhachHang không có cột TrangThai nên đây là xóa
    // thật. Nếu khách hàng đã có Phiếu Xuất liên quan, ràng buộc khóa ngoại
    // sẽ khiến câu lệnh lỗi -> bắt Exception và trả về false để Servlet báo
    // lỗi thân thiện thay vì làm sập trang.
    public boolean deleteKhachHang(int maKH) {
        String sql = "DELETE FROM KhachHang WHERE MaKH = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maKH);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
