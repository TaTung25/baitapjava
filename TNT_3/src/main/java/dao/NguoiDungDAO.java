/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package dao;

import context.DBContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.NguoiDung;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class NguoiDungDAO {

    @Inject
    private DBContext dbContext;

    public NguoiDung checkLogin(String username, String password) {
        String sql = "SELECT * FROM NguoiDung WHERE TenDangNhap = ? AND MatKhau = ? AND TrangThai = 1";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NguoiDung> getAllNguoiDung() {
        List<NguoiDung> list = new ArrayList<>();
        String sql = "SELECT * FROM NguoiDung ORDER BY MaNguoiDung DESC";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new NguoiDung(
                        rs.getInt("MaNguoiDung"),
                        rs.getString("TenDangNhap"),
                        rs.getString("MatKhau"),
                        rs.getString("HoTen"),
                        rs.getString("Email"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Quyen"),
                        rs.getBoolean("TrangThai")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean checkUsernameExists(String username) {
        String sql = "SELECT MaNguoiDung FROM NguoiDung WHERE TenDangNhap = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertNguoiDung(NguoiDung user) {
        String sql = "INSERT INTO NguoiDung (TenDangNhap, MatKhau, HoTen, Email, SoDienThoai, Quyen, TrangThai) VALUES (?, ?, ?, ?, ?, ?, 1)";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getTenDangNhap());
            ps.setString(2, user.getMatKhau());
            ps.setString(3, user.getHoTen());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getSoDienThoai());
            ps.setString(6, user.getQuyen());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin tài khoản (Sửa + Phân quyền). Không đổi TenDangNhap.
    // doiMatKhau = true khi admin nhập mật khẩu mới trong form Sửa.
    public boolean updateNguoiDung(NguoiDung user, boolean doiMatKhau) {
        String sql = doiMatKhau
                ? "UPDATE NguoiDung SET HoTen=?, Email=?, SoDienThoai=?, Quyen=?, MatKhau=? WHERE MaNguoiDung=?"
                : "UPDATE NguoiDung SET HoTen=?, Email=?, SoDienThoai=?, Quyen=? WHERE MaNguoiDung=?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getHoTen());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getSoDienThoai());
            ps.setString(4, user.getQuyen());
            if (doiMatKhau) {
                ps.setString(5, user.getMatKhau());
                ps.setInt(6, user.getMaNguoiDung());
            } else {
                ps.setInt(5, user.getMaNguoiDung());
            }
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Kiểm tra mật khẩu hiện tại của một tài khoản (dùng cho chức năng Đổi Mật Khẩu ở trang Cá Nhân).
    public boolean checkPasswordById(int maNguoiDung, String matKhau) {
        String sql = "SELECT MaNguoiDung FROM NguoiDung WHERE MaNguoiDung = ? AND MatKhau = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNguoiDung);
            ps.setString(2, matKhau);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Đổi mật khẩu mới cho chính tài khoản đang đăng nhập (trang Cá Nhân).
    public boolean doiMatKhau(int maNguoiDung, String matKhauMoi) {
        String sql = "UPDATE NguoiDung SET MatKhau = ? WHERE MaNguoiDung = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, matKhauMoi);
            ps.setInt(2, maNguoiDung);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin cá nhân (Họ Tên, Email, SĐT) do chính người dùng tự sửa.
    // Không cho tự đổi TenDangNhap / Quyen từ trang Cá Nhân.
    public boolean updateThongTinCaNhan(int maNguoiDung, String hoTen, String email, String soDienThoai) {
        String sql = "UPDATE NguoiDung SET HoTen = ?, Email = ?, SoDienThoai = ? WHERE MaNguoiDung = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hoTen);
            ps.setString(2, email);
            ps.setString(3, soDienThoai);
            ps.setInt(4, maNguoiDung);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Khóa / Mở khóa tài khoản: đảo giá trị TrangThai (1 <-> 0).
    // Tài khoản bị khóa (TrangThai = 0) sẽ không đăng nhập được (xem checkLogin).
    public boolean toggleTrangThai(int maNguoiDung) {
        String sql = "UPDATE NguoiDung SET TrangThai = CASE WHEN TrangThai = 1 THEN 0 ELSE 1 END WHERE MaNguoiDung = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNguoiDung);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa hẳn tài khoản. Nếu tài khoản đã lập Phiếu Nhập/Xuất, ràng buộc khóa
    // ngoại sẽ khiến câu lệnh lỗi -> trả về false để Servlet báo nên Khóa
    // tài khoản thay vì Xóa.
    public boolean deleteNguoiDung(int maNguoiDung) {
        String sql = "DELETE FROM NguoiDung WHERE MaNguoiDung = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNguoiDung);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
