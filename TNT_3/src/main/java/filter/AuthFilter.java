/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.NguoiDung;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String uri = req.getRequestURI();
        String context = req.getContextPath();

        boolean isPublic = uri.endsWith("index.jsp") || uri.equals(context + "/")
                || uri.endsWith("login.jsp") || uri.endsWith("RegisterServlet")
                || uri.endsWith("LoginServlet") || uri.contains("/assets/");

        if (isPublic) {
            chain.doFilter(request, response);
            return;
        }

        NguoiDung user = (session != null) ? (NguoiDung) session.getAttribute("user") : null;

        if (user == null) {
            resp.sendRedirect(context + "/login.jsp");
            return;
        }

        String quyen = user.getQuyen();

        // Phân quyền ADMIN: chặn cả JSP nằm trong /admin/ lẫn NguoiDungServlet
        // (trang này forward tới admin/list.jsp nhưng URI request lại là
        // /NguoiDungServlet nên phải kiểm tra riêng, không thể chỉ dựa vào "/admin/")
        boolean isAdminOnly = uri.contains("/admin/") || uri.endsWith("/NguoiDungServlet");
        if (isAdminOnly && !"ADMIN".equals(quyen)) {
            resp.sendRedirect(context + "/DashboardServlet?error=AccessDenied");
            return;
        }

        // Phân quyền Danh mục (Loại SP, Khách hàng) & Báo cáo/Thống kê:
        // chỉ ADMIN và QUANLY được truy cập; NHANVIENKHO không có quyền
        // (đúng theo ma trận: "Không quản lý danh mục" / "Không có quyền xem" báo cáo).
        boolean isQuanLyOnly = uri.endsWith("/LoaiSanPhamServlet")
                || uri.endsWith("/KhachHangServlet")
                || uri.endsWith("/BaoCaoServlet");
        if (isQuanLyOnly && "NHANVIENKHO".equals(quyen)) {
            resp.sendRedirect(context + "/DashboardServlet?error=AccessDenied");
            return;
        }

        // Phân quyền Sản phẩm / Nhà cung cấp: NHANVIENKHO chỉ được XEM
        // (list, search), không được thêm/sửa/xóa. Thêm/Sửa luôn đi bằng
        // POST; Xóa đi bằng GET nhưng có action=delete, nên phải kiểm tra
        // cả hai trường hợp thay vì chỉ chặn theo method.
        boolean isSanPhamOrNCC = uri.endsWith("/SanPhamServlet") || uri.endsWith("/NhaCungCapServlet");
        if (isSanPhamOrNCC && "NHANVIENKHO".equals(quyen)) {
            boolean isWriteAction = "POST".equalsIgnoreCase(req.getMethod())
                    || "delete".equals(req.getParameter("action"));
            if (isWriteAction) {
                resp.sendRedirect(context + "/DashboardServlet?error=AccessDenied");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
