/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.NguoiDungDAO;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.NguoiDung;

import java.io.IOException;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {

    @Inject
    private NguoiDungDAO nguoiDungDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("canhan/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        NguoiDung currentUser = (session != null) ? (NguoiDung) session.getAttribute("user") : null;

        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        if ("changePassword".equals(action)) {
            xuLyDoiMatKhau(request, currentUser, session);
        } else {
            xuLyCapNhatThongTin(request, currentUser, session);
        }

        request.getRequestDispatcher("canhan/profile.jsp").forward(request, response);
    }

    // Cập nhật Họ Tên / Email / SĐT của chính tài khoản đang đăng nhập.
    private void xuLyCapNhatThongTin(HttpServletRequest request, NguoiDung currentUser, HttpSession session) {
        String hoTen = request.getParameter("hoTen");
        String email = request.getParameter("email");
        String soDienThoai = request.getParameter("soDienThoai");

        if (hoTen == null || hoTen.trim().isEmpty()) {
            request.setAttribute("error", "Họ và Tên không được để trống!");
            return;
        }

        boolean ok = nguoiDungDAO.updateThongTinCaNhan(currentUser.getMaNguoiDung(), hoTen.trim(), email, soDienThoai);
        if (ok) {
            currentUser.setHoTen(hoTen.trim());
            currentUser.setEmail(email);
            currentUser.setSoDienThoai(soDienThoai);
            session.setAttribute("user", currentUser);
            request.setAttribute("success", "Cập nhật thông tin tài khoản thành công!");
        } else {
            request.setAttribute("error", "Cập nhật thông tin thất bại, vui lòng thử lại!");
        }
    }

    // Đổi mật khẩu cho chính tài khoản đang đăng nhập.
    private void xuLyDoiMatKhau(HttpServletRequest request, NguoiDung currentUser, HttpSession session) {
        String matKhauCu = request.getParameter("matKhauCu");
        String matKhauMoi = request.getParameter("matKhauMoi");
        String matKhauXacNhan = request.getParameter("matKhauXacNhan");

        if (matKhauCu == null || matKhauCu.isEmpty()
                || matKhauMoi == null || matKhauMoi.isEmpty()
                || matKhauXacNhan == null || matKhauXacNhan.isEmpty()) {
            request.setAttribute("errorPass", "Vui lòng nhập đầy đủ các trường mật khẩu!");
            return;
        }

        if (!nguoiDungDAO.checkPasswordById(currentUser.getMaNguoiDung(), matKhauCu)) {
            request.setAttribute("errorPass", "Mật khẩu hiện tại không đúng!");
            return;
        }

        if (matKhauMoi.length() < 6) {
            request.setAttribute("errorPass", "Mật khẩu mới phải có ít nhất 6 ký tự!");
            return;
        }

        if (!matKhauMoi.equals(matKhauXacNhan)) {
            request.setAttribute("errorPass", "Xác nhận mật khẩu mới không khớp!");
            return;
        }

        if (matKhauMoi.equals(matKhauCu)) {
            request.setAttribute("errorPass", "Mật khẩu mới phải khác mật khẩu hiện tại!");
            return;
        }

        boolean ok = nguoiDungDAO.doiMatKhau(currentUser.getMaNguoiDung(), matKhauMoi);
        if (ok) {
            currentUser.setMatKhau(matKhauMoi);
            session.setAttribute("user", currentUser);
            request.setAttribute("successPass", "Đổi mật khẩu thành công!");
        } else {
            request.setAttribute("errorPass", "Đổi mật khẩu thất bại, vui lòng thử lại!");
        }
    }
}
