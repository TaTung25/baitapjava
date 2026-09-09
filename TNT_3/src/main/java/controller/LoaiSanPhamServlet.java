/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.LoaiSanPhamDAO;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.LoaiSanPham;

import java.io.IOException;

@WebServlet("/LoaiSanPhamServlet")
public class LoaiSanPhamServlet extends HttpServlet {

    @Inject
    private LoaiSanPhamDAO loaiSanPhamDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int maLoai = Integer.parseInt(request.getParameter("id"));
            loaiSanPhamDAO.deleteLoai(maLoai);
            response.sendRedirect("LoaiSanPhamServlet");
            return;
        }

        request.setAttribute("listLoai", loaiSanPhamDAO.getAllLoai());
        request.getRequestDispatcher("loaisanpham/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String maLoaiStr = request.getParameter("maLoai");

        LoaiSanPham l = new LoaiSanPham();
        l.setTenLoai(request.getParameter("tenLoai"));
        l.setMoTa(request.getParameter("moTa"));

        if (maLoaiStr != null && !maLoaiStr.trim().isEmpty()) {
            // Cập nhật loại sản phẩm cũ
            l.setMaLoai(Integer.parseInt(maLoaiStr));
            loaiSanPhamDAO.updateLoai(l);
        } else {
            // Thêm mới loại sản phẩm
            loaiSanPhamDAO.insertLoai(l);
        }
        response.sendRedirect("LoaiSanPhamServlet");
    }
}
