/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BaoCaoDAO;
import dao.LoaiSanPhamDAO;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DoiTacVip;
import model.ThongKeKy;
import model.TonKhoItem;
import model.TopBanChay;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Màn hình Thống Kê tổng hợp.
 *
 * Tham số nhận từ form lọc (tất cả đều không bắt buộc):
 * - ky           : ngay | tuan | thang | quy | nam   (mặc định: thang)
 * - tuNgay       : yyyy-MM-dd
 * - denNgay      : yyyy-MM-dd
 * - topN         : số dòng của bảng xếp hạng VIP / bán chạy
 * - tonToiThieu  : ngưỡng lọc hàng tồn kho còn nhiều
 * - tuKhoaTon    : tìm theo tên sản phẩm / hãng trong bảng tồn kho
 * - maLoaiTon    : lọc theo loại sản phẩm trong bảng tồn kho
 */
@WebServlet("/BaoCaoServlet")
public class BaoCaoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    private BaoCaoDAO baoCaoDAO;

    @Inject
    private LoaiSanPhamDAO loaiSanPhamDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // ---------- 1. Đọc và chuẩn hóa tham số lọc ----------
        String ky = baoCaoDAO.chuanHoaKy(request.getParameter("ky"));

        LocalDate homNay = LocalDate.now();
        LocalDate macDinhTu = ngayBatDauMacDinh(ky, homNay);

        LocalDate tuNgay = docNgay(request.getParameter("tuNgay"), macDinhTu);
        LocalDate denNgay = docNgay(request.getParameter("denNgay"), homNay);
        if (tuNgay.isAfter(denNgay)) {
            LocalDate tam = tuNgay;
            tuNgay = denNgay;
            denNgay = tam;
        }

        int topN = docSo(request.getParameter("topN"), 5);
        int tonToiThieu = docSo(request.getParameter("tonToiThieu"), 10);
        int maLoaiTon = docSo(request.getParameter("maLoaiTon"), 0);
        String tuKhoaTon = request.getParameter("tuKhoaTon");

        Date sqlTu = Date.valueOf(tuNgay);
        Date sqlDen = Date.valueOf(denNgay);

        // ---------- 2. Lấy dữ liệu từ database ----------
        List<ThongKeKy> thongKeKy = baoCaoDAO.getThongKeTheoKy(ky, sqlTu, sqlDen);
        List<TopBanChay> topLoai = baoCaoDAO.getTopLoaiBanChay(sqlTu, sqlDen, 8);
        List<TopBanChay> topSanPham = baoCaoDAO.getTopSanPhamBanChay(sqlTu, sqlDen, topN);
        List<DoiTacVip> topNCC = baoCaoDAO.getTopNhaCungCap(sqlTu, sqlDen, topN);
        List<DoiTacVip> topKH = baoCaoDAO.getTopKhachHang(sqlTu, sqlDen, topN);
        List<TonKhoItem> tonNhieu = baoCaoDAO.getTonKhoConNhieu(tonToiThieu, tuKhoaTon, maLoaiTon, 50);
        Map<String, Double> tongQuanKho = baoCaoDAO.getTongQuanTonKho();

        // ---------- 3. Cộng dồn số liệu KPI của toàn khoảng thời gian ----------
        double tongChi = 0d;
        double tongThu = 0d;
        int soPhieuNhap = 0;
        int soPhieuXuat = 0;
        for (ThongKeKy dong : thongKeKy) {
            tongChi += dong.getTongNhap();
            tongThu += dong.getTongXuat();
            soPhieuNhap += dong.getSoPhieuNhap();
            soPhieuXuat += dong.getSoPhieuXuat();
        }

        // Tăng trưởng của kỳ cuối cùng so với kỳ liền trước
        double tangTruongCuoi = 0d;
        boolean coSoSanh = false;
        if (thongKeKy.size() >= 2) {
            ThongKeKy cuoi = thongKeKy.get(thongKeKy.size() - 1);
            tangTruongCuoi = cuoi.getTangTruong();
            coSoSanh = cuoi.isCoKyTruoc();
        }

        // ---------- 4. Chuẩn bị dữ liệu JSON cho Chart.js ----------
        List<String> nhanKy = new ArrayList<>();
        List<Double> duLieuNhap = new ArrayList<>();
        List<Double> duLieuXuat = new ArrayList<>();
        List<Double> duLieuLoiNhuan = new ArrayList<>();
        for (ThongKeKy dong : thongKeKy) {
            nhanKy.add(dong.getNhan());
            duLieuNhap.add(dong.getTongNhap());
            duLieuXuat.add(dong.getTongXuat());
            duLieuLoiNhuan.add(dong.getLoiNhuan());
        }

        List<String> nhanLoai = new ArrayList<>();
        List<Double> soLuongLoai = new ArrayList<>();
        for (TopBanChay dong : topLoai) {
            nhanLoai.add(dong.getTen());
            soLuongLoai.add((double) dong.getSoLuongBan());
        }

        List<String> nhanSanPham = new ArrayList<>();
        List<Double> soLuongSanPham = new ArrayList<>();
        for (TopBanChay dong : topSanPham) {
            nhanSanPham.add(dong.getTen());
            soLuongSanPham.add((double) dong.getSoLuongBan());
        }

        request.setAttribute("jsonNhanKy", jsonChuoi(nhanKy));
        request.setAttribute("jsonNhap", jsonSo(duLieuNhap));
        request.setAttribute("jsonXuat", jsonSo(duLieuXuat));
        request.setAttribute("jsonLoiNhuan", jsonSo(duLieuLoiNhuan));
        request.setAttribute("jsonNhanLoai", jsonChuoi(nhanLoai));
        request.setAttribute("jsonSoLuongLoai", jsonSo(soLuongLoai));
        request.setAttribute("jsonNhanSanPham", jsonChuoi(nhanSanPham));
        request.setAttribute("jsonSoLuongSanPham", jsonSo(soLuongSanPham));

        // ---------- 5. Đẩy sang JSP ----------
        request.setAttribute("thongKeKy", thongKeKy);
        request.setAttribute("topLoai", topLoai);
        request.setAttribute("topSanPham", topSanPham);
        request.setAttribute("topNCC", topNCC);
        request.setAttribute("topKH", topKH);
        request.setAttribute("tonNhieu", tonNhieu);
        request.setAttribute("tongQuanKho", tongQuanKho);
        request.setAttribute("listLoai", loaiSanPhamDAO.getAllLoai());

        request.setAttribute("tongChi", tongChi);
        request.setAttribute("tongThu", tongThu);
        request.setAttribute("loiNhuan", tongThu - tongChi);
        request.setAttribute("soPhieuNhap", soPhieuNhap);
        request.setAttribute("soPhieuXuat", soPhieuXuat);
        request.setAttribute("tangTruongCuoi", tangTruongCuoi);
        request.setAttribute("coSoSanh", coSoSanh);

        // Giữ lại giá trị đã chọn để form lọc hiển thị đúng
        request.setAttribute("ky", ky);
        request.setAttribute("tenKy", tenKy(ky));
        request.setAttribute("tuNgay", tuNgay.toString());
        request.setAttribute("denNgay", denNgay.toString());
        request.setAttribute("tuNgayHienThi", dinhDangNgay(tuNgay));
        request.setAttribute("denNgayHienThi", dinhDangNgay(denNgay));
        request.setAttribute("topN", topN);
        request.setAttribute("tonToiThieu", tonToiThieu);
        request.setAttribute("tuKhoaTon", tuKhoaTon == null ? "" : tuKhoaTon);
        request.setAttribute("maLoaiTon", maLoaiTon);

        request.getRequestDispatcher("baocao/index.jsp").forward(request, response);
    }

    // =====================================================================
    // Hàm phụ trợ
    // =====================================================================
    /** Khoảng thời gian mặc định tương ứng từng kỳ thống kê. */
    private LocalDate ngayBatDauMacDinh(String ky, LocalDate homNay) {
        switch (ky) {
            case BaoCaoDAO.KY_NGAY:
                return homNay.minusDays(29);
            case BaoCaoDAO.KY_TUAN:
                return homNay.minusWeeks(11);
            case BaoCaoDAO.KY_QUY:
                return homNay.minusMonths(21).withDayOfMonth(1);
            case BaoCaoDAO.KY_NAM:
                return homNay.minusYears(4).withDayOfYear(1);
            case BaoCaoDAO.KY_THANG:
            default:
                return homNay.minusMonths(11).withDayOfMonth(1);
        }
    }

    private String tenKy(String ky) {
        switch (ky) {
            case BaoCaoDAO.KY_NGAY:
                return "Ngày";
            case BaoCaoDAO.KY_TUAN:
                return "Tuần";
            case BaoCaoDAO.KY_QUY:
                return "Quý";
            case BaoCaoDAO.KY_NAM:
                return "Năm";
            case BaoCaoDAO.KY_THANG:
            default:
                return "Tháng";
        }
    }

    /** Đổi sang dạng dd/MM/yyyy để hiển thị cho người dùng. */
    private String dinhDangNgay(LocalDate ngay) {
        return String.format("%02d/%02d/%d",
                ngay.getDayOfMonth(), ngay.getMonthValue(), ngay.getYear());
    }

    private LocalDate docNgay(String giaTri, LocalDate macDinh) {
        if (giaTri == null || giaTri.trim().isEmpty()) {
            return macDinh;
        }
        try {
            return LocalDate.parse(giaTri.trim());
        } catch (DateTimeParseException e) {
            return macDinh;
        }
    }

    private int docSo(String giaTri, int macDinh) {
        if (giaTri == null || giaTri.trim().isEmpty()) {
            return macDinh;
        }
        try {
            return Integer.parseInt(giaTri.trim());
        } catch (NumberFormatException e) {
            return macDinh;
        }
    }

    /**
     * Ghép danh sách chuỗi thành mảng JSON an toàn để nhúng thẳng vào thẻ
     * script. Có thoát ký tự đặc biệt và dấu nhỏ hơn để tránh vỡ trang.
     */
    private String jsonChuoi(List<String> danhSach) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < danhSach.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append('"').append(thoatChuoi(danhSach.get(i))).append('"');
        }
        return sb.append(']').toString();
    }

    private String jsonSo(List<Double> danhSach) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < danhSach.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            Double giaTri = danhSach.get(i);
            double so = (giaTri == null || giaTri.isNaN() || giaTri.isInfinite()) ? 0d : giaTri;
            sb.append(java.math.BigDecimal.valueOf(so).toPlainString());
        }
        return sb.append(']').toString();
    }

    private String thoatChuoi(String chuoi) {
        if (chuoi == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chuoi.length(); i++) {
            char c = chuoi.charAt(i);
            switch (c) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '<':
                    sb.append("\\u003C");
                    break;
                case '>':
                    sb.append("\\u003E");
                    break;
                case '&':
                    sb.append("\\u0026");
                    break;
                default:
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04X", (int) c));
                    } else {
                        sb.append(c);
                    }
                    break;
            }
        }
        return sb.toString();
    }
}
