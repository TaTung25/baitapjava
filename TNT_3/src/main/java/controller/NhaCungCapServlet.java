/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.NhaCungCapDAO;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.NhaCungCap;

import java.io.IOException;

@WebServlet("/NhaCungCapServlet")
public class NhaCungCapServlet extends HttpServlet {

    @Inject
    private NhaCungCapDAO nhaCungCapDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int maNCC = Integer.parseInt(request.getParameter("id"));
            nhaCungCapDAO.deleteNCC(maNCC);
            response.sendRedirect("NhaCungCapServlet");
            return;
        }

        request.setAttribute("listNCC", nhaCungCapDAO.getAllNCC());
        request.getRequestDispatcher("nhacungcap/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String maNCCStr = request.getParameter("maNCC");

        NhaCungCap ncc = new NhaCungCap();
        ncc.setTenNCC(request.getParameter("tenNCC"));
        ncc.setSoDienThoai(request.getParameter("soDienThoai"));
        ncc.setEmail(request.getParameter("email"));
        ncc.setDiaChi(request.getParameter("diaChi"));

        if (maNCCStr != null && !maNCCStr.trim().isEmpty()) {
            // Cập nhật nhà cung cấp cũ
            ncc.setMaNCC(Integer.parseInt(maNCCStr));
            nhaCungCapDAO.updateNCC(ncc);
        } else {
            // Thêm mới nhà cung cấp
            nhaCungCapDAO.insertNCC(ncc);
        }
        response.sendRedirect("NhaCungCapServlet");
    }
}
