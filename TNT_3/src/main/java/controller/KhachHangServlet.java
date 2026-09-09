/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.KhachHangDAO;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.KhachHang;

import java.io.IOException;

@WebServlet("/KhachHangServlet")
public class KhachHangServlet extends HttpServlet {

    @Inject
    private KhachHangDAO khachHangDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int maKH = Integer.parseInt(request.getParameter("id"));
            boolean ok = khachHangDAO.deleteKhachHang(maKH);
            if (ok) {
                response.sendRedirect("KhachHangServlet");
            } else {
                // Khách hàng đã có Phiếu Xuất liên quan (ràng buộc khóa ngoại)
                response.sendRedirect("KhachHangServlet?error=coliendon");
            }
            return;
        }

        request.setAttribute("listKH", khachHangDAO.getAllKhachHang());
        request.getRequestDispatcher("khachhang/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String maKHStr = request.getParameter("maKH");

        KhachHang kh = new KhachHang();
        kh.setTenKH(request.getParameter("tenKH"));
        kh.setSoDienThoai(request.getParameter("soDienThoai"));
        kh.setEmail(request.getParameter("email"));
        kh.setDiaChi(request.getParameter("diaChi"));

        if (maKHStr != null && !maKHStr.trim().isEmpty()) {
            // Cập nhật khách hàng cũ
            kh.setMaKH(Integer.parseInt(maKHStr));
            khachHangDAO.updateKhachHang(kh);
        } else {
            // Thêm mới khách hàng
            khachHangDAO.insertKhachHang(kh);
        }
        response.sendRedirect("KhachHangServlet");
    }
}
