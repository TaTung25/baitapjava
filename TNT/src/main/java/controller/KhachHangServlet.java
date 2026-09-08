/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.KhachHangDAO;
import model.KhachHang;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/KhachHangServlet")
public class KhachHangServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        KhachHangDAO dao = new KhachHangDAO();
        request.setAttribute("listKH", dao.getAllKhachHang());
        request.getRequestDispatcher("khachhang/list.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        KhachHang kh = new KhachHang();
        kh.setTenKH(request.getParameter("tenKH"));
        kh.setSoDienThoai(request.getParameter("soDienThoai"));
        kh.setEmail(request.getParameter("email"));
        kh.setDiaChi(request.getParameter("diaChi"));

        KhachHangDAO dao = new KhachHangDAO();
        dao.insertKhachHang(kh);
        response.sendRedirect("KhachHangServlet");
    }
}
