/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.LoaiSanPhamDAO;
import model.LoaiSanPham;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoaiSanPhamServlet")
public class LoaiSanPhamServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LoaiSanPhamDAO dao = new LoaiSanPhamDAO();
        request.setAttribute("listLoai", dao.getAllLoai());
        request.getRequestDispatcher("loaisanpham/list.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LoaiSanPham l = new LoaiSanPham();
        l.setTenLoai(request.getParameter("tenLoai"));
        l.setMoTa(request.getParameter("moTa"));

        LoaiSanPhamDAO dao = new LoaiSanPhamDAO();
        dao.insertLoai(l);
        response.sendRedirect("LoaiSanPhamServlet");
    }
}
