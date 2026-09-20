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

    // 3b. Lọc + tìm kiếm nâng cao sản phẩm
    /**
     * Tìm kiếm và lọc sản phẩm theo nhiều tiêu chí cùng lúc.
     *
     * @param tuKhoa    tìm trong tên sản phẩm, hãng sản xuất và mô tả (có thể null)
     * @param maLoai    lọc theo loại sản phẩm, 0 = tất cả
     * @param maNCC     lọc theo nhà cung cấp, 0 = tất cả
     * @param tinhTrang het | saphet | conhang | connhieu | rỗng = tất cả
     * @param sapXep    moinhat | tenaz | tenza | giatang | giagiam | tonnhieu | tonit
     */
    public List<SanPham> locSanPham(String tuKhoa, int maLoai, int maNCC,
            String tinhTrang, String sapXep) {

        List<SanPham> list = new ArrayList<>();
        boolean coTuKhoa = tuKhoa != null && !tuKhoa.trim().isEmpty();

        StringBuilder sql = new StringBuilder(
                "SELECT sp.*, l.TenLoai, ncc.TenNCC FROM SanPham sp "
                + "LEFT JOIN LoaiSanPham l ON sp.MaLoai = l.MaLoai "
                + "LEFT JOIN NhaCungCap ncc ON sp.MaNCC = ncc.MaNCC "
                + "WHERE sp.TrangThai = 1 "
        );

        if (coTuKhoa) {
            sql.append("AND (sp.TenSP LIKE ? OR sp.HangSX LIKE ? OR sp.MoTa LIKE ?) ");
        }
        if (maLoai > 0) {
            sql.append("AND sp.MaLoai = ? ");
        }
        if (maNCC > 0) {
            sql.append("AND sp.MaNCC = ? ");
        }
        sql.append(dieuKienTinhTrang(tinhTrang));
        sql.append(menhDeSapXep(sapXep));

        try (Connection conn = dbContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int viTri = 1;
            if (coTuKhoa) {
                String mau = "%" + tuKhoa.trim() + "%";
                ps.setString(viTri++, mau);
                ps.setString(viTri++, mau);
                ps.setString(viTri++, mau);
            }
            if (maLoai > 0) {
                ps.setInt(viTri++, maLoai);
            }
            if (maNCC > 0) {
                ps.setInt(viTri++, maNCC);
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

    /** Điều kiện lọc theo tình trạng tồn kho. Chỉ nhận giá trị trong danh sách cho phép. */
    private String dieuKienTinhTrang(String tinhTrang) {
        if (tinhTrang == null) {
            return "";
        }
        switch (tinhTrang.trim().toLowerCase()) {
            case "het":
                return "AND sp.SoLuong = 0 ";
            case "saphet":
                return "AND sp.SoLuong > 0 AND sp.SoLuong <= 5 ";
            case "conhang":
                return "AND sp.SoLuong > 5 ";
            case "connhieu":
                return "AND sp.SoLuong >= 20 ";
            default:
                return "";
        }
    }

    /** Mệnh đề sắp xếp. Chỉ nhận giá trị trong danh sách cho phép để tránh chèn SQL. */
    private String menhDeSapXep(String sapXep) {
        if (sapXep == null) {
            return "ORDER BY sp.MaSP DESC";
        }
        switch (sapXep.trim().toLowerCase()) {
            case "tenaz":
                return "ORDER BY sp.TenSP ASC";
            case "tenza":
                return "ORDER BY sp.TenSP DESC";
            case "giatang":
                return "ORDER BY sp.DonGiaBan ASC";
            case "giagiam":
                return "ORDER BY sp.DonGiaBan DESC";
            case "tonnhieu":
                return "ORDER BY sp.SoLuong DESC";
            case "tonit":
                return "ORDER BY sp.SoLuong ASC";
            case "moinhat":
            default:
                return "ORDER BY sp.MaSP DESC";
        }
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
