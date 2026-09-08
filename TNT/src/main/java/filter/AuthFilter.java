/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package filter;

import model.NguoiDung;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        String contextPath = req.getContextPath();

        // Các URL công khai không cần đăng nhập: Trang chủ (index.jsp), Login, Register và Static Assets[cite: 1]
        boolean isPublicPath = uri.endsWith("index.jsp")
                || uri.equals(contextPath + "/")
                || uri.endsWith("login.jsp")
                || uri.endsWith("register.jsp")
                || uri.endsWith("/LoginServlet")
                || uri.endsWith("/RegisterServlet")
                || uri.contains("/assets/");

        if (isPublicPath) {
            chain.doFilter(request, response);
            return;
        }

        NguoiDung user = (session != null) ? (NguoiDung) session.getAttribute("user") : null;

        // Nếu chưa đăng nhập mà cố truy cập các trang nội bộ -> Đẩy về login.jsp[cite: 1]
        if (user == null) {
            resp.sendRedirect(contextPath + "/login.jsp");
            return;
        }

        // Phân quyền ADMIN[cite: 1]
        if (uri.contains("/admin/") && !"ADMIN".equals(user.getQuyen())) {
            resp.sendRedirect(contextPath + "/DashboardServlet?error=AccessDenied");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
