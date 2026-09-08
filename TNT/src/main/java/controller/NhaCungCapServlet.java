/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.NhaCungCapDAO;
import model.NhaCungCap;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/NhaCungCapServlet")
public class NhaCungCapServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NhaCungCapDAO dao = new NhaCungCapDAO();
        request.setAttribute("listNCC", dao.getAllNCC());
        request.getRequestDispatcher("nhacungcap/list.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        NhaCungCap ncc = new NhaCungCap();
        ncc.setTenNCC(request.getParameter("tenNCC"));
        ncc.setSoDienThoai(request.getParameter("soDienThoai"));
        ncc.setEmail(request.getParameter("email"));
        ncc.setDiaChi(request.getParameter("diaChi"));

        NhaCungCapDAO dao = new NhaCungCapDAO();
        dao.insertNCC(ncc);
        response.sendRedirect("NhaCungCapServlet");
    }
}
