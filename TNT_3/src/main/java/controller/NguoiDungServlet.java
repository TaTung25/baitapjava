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

@WebServlet("/NguoiDungServlet")
public class NguoiDungServlet extends HttpServlet {

    @Inject
    private NguoiDungDAO nguoiDungDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action) || "lock".equals(action)) {
            int maNguoiDung = Integer.parseInt(request.getParameter("id"));
            HttpSession session = request.getSession(false);
            NguoiDung currentUser = (session != null) ? (NguoiDung) session.getAttribute("user") : null;

            // Không cho tự khóa hoặc tự xóa chính tài khoản đang đăng nhập
            if (currentUser != null && currentUser.getMaNguoiDung() == maNguoiDung) {
                response.sendRedirect("NguoiDungServlet?error=selfaction");
                return;
            }

            if ("delete".equals(action)) {
                boolean ok = nguoiDungDAO.deleteNguoiDung(maNguoiDung);
                if (!ok) {
                    // Tài khoản đã lập Phiếu Nhập/Xuất (ràng buộc khóa ngoại)
                    response.sendRedirect("NguoiDungServlet?error=coliendon");
                    return;
                }
            } else {
                nguoiDungDAO.toggleTrangThai(maNguoiDung);
            }
            response.sendRedirect("NguoiDungServlet");
            return;
        }

        request.setAttribute("listUser", nguoiDungDAO.getAllNguoiDung());
        request.getRequestDispatcher("admin/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String maNguoiDungStr = request.getParameter("maNguoiDung");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String hoTen = request.getParameter("hoTen");
        String email = request.getParameter("email");
        String soDienThoai = request.getParameter("soDienThoai");
        String quyen = request.getParameter("quyen");

        boolean isUpdate = maNguoiDungStr != null && !maNguoiDungStr.trim().isEmpty();

        if (!isUpdate && nguoiDungDAO.checkUsernameExists(username)) {
            request.setAttribute("error", "Tên đăng nhập đã tồn tại!");
            request.setAttribute("listUser", nguoiDungDAO.getAllNguoiDung());
            request.getRequestDispatcher("admin/list.jsp").forward(request, response);
            return;
        }

        if (isUpdate) {
            // Cập nhật tài khoản (Sửa + Phân quyền). Không đổi Tên Đăng Nhập.
            NguoiDung user = new NguoiDung();
            user.setMaNguoiDung(Integer.parseInt(maNguoiDungStr));
            user.setHoTen(hoTen);
            user.setEmail(email);
            user.setSoDienThoai(soDienThoai);
            user.setQuyen(quyen);

            boolean doiMatKhau = password != null && !password.trim().isEmpty();
            if (doiMatKhau) {
                user.setMatKhau(password);
            }
            nguoiDungDAO.updateNguoiDung(user, doiMatKhau);
        } else {
            // Thêm mới tài khoản
            NguoiDung user = new NguoiDung();
            user.setTenDangNhap(username);
            user.setMatKhau(password);
            user.setHoTen(hoTen);
            user.setEmail(email);
            user.setSoDienThoai(soDienThoai);
            user.setQuyen(quyen);
            nguoiDungDAO.insertNguoiDung(user);
        }

        response.sendRedirect("NguoiDungServlet");
    }
}
