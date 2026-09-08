/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.SanPhamDAO;
import model.SanPham;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/SanPhamServlet")
public class SanPhamServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SanPhamDAO dao = new SanPhamDAO();
        List<SanPham> list = dao.getAllSanPham();
        request.setAttribute("listSP", list);
        request.getRequestDispatcher("sanpham/list.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        SanPham sp = new SanPham();
        sp.setTenSP(request.getParameter("tenSP"));
        sp.setMaLoai(Integer.parseInt(request.getParameter("maLoai")));
        sp.setMaNCC(Integer.parseInt(request.getParameter("maNCC")));
        sp.setHangSX(request.getParameter("hangSX"));
        sp.setDonViTinh(request.getParameter("donViTinh"));
        sp.setDonGiaNhap(Double.parseDouble(request.getParameter("donGiaNhap")));
        sp.setDonGiaBan(Double.parseDouble(request.getParameter("donGiaBan")));
        sp.setSoLuong(Integer.parseInt(request.getParameter("soLuong")));
        sp.setBaoHanh(Integer.parseInt(request.getParameter("baoHanh")));
        sp.setMoTa(request.getParameter("moTa"));

        SanPhamDAO dao = new SanPhamDAO();
        dao.insertSanPham(sp);

        response.sendRedirect("SanPhamServlet");
    }
}
